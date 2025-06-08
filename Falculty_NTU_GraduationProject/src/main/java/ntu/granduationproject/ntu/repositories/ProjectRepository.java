package ntu.granduationproject.ntu.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.Project;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    // Lấy toàn bộ đề tài với tất cả quan hệ
    @Query("SELECT p FROM Project p " +
           "JOIN FETCH p.msgv " +
           "JOIN FETCH p.theLoai " +
           "JOIN FETCH p.linhVuc " +
           "JOIN FETCH p.namHoc")
    List<Project> findAllWithRelations();
    
    List<Project> findByTrangthai(String trangthai);
    
 // Tìm kiếm cho chức năng duyệt đề tài của trưởng khoa
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
    
    // Tìm kiếm đề tài đã được duyệt theo mã giảng viên
	@Query("SELECT p FROM Project p WHERE " +
			"(:namhoc IS NULL OR p.namHoc.manamhoc = :namhoc) AND " +
			"(:theloai IS NULL OR p.theLoai.matheloai = :theloai) AND " +
			"(:linhvuc IS NULL OR p.linhVuc.malinhvuc = :linhvuc) AND " +
			"(:tendt IS NULL OR LOWER(p.tendt) LIKE LOWER(CONCAT('%', :tendt, '%'))) AND " +
			"(:msgv IS NULL OR p.msgv.msgv = :msgv) AND " + 
			"(:trangthai IS NULL OR LOWER(p.trangthai) = LOWER(:trangthai)) " +
			"ORDER BY p.msdt DESC")
	List<Project> searchProjects(
			@Param("msgv") String msgv,
			@Param("namhoc") Integer namhoc,
			@Param("theloai") Integer theloai,
			@Param("linhvuc") Integer linhvuc,
			@Param("tendt") String tendt,
			@Param("trangthai") String trangthai);
	
	List<Project> findByMsgv_Msgv(String msgv);
	
	// Hiện toàn bộ đề tài bao gồm cả đề tài chưa duyệt 
	@Query("SELECT p FROM Project p " +
			"JOIN p.msgv gv " +
			"JOIN p.theLoai tl " +
			"JOIN p.linhVuc lv " +
			"JOIN p.namHoc nh " +
			"WHERE (:namhoc IS NULL OR nh.manamhoc = :namhoc) " +
			"AND (:linhvuc IS NULL OR lv.malinhvuc = :linhvuc) " +
			"AND (:loai IS NULL OR tl.matheloai = :loai) " +
			"AND (:truongkhoa IS NULL OR LOWER(gv.hoten) LIKE LOWER(CONCAT('%', :truongkhoa, '%'))) " +
			"ORDER BY CASE WHEN p.trangthai = 'Chưa duyệt' THEN 0 ELSE 1 END, p.msdt DESC")
	Page<Project> searchByCriteriaPaged(
			@Param("namhoc") String namhoc,
			@Param("linhvuc") String linhvuc,
			@Param("loai") String loai,
			@Param("truongkhoa") String truongkhoa,
			Pageable pageable);
	
	@Query("SELECT p FROM Project p " +
			"JOIN FETCH p.msgv gv " +
			"JOIN FETCH p.theLoai tl " +
			"JOIN FETCH p.linhVuc lv " +
			"JOIN FETCH p.namHoc nh " +
			"ORDER BY CASE WHEN p.trangthai = 'Chưa duyệt' THEN 0 ELSE 1 END, p.msdt DESC")
	Page<Project> findAllOrderByTrangthai(Pageable pageable);
	
	@Query("SELECT p FROM Project p " +
			"WHERE (:tendt IS NULL OR LOWER(p.tendt) LIKE LOWER(CONCAT('%', :tendt, '%'))) " +
			"AND (:namhoc IS NULL OR p.namHoc.manamhoc = :namhoc) " +
			"AND (:theloai IS NULL OR p.theLoai.matheloai = :theloai) " +
			"AND (:linhvuc IS NULL OR p.linhVuc.malinhvuc = :linhvuc) " +
			"AND (:trangthai IS NULL OR p.trangthai = :trangthai)")
	Page<Project> searchWithFiltersPaged(@Param("tendt") String tendt,
										 @Param("namhoc") Integer namhoc,
										 @Param("theloai") Integer theloai,
										 @Param("linhvuc") Integer linhvuc,
										 @Param("trangthai") String trangthai,
										 Pageable pageable);

	// Tìm kiếm giảng viên
	@Query("SELECT p FROM Project p WHERE " +
		       "(:namhoc IS NULL OR p.namHoc.manamhoc = :namhoc) AND " +
		       "(:theloai IS NULL OR p.theLoai.matheloai = :theloai) AND " +
		       "(:linhvuc IS NULL OR p.linhVuc.malinhvuc = :linhvuc) AND " +
		       "(:tendt IS NULL OR LOWER(p.tendt) LIKE LOWER(CONCAT('%', :tendt, '%'))) AND " +
		       "(:msgv IS NULL OR p.msgv.msgv = :msgv) AND " +
		       "(:cosv IS NULL OR p.cosvthuchien = :cosv) " +
		       "ORDER BY p.msdt DESC")
	List<Project> filterProjectsByConditions(
		        @Param("msgv") String msgv,
		        @Param("namhoc") Integer namhoc,
		        @Param("theloai") Integer theloai,
		        @Param("linhvuc") Integer linhvuc,
		        @Param("tendt") String tendt,
		        @Param("cosv") Boolean cosv);

	// Tìm kiếm sinh viên
	@Query("SELECT p FROM Project p WHERE " +
		       "(:namhoc IS NULL OR p.namHoc.manamhoc = :namhoc) AND " +
		       "(:theloai IS NULL OR p.theLoai.matheloai = :theloai) AND " +
		       "(:linhvuc IS NULL OR p.linhVuc.malinhvuc = :linhvuc) AND " +
		       "(:tendt IS NULL OR LOWER(p.tendt) LIKE LOWER(CONCAT('%', :tendt, '%'))) AND " +
		       "(:cosv IS NULL OR p.cosvthuchien = :cosv) " +
		       "ORDER BY p.msdt DESC")
		List<Project> allProjectsByConditions(
		        @Param("namhoc") Integer namhoc,
		        @Param("theloai") Integer theloai,
		        @Param("linhvuc") Integer linhvuc,
		        @Param("tendt") String tendt,
		        @Param("cosv") Boolean cosv);

}
