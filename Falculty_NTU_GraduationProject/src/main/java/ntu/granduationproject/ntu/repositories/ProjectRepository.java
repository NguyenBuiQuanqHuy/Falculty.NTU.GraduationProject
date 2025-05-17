package ntu.granduationproject.ntu.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ntu.granduationproject.ntu.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
	@Query("SELECT p FROM Project p " +
		       "WHERE (:namhoc IS NULL OR p.namHoc.manamhoc = :namhoc) " +
		       "AND (:theloai IS NULL OR p.theLoai.matheloai = :theloai) " +
		       "AND (:linhvuc IS NULL OR p.linhVuc.malinhvuc = :linhvuc) " +
		       "AND (:tendt IS NULL OR LOWER(p.tendt) LIKE LOWER(CONCAT('%', :tendt, '%')))")
		List<Project> searchProjects(@Param("namhoc") Integer namhoc,
		                             @Param("theloai") Integer theloai,
		                             @Param("linhvuc") Integer linhvuc,
		                             @Param("tendt") String tendt);
}
