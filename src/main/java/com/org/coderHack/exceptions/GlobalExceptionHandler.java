package com.org.coderHack.exceptions;

import com.org.coderHack.dto.ApiExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiExceptionResponseDto> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiExceptionResponseDto apiExceptionResponseDto = new ApiExceptionResponseDto(message);

        return new ResponseEntity<>(apiExceptionResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiExceptionResponseDto> runtimeException(RuntimeException ex){
        String message = ex.getMessage();
        ApiExceptionResponseDto apiExceptionResponseDto = new ApiExceptionResponseDto(message);

        return new ResponseEntity<>(apiExceptionResponseDto, HttpStatus.BAD_REQUEST);
    }

}
