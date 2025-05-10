package ntu.granduationproject.ntu.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ntu.granduationproject.ntu.repositories.SinhVienRepository;

import ntu.granduationproject.ntu.models.SinhVien;


@Service
public class SinhVienServiceImpl implements SinhVienService {
	@Autowired
    private SinhVienRepository sinhVienRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public Optional<SinhVien> loginSinhVien(String mssv, String matkhau) {
	    Optional<SinhVien> optionalSinhVien = sinhVienRepository.findById(mssv);
	    if (optionalSinhVien.isPresent()) {
	        SinhVien sv = optionalSinhVien.get();

	        // ✅ So sánh mật khẩu nhập vào với mật khẩu đã hash trong DB
	        if (passwordEncoder.matches(matkhau, sv.getMatkhau())) {
	            return Optional.of(sv);
	        }
	    }
	    return Optional.empty();
	}

    @Override
    public void resetPassword(String mssv, String newPassword) {
        Optional<SinhVien> optionalSinhVien = sinhVienRepository.findById(mssv);
        if (optionalSinhVien.isPresent()) {
            SinhVien sinhVien = optionalSinhVien.get();
            // Băm mật khẩu mới trước khi lưu
            
            String hashedPassword = passwordEncoder.encode(newPassword);
            sinhVien.setMatkhau(hashedPassword);
            
            sinhVienRepository.save(sinhVien);  // Lưu sinh viên với mật khẩu đã mã hóa
        }
    }
    
    
    @Override
    public Optional<SinhVien> forgotPasswordSinhVien(String mssv, String email) {
        return sinhVienRepository.findById(mssv);
    }
    
    
    
    
    
    
 
    

}
