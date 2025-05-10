package ntu.granduationproject.ntu.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.repositories.GiangVienRepository;

@Service
public class GiangVienServiceImpl implements GiangVienService {
	 @Autowired
	    private GiangVienRepository giangVienRepository;

	    @Override
	    public Optional<GiangVien> loginGiangVien(String maso, String matkhau) {
	        return giangVienRepository.findByMsgvAndMatkhau(maso, matkhau);
	    }

}
