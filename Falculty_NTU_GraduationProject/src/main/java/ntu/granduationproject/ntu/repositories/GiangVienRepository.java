package ntu.granduationproject.ntu.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.GiangVien;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, String> {
	Optional<GiangVien> findByMsgvAndMatkhau(String msgv, String matkhau);

	Optional<GiangVien> findByMsgv(String msgv);

}
