package ntu.granduationproject.ntu.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.services.ProjectService;

@Controller
public class ApprvalSVController {
	@Autowired
	ProjectService projectService;
	
	@GetMapping("/danhsachdetai")
	private String danhsachdetai(ModelMap model) {
		List<Project> projects=projectService.getAllProjects();
		model.addAttribute("projects", projects);
		return "views/giangvien/approvalSV";
	}
}
