package ntu.granduationproject.ntu.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.Project;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    
    @Query("SELECT p FROM Project p " +
           "JOIN FETCH p.msgv " +
           "JOIN FETCH p.theLoai " +
           "JOIN FETCH p.linhVuc " +
           "JOIN FETCH p.namHoc")
    List<Project> findAllWithRelations();
    
    List<Project> findByTrangthai(String trangthai);

    @Query("SELECT p FROM Project p " +
 	       "JOIN FETCH p.msgv gv " +
 	       "JOIN FETCH p.theLoai tl " +
 	       "JOIN FETCH p.linhVuc lv " +
 	       "JOIN FETCH p.namHoc nh " +
 	       "WHERE (:namhoc IS NULL OR nh.manamhoc = :namhoc) " +
 	       "AND (:linhvuc IS NULL OR lv.malinhvuc = :linhvuc) " +
 	       "AND (:loai IS NULL OR tl.matheloai = :loai) " +
 	       "AND (:truongkhoa IS NULL OR LOWER(gv.hoten) LIKE LOWER(CONCAT('%', :truongkhoa, '%')))")
 	List<Project> searchByCriteria(@Param("namhoc") String namhoc,
 	                               @Param("linhvuc") String linhvuc,
 	                               @Param("loai") String loai,
 	                               @Param("truongkhoa") String truongkhoa);
	@Query("SELECT p FROM Project p WHERE " +
		       "(:namhoc IS NULL OR p.namHoc.tennamhoc = :namhoc) AND " +
		       "(:theloai IS NULL OR p.theLoai.id = :theloai) AND " +
		       "(:linhvuc IS NULL OR p.linhVuc.id = :linhvuc) AND " +
		       "(:tendt IS NULL OR LOWER(p.tendt) LIKE LOWER(CONCAT('%', :tendt, '%'))) AND " +
		       "p.msgv.id = :giangVienId AND " +
		       "p.trangthai = :trangthai " +
		       "ORDER BY p.id DESC")
		List<Project> searchProjects(
		        @Param("giangVienId") String msgv,
		        @Param("namhoc") Integer namhoc,
		        @Param("theloai") Integer theloai,
		        @Param("linhvuc") Integer linhvuc,
		        @Param("tendt") String tendt,
		        @Param("trangthai") String trangthai);
}
