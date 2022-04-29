package hu.wilderness.reporterapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ParameterService parameterService;



    private JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(parameterService.getStringParameter("email.host"));
        mailSender.setPort(parameterService.getIntegerParameter("email.port"));
        mailSender.setUsername(parameterService.getStringParameter("email.user"));
        mailSender.setPassword(parameterService.getStringParameter("email.password"));
        Properties mailProperties = new Properties();

        mailProperties.put("mail.smtp.auth", parameterService.getBooleanParameter("email.smtp.auth"));
        mailProperties.put("mail.smtp.starttls.enable", parameterService.getBooleanParameter("email.smtp.starttls"));
        mailProperties.put("mail.smtp.starttls.enable.required", parameterService.getBooleanParameter("email.smtp.starttls.required"));
        mailProperties.put("mail.smtp.ssl.trust",parameterService.getStringParameter("email.smtp.ssl.trust"));
        mailProperties.put("mail.smtp.ssl.protocols",parameterService.getStringParameter("email.smtp.ssl.protocols"));

        mailSender.setJavaMailProperties(mailProperties);
        return mailSender;
    }

    public void sendMsg(JavaMailSender javaMailSender, String address, String subject, String text) {
        try {
            log.debug("sending email to " + address + " ...");
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(text, true);
            helper.setTo(address);
            helper.setSubject(subject);
            helper.setFrom(parameterService.getStringParameter("email.user"));
            javaMailSender.send(mimeMessage);
            log.debug("email to " + address + " has been sent.");
        } catch (Exception e) {
            log.debug("something went wrong... : " + e.toString());
        }

    }

    public void sendMail(String address, String subject, String text) {
        JavaMailSender javaMailSender = getMailSender();
        sendMsg(javaMailSender, address, subject, text);
    }

    public void sendConfirmationMail(String address, String uuid) {
        sendMail(address,"Kérjük erősítse meg bejelentését", "lari fari http://localhost:8080/emailconfirm/" + uuid + " ");
    }

    public void sendRequestMailToActivateAccount(String address, String uuid) {
        sendMail(address,"Kérjük a következő linkre kattintva álítson be új jelszót ", "lari fari http://localhost:8080/admin/setpassword/" + uuid + " ");
    }
}