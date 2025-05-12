package ntu.granduationproject.ntu.repositories;

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
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("DELETE FROM SinhVien s WHERE s.mssv = :id")
	void deleteImmediatelyById(@Param("id") String id);
	
}
