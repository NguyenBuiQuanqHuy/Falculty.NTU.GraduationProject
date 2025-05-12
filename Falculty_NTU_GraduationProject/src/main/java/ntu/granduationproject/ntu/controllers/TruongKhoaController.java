package ntu.granduationproject.ntu.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TruongKhoaController {
	@GetMapping("/truongkhoa/home")
	public String adminHome(HttpSession session) {
	    if ("admin".equals(session.getAttribute("role"))) {
	        return "views/truongkhoa/Home"; // KHÔNG có .html
	    }
	    return "redirect:/login?unauthorized=true";
	}

    @GetMapping("/createaccount")
    public String createAccount() {
    	return "views/truongkhoa/CreateAccount";
    }
}

