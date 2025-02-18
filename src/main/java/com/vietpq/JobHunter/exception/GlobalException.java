package com.vietpq.JobHunter.exception;

import com.vietpq.JobHunter.entity.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    // Xử lý lỗi xác thực (Username không tồn tại, Mật khẩu sai)
    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<RestResponse<Object>> handleAuthenticationException(Exception ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Authentication Error", ex.getMessage());
    }

    // Xử lý lỗi hợp lệ dữ liệu
    @ExceptionHandler({
            NotNullException.class,
            InvalidEmailException.class,
            InvalidPasswordException.class,
            DuplicatedException.class
    })
    public ResponseEntity<RestResponse<Object>> handleValidationException(RuntimeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Error", ex.getMessage());
    }

    // Xử lý lỗi không tìm thấy tài nguyên
    @ExceptionHandler({NotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<RestResponse<Object>> handleNotFoundException(RuntimeException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Resource Not Found", ex.getMessage());
    }

    // Xử lý lỗi khi tham số không hợp lệ
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errors = result.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Error", errors);
    }

    // Xử lý lỗi chung
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Object>> handleGlobalException(Exception ex) {
        logger.error("Unexpected error: ", ex); // Log lỗi để debug dễ hơn
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred.");
    }

    private ResponseEntity<RestResponse<Object>> buildResponse(HttpStatus status, String error, Object message) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(status.value());
        response.setError(error);
        response.setMessage(message);
        return ResponseEntity.status(status).body(response);
    }
}
