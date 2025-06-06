package ntu.granduationproject.ntu.services;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;

@Service
public class OtherService {

    public byte[] generatePdf(String htmlContent) throws Exception {
        String unescapedHtml = org.apache.commons.text.StringEscapeUtils.unescapeHtml4(htmlContent);
        unescapedHtml = unescapedHtml.replace("&nbsp;", "\u00A0");

        Document document = Jsoup.parse(unescapedHtml);
        document.outputSettings()
                .syntax(Document.OutputSettings.Syntax.xml)
                .escapeMode(Entities.EscapeMode.xhtml)
                .charset("UTF-8")
                .prettyPrint(true);

        Elements imgElements = document.select("img[src^=uploads/]");
        for (Element img : imgElements) {
            String src = img.attr("src");
            File imageFile = new File("uploads/" + src.substring("uploads/".length()));
            System.out.println("Original src: " + img.attr("src"));
            if (imageFile.exists()) {
                String base64 = encodeImageToBase64(imageFile);
                String contentType = getImageContentType(imageFile.getName());
                if (base64 != null && contentType != null) {
                    img.attr("src", "data:" + contentType + ";base64," + base64);
                }
            }
        }

        String xhtml = document.outerHtml();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            InputStream fontStream = getClass().getResourceAsStream("/font/times.ttf");
            if (fontStream == null) {
                throw new RuntimeException("Không tìm thấy font times.ttf trong resources/font/");
            }

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.useFont(() -> fontStream, "Times New Roman", 400, PdfRendererBuilder.FontStyle.NORMAL, true);
            builder.withHtmlContent(xhtml, null);
            builder.toStream(outputStream);
            builder.run();

            fontStream.close();
            return outputStream.toByteArray();
        }
    }

// Và nhớ bổ sung 2 hàm hỗ trợ encodeImageToBase64, getImageContentType như ví dụ trước
private String encodeImageToBase64(File imageFile) {
    try {
        byte[] fileContent = Files.readAllBytes(imageFile.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}

    private String getImageContentType(String filename) {
        String lower = filename.toLowerCase();
        if (lower.endsWith(".png")) {
            return "image/png";
        } else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lower.endsWith(".gif")) {
            return "image/gif";
        }
        // Bạn có thể bổ sung các loại ảnh khác nếu cần
        return null;
    }
}
