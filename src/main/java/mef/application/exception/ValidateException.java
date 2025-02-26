package mef.application.exception;

import mef.application.dto.ResponseError;

import java.util.List;

public class ValidateException extends RuntimeException{

    private List<ResponseError> errors;

    public ValidateException(List<ResponseError> errors){
        this.errors = errors;
    }

    public List<ResponseError> getErrors() {
        return errors;
    }

    public void setErrors(List<ResponseError> errors) {
        this.errors = errors;
    }
}
