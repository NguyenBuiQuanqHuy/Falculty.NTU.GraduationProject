package ntu.granduationproject.ntu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.granduationproject.ntu.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public boolean Login(String mssv, String matkhau) {
    return userRepository.findByMssv(mssv)
        .map(ten -> ten.getMatkhau().equals(matkhau))
        .orElse(false);
  }
}