package ntu.granduationproject.ntu.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	
	@GetMapping("/danhsachdetai")
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

	    List<Project> projects = projectService.searchProjects(
	        giangVien.getMsgv(), namhoc, theloai, linhvuc, tendt, "Đã duyệt"
	    );
	    
	    Map<Integer, Integer> soSvDaDuyetMap = new HashMap<>();
	    for (Project p : projects) {
	        int count = svApprovalService.countByMsdt_MsdtAndTrangthai(p.getMsdt(), "Đã duyệt");
	        soSvDaDuyetMap.put(p.getMsdt(), count);
	    }

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
	
	
	@GetMapping("/projects/approve/{msdt}")
	public String viewStudentsForApproval(@PathVariable("msdt") int msdt, ModelMap model, HttpSession session) {
	    Object userObj = session.getAttribute("user");
	    if (!(userObj instanceof GiangVien)) {
	        return "redirect:/login";
	    }

	    GiangVien giangVien = (GiangVien) userObj;
	    List<DangKyDetai> danhSachDK = svApprovalService.findByMsdt_Msdt(msdt);
	    Project project = projectService.findByMsdt(msdt);
	    
	    int soSvDaDuyet = svApprovalService.countByMsdt_MsdtAndTrangthai(msdt, "Đã duyệt");

	    model.addAttribute("project", project);
	    model.addAttribute("soSvDaDuyet", soSvDaDuyet);
	    model.addAttribute("soSvToiDa", project.getSosvtoida());

	    model.addAttribute("tenDetai", project.getTendt());
	    model.addAttribute("danhSachDK", danhSachDK);
	    model.addAttribute("hanMucDA", giangVien.getHMHDDA());
	    model.addAttribute("hanMucCD", giangVien.getHMHDCD());
	    
	    return "views/giangvien/SVapproval";
	}

	
	
	
	@PostMapping("/projects/approve/{msdt}/duyet/{mssv}")
	public String duyetSinhVien(
	    @PathVariable("msdt") int msdt,
	    @PathVariable("mssv") String mssv,
	    HttpSession session
	) {
		/*
		 * Object userObj = session.getAttribute("user"); if (!(userObj instanceof
		 * GiangVien)) { return "redirect:/login"; }
		 */
			
	    svApprovalService.approveStudent(msdt, mssv);
	    return "redirect:/projects/approve/" + msdt;
	}
	

}
