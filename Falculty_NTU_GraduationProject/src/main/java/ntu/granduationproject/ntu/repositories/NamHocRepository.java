package ntu.granduationproject.ntu.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.NamHoc;
@Repository
public interface NamHocRepository extends JpaRepository<NamHoc, Integer>{

	Optional<NamHoc> findByTennamhoc(int tennamhoc);

}
