package ntu.granduationproject.ntu.controllers;

import ntu.granduationproject.ntu.services.SinhVienService;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.services.GiangVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private GiangVienService giangVienService;

    @GetMapping("/login")
    public String LoginForm() {
        return "views/Login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String maso,
            @RequestParam String matkhau,
            HttpSession session,
            Model model
    ) {
       
        var sv = sinhVienService.loginSinhVien(maso, matkhau);
        if (sv.isPresent()) {
            session.setAttribute("user", sv.get());
            session.setAttribute("role", "sinhvien");
            session.setAttribute("username", sv.get().getHoten());
            return "redirect:/sinhvien/home";
        }

        
        var gv = giangVienService.loginGiangVien(maso, matkhau);
        if (gv.isPresent()) {
            session.setAttribute("user", gv.get());
            session.setAttribute("role", gv.get().isIsAdmin() ? "admin" : "giangvien");
            session.setAttribute("username", gv.get().getHoten());

            if (gv.get().isIsAdmin()) {
                return "redirect:/truongkhoa/home";
            } else {
                return "redirect:/giangvien/home";
            }
        }

        
        model.addAttribute("error", "Mã số hoặc mật khẩu không đúng");
        return "views/Login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/forgotpassword")
    public String forgotPassForm() {
    	return "views/ForgotPassword";
    }
    
    

}
