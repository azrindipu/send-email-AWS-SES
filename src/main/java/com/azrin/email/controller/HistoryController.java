package com.azrin.email.controller;

import com.azrin.email.ExceptionHandler.BadRequest;
import com.azrin.email.ExceptionHandler.InternalServerError;
import com.azrin.email.dto.HistoryDto;
import com.azrin.email.dto.PageInfoDto;
import com.azrin.email.service.HistoryService;
import com.azrin.email.utils.ApiVersion;
import com.azrin.email.utils.Constants;
import com.azrin.email.utils.SwaggerConstants;
import io.swagger.annotations.*;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(ApiVersion.BASE_URL)
@Api(value = SwaggerConstants.HISTORY_CLASS_VALUE)
public class HistoryController {
    private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);

    @Autowired
    private HistoryService historyService;

    @ApiOperation(value = SwaggerConstants.HISTORY_CONTROLLER_GET_VALUE,
            notes = SwaggerConstants.HISTORY_CONTROLLER_GET_NOTES,
            position = SwaggerConstants.HISTORY_CONTROLLER_GET_POSITION)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerConstants.STATUS_OK, response = ResponseEntity.class),
            @ApiResponse(code = 400, message = SwaggerConstants.STATUS_BAD_REQUEST, response = String.class),
            @ApiResponse(code = 500, message = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, response = String.class),
            @ApiResponse(code = 404, message = SwaggerConstants.STATUS_NOT_FOUND, response = String.class)
    })
    @GetMapping(value = "history",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> getHistory(@ApiParam(name = "page_number", example = "0",required = false, value = "Page Number")
                                                 @RequestParam(required = false) String page_number,
                                                 @ApiParam(name = "page_size", example = "10",required = false, value = "Page Size")
                                                 @RequestParam(required = false) String page_size) throws Exception{
        logger.info("Get history is invoked");
        List<HistoryDto> result = null;
        PageInfoDto pageInfoDto = new PageInfoDto();
        try {
            logger.info("Calling service");
            result = historyService.getHistory(page_number, page_size, pageInfoDto);
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
        responseBody.put(Constants.RESPONSE_BODY_PAGEINFO, pageInfoDto);

        logger.info(responseBody.toString());
        return ResponseEntity.ok(responseBody);
    }
}
