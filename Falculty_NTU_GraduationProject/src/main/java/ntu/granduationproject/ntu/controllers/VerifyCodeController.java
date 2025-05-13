package ntu.granduationproject.ntu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.services.EmailService;
import ntu.granduationproject.ntu.services.GiangVienService;
import ntu.granduationproject.ntu.services.SinhVienService;

@Controller
@Configuration
@RequestMapping("/")
public class VerifyCodeController {

	  @Autowired
	    private SinhVienService sinhVienService;
	  
	  @Autowired
	  private GiangVienService giangVienService;

	  
	    @GetMapping("/resetpassword")
	    public String verifycode() {
	        return "views/ResetPassword";
	    }

	   
	    @PostMapping("/verifycode")
	    public String checkVerifyCode(@RequestParam String code, HttpSession session, Model model) {
	        String storedCode = (String) session.getAttribute("verificationCode");

	        if (storedCode == null || !storedCode.equals(code)) {
	            model.addAttribute("error", "Mã xác thực không đúng!");
	            return "views/VerifyCode";
	        }

	        // Mã đúng, chuyển đến form đặt lại mật khẩu
	        return "redirect:/resetpassword";
	    }
	    
	  
}
