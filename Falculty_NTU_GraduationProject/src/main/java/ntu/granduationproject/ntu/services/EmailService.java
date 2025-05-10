package ntu.granduationproject.ntu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService {
	 @Autowired
	 private JavaMailSender mailSender;

	    public void sendVerificationEmail(String toEmail, String code) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject("Mã xác nhận quên mật khẩu");
	        message.setText("Mã xác nhận của bạn là: " + code);
	        mailSender.send(message);
	    }

}
