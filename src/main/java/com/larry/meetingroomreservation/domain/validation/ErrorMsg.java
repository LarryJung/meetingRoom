package com.larry.meetingroomreservation.domain.validation;

import com.larry.meetingroomreservation.domain.exceptions.ValidationException;
import org.springframework.validation.FieldError;

public class ErrorMsg<T> {

    private String field;

    private T value;

    private String message;

    private String documentation_url = "http://localhost:8080/api/errors";

    public ErrorMsg() {

    }

    public ErrorMsg(String field, T value, String message) {
        this.field = field;
        this.value = value;
        this.message = message;
    }

    public ErrorMsg(ValidationException exception) {
        this.field = exception.getField();
        this.value = (T) exception.getValue();
        this.message = exception.getMessage();
    }

    public ErrorMsg(FieldError error) {
        this.field = error.getField();
        this.value = (T) error.getRejectedValue();
        this.message = error.getDefaultMessage();
    }

    public String getDocumentation_url() {
        return documentation_url;
    }

    public String getField() {
        return field;
    }

    public T getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public ErrorMsg setDocumentation_url(String documentation_url) {
        this.documentation_url = documentation_url;
        return this;
    }

    public ErrorMsg setField(String field) {
        this.field = field;
        return this;
    }

    public ErrorMsg setValue(T value) {
        this.value = value;
        return this;
    }

    public ErrorMsg setMessage(String message) {
        this.message = message;
        return this;
    }
}
