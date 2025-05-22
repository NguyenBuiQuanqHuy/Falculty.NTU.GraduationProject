package ntu.granduationproject.ntu.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ntu.granduationproject.ntu.models.DangKyDetai;

public interface DangKyDeTaiRepository extends JpaRepository<DangKyDetai, Integer> {

    List<DangKyDetai> findByMsdt_Msdt(int msdt);
    DangKyDetai findByMsdt_MsdtAndMssv_Mssv(int msdt, String mssv);
    int countByMsdt_MsdtAndTrangthai(int msdt, String trangthai);

    @Query("SELECT COUNT(d) FROM DangKyDetai d WHERE d.msdt.msgv.msgv = :msgv AND d.msdt.theLoai.matheloai = :matheloai AND d.trangthai = 'Đã duyệt'")
    int countByGiangVienAndTheLoai(@Param("msgv") String msgv, @Param("matheloai") int matheloai);

    List<DangKyDetai> findByMsdt_MsdtAndTrangthai(int msdt, String trangthai);
    List<DangKyDetai> findByMsdt_Msgv_MsgvAndTrangthaiAndMsdt_TheLoai_Matheloai(String msgv, String trangthai, int matheloai);
}
