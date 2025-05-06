package ntu.granduationproject.ntu.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.granduationproject.ntu.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByMssv(String mssv);
}