package com.azrin.email.dto;

import com.azrin.email.utils.ValidationMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Email DTO")
public class EmailDto {

    @JsonProperty("email_to")
    @NotBlank(message = ValidationMessage.EMAIL_TO_CAN_NOT_BE_EMPTY)
    @NotNull(message = ValidationMessage.EMAIL_TO_CAN_NOT_BE_NULL)
    @ApiModelProperty(required = true, example = "xyz@gmail.com")
    private String emailTo;

    @JsonProperty("email_from")
    @NotBlank(message = ValidationMessage.EMAIL_FROM_CAN_NOT_BE_EMPTY)
    @NotNull(message = ValidationMessage.EMAIL_FROM_CAN_NOT_BE_NULL)
    @ApiModelProperty(required = true, example = "zyx@gmail.com")
    private String emailFrom;

    @JsonProperty("email_subject")
    @NotBlank(message = ValidationMessage.EMAIL_SUBJECT_CAN_NOT_BE_EMPTY)
    @NotNull(message = ValidationMessage.EMAIL_SUBJECT_CAN_NOT_BE_NULL)
    @ApiModelProperty(required = true, example = "zyx@gmail.com")
    private String emailSubject;

    @JsonProperty("email_body")
    @NotBlank(message = ValidationMessage.EMAIL_BODY_CAN_NOT_BE_EMPTY)
    @NotNull(message = ValidationMessage.EMAIL_BODY_CAN_NOT_BE_NULL)
    @ApiModelProperty(required = true, example = "zyx@gmail.com")
    private String emailBody;

}
