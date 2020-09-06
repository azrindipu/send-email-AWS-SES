package com.azrin.email.utils;

import com.azrin.email.Model.EmailModel;
import com.azrin.email.Model.History;
import com.azrin.email.dto.EmailDto;
import com.azrin.email.dto.HistoryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converters {
    private static final Logger logger = LoggerFactory.getLogger(Converters.class);

    @Autowired
    private DateUtils dateUtils;

    public EmailModel convertDtoToEntity(EmailDto emailDto) throws Exception{
        logger.info("dto to entity conversion start");
        EmailModel emailModel = new EmailModel();
        emailModel.setEmail_to(emailDto.getEmailTo());
        emailModel.setEmail_from(emailDto.getEmailFrom());
        emailModel.setEmail_subject(emailDto.getEmailSubject());
        emailModel.setEmail_body(emailDto.getEmailBody());
        emailModel.setCreation_time(dateUtils.getCurrentDate());
        emailModel.setSend_attempt(0);
        logger.info("Conversion complete");
        return emailModel;
    }

    public EmailTamplate convertEntityToTamplate(EmailModel emailModel) throws Exception{
        logger.info("Entity to template conversion start");
        EmailTamplate emailTamplate = new EmailTamplate();
        emailTamplate.setEmailTo(emailModel.getEmail_to());
        emailTamplate.setEmailFrom(emailModel.getEmail_from());
        emailTamplate.setEmailSubject(emailModel.getEmail_subject());
        emailTamplate.setEmailBody(emailModel.getEmail_body());
        logger.info("Conversion complete");
        return emailTamplate;
    }

    public History convertEmailModelToHistory(EmailModel emailModel, String status) throws Exception {
        logger.info("Model to history conversion start");
        History history = new History();
        history.setEmailModel(emailModel);
        history.setStatus(status);
        history.setTime(dateUtils.getCurrentDate());
        logger.info("Conversion complete");
        return history;
    }

    public HistoryDto convertHistoryEntitytoDto(History history) throws Exception{
        logger.info("history to historyDto conversion start");
        HistoryDto historyDto = new HistoryDto();
        historyDto.setEmailTo(history.getEmailModel().getEmail_to());
        historyDto.setEmailFrom(history.getEmailModel().getEmail_from());
        historyDto.setAttempt(history.getEmailModel().getSend_attempt());
        historyDto.setCreateTime(dateUtils.dateToString(history.getEmailModel().getCreation_time()));
        historyDto.setStatusChangeTime(dateUtils.dateToString(history.getTime()));
        history.setStatus(history.getStatus());
        logger.info("history to historyDto conversion end");
        return historyDto;
    }
}
