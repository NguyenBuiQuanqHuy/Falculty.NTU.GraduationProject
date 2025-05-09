package ntu.granduationproject.ntu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class GiangVienController {

    @GetMapping("/giangvien/home")
    public String giangVienHome(HttpSession session) {
        if ("giangvien".equals(session.getAttribute("role"))) {
            return "views/giangvien/Home";
        }
        return "redirect:/login?unauthorized=true";
    }
}