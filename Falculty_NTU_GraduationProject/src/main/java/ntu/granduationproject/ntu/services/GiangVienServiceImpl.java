package ntu.granduationproject.ntu.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.repositories.GiangVienRepository;
import ntu.granduationproject.ntu.repositories.SinhVienRepository;

@Service
public class GiangVienServiceImpl implements GiangVienService {
	@Autowired
    private GiangVienRepository giangVienRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public Optional<GiangVien> loginGiangVien(String msgv, String matkhau) {
	    Optional<GiangVien> optionalGiangVien = giangVienRepository.findById(msgv);
	    if (optionalGiangVien.isPresent()) {
	        GiangVien gv = optionalGiangVien.get();

	        if (passwordEncoder.matches(matkhau, gv.getMatkhau())) {
	            return Optional.of(gv);
	        }
	    }
	    return Optional.empty();
	}

    @Override
    public void resetPassword(String msgv, String newPassword) {
        Optional<GiangVien> optionalGiangVien = giangVienRepository.findById(msgv);
        if (optionalGiangVien.isPresent()) {
            GiangVien giangVien = optionalGiangVien.get();
            // Băm mật khẩu mới trước khi lưu
            
            String hashedPassword = passwordEncoder.encode(newPassword);
            giangVien.setMatkhau(hashedPassword);
            
            giangVienRepository.save(giangVien);  
        }
    }
    
    
    @Override
    public Optional<GiangVien> forgotPasswordGiangVien(String msgv, String email) {
        return giangVienRepository.findById(msgv);
    }
    
    @Override
    public void AddGiangVien (GiangVien giangvien) {
    	giangVienRepository.save(giangvien); 
    }
}
