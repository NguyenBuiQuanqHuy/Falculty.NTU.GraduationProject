package ntu.granduationproject.ntu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.LinhVuc;

@Repository
public interface LinhVucRepository extends JpaRepository<LinhVuc, Integer>{

}
