package com.larry.meetingroomreservation.domain.validation;

import com.larry.meetingroomreservation.domain.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ReservationValidationAdvice {

    private final Logger log = LoggerFactory.getLogger(ReservationValidationAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorMsg> handleValidationException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().stream()
                .map(error -> new ErrorMsg((FieldError) error)).collect(Collectors.toList());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMsg handleValidationException(ValidationException exception) {
        ErrorMsg errorMsg = new ErrorMsg(exception);
        return errorMsg;
    }

}
