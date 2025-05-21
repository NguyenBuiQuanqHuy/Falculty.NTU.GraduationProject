package ntu.granduationproject.ntu.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.LinhVuc;
import ntu.granduationproject.ntu.models.NamHoc;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.models.TheLoai;
import ntu.granduationproject.ntu.repositories.GiangVienRepository;
import ntu.granduationproject.ntu.repositories.LinhVucRepository;
import ntu.granduationproject.ntu.repositories.NamHocRepository;
import ntu.granduationproject.ntu.repositories.TheLoaiRepository;
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

	@GetMapping("/taodetai")
	public String showCreateProjectForm(ModelMap model, HttpSession session) {
		Object userObj = session.getAttribute("user");
		if (userObj != null && userObj instanceof GiangVien) {
			GiangVien giangVien = (GiangVien) userObj;

			model.addAttribute("tenGiangVien", giangVien.getHoten());
		}

		int currentYear = LocalDate.now().getYear();

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
		return "views/giangvien/createproject";
	}

	@PostMapping("/taodetai")
	public String taodetai(@RequestParam String tendt, @RequestParam int theloai, @RequestParam String mota,
			@RequestParam String noidung, @RequestParam int linhvuc, @RequestParam int sosvtoida,
			@RequestParam int khoasv, @RequestParam int namhoc, HttpSession session,
			RedirectAttributes redirectAttributes, ModelMap model) {

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
			return "redirect:/taodetai";
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
		return "redirect:/giangvien/home";
	}
	
}