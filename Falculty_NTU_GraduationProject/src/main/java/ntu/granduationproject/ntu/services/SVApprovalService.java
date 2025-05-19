package ntu.granduationproject.ntu.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.granduationproject.ntu.models.DangKyDetai;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.repositories.DangKyDeTaiRepository;
import ntu.granduationproject.ntu.repositories.ProjectRepository;

@Service
public class SVApprovalService {
	
	@Autowired
	DangKyDeTaiRepository dangKyDeTaiRepository;
	@Autowired
	ProjectService projectService;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	private EmailService emailService;
	
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
	    // Lấy đề tài
	    Project project = projectService.findByMsdt(msdt);
	    if (project == null) return false;

	    // 1. Kiểm tra số SV đã được duyệt trong đề tài
	    long soSvDaDuyetTrongDeTai = dangKyDeTaiRepository.countByMsdt_MsdtAndTrangthai(msdt, "Đã duyệt");
	    if (soSvDaDuyetTrongDeTai >= project.getSosvtoida()) {
	        return false; // Đề tài đã đủ SV
	    }

	    // 2. Kiểm tra tổng số SV đã được duyệt theo loại đề tài của giảng viên
	    String msgv = project.getMsgv().getMsgv();
	    int theloai = project.getTheLoai().getMatheloai();

	    int tongSoDaDuyetTheoLoai = dangKyDeTaiRepository.countByGiangVienAndTheLoai(msgv, theloai);

	    int hanMuc = (theloai == 1) ? project.getMsgv().getHMHDDA() : project.getMsgv().getHMHDCD();


	    // 3. Duyệt SV nếu hợp lệ
	    DangKyDetai dk = dangKyDeTaiRepository.findByMsdt_MsdtAndMssv_Mssv(msdt, mssv);
	    if (dk != null && !"Đã duyệt".equals(dk.getTrangthai()) || tongSoDaDuyetTheoLoai >= hanMuc) {
	        dk.setTrangthai("Đã duyệt");
	        dangKyDeTaiRepository.save(dk);

	        if (!project.isCosvthuchien()) {
	            project.setCosvthuchien(true);
	            projectRepository.save(project);
	        }
	        
	        // Kiểm tra lại sau khi duyệt: nếu đề tài đã đủ SV => gửi mail cho SV chưa được duyệt
	        int soSvSauKhiDuyet = dangKyDeTaiRepository.countByMsdt_MsdtAndTrangthai(msdt, "Đã duyệt");
	        if (soSvSauKhiDuyet >= project.getSosvtoida()) {
	            List<DangKyDetai> chuaDuyetList = dangKyDeTaiRepository.findByMsdt_MsdtAndTrangthai(msdt, "chưa duyệt");
	            for (DangKyDetai cho : chuaDuyetList) {
	                String toEmail = cho.getMssv().getEmail();
	                String subject = "Thông báo kết quả đăng ký đề tài";
	                String body = "Xin chào, đề tài bạn đăng ký hiện đã đủ số lượng sinh viên được duyệt. Bạn vui lòng chọn đề tài khác.";
	                emailService.sendNotificationEmail(toEmail, subject, body);
	            }
	        }

	        return true;
	    }

	    return false;
	}

	 
	 	public int countByMsdt_MsdtAndTrangthai(int msdt, String trangthai) {
	        return dangKyDeTaiRepository.countByMsdt_MsdtAndTrangthai(msdt, trangthai);
	    }
	 	
	 	public int countByGiangVienAndLoai(String msgv, int matheloai) {
	 	    return dangKyDeTaiRepository.countByGiangVienAndTheLoai(msgv, matheloai);
	 	}

	 
}
