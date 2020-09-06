package com.azrin.email.controller;

import com.azrin.email.ExceptionHandler.BadRequest;
import com.azrin.email.ExceptionHandler.InternalServerError;
import com.azrin.email.dto.EmailDto;
import com.azrin.email.service.EmailService;
import com.azrin.email.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiVersion.BASE_URL)
@Api(value = SwaggerConstants.EMAIL_CLASS_VALUE)
public class EmailController {
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private BindResultChecking bindResultChecking;

    @ApiOperation(value = SwaggerConstants.EMAIL_CONTROLLER_POST_VALUE,
            notes = SwaggerConstants.EMAIL_CONTROLLER_POST_NOTES,
            position = SwaggerConstants.EMAIL_CONTROLLER_POST_POSITION)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerConstants.STATUS_OK, response = ResponseEntity.class),
            @ApiResponse(code = 400, message = SwaggerConstants.STATUS_BAD_REQUEST, response = String.class),
            @ApiResponse(code = 500, message = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, response = String.class),
            @ApiResponse(code = 404, message = SwaggerConstants.STATUS_NOT_FOUND, response = String.class)
    })
    @PostMapping(value = "emails",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> sendEmail(@Valid @RequestBody final EmailDto emailDto,
                                                BindingResult bindingResult) throws Exception{
        logger.info("Email send POST method is invoked");
        bindResultChecking.checkBindingResult(bindingResult);
        boolean result = false;
        try {
            logger.info("Calling service to send the email");
            result = emailService.sendEmail(emailDto);
        }catch (BadRequest e){
            throw new BadRequest(e.getMessage());
        }catch (InterruptedException e){
            throw new InternalServerError(e.getMessage());
        }catch (Exception e){
            throw new InternalServerError(e.getMessage());
        }

        JSONObject responseBody = new JSONObject();
        responseBody.put(Constants.RESPONSE_BODY_DATA, result);
        responseBody.put(Constants.RESPONSE_BODY_STATUS, HttpStatus.OK);
        responseBody.put(Constants.RESPONSE_BODY_ERROR_MESSAGE, Constants.MESSAGE_ERROR);

        logger.info(responseBody.toString());
        return ResponseEntity.ok(responseBody);
    }
}
