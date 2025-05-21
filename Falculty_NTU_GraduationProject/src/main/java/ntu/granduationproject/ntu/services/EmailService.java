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
	    
	    public void sendNewPassword(String toEmail, String password) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject("PASSWORD");
	        message.setText("Mật khẩu được cấp là : " + password);
	        mailSender.send(message);
	    }
	    

	    public void sendNotificationEmail(String toEmail, String subject, String body) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject(subject);
	        message.setText(body);
	        mailSender.send(message);
	    }

	    
	    public void sendProjectApprovalEmail(String toEmail, String tenDeTai) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject("Đề tài đã được duyệt");
	        message.setText("Đề tài \"" + tenDeTai + "\" của bạn đã được duyệt.");
	        mailSender.send(message);
	    }

}
