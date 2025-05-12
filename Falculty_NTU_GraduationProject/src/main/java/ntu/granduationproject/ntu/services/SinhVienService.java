package ntu.granduationproject.ntu.services;

import java.util.Optional;

import ntu.granduationproject.ntu.models.SinhVien;

public interface SinhVienService {
	Optional<SinhVien> loginSinhVien(String maso, String matkhau);
	void resetPassword(String mssv, String newPassword);
	
	Optional<SinhVien> forgotPasswordSinhVien(String maso, String email);
	void AddSinhVien(SinhVien sinhvien);
	

}
