package ntu.granduationproject.ntu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {
	@GetMapping("/giangvien/taodetai")
	public String detai() {
		return "views/giangvien/createproject";
	}
}
