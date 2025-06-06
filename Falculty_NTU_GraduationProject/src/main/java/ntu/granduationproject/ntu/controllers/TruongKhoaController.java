package ntu.granduationproject.ntu.controllers;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.repositories.GiangVienRepository;
import ntu.granduationproject.ntu.repositories.LinhVucRepository;
import ntu.granduationproject.ntu.repositories.NamHocRepository;
import ntu.granduationproject.ntu.repositories.SinhVienRepository;
import ntu.granduationproject.ntu.repositories.TheLoaiRepository;
import ntu.granduationproject.ntu.services.EmailService;
import ntu.granduationproject.ntu.services.ProjectService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TruongKhoaController {
	
	@Autowired
	private EmailService emailService;
	
    @Autowired
    TheLoaiRepository theLoaiRepository;
    
    @Autowired
    LinhVucRepository linhVucRepository;
    
    @Autowired
    NamHocRepository namHocRepository;
	
    @Autowired
    ProjectService projectService;
    
    @Autowired 
    GiangVienRepository giangVienRepository;
    @Autowired
    SinhVienRepository sinhVienRepository;
    
	@GetMapping("/truongkhoa")
	public String adminHome(HttpSession session) {
	    if ("admin".equals(session.getAttribute("role"))) {
	        return "views/truongkhoa/Home"; // KHÔNG có .html
	    }
	    return "redirect:/login?unauthorized=true";
	}

    @GetMapping("/truongkhoa/taotaikhoan")
    public String createAccount() {
    	return "views/truongkhoa/CreateAccount";
    }
    
    @GetMapping("/truongkhoa/duyetdetai")
    public String duyetdetai(
            @RequestParam(name = "namhoc", required = false) String maNamHoc,
            @RequestParam(name = "linhvuc", required = false) String maLinhVuc,
            @RequestParam(name = "loai", required = false) String maTheLoai,
            @RequestParam(name = "truongkhoa", required = false) String truongkhoa,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            Model model) {

        // Chuyển chuỗi rỗng sang null để tránh lỗi query
        if (maNamHoc != null && maNamHoc.isEmpty()) maNamHoc = null;
        if (maLinhVuc != null && maLinhVuc.isEmpty()) maLinhVuc = null;
        if (maTheLoai != null && maTheLoai.isEmpty()) maTheLoai = null;
        if (truongkhoa != null && truongkhoa.isEmpty()) truongkhoa = null;

        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Project> projects;

        if (maNamHoc == null && maLinhVuc == null && maTheLoai == null && truongkhoa == null) {
            projects = projectService.getPagedProjects(pageable);
        } else {
            projects = projectService.searchProjectsPaged(maNamHoc,maLinhVuc, maTheLoai, truongkhoa, page, pageSize);
        }

        model.addAttribute("projects", projects);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", projects.getTotalPages());
        model.addAttribute("dsLinhVuc", linhVucRepository.findAll());
        model.addAttribute("dsNamHoc", namHocRepository.findAll());
        model.addAttribute("dsTheLoai", theLoaiRepository.findAll());
        model.addAttribute("truongkhoa", truongkhoa);

        return "views/truongkhoa/ApproveProject";
    }
    
    
    
    @PostMapping("/truongkhoa/duyetdetai")
    public String duyetDeTai(@RequestParam("msdt") int msdt, RedirectAttributes redirectAttributes) {
        try {
            Project project = projectService.getProjectById(msdt);
            if (project != null) {
                project.setTrangthai("Đã duyệt");
                projectService.save(project);

                String emailGV = project.getMsgv().getEmail();
                if(emailGV != null && !emailGV.isEmpty()) {
                    String tenDeTai = project.getTendt();
                    emailService.sendProjectApprovalEmail(emailGV, tenDeTai);
                }

                redirectAttributes.addFlashAttribute("success", "Đã duyệt đề tài thành công.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy đề tài.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi duyệt đề tài.");
        }
        return "redirect:/truongkhoa/duyetdetai";
    }

    
    
    @PostMapping("/truongkhoa/tuchoidetai")
    public String tuChoiDeTai(@RequestParam("msdt") int msdt, RedirectAttributes redirectAttributes) {
        Project project = projectService.getProjectById(msdt);
        if (project != null) {
            projectService.deleteById(msdt); // cần có hàm này
            redirectAttributes.addFlashAttribute("success", "Từ chối (xóa) đề tài thành công.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy đề tài.");
        }
        return "redirect:/truongkhoa/duyetdetai";
    }

    
    @GetMapping("/truongkhoa/quanlytaikhoan")
    public String listAccounts(
            @RequestParam(required = false) String msgv,
            @RequestParam(required = false) String mssv,
            @RequestParam(required = false) String hotengv,
            @RequestParam(required = false) String hotensv,
            Model model) {

        List<GiangVien> giangviens;
        List<SinhVien> sinhviens;

        // Tìm Giảng viên theo mã và họ tên (có thể null hoặc rỗng)
        if ((msgv == null || msgv.isEmpty()) && (hotengv == null || hotengv.isEmpty())) {
            giangviens = giangVienRepository.findAll();
        } else {
            giangviens = giangVienRepository.searchByMsgvAndHoten(
                msgv == null ? "" : msgv,
                hotengv == null ? "" : hotengv);
        }

        // Tìm Sinh viên theo mã và họ tên (có thể null hoặc rỗng)
        if ((mssv == null || mssv.isEmpty()) && (hotensv == null || hotensv.isEmpty())) {
            sinhviens = sinhVienRepository.findAll();
        } else {
            sinhviens = sinhVienRepository.searchByMssvAndHoten(
                mssv == null ? "" : mssv,
                hotensv == null ? "" : hotensv);
        }

        // Đẩy dữ liệu ra view
        model.addAttribute("giangviens", giangviens);
        model.addAttribute("sinhviens", sinhviens);

        model.addAttribute("msgv", msgv);
        model.addAttribute("mssv", mssv);
        model.addAttribute("hotengv", hotengv);
        model.addAttribute("hotensv", hotensv);

        return "views/truongkhoa/AccountManagement";
    }
    
    
 // GET: Hiển thị form sửa giảng viên
    @GetMapping("/truongkhoa/sua-giangvien/{msgv}")
    public String showEditGiangVienForm(@PathVariable("msgv") String msgv, Model model) {
        Optional<GiangVien> gvOpt = giangVienRepository.findById(msgv);
        if (gvOpt.isPresent()) {
            model.addAttribute("giangvien", gvOpt.get());
            return "views/truongkhoa/editGiangVien"; 
        } else {
            return "redirect:/truongkhoa/quanlytaikhoan";
        }
    }

    // POST: Xử lý cập nhật giảng viên
    @PostMapping("/truongkhoa/sua-giangvien")
    public String updateGiangVien(@ModelAttribute("giangvien") GiangVien formGV) {
        Optional<GiangVien> gvOpt = giangVienRepository.findById(formGV.getMsgv());
        if (gvOpt.isPresent()) {
            GiangVien existingGV = gvOpt.get();
            
            // Cập nhật các trường được sửa
            existingGV.setHoten(formGV.getHoten());
            existingGV.setEmail(formGV.getEmail());
            existingGV.setHMHDDA(formGV.getHMHDDA());
            existingGV.setHMHDCD(formGV.getHMHDCD());
            existingGV.setIsAdmin(formGV.isIsAdmin());

            // Giữ nguyên mật khẩu và hồ sơ năng lực
            // (không làm gì với matkhau và cvnangluc để không bị null hoặc ghi đè)

            giangVienRepository.save(existingGV);
            return "redirect:/truongkhoa/quanlytaikhoan";
        } else {
            // Nếu không tìm thấy giảng viên, chuyển hướng về trang quản lý
            return "redirect:/truongkhoa/quanlytaikhoan";
        }
    }


    // Xóa giảng viên theo msgv
    @GetMapping("/truongkhoa/xoa-giangvien/{msgv}")
    public String deleteGiangVien(@PathVariable("msgv") String msgv) {
        giangVienRepository.deleteById(msgv);
        return "redirect:/truongkhoa/quanlytaikhoan";
    }
    
    
 // GET: Hiển thị form sửa sinh viên
    @GetMapping("/truongkhoa/sua-sinhvien/{mssv}")
    public String showEditSinhVienForm(@PathVariable("mssv") String mssv, Model model) {
        Optional<SinhVien> svOpt = sinhVienRepository.findById(mssv);
        if (svOpt.isPresent()) {
            model.addAttribute("sinhvien", svOpt.get());
            return "views/truongkhoa/editSinhVien";
        } else {
            return "redirect:/truongkhoa/quanlytaikhoan";
        }
    }

    // POST: Xử lý cập nhật sinh viên
    @PostMapping("/truongkhoa/sua-sinhvien")
    public String updateSinhVien(@ModelAttribute("sinhvien") SinhVien formSV) {
        Optional<SinhVien> svOpt = sinhVienRepository.findById(formSV.getMssv());
        if (svOpt.isPresent()) {
            SinhVien existingSV = svOpt.get();
            existingSV.setHoten(formSV.getHoten());
            existingSV.setEmail(formSV.getEmail());

            sinhVienRepository.save(existingSV);
        }
        return "redirect:/truongkhoa/quanlytaikhoan";
    }


    // Xóa sinh viên theo mssv
    @GetMapping("/truongkhoa/xoa-sinhvien/{mssv}")
    public String deleteSinhVien(@PathVariable("mssv") String mssv) {
        sinhVienRepository.deleteById(mssv);
        return "redirect:/truongkhoa/quanlytaikhoan";
    }


}
