package io.samos.nuls.controller;

import io.samos.nuls.common.RestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorController {
    private static final Logger log = LogManager.getLogger(ErrorController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult systemException(Exception e) {
        log.error("System error"+e.getMessage());
        e.printStackTrace();
        return RestResult.resultOf(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),"");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResult methodNotSupported(NoHandlerFoundException e) {
        log.error("method not found : 404"+e.getMessage());
        e.printStackTrace();
        return  RestResult.resultOf(HttpStatus.NOT_FOUND.value(), e.getMessage(),"");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public RestResult methodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("method not support : 405"+e.getMessage());
        e.printStackTrace();
        return RestResult.resultOf(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage(),"");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public RestResult mediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        log.error("media type not support : 415"+e.getMessage());
        e.printStackTrace();
        return RestResult.resultOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), e.getMessage(),"");
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public RestResult mediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e) {
        log.error("media type not acceptable : 406"+e.getMessage());
        e.printStackTrace();
        return RestResult.resultOf(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage(),"");
    }
}
