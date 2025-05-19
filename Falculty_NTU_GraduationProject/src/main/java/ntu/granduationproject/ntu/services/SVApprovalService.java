package ntu.granduationproject.ntu.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.granduationproject.ntu.models.DangKyDetai;
import ntu.granduationproject.ntu.repositories.DangKyDeTaiRepository;

@Service
public class SVApprovalService {
	
	@Autowired
	DangKyDeTaiRepository dangKyDeTaiRepository;
	
	public List<DangKyDetai> findByMsdt_Msdt(int msdt){
		return dangKyDeTaiRepository.findByMsdt_Msdt(msdt);
	}
	
	 public void acceptRegistration(int dkId) {
	        // Tìm đăng ký đề tài theo id
	        DangKyDetai dangKyDetai = dangKyDeTaiRepository.findById(dkId)
	            .orElseThrow(() -> new RuntimeException("Đăng ký đề tài không tồn tại: " + dkId));
	        
	        // Cập nhật trạng thái
	        dangKyDetai.setTrangthai("chấp nhận");
	        
	        // Lưu lại vào database
	        dangKyDeTaiRepository.save(dangKyDetai);
	    }

}
