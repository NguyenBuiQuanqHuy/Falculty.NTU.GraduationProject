package ntu.granduationproject.ntu.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ntu.granduationproject.ntu.models.DangKyDetai;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.repositories.DangKyDeTaiRepository;
import ntu.granduationproject.ntu.repositories.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	DangKyDeTaiRepository dangKyDetaiRepository;

	@Autowired
	private EmailService emailService;
	
	
	
	
	@Override
	public void createProject(Project project) {
		project.setTrangthai("chưa duyệt");
		project.setCosvthuchien(false);
	    projectRepository.save(project);
	}

	@Override
	public List<Project> getAllProjects() {
		 return projectRepository.findAll(Sort.by(Sort.Direction.DESC, "msdt"));	
    }
	
	@Override
    public List<Project> getProjectsByTrangThai(String trangThai) {
        return projectRepository.findByTrangthai(trangThai);
    }
	 
	@Override
	public Optional<Project> findById(int msdt) {
	    return projectRepository.findById(msdt);
	}
	 
	@Override
	public List<Project> searchProjects(String maNamHoc, String maLinhVuc, String maTheLoai, String tenGiangVien) {
	    return projectRepository.searchByCriteria(maNamHoc, maLinhVuc, maTheLoai, tenGiangVien);
	}

	
	@Override
	public List<Project> searchProjects(String msgv, Integer namhoc, Integer theloai, Integer linhvuc, String tendt,
			String trangthai) {
		// TODO Auto-generated method stub
		return projectRepository.searchProjects(msgv, namhoc, theloai, linhvuc, tendt, trangthai);
	}
	@Override
	public Project findByMsdt(int msdt) {
		// TODO Auto-generated method stub
		Optional<Project> optionalProject = projectRepository.findById(msdt);
        return optionalProject.orElse(null);
	}
	

	@Override
	@Transactional(readOnly = true)
	public Project getProjectById(int msdt) {
	    Project project = projectRepository.findById(msdt).orElse(null);
	    if (project != null && project.getMsgv() != null) {
	        project.getMsgv().getEmail(); // Load dữ liệu lazy
	    }
	    return project;
	}


	    @Override
	    public void save(Project project) {
	        projectRepository.save(project);
	    }
	    

		@Override
		public void deleteById(int msdt) {
		    projectRepository.deleteById(msdt);
		}

		@Override
		public List<Project> getProjects() {
			// TODO Auto-generated method stub
			return projectRepository.findAll();
		}	
}

