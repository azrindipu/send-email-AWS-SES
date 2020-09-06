package com.azrin.email.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "History DTO")
public class HistoryDto {

    @JsonProperty("email_to")
    private String emailTo;

    @JsonProperty("email_from")
    private String emailFrom;

    @JsonProperty("status")
    private String status;

    @JsonProperty("created_time")
    private String createTime;

    @JsonProperty("status_change_time")
    private String statusChangeTime;

    @JsonProperty("attempt")
    private int attempt;
}
