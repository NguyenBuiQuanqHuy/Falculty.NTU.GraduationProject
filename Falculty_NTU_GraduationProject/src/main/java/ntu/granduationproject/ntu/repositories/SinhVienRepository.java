package ntu.granduationproject.ntu.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.SinhVien;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
	Optional<SinhVien> findByMssvAndMatkhau(String mssv, String matkhau);
}