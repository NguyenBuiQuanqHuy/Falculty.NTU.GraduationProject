package ntu.granduationproject.ntu.controllers;

import jakarta.servlet.http.HttpSession;
import ntu.granduationproject.ntu.models.GiangVien;
import ntu.granduationproject.ntu.models.Project;
import ntu.granduationproject.ntu.models.SinhVien;
import ntu.granduationproject.ntu.services.GiangVienService;
import ntu.granduationproject.ntu.services.OtherService;
import ntu.granduationproject.ntu.services.ProjectService;
import ntu.granduationproject.ntu.services.SinhVienService;
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

    private String cleanHtml(String rawHtml) {
        String html = org.apache.commons.text.StringEscapeUtils.unescapeHtml4(rawHtml);

        // Sửa các thẻ sai chuẩn XML
        html = html.replaceAll("(?i)<br\\s*>", "<br/>")
                .replaceAll("(?i)</br>", "")
                .replaceAll("(?i)<hr\\s*>", "<hr/>")
                .replaceAll("(?i)</hr>", "")
                .replaceAll("&nbsp;", "&#160;");

        return html;
    }

    @GetMapping("/export/sinhvien/{id}")
    public ResponseEntity<byte[]> exportSinhVienPdf(@PathVariable String id, HttpSession session) throws Exception {
        String role = (String) session.getAttribute("role");
        System.out.println("Role: " + role + ", ID: " + id);

        SinhVien sinhVien = sinhVienService.findByMssv(id);
        String htmlClean = cleanHtml(sinhVien.getCvhoso());
        System.out.println("HTML after clean:\n" + htmlClean);


        String html = "<html><head>" +
                "<style>" +
                "  @font-face {" +
                "    font-family: 'DejaVuSans';" +
                "    src: url('file:src/main/resources/static/font/DejaVuSans.ttf') format('truetype');" +
                "  }" +
                "  body { font-family: 'DejaVuSans'; }" +
                "</style>" +
                "</head><body>" + htmlClean + "</body></html>";

        byte[] pdfBytes = pdfService.generatePdf(html);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline()
                .filename("hoso_" + id + ".pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/export/giangvien/{id}")
    public ResponseEntity<byte[]> exportGiangVienPdf(@PathVariable String id, HttpSession session) throws Exception {
        String role = (String) session.getAttribute("role");
        System.out.println("Role: " + role + ", ID: " + id);

        GiangVien giangVien = giangVienService.findByMsgv(id);
        String htmlClean = cleanHtml(giangVien.getCvnangluc());
        System.out.println("HTML after clean:\n" + htmlClean);


        String html = "<html><head>" +
                "<style>" +
                "  @font-face {" +
                "    font-family: 'DejaVuSans';" +
                "    src: url('file:src/main/resources/static/font/DejaVuSans.ttf') format('truetype');" +
                "  }" +
                "  body { font-family: 'DejaVuSans'; }" +
                "</style>" +
                "</head><body>" + htmlClean + "</body></html>";

        byte[] pdfBytes = pdfService.generatePdf(html);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline()
                .filename("hoso_" + id + ".pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/export/detai{id}")
    public ResponseEntity<byte[]> exportPdf(@PathVariable int id) throws Exception {
        Project project = projectService.findByMsdt(id);
        String htmlClean = cleanHtml(project.getNoidung());
        System.out.println("HTML after clean:\n" + htmlClean);


        String html = "<html><head>" +
                "<style>" +
                "  @font-face {" +
                "    font-family: 'DejaVuSans';" +
                "    src: url('file:src/main/resources/static/font/DejaVuSans.ttf') format('truetype');" +
                "  }" +
                "  body { font-family: 'DejaVuSans'; }" +
                "</style>" +
                "</head><body>" + htmlClean + "</body></html>";

        byte[] pdfBytes = pdfService.generatePdf(html);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline()
                .filename("detai_" + id + ".pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
