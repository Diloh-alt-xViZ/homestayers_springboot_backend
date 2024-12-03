package com.developer.homestayersbackend.service.impl;


import com.developer.homestayersbackend.service.api.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value(value = "${spring.application.production.url}")
    private  String PRODUCTION_URL;
    @Override
    public void sendVerificationEmail(String email, String emailToken) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try{
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Confirm your email address");
        Context context = new Context();
        context.setVariable("companyName", "Homestayers");
        context.setVariable("logo","https://imgur.com/H8SVLSi");
        context.setVariable("user", new UserEmailContext(email));
        context.setVariable("userEmail",email);
        context.setVariable("verificationLink",PRODUCTION_URL+"/auth/email/verify?token="+emailToken);
        String htmlTemplate = templateEngine.process("email-template", context);
        mimeMessageHelper.setText(htmlTemplate,true);
        mailSender.send(mimeMessage);
        }
        catch (MessagingException e){
            System.out.println("Auth");
            System.out.println(e.getMessage());
        }
    }

    @Getter
    private static class UserEmailContext {
        private final String email;
        public UserEmailContext(String email) {
            this.email = email;
        }
    }
}
