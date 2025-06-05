package ntu.granduationproject.ntu.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
		project.setTrangthai("Chưa duyệt");
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

	public List<Project> getDetaiFromDatabase() {
		return projectRepository.findAll();
	}

	@Override
	public List<Project> findProjectsByGiangVien(String msgv) {
		return projectRepository.findByMsgv_Msgv(msgv);
	}

	@Override
	public Page<Project> getPagedProjects(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return projectRepository.findAll(pageable);
	}

	@Override
	public Page<Project> searchProjectsPaged(String maNamHoc, String maLinhVuc, String maTheLoai, String truongkhoa, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return projectRepository.searchByCriteriaPaged(maNamHoc, maLinhVuc, maTheLoai, truongkhoa, pageable);
	}
	@Override
	public Page<Project> getPagedProjects(Pageable pageable) {
		return projectRepository.findAllOrderByTrangthai(pageable);
	}

	@Override
	public Page<Project> searchProjectsPaged(String tendt, Integer namhoc, Integer theloai, Integer linhvuc, String trangthai, Pageable pageable) {
		return projectRepository.searchWithFiltersPaged(tendt, namhoc, theloai, linhvuc, trangthai, pageable);
	}

	@Override
	public List<Project> filterProjectsByConditions(String msgv, Integer namhoc, Integer theloai, Integer linhvuc,
			String tendt, Boolean cosvthuchien) {
		// TODO Auto-generated method stub
		return projectRepository.filterProjectsByConditions(msgv, namhoc, theloai, linhvuc, tendt, cosvthuchien);
	}

	
}

