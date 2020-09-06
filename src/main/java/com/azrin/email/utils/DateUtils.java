package com.azrin.email.utils;

import com.azrin.email.ExceptionHandler.BadRequest;
import com.azrin.email.config.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    @Autowired
    private Messages messages;

    private String pattern = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public Date getCurrentDate() throws Exception{
        logger.info("Getting current date");
        Date date = null;
        try {
            String dateStr = simpleDateFormat.format(new Date());
            date = simpleDateFormat.parse(dateStr);
        }catch (Exception e){
            throw new BadRequest(messages.get(MessagePropertyKey.DATE_FORMAT_EXCEPTION));
        }
        logger.info("Got the date");
        return date;
    }

    public Date stringToDate(String dateStr) throws Exception{
        logger.info("String to date conversion start");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        }catch (Exception e){
            throw new BadRequest(messages.get(MessagePropertyKey.DATE_FORMAT_EXCEPTION));
        }
        logger.info("Converted");
        return date;
    }

    public String dateToString(Date date){
        logger.info("Date to string conversion start");
        String dateStr = null;
        try {
            dateStr = simpleDateFormat.format(date);
        }catch (Exception e){
            throw new BadRequest(messages.get(MessagePropertyKey.DATE_FORMAT_EXCEPTION));
        }
        logger.info("Converted");
        return dateStr;
    }
}
