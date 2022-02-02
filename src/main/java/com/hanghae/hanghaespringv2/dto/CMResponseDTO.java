package com.hanghae.hanghaespringv2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CMResponseDTO<T> {
    private boolean success;
    private int status;
    private String message;
    private T response;
}
