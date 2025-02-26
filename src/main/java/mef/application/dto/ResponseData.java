package mef.application.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseData{

    private int code;
    private List<ResponseError> errors;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResponseError> getErrors() {
        return errors;
    }

    public void setErrors(List<ResponseError> errors) {
        this.errors = errors;
    }

    public static ResponseData ERROR(int code, List<ResponseError> errors){
        ResponseData data = new ResponseData();
        data.setCode(code);
        data.setErrors(errors);
        return data;
    }

    public static  ResponseData ERROR(int code, String error){
        List<ResponseError> errors = new ArrayList<>();
        ResponseError field = new ResponseError();
        field.setMessage(error);
        errors.add(field);
        return ERROR(code, errors);
    }

}
