package ntu.granduationproject.ntu.controllers;

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

	@GetMapping("/giangvien/taodetai")
	public String showCreateProjectForm(ModelMap model, HttpSession session) {
	    // Kiểm tra xem có người dùng giảng viên trong session không
	    Object userObj = session.getAttribute("user");
	    if (userObj != null && userObj instanceof GiangVien) {
	        GiangVien giangVien = (GiangVien) userObj;
	        // Gán tên giảng viên vào model
	        model.addAttribute("tenGiangVien", giangVien.getHoten());
	    }

	    // Thêm các đối tượng cần thiết vào model
	    model.addAttribute("linhVucs", linhVucRepository.findAll());
	    model.addAttribute("theLoais", theLoaiRepository.findAll());
	    model.addAttribute("namhocs", namHocRepository.findAll());
	    model.addAttribute("giangviens", giangVienRepository.findAll());
	    return "views/giangvien/createproject"; // trả về trang HTML tạo đề tài
	}


	@PostMapping("/giangvien/taodetai")
	public String taodetai(@RequestParam String msdt,
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
		
		  // Kiểm tra mã số đề tài đã tồn tại chưa
		if (projectService.findbyID(msdt).isPresent()) {
		    redirectAttributes.addFlashAttribute("message", "Mã số đề tài đã tồn tại.");
		    redirectAttributes.addFlashAttribute("messageType", "error");
		    return "redirect:/giangvien/taodetai"; // ✅ Redirect luôn
		}

	    // Lấy giảng viên từ session
	    Object userObj = session.getAttribute("user");
	    if (userObj == null || !(userObj instanceof GiangVien)) {
	        redirectAttributes.addFlashAttribute("error", "Bạn chưa đăng nhập.");
	        return "redirect:/login"; // Nếu không phải giảng viên hoặc không đăng nhập, quay lại trang login
	    }
	    GiangVien giangVien = (GiangVien) userObj;

	    // Lấy các đối tượng từ cơ sở dữ liệu
	    TheLoai loai = theLoaiRepository.findById(theloai).orElse(null);
	    LinhVuc linhVucObj = linhVucRepository.findById(linhvuc).orElse(null);
	    NamHoc namHoc = namHocRepository.findById(namhoc).orElse(null);

	    // Kiểm tra xem tất cả các đối tượng đã được lấy đúng chưa
	    if (giangVien == null || loai == null || linhVucObj == null || namHoc == null) {
	        redirectAttributes.addFlashAttribute("error", "Một trong các thông tin không hợp lệ.");
	        return "redirect:/giangvien/taodetai"; // Quay lại trang tạo đề tài
	    }

	    // Tạo đối tượng DeTai mới
	    Project deTai = new Project();
	    deTai.setMsdt(msdt);
	    deTai.setTendt(tendt);
	    deTai.setMsgv(giangVien);  // Sử dụng giảng viên từ session
	    deTai.setTheLoai(loai);
	    deTai.setMota(mota);
	    deTai.setNoidung(noidung);
	    deTai.setLinhVuc(linhVucObj);
	    deTai.setSosvtoida(sosvtoida);
	    deTai.setKhoasv(khoasv);
	    deTai.setNamHoc(namHoc);

	    // Lưu đề tài vào cơ sở dữ liệu
	    projectService.createProject(deTai);

	    redirectAttributes.addFlashAttribute("success", "Đề tài đã được tạo thành công!");
	    return "redirect:/giangvien/home"; // Quay lại trang tạo đề tài
	}

}
