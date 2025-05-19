package ntu.granduationproject.ntu.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ntu.granduationproject.ntu.models.DangKyDetai;

public interface DangKyDeTaiRepository extends JpaRepository<DangKyDetai, Integer>{

	List<DangKyDetai> findByMsdt_Msdt(int msdt);
	DangKyDetai findByMsdt_MsdtAndMssv_Mssv(int msdt, String mssv);
	int countByMsdt_MsdtAndTrangthai(int msdt, String trangthai);
}
