package com.azrin.email.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmailTamplate {

    private String emailFrom;
    private String emailTo;
    private String emailSubject;
    private String emailBody;
}
