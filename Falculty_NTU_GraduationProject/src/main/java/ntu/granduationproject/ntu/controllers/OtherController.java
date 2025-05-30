package ntu.granduationproject.ntu.controllers;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.services.GiangVienService;
import ntu.granduationproject.ntu.services.OtherService;
import ntu.granduationproject.ntu.services.ProjectService;
import ntu.granduationproject.ntu.services.SinhVienService;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pdf")
public class OtherController {

    @Autowired
    SinhVienService sinhVienService;

    @Autowired
    GiangVienService giangVienService;

    @Autowired
    ProjectService projectService;

    @Autowired
    OtherService pdfService;

    // Xuất PDF cho sinh viên
    @GetMapping("/export/sinhvien/{mssv}")
    public ResponseEntity<byte[]> exportPdfSinhVien(@PathVariable String mssv) throws Exception {
        SinhVien sinhVien = sinhVienService.findByMssv(mssv);
        if (sinhVien == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String htmlRaw = sinhVien.getCvhoso();

        return buildPdfResponse(htmlRaw, "hoso_" + mssv + ".pdf");
    }

    // Xuất PDF cho giảng viên
    @GetMapping("/export/giangvien/{msgv}")
    public ResponseEntity<byte[]> exportPdfGiangVien(@PathVariable String msgv) throws Exception {
        GiangVien giangVien = giangVienService.findByMsgv(msgv);
        if (giangVien == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String htmlRaw = giangVien.getCvnangluc();

        return buildPdfResponse(htmlRaw, "hoso_" + msgv + ".pdf");
    }

    private ResponseEntity<byte[]> buildPdfResponse(String htmlRaw, String filename) throws Exception {
        String htmlClean = StringEscapeUtils.unescapeHtml4(htmlRaw);
        Document document = Jsoup.parse(htmlClean);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        String xhtml = document.html();

        String html = "<html><head>" +
                "<style>" +
                "  @font-face {" +
                "    font-family: 'DejaVuSans';" +
                "    src: url('file:src/main/resources/static/font/DejaVuSans.ttf') format('truetype');" +
                "  }" +
                "  body { font-family: 'DejaVuSans'; }" +
                "</style>" +
                "</head><body>" + xhtml + "</body></html>";

        byte[] pdfBytes = pdfService.generatePdf(html);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename(filename).build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/export/detai{id}")
    public ResponseEntity<byte[]> exportPdf(@PathVariable int id) throws Exception {
        Project project = projectService.findByMsdt(id);
        if (project == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String htmlRaw = project.getNoidung();

        String htmlClean = StringEscapeUtils.unescapeHtml4(htmlRaw);

        Document document = Jsoup.parse(htmlClean);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        String xhtml = document.html();

        String html = "<html><head>" +
                "<style>" +
                "  @font-face {" +
                "    font-family: 'DejaVuSans';" +
                "    src: url('file:src/main/resources/static/font/DejaVuSans.ttf') format('truetype');" +
                "  }" +
                "  body { font-family: 'DejaVuSans'; }" +
                "</style>" +
                "</head><body>" + xhtml + "</body></html>";

        byte[] pdfBytes = pdfService.generatePdf(html);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline()
                .filename("detai_" + id + ".pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
