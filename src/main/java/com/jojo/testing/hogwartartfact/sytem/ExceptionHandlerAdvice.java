package com.jojo.testing.hogwartartfact.sytem;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class  ExceptionHandlerAdvice {

    @ExceptionHandler(ArtifactNotFoundException.class)
    Result handleArtifactNotFoundException(ArtifactNotFoundException ex){
        return new Result(false,404, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleValidationRException(MethodArgumentNotValidException ex){
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String,String> map = new HashMap<>(errors.size());
        errors.forEach(error->{
            String key = ((FieldError)error).getField();
            String val = error.getDefaultMessage();
            map.put(key,val);
        });

        return new Result(false,404,"provided arguments are invalid", map);
    }
}
