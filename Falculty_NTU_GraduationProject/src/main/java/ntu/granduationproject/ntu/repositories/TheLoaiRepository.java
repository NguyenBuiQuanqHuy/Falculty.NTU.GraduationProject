package ntu.granduationproject.ntu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.TheLoai;

@Repository
public interface TheLoaiRepository extends JpaRepository<TheLoai, Integer>{

}
