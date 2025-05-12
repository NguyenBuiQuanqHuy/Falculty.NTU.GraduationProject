package ntu.granduationproject.ntu.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UpdateProfileController {
    @GetMapping("/profile")
    public String FormProFile(HttpSession session) {
        String role = (String) session.getAttribute("role");
        System.out.println("Session username: " + session.getAttribute("username"));

        if ("sinhvien".equals(role)) {
            return "views/sinhvien/updateProfile";
        } else if ("giangvien".equals(role)) {
            return "views/giangvien/updateProfile";
        } else {
            return "redirect:login";
        }
    }
}
