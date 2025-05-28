package ntu.granduationproject.ntu.services;

import java.util.List;
import java.util.Optional;

import ntu.granduationproject.ntu.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

	void createProject(Project project);
	List<Project> getAllProjects();
	List<Project> getProjects();
	public List<Project> searchProjects(String msgv, Integer namhoc, Integer theloai, Integer linhvuc, String tendt, String trangthai);
	
	public Project findByMsdt(int msdt);

    List<Project> getProjectsByTrangThai(String trangThai);
    Optional<Project> findById(int msdt);
    List<Project> searchProjects(String maNamHoc, String maLinhVuc, String maTheLoai, String tenGiangVien);


    Project getProjectById(int msdt);
    void save(Project project);
    
    void deleteById(int msdt);


    List<Project> getDetaiFromDatabase();

    List<Project> findProjectsByGiangVien(String msgv);

    Page<Project> getPagedProjects(int page, int size);

    Page<Project> searchProjectsPaged(String maNamHoc, String maLinhVuc, String maTheLoai, String truongkhoa, int page, int size);

    Page<Project> getPagedProjects(Pageable pageable);

    Page<Project> searchProjectsPaged(String tendt, Integer namhoc, Integer theloai, Integer linhvuc, String trangthai, Pageable pageable);

}
    
    

