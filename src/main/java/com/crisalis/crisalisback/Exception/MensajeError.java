package com.crisalis.crisalisback.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensajeError {
    private String exception;
    private String message;
    private String path;

    public MensajeError(Exception exception, String path){
        this.exception = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.path = path;
    }
}
