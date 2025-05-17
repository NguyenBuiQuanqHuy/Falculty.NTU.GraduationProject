package ntu.granduationproject.ntu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListDTController {
	@GetMapping("/danhsachdetai")
	private String danhsachdetai() {
		return "views/giangvien/approvalSV";
	}
}
