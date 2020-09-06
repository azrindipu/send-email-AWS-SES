package com.azrin.email.scheduler;

import com.azrin.email.Model.EmailModel;
import com.azrin.email.repository.EmailRepository;
import com.azrin.email.service.EmailSender;
import com.azrin.email.service.HistoryService;
import com.azrin.email.utils.Converters;
import com.azrin.email.utils.EmailTamplate;
import com.azrin.email.utils.SchedulerLocker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailScheduler {
    private static final Logger logger = LoggerFactory.getLogger(EmailScheduler.class);

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private Converters converters;

    @Autowired
    private HistoryService historyService;

    @Value("${email.limit}")
    private String emailLimit;

    @Value("${email.attempt.max.limit}")
    private String emailAttemptMaxLimit;

    private SchedulerLocker schedulerLocker = SchedulerLocker.getInstance();

    @Scheduled(cron = "${cron.expression.for.email.notification}")
    public void emailShooter(){
        logger.info("Scheduler start");
        try {
            if(!schedulerLocker.isLocked()){
                schedulerLocker.setLocked(true);
                logger.info("Scheduler locker activated");
                logger.info("Getting emails from repository");
                List<EmailModel> emailModels = emailRepository.getEmails(Integer.parseInt(emailLimit));
                if(emailModels != null && emailModels.size()>0){
                    for(EmailModel emailModel : emailModels){
                        try {
                            if(emailModel.getSend_attempt() >= Integer.parseInt(emailAttemptMaxLimit)){
                                logger.info("Email max attempt over");
                                logger.info("Start creating history");
                                boolean result = historyService.saveHistory(emailModel, false);
                                if(result){
                                    logger.info("email sending fail");
                                }
                            }
                            logger.info("Calling converters");
                            EmailTamplate emailTamplate = converters.convertEntityToTamplate(emailModel);
                            logger.info("Calling email sender to send the mail");
                            boolean isSent = emailSender.sendMail(emailTamplate);
                            if(isSent){
                                boolean result = historyService.saveHistory(emailModel, true);
                                if(result){
                                    logger.info("Email has sent");
                                }
                            }
                        }catch (Exception e){
                            logger.info("Exception in scheduler: "+e.getMessage());
                            emailModel.setSend_attempt(emailModel.getSend_attempt() +1);
                            EmailModel savedEmailModel = emailRepository.saveEmail(emailModel);
                            if(savedEmailModel != null){
                                logger.info("Email con not be sent. Incrementing the attempt value");
                            }
                        }
                    }
                }
            }
            schedulerLocker.setLocked(false);
        }catch (Exception e){
            logger.info("Exception in scheduler: "+e.getMessage());
            schedulerLocker.setLocked(false);
        }finally {
            logger.info("Scheduler end");
            schedulerLocker.setLocked(false);
        }
    }
}
