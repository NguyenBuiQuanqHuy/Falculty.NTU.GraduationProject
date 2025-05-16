package ntu.granduationproject.ntu.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ntu.granduationproject.ntu.models.NamHoc;

public interface NamHocRepository extends JpaRepository<NamHoc, Integer>{

	Optional<NamHoc> findByTennamhoc(int tennamhoc);

}
