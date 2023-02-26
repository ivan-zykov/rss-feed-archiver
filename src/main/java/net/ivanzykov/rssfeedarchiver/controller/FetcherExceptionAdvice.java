package net.ivanzykov.rssfeedarchiver.controller;

import net.ivanzykov.rssfeedarchiver.services.fetcher.FetcherException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FetcherExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(FetcherException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String fetcherExceptionAdvice(FetcherException exception) {
        return exception.getMessage();
    }
}
