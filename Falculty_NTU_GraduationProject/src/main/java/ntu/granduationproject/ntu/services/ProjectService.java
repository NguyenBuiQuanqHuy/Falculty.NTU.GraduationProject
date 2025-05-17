package ntu.granduationproject.ntu.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ntu.granduationproject.ntu.models.Project;
@Service
public interface ProjectService {
	void createProject(Project project);
	List<Project> getAllProjects();
	public List<Project> searchProjects(Integer namhoc, Integer theloai, Integer linhvuc, String tendt);

}
