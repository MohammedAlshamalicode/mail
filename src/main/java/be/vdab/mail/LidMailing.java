package be.vdab.mail;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
class LidMailing {
    private final JavaMailSender sender;
    private final String userName;
    LidMailing(JavaMailSender sender,
               @Value("${spring.mail.username}") String userName) {
        this.sender = sender;
        this.userName = userName;
    }

    @Async
    void stuurMailNaRegistratie(Lid lid) throws MessagingException {
//        var message = new SimpleMailMessage();
        var message = sender.createMimeMessage();
        var helper = new MimeMessageHelper(message);
        helper.setFrom(userName);
        helper.setTo(lid.getEmailAdres());
        helper.setSubject("Geregistreerd");
        helper.setText("<h1>Je bent nu lid. </h1> Je nummer is:" + lid.getId(), true);
        sender.send(message);
    }
}