package net.ivanzykov.rssfeedarchiver.controller;

import net.ivanzykov.rssfeedarchiver.services.validator.InvalidFeedUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidFeedUrlExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidFeedUrlException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidFeedUrlExceptionAdvice(InvalidFeedUrlException ex) {
        return ex.getMessage();
    }
}
