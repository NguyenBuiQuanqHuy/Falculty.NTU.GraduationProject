package ntu.granduationproject.ntu.services;

import java.util.Optional;

import ntu.granduationproject.ntu.models.GiangVien;
public interface GiangVienService {
	Optional<GiangVien> loginGiangVien(String maso, String matkhau);
}
