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

//    @Autowired
//    ProjectService projectService;
//
//    @Autowired
//    GiangVienRepository giangVienRepository;
//
//    @Autowired
//    TheLoaiRepository theLoaiRepository;
//
//    @Autowired
//    LinhVucRepository linhVucRepository;
//
//    @Autowired
//    NamHocRepository namHocRepository;

//    // Hiển thị trang tạo đề tài
//    @GetMapping("/giangvien/taodetai")
//    public String showCreateProjectForm(ModelMap model, HttpSession session) {
//        // Kiểm tra xem có người dùng giảng viên trong session không
//        Object userObj = session.getAttribute("user");
//        if (userObj != null && userObj instanceof GiangVien) {
//            GiangVien giangVien = (GiangVien) userObj;
//            // Gán mã giảng viên vào model
//            model.addAttribute("msgv", giangVien.getMsgv()); // Truyền mã giảng viên vào model
//        }
//
//        // Thêm các đối tượng cần thiết vào model
//        model.addAttribute("linhVucs", linhVucRepository.findAll());
//        model.addAttribute("theLoais", theLoaiRepository.findAll());
//        model.addAttribute("namhocs", namHocRepository.findAll());
//        model.addAttribute("giangviens", giangVienRepository.findAll());
//        return "views/giangvien/createproject"; // trả về trang HTML tạo đề tài
//    }
//
//    // Xử lý khi tạo đề tài
//    @PostMapping("/giangvien/taodetai")
//    public String taodetai(@RequestParam String msdt,
//                           @RequestParam String tendt,
//                           @RequestParam String msgv,
//                           @RequestParam int theloai,
//                           @RequestParam int linhvuc,
//                           @RequestParam int namhoc,
//                           @RequestParam String mota,
//                           @RequestParam String noidung,
//                           @RequestParam int sosvtoida,
//                           @RequestParam int khoasv,
//                           HttpSession session,
//                           RedirectAttributes redirectAttributes) {
//
//        // Lấy các đối tượng từ cơ sở dữ liệu
//        GiangVien giangVien = giangVienRepository.findById(msgv).orElse(null);
//        TheLoai loai = theLoaiRepository.findById(theloai).orElse(null);
//        LinhVuc linhVucObj = linhVucRepository.findById(linhvuc).orElse(null);
//        NamHoc namHoc = namHocRepository.findById(namhoc).orElse(null);
//
//        // Kiểm tra xem tất cả các đối tượng đã được lấy đúng chưa
//        if (giangVien == null || loai == null || linhVucObj == null || namHoc == null) {
//            redirectAttributes.addFlashAttribute("error", "Một trong các thông tin không hợp lệ.");
//            return "redirect:/giangvien/taodetai"; // Quay lại trang tạo đề tài
//        }
//
//        // Tạo đối tượng DeTai mới
//        Project deTai = new Project();
//        deTai.setMsdt(msdt);
//        deTai.setTendt(tendt);
//        deTai.setGiangVien(giangVien);
//        deTai.setTheLoai(loai);
//        deTai.setLinhVuc(linhVucObj);
//        deTai.setNamHoc(namHoc);
//        deTai.setMota(mota);
//        deTai.setNoidung(noidung);
//        deTai.setSosvtoida(sosvtoida);
//        deTai.setKhoasv(khoasv);
//
//        // Lưu đề tài vào cơ sở dữ liệu (đảm bảo bạn có repository cho DeTai)
//        projectService.createProject(deTai);
//
//        redirectAttributes.addFlashAttribute("success", "Đề tài đã được tạo thành công!");
//        return "redirect:/giangvien/taodetai"; // Quay lại trang tạo đề tài
//    }
    
    @GetMapping("giangvien/taodetai")
    public String detai() {
    	
    	return "views/giangvien/createproject";
    }
}
