package com.azrin.email.controller;

import com.azrin.email.utils.ApiVersion;
import com.azrin.email.utils.Constants;
import com.azrin.email.utils.SwaggerConstants;
import io.swagger.annotations.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiVersion.BASE_URL)
@Api(value = SwaggerConstants.HEART_BEAT_CLASS_VALUE)
public class HeartBeatController {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatController.class);

    @Value("${heart.beat.response}")
    private String heartBeatResponse;

    @ApiOperation(value = SwaggerConstants.HEART_BEAT_CONTROLLER_GET_VALUE,
            notes = SwaggerConstants.HEART_BEAT_CONTROLLER_GET_NOTES,
            position = SwaggerConstants.HEART_BEAT_CONTROLLER_GET_POSITION)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerConstants.STATUS_OK, response = ResponseEntity.class),
            @ApiResponse(code = 400, message = SwaggerConstants.STATUS_BAD_REQUEST, response = String.class),
            @ApiResponse(code = 500, message = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, response = String.class),
            @ApiResponse(code = 404, message = SwaggerConstants.STATUS_NOT_FOUND, response = String.class)
    })
    @GetMapping(value = "heatbeat",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> getHeartBeat() throws Exception{
        logger.info("HeartBeat is invoked");
        String result = heartBeatResponse;

        JSONObject responseBody = new JSONObject();
        responseBody.put(Constants.RESPONSE_BODY_DATA, result);
        responseBody.put(Constants.RESPONSE_BODY_STATUS, HttpStatus.OK);
        responseBody.put(Constants.RESPONSE_BODY_ERROR_MESSAGE, Constants.MESSAGE_ERROR);
        return ResponseEntity.ok(responseBody);
    }
}
