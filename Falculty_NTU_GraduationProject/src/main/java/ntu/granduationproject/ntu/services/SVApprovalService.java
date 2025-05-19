package ntu.granduationproject.ntu.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.granduationproject.ntu.models.DangKyDetai;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.repositories.DangKyDeTaiRepository;

@Service
public class SVApprovalService {
	
	@Autowired
	DangKyDeTaiRepository dangKyDeTaiRepository;
	@Autowired
	ProjectService projectService;
	
	public List<DangKyDetai> findByMsdt_Msdt(int msdt){
		return dangKyDeTaiRepository.findByMsdt_Msdt(msdt);
	}
	 
	/*
	 * public void approveStudent(int msdt, String mssv) { DangKyDetai dk =
	 * dangKyDeTaiRepository.findByMsdt_MsdtAndMssv_Mssv(msdt, mssv); if (dk != null
	 * && !"Đã duyệt".equals(dk.getTrangthai())) { dk.setTrangthai("Đã duyệt");
	 * dangKyDeTaiRepository.save(dk); }
	 * 
	 * 
	 * }
	 */
	 public boolean approveStudent(int msdt, String mssv) {
		    // Đếm số sinh viên đã được duyệt
		    long soSvDaDuyet = dangKyDeTaiRepository.countByMsdt_MsdtAndTrangthai(msdt, "Đã duyệt");

		    // Lấy đề tài
		    Project project = projectService.findByMsdt(msdt);

		    // Nếu đủ số lượng, từ chối duyệt
		    if (soSvDaDuyet >= project.getSosvtoida()) {
		        return false; // Quá giới hạn, không cho duyệt
		    }

		    // Tìm đăng ký của sinh viên này
		    DangKyDetai dk = dangKyDeTaiRepository.findByMsdt_MsdtAndMssv_Mssv(msdt, mssv);
		    if (dk != null && !"Đã duyệt".equals(dk.getTrangthai())) {
		        dk.setTrangthai("Đã duyệt");
		        dangKyDeTaiRepository.save(dk);
		        return true;
		    }

		    return false; // Không tìm thấy hoặc đã duyệt trước đó
		}
	 
	 	public int countByMsdt_MsdtAndTrangthai(int msdt, String trangthai) {
	        return dangKyDeTaiRepository.countByMsdt_MsdtAndTrangthai(msdt, trangthai);
	    }
}
