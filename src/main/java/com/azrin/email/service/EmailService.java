package com.azrin.email.service;

import com.azrin.email.ExceptionHandler.InternalServerError;
import com.azrin.email.Model.EmailModel;
import com.azrin.email.config.Messages;
import com.azrin.email.dto.EmailDto;
import com.azrin.email.repository.EmailRepository;
import com.azrin.email.utils.Converters;
import com.azrin.email.utils.MessagePropertyKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private Converters converters;

    @Autowired
    private Messages messages;

    public boolean sendEmail(EmailDto emailDto) throws Exception{
        logger.info("Calling converter");
        EmailModel emailModel = converters.convertDtoToEntity(emailDto);
        logger.info("Calling repository");
        EmailModel savedEmail = emailRepository.saveEmail(emailModel);
        if(savedEmail == null){
            throw new InternalServerError(messages.get(MessagePropertyKey.INTERNAL_SERVER_ERROR));
        }
        return true;
    }
}
