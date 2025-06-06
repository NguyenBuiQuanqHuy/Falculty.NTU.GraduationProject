package ntu.granduationproject.ntu.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.SinhVien;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
	Optional<SinhVien> findByMssvAndMatkhau(String mssv, String matkhau);
	Optional<SinhVien> findByMssv(String mssv);
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("DELETE FROM SinhVien s WHERE s.mssv = :id")
	void deleteImmediatelyById(@Param("id") String id);
	@Query("SELECT sv FROM SinhVien sv " +
	           "WHERE (:mssv IS NULL OR :mssv = '' OR sv.mssv = :mssv) " +
	           "AND (:hoten IS NULL OR :hoten = '' OR LOWER(sv.hoten) LIKE LOWER(CONCAT('%', :hoten, '%')))")
	    List<SinhVien> searchByMssvAndHoten(@Param("mssv") String mssv, @Param("hoten") String hoten);
}
