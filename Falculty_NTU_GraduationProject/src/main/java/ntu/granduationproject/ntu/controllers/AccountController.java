package ntu.granduationproject.ntu.controllers;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.services.AccountService;
import ntu.granduationproject.ntu.services.EmailService;
import ntu.granduationproject.ntu.services.GiangVienService;
import ntu.granduationproject.ntu.services.SinhVienService;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SinhVienService sinhVienService;
    
    @Autowired
    private GiangVienService giangVienService;
    
	@Autowired
    private PasswordEncoder passwordEncoder;
	
    @Autowired
    private EmailService emailService;
    
	private String generateRandomPassword() {
	    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    int length = 6;
	    SecureRandom random = new SecureRandom();
	    StringBuilder sb = new StringBuilder(length);
	    for (int i = 0; i < length; i++) {
	        int index = random.nextInt(characters.length());
	        sb.append(characters.charAt(index));
	    }
	    return sb.toString();
	}

	
    @PostMapping("/createaccount")
    public String createaccount (@RequestParam("maso") String maso,
                                 @RequestParam("hoten") String hoten,
                                 @RequestParam("email") String email,
                                 @RequestParam("role") String role,
                                 @RequestParam(value = "hmhdda", required = false) Integer hmhdda,
                                 @RequestParam(value = "hmhdcd", required = false) Integer hmhdcd,
                                 Model model) {
    	String matkhau = generateRandomPassword();
    	emailService.sendNewPassword(email, matkhau);
    	String hashedPassword = passwordEncoder.encode(matkhau);
    	if ("sinhvien".equals(role)) {


            SinhVien sv = new  SinhVien(maso, hashedPassword, email, hoten);
            
            sinhVienService.AddSinhVien(sv);
    	}
    	if ("giangvien".equals(role)) {
    		
    		// Tránh null, fallback nếu không nhập
            int da = (hmhdda != null) ? hmhdda : 0;
            int cd = (hmhdcd != null) ? hmhdcd : 0;
            GiangVien gv = new GiangVien(maso, hashedPassword, email , da,cd , "123456789" , hoten);
            
            giangVienService.AddGiangVien(gv);
    	}


        
        
        model.addAttribute("message", "Tạo tài khoản thành công!");
        return "views/truongkhoa/Home";
    }

}
