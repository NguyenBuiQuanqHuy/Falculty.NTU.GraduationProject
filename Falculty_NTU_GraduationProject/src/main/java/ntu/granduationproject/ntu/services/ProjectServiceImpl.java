package ntu.granduationproject.ntu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.repositories.GiangVienRepository;
import ntu.granduationproject.ntu.repositories.LinhVucRepository;
import ntu.granduationproject.ntu.repositories.NamHocRepository;
import ntu.granduationproject.ntu.repositories.ProjectRepository;
import ntu.granduationproject.ntu.repositories.TheLoaiRepository;
@Service
public class ProjectServiceImpl implements ProjectService{
	@Autowired
	GiangVienRepository giangVienRepository;
	@Autowired
	TheLoaiRepository theLoaiRepository;
	@Autowired
	LinhVucRepository linhVucRepository;
	@Autowired
	NamHocRepository namHocRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	@Override
	public void createProject(Project project) {
		// TODO Auto-generated method stub
		 project.setTrangthai("chưa duyệt");
		 project.setSosvtoida(0);
	     projectRepository.save(project);
	}

}
