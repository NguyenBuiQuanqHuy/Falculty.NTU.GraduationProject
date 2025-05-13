package ntu.granduationproject.ntu.controllers;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.repositories.GiangVienRepository;
import ntu.granduationproject.ntu.repositories.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                    session.setAttribute("cv", sv.getCv());
                    model.addAttribute("sinhVien",sv);
                }
            }
            return "views/sinhvien/Profile";
        } else if ("giangvien".equals(role) || "admin".equals(role)) {
            if (maso != null) {
                Optional<GiangVien> gvOpt = giangVienRepository.findByMsgv(maso);
                if (gvOpt.isPresent()) {
                    GiangVien gv = gvOpt.get();
                    session.setAttribute("cv", gv.getCv());
                    model.addAttribute("giangVien",gv);
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
                svOpt.ifPresent(sinhVien -> model.addAttribute("sinhvien",sinhVien));
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
                sv.setCv(cv);
                sinhVienRepository.save(sv);
                session.setAttribute("cv",cv);
            }
        } else if ("giangvien".equals(role) || "admin".equals(role)) {
            Optional<GiangVien> gvOpt = giangVienRepository.findByMsgv(maso);
            if (gvOpt.isPresent()) {
                GiangVien gv = gvOpt.get();
                gv.setCv(cv);
                giangVienRepository.save(gv);
                session.setAttribute("cv",cv);
            }
        }
        return "redirect:/profile";
    }
}
