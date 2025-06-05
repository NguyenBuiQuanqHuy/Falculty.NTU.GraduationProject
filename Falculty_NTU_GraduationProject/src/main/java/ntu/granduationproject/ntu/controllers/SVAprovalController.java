package ntu.granduationproject.ntu.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ntu.granduationproject.ntu.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.DangKyDetai;
import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.repositories.DangKyDeTaiRepository;
import ntu.granduationproject.ntu.repositories.LinhVucRepository;
import ntu.granduationproject.ntu.repositories.NamHocRepository;
import ntu.granduationproject.ntu.repositories.TheLoaiRepository;
import ntu.granduationproject.ntu.services.ProjectService;
import ntu.granduationproject.ntu.services.SVApprovalService;

@Controller
public class SVAprovalController {
	@Autowired
	ProjectService projectService;
	@Autowired
	TheLoaiRepository theLoaiRepository;
	@Autowired
	LinhVucRepository linhVucRepository;
	@Autowired
	NamHocRepository namHocRepository;
	@Autowired
	SVApprovalService svApprovalService;

	@Autowired
	EmailService emailService;

	
	@GetMapping("/giangvien/detaidaduyet")
	public String listProjects(
	        @RequestParam(required = false) Integer namhoc,
	        @RequestParam(required = false) Integer theloai,
	        @RequestParam(required = false) Integer linhvuc,
	        @RequestParam(required = false) String tendt,
	        HttpSession session,
	        ModelMap model) {

	    // Lấy giảng viên từ session
	    Object userObj = session.getAttribute("user");
	    if (!(userObj instanceof GiangVien)) {
	        return "redirect:/login";
	    }
	    GiangVien giangVien = (GiangVien) userObj;
	    String msgv = giangVien.getMsgv();

	    List<Project> projects = projectService.searchProjects(
	        giangVien.getMsgv(), namhoc, theloai, linhvuc, tendt, "Đã duyệt"
	    );
	    
	    Map<Integer, Integer> soSvDaDuyetMap = new HashMap<>();
	    for (Project p : projects) {
	        int count = svApprovalService.countByMsdt_MsdtAndTrangthai(p.getMsdt(), "Đã duyệt");
	        soSvDaDuyetMap.put(p.getMsdt(), count);
	    }
	    
	    int soSvDaDuyetDA = svApprovalService.countByGiangVienAndLoai(msgv, 1); 
	    int soSvDaDuyetCD = svApprovalService.countByGiangVienAndLoai(msgv, 2); 

	    model.addAttribute("soSvDaDuyetDA", soSvDaDuyetDA);
	    model.addAttribute("soSvDaDuyetCD", soSvDaDuyetCD);

	    model.addAttribute("projects", projects);
	    model.addAttribute("soSvDaDuyetMap", soSvDaDuyetMap);
	    // Gửi dữ liệu về view
	    model.addAttribute("projects", projects);
	    model.addAttribute("namhocs", namHocRepository.findAll());
	    model.addAttribute("theloais", theLoaiRepository.findAll());
	    model.addAttribute("linhvucs", linhVucRepository.findAll());
	    
	    model.addAttribute("hanMucDA", giangVien.getHMHDDA());
	    model.addAttribute("hanMucCD", giangVien.getHMHDCD());

	    
	    model.addAttribute("selectedNamHoc", namhoc);
	    model.addAttribute("selectedTheLoai", theloai);
	    model.addAttribute("selectedLinhVuc", linhvuc);
	    model.addAttribute("tendt", tendt);

	    return "views/giangvien/myproject";
	}
	
	
	@GetMapping("/detai/duyetsv/{msdt}")
	public String viewStudentsForApproval(@PathVariable("msdt") int msdt, ModelMap model, HttpSession session) {
	    Object userObj = session.getAttribute("user");
	    if (!(userObj instanceof GiangVien)) {
	        return "redirect:/login";
	    }

	    GiangVien giangVien = (GiangVien) userObj;
	    String msgv = giangVien.getMsgv();
	    List<DangKyDetai> danhSachDK = svApprovalService.findByMsdt_Msdt(msdt);
	    Project project = projectService.findByMsdt(msdt);
	    
	    int soSvDaDuyet = svApprovalService.countByMsdt_MsdtAndTrangthai(msdt, "Đã duyệt");
	    
	    int soSvDaDuyetDA = svApprovalService.countByGiangVienAndLoai(msgv, 1); 
	    int soSvDaDuyetCD = svApprovalService.countByGiangVienAndLoai(msgv, 2); 
	    model.addAttribute("soSvDaDuyetDA", soSvDaDuyetDA);
	    model.addAttribute("soSvDaDuyetCD", soSvDaDuyetCD);

	    model.addAttribute("project", project);
	    model.addAttribute("soSvDaDuyet", soSvDaDuyet);
	    model.addAttribute("soSvToiDa", project.getSosvtoida());

	    model.addAttribute("tenDetai", project.getTendt());
	    model.addAttribute("danhSachDK", danhSachDK);
	    model.addAttribute("hanMucDA", giangVien.getHMHDDA());
	    model.addAttribute("hanMucCD", giangVien.getHMHDCD());
	    
	    return "views/giangvien/SVapproval";
	}

	
	
	
	@PostMapping("/detai/duyetsv/{msdt}/duyet/{mssv}")
	public String duyetSinhVien(
	    @PathVariable("msdt") int msdt,
	    @PathVariable("mssv") String mssv,
	    HttpSession session,
	    ModelMap model
	) {
	    Object userObj = session.getAttribute("user");
	    if (!(userObj instanceof GiangVien)) {
	        return "redirect:/login";
	    }

	    GiangVien gv = (GiangVien) userObj;
	    Project project = projectService.findByMsdt(msdt);
	    
	    int svDaDuyetTrongDeTai = svApprovalService.countByMsdt_MsdtAndTrangthai(msdt, "Đã duyệt");
	    if (svDaDuyetTrongDeTai >= project.getSosvtoida()) {
	        model.addAttribute("error", "Đề tài này đã đủ số lượng sinh viên thực hiện.");
	        return "redirect:/detai/duyetsv/" + msdt;
	    }

	    int svDaDuyetCungLoai = svApprovalService.countByGiangVienAndLoai(gv.getMsgv(), project.getTheLoai().getMatheloai());
	    int hanMuc = project.getTheLoai().getMatheloai() == 1 ? gv.getHMHDDA() : gv.getHMHDCD();

		boolean success = svApprovalService.approveStudent(msdt, mssv);

		if (!success) {
			model.addAttribute("error", "Duyệt sinh viên không thành công. Có thể đề tài đã đủ số lượng hoặc sinh viên đã được duyệt.");
			return "redirect:/detai/duyetsv/" + msdt;
		}
	    if (svDaDuyetCungLoai >= hanMuc) {
	        model.addAttribute("error", "Bạn đã vượt quá hạn mức sinh viên cho loại đề tài này.");
	        return "redirect:/detai/duyetsv/" + msdt;
	    }

	    return "redirect:/detai/duyetsv/" + msdt;
	}
}
