package com.azrin.email.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PageInfoDto {
    private boolean last;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int numberOfElements;
    private int size;
    private int number;
}
