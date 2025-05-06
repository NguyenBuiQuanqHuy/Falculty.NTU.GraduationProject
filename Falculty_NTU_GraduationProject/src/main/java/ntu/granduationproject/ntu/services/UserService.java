package ntu.granduationproject.ntu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.granduationproject.ntu.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public boolean Login(String tendn, String matkhau) {
    return userRepository.findByTendn(tendn)
        .map(ten -> ten.getMatkhau().equals(matkhau))
        .orElse(false);
  }
}