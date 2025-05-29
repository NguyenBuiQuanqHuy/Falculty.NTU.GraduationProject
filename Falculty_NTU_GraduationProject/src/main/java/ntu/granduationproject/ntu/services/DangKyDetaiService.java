package ntu.granduationproject.ntu.services;
import ch.qos.logback.core.status.NopStatusListener;
import ntu.granduationproject.ntu.models.DangKyDetai;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.repositories.DangKyDeTaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DangKyDetaiService {
    @Autowired
    private DangKyDeTaiRepository dangKyDeTaiRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SinhVienService sinhVienService;

    @Transactional
    public boolean registeredDetai(String mssv, int msdt) {
        System.out.println("Bắt đầu đăng ký với mssv: " + mssv + " - msdt: " + msdt);

        if (dangKyDeTaiRepository.existsByMssv_Mssv(mssv)) {
            System.out.println("SV đã đăng ký trước đó");
            return false;
        }

        Project project = projectService.findByMsdt(msdt);
        if (project == null) {
            System.out.println("Không tìm thấy đề tài");
            return false;
        }

        int countRegistered = dangKyDeTaiRepository.countByMsdt_MsdtAndTrangthai(msdt,"đã duyệt");
        System.out.println("Số SV đã đăng ký: " + countRegistered + "/" + project.getSosvtoida());

        if (countRegistered >= project.getSosvtoida()) {
            System.out.println("Đề tài đã đủ số lượng");
            return false;
        }
        
        // Kiểm tra hạn mức giảng viên theo thể loại
        int theLoai = project.getTheLoai().getMatheloai(); // ví dụ: 1 = ĐA, 2 = CĐ
        int hanMuc = (theLoai == 1) ? project.getMsgv().getHMHDDA() : project.getMsgv().getHMHDCD();
        int approvedCountForGV = dangKyDeTaiRepository.countByGiangVienAndTheLoai(
            project.getMsgv().getMsgv(), theLoai
        );

        System.out.println("Số SV đã duyệt của GV: " + approvedCountForGV + "/" + hanMuc);
        if (approvedCountForGV >= hanMuc) {
            System.out.println("Giảng viên đã vượt hạn mức duyệt đề tài");
            return false;
        }


        SinhVien sv = sinhVienService.findByMssv(mssv);
        if (sv == null) {
            System.out.println("Không tìm thấy sinh viên");
            return false;
        }

        DangKyDetai dk = new DangKyDetai();
        dk.setMssv(sv);
        dk.setMsdt(project);
        dk.setTrangthai("Chưa duyệt");

        System.out.println("Lưu đăng ký vào DB");
        dangKyDeTaiRepository.save(dk);
        return true;
    }

    public boolean cancelDangKy(String mssv, int msdt) {
        Optional<DangKyDetai> opt = dangKyDeTaiRepository.findByMssv_Mssv(mssv);
        if (opt.isPresent()) {
            DangKyDetai dk = opt.get();
            if (dk.getMsdt().getMsdt() == msdt) {
                dangKyDeTaiRepository.delete(dk);
                return true;
            }
        }
        return false;
    }
}
