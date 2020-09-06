package com.azrin.email.utils;

import com.azrin.email.ExceptionHandler.ControllerLevelException;
import com.azrin.email.config.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Component
public class BindResultChecking {
    private static final Logger logger = LoggerFactory.getLogger(BindResultChecking.class);

    @Autowired
    private Messages messages;

    public void checkBindingResult(BindingResult bindingResult) throws Exception{
        logger.info("Bind result checking start");
        if(bindingResult.hasErrors()){
            logger.info("Bind result has error");
            List<ObjectError> errorList = bindingResult.getAllErrors();
            List<String> errors = new ArrayList<>();
            for(int i = 0; i < errorList.size(); i++){
                errors.add(errorList.get(i).getDefaultMessage());
            }
            throw new ControllerLevelException(messages.get(MessagePropertyKey.CONTROLLER_LEVEL_EXCEPTION), errors);
        }
    }
}
