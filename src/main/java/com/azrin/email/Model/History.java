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
@Document(collection = "email_history")
public class History {

    @Id
    private String id;
    private EmailModel emailModel;
    private String status;
    private Date time;
}
