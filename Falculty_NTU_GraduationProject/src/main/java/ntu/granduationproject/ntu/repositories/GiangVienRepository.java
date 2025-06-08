package ntu.granduationproject.ntu.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.GiangVien;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, String> {
	Optional<GiangVien> findByMsgvAndMatkhau(String msgv, String matkhau);
	
	// Tìm giảng viên
	Optional<GiangVien> findByMsgv(String msgv);
	@Query("SELECT gv FROM GiangVien gv WHERE " +
		       "(:msgv = '' OR gv.msgv LIKE %:msgv%) AND " +
		       "(:hotengv = '' OR gv.hoten LIKE %:hotengv%)")
		List<GiangVien> searchByMsgvAndHoten(@Param("msgv") String msgv, @Param("hotengv") String hotengv);


}
