package ntu.granduationproject.ntu.controllers;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import ntu.granduationproject.ntu.models.*;
import ntu.granduationproject.ntu.repositories.*;
import ntu.granduationproject.ntu.services.DangKyDetaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.services.ProjectService;

@Controller
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	GiangVienRepository giangVienRepository;

	@Autowired
	TheLoaiRepository theLoaiRepository;

	@Autowired
	LinhVucRepository linhVucRepository;

	@Autowired
	NamHocRepository namHocRepository;

	@Autowired
	DangKyDeTaiRepository dangKyDeTaiRepository;

	@GetMapping("/giangvien/taodetai")
	public String showCreateProjectForm(ModelMap model, HttpSession session) {
	    Object userObj = session.getAttribute("user");
	    if (userObj != null && userObj instanceof GiangVien) {
	        GiangVien giangVien = (GiangVien) userObj;
	        model.addAttribute("tenGiangVien", giangVien.getHoten());
	    }

	    int currentYear = LocalDate.now().getYear();

	    // Tính khóa SV từ năm hiện tại (VD: 2025 => 63, 2026 => 64, ...)
	    int baseYear = 2025;   // Mốc năm hiện tại
	    int baseKhoa = 63;     // Khóa tương ứng với năm 2025

	    int khoaMacDinh = baseKhoa + (currentYear - baseYear);
	    model.addAttribute("khoaMacDinh", khoaMacDinh);

	    Optional<NamHoc> existingNamHoc = namHocRepository.findByTennamhoc(currentYear);
	    NamHoc namHoc;
	    if (existingNamHoc.isPresent()) {
	        namHoc = existingNamHoc.get();
	    } else {
	        namHoc = new NamHoc();
	        namHoc.setTennamhoc(currentYear);
	        namHocRepository.save(namHoc);
	    }
	    model.addAttribute("namHocHienTai", namHoc);

	    model.addAttribute("linhVucs", linhVucRepository.findAll());
	    model.addAttribute("theLoais", theLoaiRepository.findAll());
	    model.addAttribute("giangviens", giangVienRepository.findAll());
	    return "views/giangvien/CreateProject";
	}



	@PostMapping("/giangvien/taodetai")
	public String taodetai(
	                       @RequestParam String tendt,
	                       @RequestParam int theloai,
	                       @RequestParam String mota,
	                       @RequestParam String noidung,
	                       @RequestParam int linhvuc,
	                       @RequestParam int sosvtoida,
	                       @RequestParam int khoasv,
	                       @RequestParam int namhoc,
	                       HttpSession session,
	                       RedirectAttributes redirectAttributes,ModelMap model) {
		

	    Object userObj = session.getAttribute("user");
	    if (userObj == null || !(userObj instanceof GiangVien)) {
	        redirectAttributes.addFlashAttribute("error", "Bạn chưa đăng nhập.");
	        return "redirect:/login"; 
	    }
	    GiangVien giangVien = (GiangVien) userObj;

	    TheLoai loai = theLoaiRepository.findById(theloai).orElse(null);
	    LinhVuc linhVucObj = linhVucRepository.findById(linhvuc).orElse(null);
	    NamHoc namHoc = namHocRepository.findByTennamhoc(namhoc).orElse(null);



	    if (giangVien == null || loai == null || linhVucObj == null || namHoc == null) {
	        redirectAttributes.addFlashAttribute("error", "Một trong các thông tin không hợp lệ.");
	        return "redirect:/giangvien/taodetai"; 
	    }

	    Project deTai = new Project();
	    deTai.setTendt(tendt);
	    deTai.setMsgv(giangVien); 
	    deTai.setTheLoai(loai);
	    deTai.setMota(mota);
	    deTai.setNoidung(noidung);
	    deTai.setLinhVuc(linhVucObj);
	    deTai.setSosvtoida(sosvtoida);
	    deTai.setKhoasv(khoasv);
	    deTai.setNamHoc(namHoc);

	    projectService.createProject(deTai);

	    redirectAttributes.addFlashAttribute("success", "Đề tài đã được tạo thành công!");
		return "redirect:/giangvien/danhsachdetai?tab=MyTopics";
	}

	@GetMapping("/giangvien/danhsachdetai")
	public String listTopic(
			@RequestParam(value = "tendt", required = false) String tendt,
			@RequestParam(value = "namhoc", required = false) Integer namhoc,
			@RequestParam(value = "theloai", required = false) Integer theloai,
			@RequestParam(value = "linhvuc", required = false) Integer linhvuc,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			Model model, HttpSession session, RedirectAttributes redirectAttrs) {

		// Kiểm tra đăng nhập
		String msgv = (String) session.getAttribute("maso");
		if (msgv == null) {
			redirectAttrs.addFlashAttribute("error", "Bạn chưa đăng nhập");
			return "redirect:/login";
		}

		int pageSize = 5;
		Pageable pageable = PageRequest.of(page, pageSize);

		// Gọi service trả về Page<Project>
		Page<Project> pagedProjects = projectService.searchProjectsPaged(
				tendt,
				namhoc,
				theloai,
				linhvuc,
				"Đã duyệt",
				pageable
		);

		// Danh sách đề tài đang hiển thị theo trang
		List<Project> dsFiltered = pagedProjects.getContent();

		// Đếm số sinh viên đã đăng ký và đã duyệt theo đề tài
		Map<Integer, Integer> mapCountRegistered = new HashMap<>();
		Map<Integer, Integer> mapCountApproved = new HashMap<>();
		for (Project project : dsFiltered) {
			int count = dangKyDeTaiRepository.countByMsdt_Msdt(project.getMsdt());
			int approved = dangKyDeTaiRepository.countApprovedByMsdt(project.getMsdt());
			mapCountRegistered.put(project.getMsdt(), count);
			mapCountApproved.put(project.getMsdt(), approved);
		}

		// Lấy danh sách đề tài của giảng viên hiện tại
		List<Project> myProjects = projectService.findProjectsByGiangVien(msgv);
		myProjects.sort(Comparator.comparing(dt -> !"Chưa duyệt".equalsIgnoreCase(dt.getTrangthai())));
		model.addAttribute("myprojects", myProjects);

		// Truyền dữ liệu cho view
		model.addAttribute("dsdetai", dsFiltered);
		model.addAttribute("countRegistered", mapCountRegistered);
		model.addAttribute("countApproved", mapCountApproved);
		model.addAttribute("myprojects", myProjects);

		// Danh sách lọc
		model.addAttribute("namhocs", namHocRepository.findAll());
		model.addAttribute("theloais", theLoaiRepository.findAll());
		model.addAttribute("linhvucs", linhVucRepository.findAll());

		// Truyền lại giá trị đã chọn
		model.addAttribute("tendt", tendt);
		model.addAttribute("selectedNamHoc", namhoc);
		model.addAttribute("selectedTheLoai", theloai);
		model.addAttribute("selectedLinhVuc", linhvuc);

		// Phân trang
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", pagedProjects.getTotalPages());
		System.out.println("Total pages: " + pagedProjects.getTotalPages());
		System.out.println("Total elements: " + pagedProjects.getTotalElements());


		return "views/giangvien/ListProject";
	}
}
