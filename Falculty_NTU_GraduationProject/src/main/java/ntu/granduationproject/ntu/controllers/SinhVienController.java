package ntu.granduationproject.ntu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class SinhVienController {

    @GetMapping("/sinhvien")
    public String sinhVienHome(HttpSession session) {
        if ("sinhvien".equals(session.getAttribute("role"))) {
            return "views/sinhvien/Home";
        }
        return "redirect:/login?unauthorized=true";
    }
}
