package ntu.granduationproject.ntu.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ntu.granduationproject.ntu.models.DangKyDetai;

public interface DangKyDeTaiRepository extends JpaRepository<DangKyDetai, Integer> {

    List<DangKyDetai> findByMsdt_Msdt(int msdt);

    int countByMsdt_MsdtAndTrangthai(int msdt, String trangthai);

    @Query("SELECT COUNT(d) FROM DangKyDetai d WHERE d.msdt.msgv.msgv = :msgv AND d.msdt.theLoai.matheloai = :matheloai AND d.trangthai = 'Đã duyệt'")
    int countByGiangVienAndTheLoai(@Param("msgv") String msgv, @Param("matheloai") int matheloai);

    List<DangKyDetai> findByMsdt_MsdtAndTrangthai(int msdt, String trangthai);

    boolean existsByMssv_Mssv(String mssv);

    List<DangKyDetai> findByMsdt_Msgv_MsgvAndTrangthaiAndMsdt_TheLoai_Matheloai(String msgv, String trangthai, int matheloai);

    Optional<DangKyDetai> findByMssv_Mssv(String mssv);
    DangKyDetai findByMsdt_MsdtAndMssv_Mssv(int msdt, String mssv);

    int countByMsdt_Msdt(int msdt);

    @Query("SELECT COUNT(dk) FROM DangKyDetai dk WHERE dk.msdt.msdt = :msdt AND dk.trangthai = 'Đã duyệt'")
    int countApprovedByMsdt(@Param("msdt") int msdt);
}
