package ntu.granduationproject.ntu.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ntu.granduationproject.ntu.models.Project;

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
}
