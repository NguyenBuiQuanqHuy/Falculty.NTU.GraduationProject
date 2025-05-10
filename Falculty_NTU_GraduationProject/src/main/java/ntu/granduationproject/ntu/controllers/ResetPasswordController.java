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
import ntu.granduationproject.ntu.services.SinhVienService;

@Controller
@Configuration
@RequestMapping("/")
public class ResetPasswordController {
	
	
	public boolean isValidPassword(String password) {
	    if (password == null || password.length() < 6) {
	        return false;
	    }

	    boolean hasUppercase = false;
	    boolean hasDigit = false;
	    boolean hasSpecialChar = false;
	    boolean hasWhitespace = false;

	    for (char c : password.toCharArray()) {
	        if (Character.isUpperCase(c)) hasUppercase = true;
	        else if (Character.isDigit(c)) hasDigit = true;
	        else if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
	        else if (Character.isWhitespace(c)) hasWhitespace = true;
	    }

	    return hasUppercase && hasDigit && hasSpecialChar && !hasWhitespace;
	}
	
	@Autowired
    private SinhVienService sinhVienService;

  
   
    @PostMapping("/resetpassword")
    public String Resetpassword(@RequestParam String newPassword,@RequestParam String newPasswordConfirm, HttpSession session, Model model) {

    	{
    	    // Kiểm tra xác nhận mật khẩu
    	    if (!newPassword.equals(newPasswordConfirm)) {
    	        model.addAttribute("error", "Xác nhận mật khẩu không khớp.");
    	        return "views/ResetPassword";
    	    }

    	    // Kiểm tra mật khẩu theo yêu cầu
    	    if (!isValidPassword(newPassword)) {
    	        model.addAttribute("error", "Mật khẩu phải từ 6 kí tự, có ít nhất 1 chữ in hoa, 1 số, 1 kí tự đặc biệt và không chứa khoảng trắng.");
    	        return "views/ResetPassword";
    	    }

    	    // Lấy user từ session (đã được xác thực qua mã code)
    	    SinhVien sinhVien = (SinhVien) session.getAttribute("user");
    	    if (sinhVien == null) {
    	        model.addAttribute("error", "Phiên đã hết hạn. Vui lòng thực hiện lại.");
    	        return "views/ForgotPassword";
    	    }

    	    // Cập nhật mật khẩu đã mã hóa
    	    
    	    sinhVienService.resetPassword(sinhVien.getMssv(), newPassword);

    	    session.removeAttribute("user");
    	    model.addAttribute("message", "Mật khẩu đã được cập nhật thành công.");
    	    return "views/Login";
    	
        

    }

    }
}
