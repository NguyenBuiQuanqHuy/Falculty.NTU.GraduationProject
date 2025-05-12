package ntu.granduationproject.ntu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.services.ProjectService;

@Controller
public class ProjectController {
	@Autowired
	ProjectService projectService;
	
	@PostMapping("/giangvien/taodetai")
	public String taodetai( @RequestParam String msdt, 
	        @RequestParam String tendt, 
	        @RequestParam String msgv, 
	        @RequestParam int theloai, 
	        @RequestParam int linhvuc, 
	        @RequestParam int namhoc, 
	        @RequestParam String mota, 
	        @RequestParam String noidung, 
	        @RequestParam int sosvtoida, 
	        @RequestParam int khoasv,
	        HttpSession session,
	        RedirectAttributes redirectAttributes) {
		
		Object userObj = session.getAttribute("user");
	    if (userObj == null || !(userObj instanceof ntu.granduationproject.ntu.models.GiangVien)) {
	        redirectAttributes.addFlashAttribute("error", "Bạn cần đăng nhập bằng tài khoản giảng viên.");
	        return "redirect:/login";
	    }


	    return "redirect:/project/list";
	}
	
	
	@GetMapping("/giangvien/taodetai")
	public String detai() {
		return "views/giangvien/createproject";
	}
}
