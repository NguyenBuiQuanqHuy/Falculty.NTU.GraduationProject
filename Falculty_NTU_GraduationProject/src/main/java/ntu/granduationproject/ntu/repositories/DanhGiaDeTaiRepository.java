package ntu.granduationproject.ntu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.DanhGiaDeTai;

@Repository
public interface DanhGiaDeTaiRepository extends JpaRepository<DanhGiaDeTai, Integer>{

}
