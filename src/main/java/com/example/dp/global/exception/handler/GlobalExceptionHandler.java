package com.example.dp.global.exception.handler;

import static com.example.dp.global.exception.code.CommonErrorCode.INVALID_PARAMETER;

import com.example.dp.global.exception.RestApiException;
import com.example.dp.global.exception.response.ErrorResponse;
import com.example.dp.global.exception.response.ErrorResponse.ValidationError;
import com.example.dp.global.exception.code.CommonErrorCode;
import com.example.dp.global.exception.code.ErrorCode;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomException(RestApiException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(errorCode));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
        WebRequest request) {
        List<ValidationError> validationList = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(ValidationError::of)
            .toList();

        CommonErrorCode errorCode = INVALID_PARAMETER;

        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(errorCode, validationList));
    }

    private static ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
            .code(errorCode.name())
            .status(errorCode.getHttpStatus().value())
            .message(errorCode.getMessage())
            .build();
    }

    private static ErrorResponse makeErrorResponse(ErrorCode errorCode,
        List<ValidationError> validationList) {
        return ErrorResponse.builder()
            .code(errorCode.name())
            .status(errorCode.getHttpStatus().value())
            .message(errorCode.getMessage())
            .errors(validationList)
            .build();
    }

}
