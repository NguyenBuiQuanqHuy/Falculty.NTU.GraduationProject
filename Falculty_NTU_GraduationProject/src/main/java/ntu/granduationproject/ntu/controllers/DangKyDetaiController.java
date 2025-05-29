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

    @GetMapping("/listTopic")
    public String ListTopic(
            @RequestParam(value = "tendt", required = false) String tendt,
            @RequestParam(value = "namhoc", required = false) Integer namhoc,
            @RequestParam(value = "theloai", required = false) Integer theloai,
            @RequestParam(value = "linhvuc", required = false) Integer linhvuc,
            Model model, HttpSession session, RedirectAttributes redirectAttrs) {

        String mssv = (String) session.getAttribute("maso");
        if (mssv == null) {
            redirectAttrs.addFlashAttribute("error", "Bạn chưa đăng nhập");
            return "redirect:/login";
        }

        // Lấy danh sách đề tài đã lọc, trạng thái "Đã duyệt"
        List<Project> dsFiltered = projectService.searchProjects(
                null,
                namhoc,
                theloai,
                linhvuc,
                tendt,
                "Đã duyệt"
        );
        model.addAttribute("dsdetai", dsFiltered);


        Map<Integer, Integer> mapCountRegistered = new HashMap<>();
        for (Project project : dsFiltered) {
            int count = dangKyDeTaiRepository.countByMsdt_Msdt(project.getMsdt());
            mapCountRegistered.put(project.getMsdt(), count);
        }
        model.addAttribute("countRegistered", mapCountRegistered);

        dangKyDeTaiRepository.findByMssv_Mssv(mssv).ifPresent(dk -> {
            model.addAttribute("dangKy", dk);
        });

        Map<Integer, Integer> mapCountApproved = new HashMap<>();

        for (Project project : dsFiltered) {
            int count = dangKyDeTaiRepository.countByMsdt_Msdt(project.getMsdt());
            mapCountRegistered.put(project.getMsdt(), count);

            int approved = dangKyDeTaiRepository.countApprovedByMsdt(project.getMsdt());
            mapCountApproved.put(project.getMsdt(), approved);
        }
        model.addAttribute("countRegistered", mapCountRegistered);
        model.addAttribute("countApproved", mapCountApproved);


        model.addAttribute("namhocs", namHocRepository.findAll());
        model.addAttribute("theloais", theLoaiRepository.findAll());
        model.addAttribute("linhvucs", linhVucRepository.findAll());

        model.addAttribute("tendt", tendt);
        model.addAttribute("selectedNamHoc", namhoc);
        model.addAttribute("selectedTheLoai", theloai);
        model.addAttribute("selectedLinhVuc", linhvuc);

        return "views/sinhvien/listTopic";
    }


    @GetMapping("/listTopic/{id}")
    public String TopicInfo(@PathVariable("id") int msdt, Model model, HttpSession session) {
        Project project = projectService.findById(msdt)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đề tài"));

        String role = (String) session.getAttribute("role");
        
        if (project.getNoidung() != null) {
            String noiDung = project.getNoidung();
            // Thay tất cả src="uploads/... thành src="/uploads/...
            noiDung = noiDung.replaceAll("src=\"(?!/)", "src=\"/");
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
        return "redirect:/listTopic";
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
        return "redirect:/listTopic";
    }
}
