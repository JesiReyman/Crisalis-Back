package com.crisalis.crisalisback.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            ExcepcionElementoVacio.class
    })
    @ResponseBody
    public MensajeError badRequest(HttpServletRequest request, Exception exception){
        return new MensajeError(exception, request.getRequestURI());
    }
}
