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
	 
	
	public boolean approveStudent(int msdt, String mssv) {
	    Project project = projectService.findByMsdt(msdt);
	    if (project == null) return false;
	    
	    // Kiểm tra xem đã đủ sinh viên đc duyệt chưa
	    long soSvDaDuyetTrongDeTai = dangKyDeTaiRepository.countByMsdt_MsdtAndTrangthai(msdt, "Đã duyệt");
	    if (soSvDaDuyetTrongDeTai >= project.getSosvtoida()) {
	        return false; 
	    }

	    String msgv = project.getMsgv().getMsgv();
	    int theloai = project.getTheLoai().getMatheloai();

	    int tongSoDaDuyetTheoLoai = dangKyDeTaiRepository.countByGiangVienAndTheLoai(msgv, theloai);

	    int hanMuc = (theloai == 1) ? project.getMsgv().getHMHDDA() : project.getMsgv().getHMHDCD();
	    
	    DangKyDetai dk = dangKyDeTaiRepository.findByMsdt_MsdtAndMssv_Mssv(msdt, mssv);
	    if (dk != null && !"Đã duyệt".equals(dk.getTrangthai())) {
	        dk.setTrangthai("Đã duyệt");
	        dangKyDeTaiRepository.save(dk);
			String acceptedEmail = dk.getMssv().getEmail();
			String acceptedSubject = "Kết quả đăng ký đề tài";
			String acceptedBody = "Chúc mừng! Đề tài \"" + project.getTendt() + "\" bạn đăng ký đã được duyệt.";

			if (acceptedEmail != null && !acceptedEmail.isEmpty()) {
				emailService.sendNotificationEmail(acceptedEmail, acceptedSubject, acceptedBody);
				System.out.println("Đã gửi mail xác nhận duyệt tới sinh viên: " + acceptedEmail);
			}

	        if (!project.isCosvthuchien()) {
	            project.setCosvthuchien(true);
	            projectRepository.save(project);
	        }
	        
	        int soSvSauKhiDuyet = dangKyDeTaiRepository.countByMsdt_MsdtAndTrangthai(msdt, "Đã duyệt");
	        if (soSvSauKhiDuyet >= project.getSosvtoida()) {
	            List<DangKyDetai> chuaDuyetList = dangKyDeTaiRepository.findByMsdt_MsdtAndTrangthai(msdt, "chưa duyệt");
	            for (DangKyDetai cho : chuaDuyetList) {
	                String toEmail = cho.getMssv().getEmail();
	                String subject = "Thông báo kết quả đăng ký đề tài";
	                String body = "Xin chào, đề tài bạn đăng ký hiện đã đủ số lượng sinh viên được duyệt. Bạn vui lòng chọn đề tài khác.";
					if (toEmail != null && !toEmail.isEmpty()) {
						emailService.sendNotificationEmail(toEmail, subject, body);
						System.out.println("Đã gửi mail tới sinh viên: " + toEmail);
					} else {
						System.out.println("Email sinh viên bị null hoặc rỗng, không gửi được mail!");
					}
	            }
	        }
	        
	        
	        // Kiểm tra xem giảng viên đó đã đủ hạn mức theo thể loại ch
	        tongSoDaDuyetTheoLoai = dangKyDeTaiRepository.countByGiangVienAndTheLoai(msgv, theloai);
	        if (tongSoDaDuyetTheoLoai >= hanMuc) {
	            List<DangKyDetai> chuaDuyetGVList = dangKyDeTaiRepository.findByMsdt_Msgv_MsgvAndTrangthaiAndMsdt_TheLoai_Matheloai(msgv, "chưa duyệt", theloai);
	            for (DangKyDetai cho : chuaDuyetGVList) {
	                String toEmail = cho.getMssv().getEmail();
	                String subject = "Thông báo kết quả đăng ký đề tài";
	                String body = "Xin chào, giảng viên của đề tài bạn đăng ký đã đạt hạn mức sinh viên cho loại đề tài này. Bạn vui lòng đăng ký giảng viên khác.";
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

	public EmailService getEmailService() {
		return emailService;
	}
}
