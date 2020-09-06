package com.azrin.email.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private String datePattern = "yyyy-MM-dd HH:mm";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);

    @ExceptionHandler(ControllerLevelException.class)
    public final ResponseEntity<Object> controllerLevelException(ControllerLevelException controllerLevelException,
                                                                 WebRequest request){
        logger.info("Controller level exception");
        ExceptionResponseTemplate response = new ExceptionResponseTemplate(simpleDateFormat.format(new Date()),
                controllerLevelException.getMessage(),
                controllerLevelException.getErrors());

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerError.class)
    public final ResponseEntity<Object> internalServerException(InternalServerError internalServerError, WebRequest request){
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(request.getDescription(false));

        logger.info("Internal server error exception");
        ExceptionResponseTemplate response = new ExceptionResponseTemplate(simpleDateFormat.format(new Date()),
                internalServerError.getMessage(),
                errorDetails);

        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequest.class)
    public final ResponseEntity<Object> badRequestException(BadRequest badRequest, WebRequest request){
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(request.getDescription(false));

        logger.info("Bad request exception");
        ExceptionResponseTemplate response = new ExceptionResponseTemplate(simpleDateFormat.format(new Date()),
                badRequest.getMessage(),
                errorDetails);

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}
