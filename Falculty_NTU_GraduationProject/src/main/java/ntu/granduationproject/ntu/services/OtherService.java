package ntu.granduationproject.ntu.services;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class OtherService {
    // mở file điểm sang 1 tab mới
    public byte[] generatePdf(String htmlContent) throws Exception {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null); // Nội dung HTML từ DB
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        }
    }
}
