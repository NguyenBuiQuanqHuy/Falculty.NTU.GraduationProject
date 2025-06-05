package ntu.granduationproject.ntu.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.DangKyDetai;
import ntu.granduationproject.ntu.models.DanhGiaDeTai;
import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.repositories.DangKyDeTaiRepository;
import ntu.granduationproject.ntu.repositories.DanhGiaDeTaiRepository;
import ntu.granduationproject.ntu.repositories.LinhVucRepository;
import ntu.granduationproject.ntu.repositories.NamHocRepository;
import ntu.granduationproject.ntu.repositories.ProjectRepository;
import ntu.granduationproject.ntu.repositories.TheLoaiRepository;
import ntu.granduationproject.ntu.services.DanhGiaDeTaiService;
import ntu.granduationproject.ntu.services.ProjectService;

@Controller
public class DanhGiaDeTaiController {
	@Autowired
	ProjectService projectService;
	@Autowired
	TheLoaiRepository theLoaiRepository;
	@Autowired
	LinhVucRepository linhVucRepository;
	@Autowired
	NamHocRepository namHocRepository;
	@Autowired
	DanhGiaDeTaiRepository danhGiaDeTaiRepository;
	@Autowired
	DanhGiaDeTaiService danhGiaDeTaiService;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	DangKyDeTaiRepository dangKyDeTaiRepository;
	
	@GetMapping("giangvien/evaluateproject")
	public String listProjects( @RequestParam(required = false) Integer namhoc,
	        @RequestParam(required = false) Integer theloai,
	        @RequestParam(required = false) Integer linhvuc,
	        @RequestParam(required = false) String tendt,
	        HttpSession session,
	        ModelMap model) {
		Object userObj = session.getAttribute("user");
	    if (!(userObj instanceof GiangVien)) {
	        return "redirect:/login";
	    }
	    GiangVien giangVien = (GiangVien) userObj;
	    String msgv = giangVien.getMsgv();

	    List<Project> projects = projectService.filterProjectsByConditions(
	        giangVien.getMsgv(), namhoc, theloai, linhvuc, tendt, true
	    );
	    
	    model.addAttribute("projects", projects);
	    model.addAttribute("namhocs", namHocRepository.findAll());
	    model.addAttribute("theloais", theLoaiRepository.findAll());
	    model.addAttribute("linhvucs", linhVucRepository.findAll());

	    model.addAttribute("selectedNamHoc", namhoc);
	    model.addAttribute("selectedTheLoai", theloai);
	    model.addAttribute("selectedLinhVuc", linhvuc);
	    model.addAttribute("tendt", tendt);
	    
	    return "views/giangvien/listevaluate";
	}
	
	@PostMapping("/giangvien/evaluateproject/submit")
	public String submitEvaluation(@RequestParam("msdt") int msdt,
	                               @RequestParam("diem") BigDecimal diem,
	                               @RequestParam("binhluan") String binhluan,
	                               HttpSession session) {
	    Object userObj = session.getAttribute("user");
	    if (!(userObj instanceof GiangVien)) {
	        return "redirect:/login";
	    }

	    GiangVien gv = (GiangVien) userObj;
	    Project project = projectService.findById(msdt).orElse(null);
	    if (project == null) {
	        return "redirect:/giangvien/evaluateproject?error=notfound";
	    }

	    // Kiểm tra đã đánh giá hay chưa
	    Optional<DanhGiaDeTai> existing = danhGiaDeTaiRepository.findByMsgvAndMsdt(gv, project);

	    DanhGiaDeTai danhGiaDeTai = existing.orElse(new DanhGiaDeTai());
	    danhGiaDeTai.setMsgv(gv);
	    danhGiaDeTai.setMsdt(project);
	    danhGiaDeTai.setDiem(diem);
	    danhGiaDeTai.setBinhluan(binhluan);

	    danhGiaDeTaiRepository.save(danhGiaDeTai); // sẽ update nếu đã có

	    return "redirect:/giangvien/evaluateproject?success=true";
	}
	
	@GetMapping("/giangvien/evaluateproject/getEvaluation")
	@ResponseBody
	public ResponseEntity<?> getEvaluation(@RequestParam("msdt") int msdt, HttpSession session) {
	    Object userObj = session.getAttribute("user");
	    if (!(userObj instanceof GiangVien)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
	    }

	    GiangVien gv = (GiangVien) userObj;
	    Project project = projectService.findById(msdt).orElse(null);
	    if (project == null) {
	        return ResponseEntity.badRequest().body("Project not found");
	    }

	    Optional<DanhGiaDeTai> evaluation = danhGiaDeTaiRepository.findByMsgvAndMsdt(gv, project);

	    if (evaluation.isPresent()) {
	        DanhGiaDeTai dg = evaluation.get();
	        Map<String, Object> data = new HashMap<>();
	        data.put("diem", dg.getDiem());
	        data.put("binhluan", dg.getBinhluan());
	        return ResponseEntity.ok(data);
	    } else {
	        return ResponseEntity.ok(null); // hoặc {}
	    }
	}

	@GetMapping("/sinhvien/evaluateproject")
	public String Seeevaluate( @RequestParam(required = false) Integer namhoc,
	        @RequestParam(required = false) Integer theloai,
	        @RequestParam(required = false) Integer linhvuc,
	        @RequestParam(required = false) String tendt,
	        HttpSession session,
	        ModelMap model) {
		
		String mssv = (String) session.getAttribute("maso");
	    if (mssv == null) {
	        return "redirect:/login";
	    }
		
	    List<Project> projects = projectRepository.allProjectsByConditions(
	  namhoc, theloai, linhvuc, tendt, true
	    );
	    
	    List<DangKyDetai> myApprovedTopics = dangKyDeTaiRepository
	            .findByMssv_MssvAndTrangthai(mssv, "Đã duyệt");
	    
	    model.addAttribute("projects", projects);
	    model.addAttribute("myTopics", myApprovedTopics);
	    model.addAttribute("namhocs", namHocRepository.findAll());
	    model.addAttribute("theloais", theLoaiRepository.findAll());
	    model.addAttribute("linhvucs", linhVucRepository.findAll());

	    model.addAttribute("selectedNamHoc", namhoc);
	    model.addAttribute("selectedTheLoai", theloai);
	    model.addAttribute("selectedLinhVuc", linhvuc);
	    model.addAttribute("tendt", tendt);
	    
	    return "views/sinhvien/seereviews";
	}
	
	@GetMapping("/sinhvien/evaluateproject/getEvaluations")
	@ResponseBody
	public ResponseEntity<?> getEvaluationsForProject(@RequestParam("msdt") int msdt) {
	    Project project = projectService.findById(msdt).orElse(null);
	    if (project == null) {
	        return ResponseEntity.badRequest().body("Project not found");
	    }

	    List<DanhGiaDeTai> danhGiaList = danhGiaDeTaiRepository.findByMsdt(project);

	    List<Map<String, Object>> response = danhGiaList.stream().map(dg -> {
	        Map<String, Object> item = new HashMap<>();
	        item.put("giangvien", dg.getMsgv().getHoten()); // hoặc msgv.getUsername()
	        item.put("diem", dg.getDiem());
	        item.put("binhluan", dg.getBinhluan());
	        return item;
	    }).toList();

	    return ResponseEntity.ok(response);
	}


}
