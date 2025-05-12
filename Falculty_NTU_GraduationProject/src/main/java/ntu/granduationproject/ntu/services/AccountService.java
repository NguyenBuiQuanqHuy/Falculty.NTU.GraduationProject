package ntu.granduationproject.ntu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.repositories.SinhVienRepository;

@Service
public class AccountService {

  @Autowired
  private SinhVienRepository sinhVienRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void createAccount(String accountId, String hoten, String email, boolean isAdmin, String role) {
    String matkhau = "1234";

    // Kiểm tra nếu đã tồn tại, xóa trước (chỉ dùng khi đang test)
    if (sinhVienRepository.existsById(accountId)) {
      sinhVienRepository.deleteById(accountId);
    }

    // Tạo đối tượng SinhVien mới
    SinhVien sinhVien = new SinhVien(accountId, matkhau, hoten, email);

    // Lưu đối tượng vào cơ sở dữ liệu và đồng bộ hóa ngay lập tức
    sinhVienRepository.saveAndFlush(sinhVien);
  }
}
