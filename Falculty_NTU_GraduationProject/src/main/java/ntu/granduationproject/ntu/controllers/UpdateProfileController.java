package ntu.granduationproject.ntu.controllers;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.repositories.GiangVienRepository;
import ntu.granduationproject.ntu.repositories.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class UpdateProfileController {
    @Autowired
    private GiangVienRepository giangVienRepository;
    @Autowired
    private SinhVienRepository sinhVienRepository;


    @GetMapping("/profile")
    public String FormProFile(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        String maso = (String) session.getAttribute("maso");

        if ("sinhvien".equals(role)) {
            if (maso != null) {
                Optional<SinhVien> svOpt = sinhVienRepository.findByMssv(maso);
                if (svOpt.isPresent()) {
                    SinhVien sv = svOpt.get();
                    session.setAttribute("cv", sv.getCvhoso());
                    model.addAttribute("sinhVien", sv);
                }
            }
            return "views/sinhvien/Profile";
        } else if ("giangvien".equals(role) || "admin".equals(role)) {
            if (maso != null) {
                Optional<GiangVien> gvOpt = giangVienRepository.findByMsgv(maso);
                if (gvOpt.isPresent()) {
                    GiangVien gv = gvOpt.get();
                    session.setAttribute("cv", gv.getCvnangluc());
                    model.addAttribute("giangVien", gv);
                }
            }
            return "views/giangvien/Profile";
        } else {
            return "redirect:login";
        }
    }

    @GetMapping("/editProfile")
    public String editProfilePage(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String maso = (String) session.getAttribute("maso");

        if ("sinhvien".equals(role)) {
            if (maso != null) {
                Optional<SinhVien> svOpt = sinhVienRepository.findByMssv(maso);
                svOpt.ifPresent(sinhVien -> model.addAttribute("sinhvien", sinhVien));
            }
            return "views/sinhvien/editProfile";
        } else if ("giangvien".equals(role) || "admin".equals(role)) {
            if (maso != null) {
                Optional<GiangVien> gvOpt = giangVienRepository.findByMsgv(maso);
                gvOpt.ifPresent(giangVien -> model.addAttribute("giangVien", giangVien));
            }
            return "views/giangvien/editProfile";
        } else {
            return "redirect:login";
        }
    }

    @PostMapping("/updateProfile")
    public String updateProfile(HttpSession session,
                                @RequestParam("cv") String cv) {
        String role = (String) session.getAttribute("role");
        String maso = (String) session.getAttribute("maso");

        if ("sinhvien".equals(role)) {
            Optional<SinhVien> svOpt = sinhVienRepository.findByMssv(maso);
            if (svOpt.isPresent()) {
                SinhVien sv = svOpt.get();
                sv.setCvhoso(cv);
                sinhVienRepository.save(sv);
                session.setAttribute("cv", cv);
            }
        } else if ("giangvien".equals(role) || "admin".equals(role)) {
            Optional<GiangVien> gvOpt = giangVienRepository.findByMsgv(maso);
            if (gvOpt.isPresent()) {
                GiangVien gv = gvOpt.get();
                gv.setCvnangluc(cv);
                giangVienRepository.save(gv);
                session.setAttribute("cv", cv);
            }
        }
        return "redirect:/profile";
    }

    @GetMapping("/profile/sinhvien/{id}")
    public String ProfileInfo(@PathVariable("id") String mssv, Model model, HttpSession session) {
        System.out.println("Received mssv: " + mssv);  // debug xem mssv có đúng không
        SinhVien sinhVien = sinhVienRepository.findByMssv(mssv)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã số sinh viên"));

        String role = (String) session.getAttribute("role");
        String returnUrl = "/";
        if ("giangvien".equals(role)) {
            returnUrl = "/danhsachdetaicuatoi";
        }
        model.addAttribute("sinhvien", sinhVien);
        model.addAttribute("returnUrl", returnUrl);

        return "views/sinhvien/ProfileInfo";
    }

    @GetMapping("/profile/giangvien/{id}")
    public String ProfileInfo(@PathVariable("id") String msgv, Model model) {
        System.out.println("Received msgv: " + msgv);  // debug xem mssv có đúng không
        GiangVien giangVien = giangVienRepository.findByMsgv(msgv)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã số giảng viên"));

        model.addAttribute("giangvien", giangVien);
        return "views/giangvien/ProfileInfo";
    }
}
