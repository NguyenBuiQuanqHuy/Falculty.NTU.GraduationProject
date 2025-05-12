package ntu.granduationproject.ntu.controllers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.services.EmailService;
import ntu.granduationproject.ntu.services.GiangVienService;
import ntu.granduationproject.ntu.services.SinhVienService;


@Controller
@Configuration
@RequestMapping("/")
public class ForgotPasswordController {
    

    public String generateRandomCode() {
        int length = 6; 
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10)); 
        }
        return code.toString();
    }

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private GiangVienService giangVienService;
    
    @Autowired
    private EmailService emailService;

  
    @GetMapping("/verifycode")
    public String verifycode() {
        return "views/VerifyCode";
    }

   
    @PostMapping("/forgotpassword")
    public String forgotPassword(
            @RequestParam String maso,
            @RequestParam String email,
            HttpSession session,
            Model model
    ) {
        var sv = sinhVienService.forgotPasswordSinhVien(maso, email);
        if (sv.isPresent()) {
            session.setAttribute("userSinhVien", sv.get());
            SinhVien sv1 = sv.get();
            
           
            if(sv1.getEmail().equals(email)) {
               
                String verificationCode = generateRandomCode();
                
        
                session.setAttribute("verificationCode", verificationCode);
                
                emailService.sendVerificationEmail(email, verificationCode);
                

                return "redirect:/verifycode";  
            }
  
        }
        
        var gv = giangVienService.forgotPasswordGiangVien(maso, email);
        if (gv.isPresent()) {
            session.setAttribute("userGiangVien", gv.get());
            GiangVien gv1 = gv.get();
            
           
            if(gv1.getEmail().equals(email)) {
               
                String verificationCode = generateRandomCode();
                
        
                session.setAttribute("verificationCode", verificationCode);
                
                emailService.sendVerificationEmail(email, verificationCode);
                

                return "redirect:/verifycode";  
            }
        }
        
        model.addAttribute("error", "Mã số hoặc email không đúng");
        return "redirect:/forgotpassword";  
    }
    

    
}
