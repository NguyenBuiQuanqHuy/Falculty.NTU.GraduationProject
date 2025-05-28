package ntu.granduationproject.ntu.controllers;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.repositories.LinhVucRepository;
import ntu.granduationproject.ntu.repositories.NamHocRepository;
import ntu.granduationproject.ntu.repositories.TheLoaiRepository;
import ntu.granduationproject.ntu.services.EmailService;
import ntu.granduationproject.ntu.services.ProjectService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    
	@GetMapping("/truongkhoa/home")
	public String adminHome(HttpSession session) {
	    if ("admin".equals(session.getAttribute("role"))) {
	        return "views/truongkhoa/Home"; // KHÔNG có .html
	    }
	    return "redirect:/login?unauthorized=true";
	}

    @GetMapping("/createaccount")
    public String createAccount() {
    	return "views/truongkhoa/CreateAccount";
    }
    
    @GetMapping("/duyetdetai")
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
    
    
    
    @PostMapping("/duyetdetai")
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
        return "redirect:/duyetdetai";
    }

    
    
    @PostMapping("/tuchoidetai")
    public String tuChoiDeTai(@RequestParam("msdt") int msdt, RedirectAttributes redirectAttributes) {
        Project project = projectService.getProjectById(msdt);
        if (project != null) {
            projectService.deleteById(msdt); // cần có hàm này
            redirectAttributes.addFlashAttribute("success", "Từ chối (xóa) đề tài thành công.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy đề tài.");
        }
        return "redirect:/duyetdetai";
    }

    

}
