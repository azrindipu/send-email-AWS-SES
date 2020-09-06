package com.azrin.email.service;

import com.azrin.email.utils.EmailTamplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Transactional
public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private JavaMailSender sender;

    public boolean sendMail(EmailTamplate emailTamplate){
        logger.info("Email sending start");
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            try {
                helper.setTo(emailTamplate.getEmailTo());
                helper.setFrom(emailTamplate.getEmailFrom());
            }catch (Exception e){
                logger.info("Exception: "+e.getMessage());
                return false;
            }
            helper.setSubject(emailTamplate.getEmailSubject());
            helper.setText(emailTamplate.getEmailBody(),true);
        } catch (MessagingException e) {
            logger.info("Exception: "+e.getMessage());
            return false;
        }
        try {
            sender.send(message);
        }catch (Exception e){
            logger.info("Exception: "+e.getMessage());
            return false;
        }
        logger.info("Email has sent successfully");
        return true;
    }
}
