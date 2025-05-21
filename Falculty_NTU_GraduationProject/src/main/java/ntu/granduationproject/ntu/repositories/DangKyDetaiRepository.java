package ntu.granduationproject.ntu.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.DangKyDetai;
import ntu.granduationproject.ntu.models.Project;

@Repository
public interface DangKyDetaiRepository extends JpaRepository<DangKyDetai, Integer> {

    List<DangKyDetai> findByMsdt(Project msdt);
}
