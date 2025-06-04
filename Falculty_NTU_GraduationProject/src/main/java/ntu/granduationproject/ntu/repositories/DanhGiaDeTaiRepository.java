package ntu.granduationproject.ntu.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.DanhGiaDeTai;
import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.Project;

@Repository
public interface DanhGiaDeTaiRepository extends JpaRepository<DanhGiaDeTai, Integer>{
	Optional<DanhGiaDeTai> findByMsgvAndMsdt(GiangVien msgv, Project msdt);

}
