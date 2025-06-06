package ntu.granduationproject.ntu.controllers;
import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.repositories.DangKyDeTaiRepository;
import ntu.granduationproject.ntu.repositories.LinhVucRepository;
import ntu.granduationproject.ntu.repositories.NamHocRepository;
import ntu.granduationproject.ntu.repositories.TheLoaiRepository;
import ntu.granduationproject.ntu.services.DangKyDetaiService;
import ntu.granduationproject.ntu.services.ProjectService;
import ntu.granduationproject.ntu.services.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DangKyDetaiController {
    @Autowired
    private DangKyDetaiService dangKyDetaiService;

    @Autowired
    private DangKyDeTaiRepository dangKyDeTaiRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TheLoaiRepository theLoaiRepository;
    @Autowired
    private LinhVucRepository linhVucRepository;
    @Autowired
    private NamHocRepository namHocRepository;

    @GetMapping("/sinhvien/dangkydetai")
    public String ListTopic(
            @RequestParam(value = "tendt", required = false) String tendt,
            @RequestParam(value = "namhoc", required = false) Integer namhoc,
            @RequestParam(value = "theloai", required = false) Integer theloai,
            @RequestParam(value = "linhvuc", required = false) Integer linhvuc,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model, HttpSession session, RedirectAttributes redirectAttrs) {

        String mssv = (String) session.getAttribute("maso");
        if (mssv == null) {
            redirectAttrs.addFlashAttribute("error", "Bạn chưa đăng nhập");
            return "redirect:/login";
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("msdt").descending());
        Page<Project> pageProjects = projectService.searchProjectsPaged(tendt, namhoc, theloai, linhvuc, "Đã duyệt", pageable);
        List<Project> dsFiltered = pageProjects.getContent();

        model.addAttribute("dsdetai", dsFiltered);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageProjects.getTotalPages());
        model.addAttribute("totalItems", pageProjects.getTotalElements());

        // Đếm số sinh viên đã đăng ký và được duyệt cho từng đề tài
        Map<Integer, Integer> mapCountRegistered = new HashMap<>();
        Map<Integer, Integer> mapCountApproved = new HashMap<>();

        for (Project project : dsFiltered) {
            int msdt = project.getMsdt();
            int count = dangKyDeTaiRepository.countByMsdt_Msdt(msdt);
            int approved = dangKyDeTaiRepository.countApprovedByMsdt(msdt);
            mapCountRegistered.put(msdt, count);
            mapCountApproved.put(msdt, approved);
        }

        model.addAttribute("countRegistered", mapCountRegistered);
        model.addAttribute("countApproved", mapCountApproved);

        dangKyDeTaiRepository.findByMssv_Mssv(mssv).ifPresent(dk -> model.addAttribute("dangKy", dk));

        model.addAttribute("namhocs", namHocRepository.findAll());
        model.addAttribute("theloais", theLoaiRepository.findAll());
        model.addAttribute("linhvucs", linhVucRepository.findAll());

        // Giữ lại filter
        model.addAttribute("tendt", tendt);
        model.addAttribute("selectedNamHoc", namhoc);
        model.addAttribute("selectedTheLoai", theloai);
        model.addAttribute("selectedLinhVuc", linhvuc);
        
        Map<Integer, Boolean> canRegisterMap = new HashMap<>();
        for (Project project : dsFiltered) {
            int msdt = project.getMsdt();
            boolean canRegister = true;

            int countRegistered = dangKyDeTaiRepository.countByMsdt_MsdtAndTrangthai(msdt, "đã duyệt");
            if (countRegistered >= project.getSosvtoida()) {
                canRegister = false; // đề tài đầy sinh viên
            } else {
                int theLoai = project.getTheLoai().getMatheloai();
                int hanMuc = (theLoai == 1) ? project.getMsgv().getHMHDDA() : project.getMsgv().getHMHDCD();
                int approvedCountForGV = dangKyDeTaiRepository.countByGiangVienAndTheLoai(project.getMsgv().getMsgv(), theLoai);

                if (approvedCountForGV >= hanMuc) {
                    canRegister = false; // giảng viên vượt hạn mức
                }
            }
            canRegisterMap.put(msdt, canRegister);
        }

        model.addAttribute("canRegisterMap", canRegisterMap);


        return "views/sinhvien/listTopic";
    }


    @GetMapping("/detai/{id}")
    public String TopicInfo(@PathVariable("id") int msdt, Model model, HttpSession session) {
        Project project = projectService.findById(msdt)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đề tài"));

        String role = (String) session.getAttribute("role");
        
        if (project.getNoidung() != null) {
            String noiDung = project.getNoidung();
            // Thay tất cả src="uploads/... thành src="/uploads/...
            noiDung = noiDung.replaceAll("src=\"(?!/|http)([^\"]+)\"", "src=\"/$1\"");
            project.setNoidung(noiDung);
        }
        
        String returnUrl = "/";
        if ("admin".equals(role)) {
            returnUrl = "/duyetdetai";
        } else if ("sinhvien".equals(role)) {
            returnUrl = "/listTopic";
        } else if ("giangvien".equals(role)) {
            returnUrl = "/danhsachdetai";
        }

        model.addAttribute("detai", project);
        model.addAttribute("returnUrl", returnUrl);

        return "views/sinhvien/TopicInfo";
    }

    @PostMapping("/registerTopic/{msdt}")
    public String registerDetai(@PathVariable int msdt, HttpSession session, RedirectAttributes redirectAttrs) {
        String mssv = (String) session.getAttribute("maso");
        if (mssv == null) {
            redirectAttrs.addFlashAttribute("error", "Bạn chưa đăng nhập");
            return "redirect:/login";
        }

        boolean success = dangKyDetaiService.registeredDetai(mssv, msdt);
        if (!success) {
            redirectAttrs.addFlashAttribute("error", "Bạn đã đăng ký đề tài khác hoặc đề tài đã đủ số lượng sinh viên.");
        } else {
            redirectAttrs.addFlashAttribute("message", "Đăng ký đề tài thành công, chờ phê duyệt.");
        }
        return "redirect:/sinhvien/dangkydetai";
    }

    @PostMapping("/cancelRegister/{msdt}")
    public String cancelRegister(@PathVariable int msdt, HttpSession session, RedirectAttributes redirectAttrs) {
        String mssv = (String) session.getAttribute("maso");
        if (mssv == null) {
            redirectAttrs.addFlashAttribute("error", "Bạn chưa đăng nhập");
            return "redirect:/login";
        }

        boolean canceled = dangKyDetaiService.cancelDangKy(mssv, msdt);
        if (canceled) {
            redirectAttrs.addFlashAttribute("message", "Hủy đăng ký thành công");
        } else {
            redirectAttrs.addFlashAttribute("error", "Không thể hủy đăng ký");
        }
        return "redirect:/sinhvien/dangkydetai";
    }
}
