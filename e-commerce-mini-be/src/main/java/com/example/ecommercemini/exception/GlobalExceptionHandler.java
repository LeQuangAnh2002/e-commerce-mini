package com.example.ecommercemini.exception;

import com.example.ecommercemini.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResponseNotFoundException(ResourceNotFoundException e){
        String message = e.getMessage();
        ApiResponse response = ApiResponse.builder()
                .message(message)
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(System.currentTimeMillis())
                .build();

        return  new ResponseEntity<ApiResponse>(response,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handlerConstraintVoilationException(ConstraintViolationException ex){
        String message=ex.getMessage();
        ApiResponse response=ApiResponse.builder().message(message).status(HttpStatus.BAD_REQUEST.value()).timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponse> handleBadApiRequest(BadApiRequestException ex){
        String message=ex.getMessage();
        ApiResponse response=ApiResponse.builder().message(message).status(HttpStatus.BAD_REQUEST.value()).timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.BAD_REQUEST);
    }
}
