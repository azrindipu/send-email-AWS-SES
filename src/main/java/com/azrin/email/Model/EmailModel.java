package com.azrin.email.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "email")
public class EmailModel {

    @Id
    private String id;
    private String email_to;
    private String email_from;
    private String email_subject;
    private String email_body;
    private Date creation_time;
    private int send_attempt;
}
