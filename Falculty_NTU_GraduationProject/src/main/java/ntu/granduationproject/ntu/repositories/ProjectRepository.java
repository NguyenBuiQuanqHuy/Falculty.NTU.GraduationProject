package ntu.granduationproject.ntu.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ntu.granduationproject.ntu.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	
	@Query("SELECT p FROM Project p WHERE " +
		       "(:namhoc IS NULL OR p.namHoc.tennamhoc = :namhoc) AND " +
		       "(:theloai IS NULL OR p.theLoai.id = :theloai) AND " +
		       "(:linhvuc IS NULL OR p.linhVuc.id = :linhvuc) AND " +
		       "(:tendt IS NULL OR LOWER(p.tendt) LIKE LOWER(CONCAT('%', :tendt, '%'))) AND " +
		       "p.msgv.id = :giangVienId AND " +
		       "p.trangthai = :trangthai")
		List<Project> searchProjects(
		        @Param("giangVienId") String msgv,
		        @Param("namhoc") Integer namhoc,
		        @Param("theloai") Integer theloai,
		        @Param("linhvuc") Integer linhvuc,
		        @Param("tendt") String tendt,
		        @Param("trangthai") String trangthai);

}
