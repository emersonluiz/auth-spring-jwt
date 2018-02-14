package br.com.emersonluiz.mail;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Named
public class EmailService {

    @Inject
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void send(Mail mail) throws Exception {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            if (mail.getFrom() != null) {
                message.setFrom(mail.getFrom());
            }
            if (mail.getCc() != null) {
                message.setCc(mail.getCc());
            }
            message.setTo(mail.getTo());
            message.setSubject(mail.getSubject());
            message.setText(mail.getText());

            mailSender.send(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
