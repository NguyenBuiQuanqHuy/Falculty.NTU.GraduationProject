package ntu.granduationproject.ntu.services;

import java.util.Optional;

import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.SinhVien;

public interface GiangVienService {
	Optional<GiangVien> loginGiangVien(String maso, String matkhau);
	
	void resetPassword(String msgv, String newPassword);
	
	Optional<GiangVien> forgotPasswordGiangVien(String maso, String email);
	
	void AddGiangVien (GiangVien giangvien);

	GiangVien findByMsgv(String id);
}
