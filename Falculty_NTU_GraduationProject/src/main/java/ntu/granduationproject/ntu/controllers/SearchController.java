package ntu.granduationproject.ntu.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.repositories.LinhVucRepository;
import ntu.granduationproject.ntu.repositories.NamHocRepository;
import ntu.granduationproject.ntu.repositories.TheLoaiRepository;
import ntu.granduationproject.ntu.services.ProjectService;

@Controller
public class SearchController {
	@Autowired
	ProjectService projectService;
	@Autowired
	TheLoaiRepository theLoaiRepository;
	@Autowired
	LinhVucRepository linhVucRepository;
	@Autowired
	NamHocRepository namHocRepository;
	
	
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

	    // Gửi dữ liệu về view
	    model.addAttribute("projects", projects);
	    model.addAttribute("namhocs", namHocRepository.findAll());
	    model.addAttribute("theloais", theLoaiRepository.findAll());
	    model.addAttribute("linhvucs", linhVucRepository.findAll());

	    model.addAttribute("selectedNamHoc", namhoc);
	    model.addAttribute("selectedTheLoai", theloai);
	    model.addAttribute("selectedLinhVuc", linhvuc);
	    model.addAttribute("tendt", tendt);

	    return "views/giangvien/myproject";
	}


}
