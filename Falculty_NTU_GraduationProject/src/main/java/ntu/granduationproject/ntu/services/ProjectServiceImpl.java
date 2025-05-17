package ntu.granduationproject.ntu.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.LinhVuc;
import ntu.granduationproject.ntu.models.NamHoc;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.models.TheLoai;
import ntu.granduationproject.ntu.repositories.GiangVienRepository;
import ntu.granduationproject.ntu.repositories.LinhVucRepository;
import ntu.granduationproject.ntu.repositories.NamHocRepository;
import ntu.granduationproject.ntu.repositories.ProjectRepository;
import ntu.granduationproject.ntu.repositories.TheLoaiRepository;
@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	ProjectRepository projectRepository;
	@Override
	public void createProject(Project project) {
		// TODO Auto-generated method stub
		 project.setTrangthai("chưa duyệt");
		 project.setCosvthuchien(false);
	     projectRepository.save(project);
	     
	}
	@Override
	public List<Project> getAllProjects() {
		// TODO Auto-generated method stub
		return projectRepository.findAll();
	}
	
}
