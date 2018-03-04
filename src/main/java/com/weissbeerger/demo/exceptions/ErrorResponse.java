package com.weissbeerger.demo.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
public class ErrorResponse {
    private String message;
    private HttpStatus httpStatus;
    public ErrorResponse(Exception e){
        if(e instanceof NullPointerException){
            httpStatus = HttpStatus.BAD_REQUEST;
        }
    }

}
