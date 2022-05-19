package io.github.xmchxup.backend.core;

import io.github.xmchxup.backend.core.configuration.ExceptionCodeConfiguration;
import io.github.xmchxup.backend.exception.http.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author huayang (sunhuayangak47@gmail.com)
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private ExceptionCodeConfiguration codeConfiguration;

    //	未知异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest req, Exception e) {
        String url = req.getRequestURI();
        String method = req.getMethod();

        return new UnifyResponse(9999, codeConfiguration.getMessage(9999), method + " " + url);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleUploadFileException(HttpServletRequest req, HttpException e) {
        String url = req.getRequestURI();
        String method = req.getMethod();

        return new UnifyResponse(30004, codeConfiguration.getMessage(30004), method + " " + url);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleAuthentication(HttpServletRequest req, HttpException e) {
        String url = req.getRequestURI();
        String method = req.getMethod();

        return new UnifyResponse(20008, codeConfiguration.getMessage(20008), method + " " + url);
    }

    //	已知异常
    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest req, HttpException e) {
        String url = req.getRequestURI();
        String method = req.getMethod();

        UnifyResponse message = new UnifyResponse(
                e.getCode(),
                codeConfiguration.getMessage(e.getCode()),
                method + " " + url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());

        return new ResponseEntity<>(message, headers, httpStatus);
    }

    // 参数校验失败 @Validated @Valid
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyResponse handleBeanValidation(HttpServletRequest req,
                                              MethodArgumentNotValidException e) {
        String url = req.getRequestURI();
        String method = req.getMethod();

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = formatAllErrorMessage(errors);
        return new UnifyResponse(10001, message, method + " " + url);

    }

    // @NotBlank @NotNull @NotEmpty
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyResponse handleConstraintException(HttpServletRequest req,
                                                   ConstraintViolationException e) {
        String url = req.getRequestURI();

        String method = req.getMethod();

//		特殊处理
        StringBuilder errorMsg = new StringBuilder();
        for (ConstraintViolation<?> error : e.getConstraintViolations()) {
            String msg = error.getMessage();
            String m = error.getPropertyPath().toString();
            String name = m.split("[.]")[1];
            errorMsg.append(name).append(": ").append(msg).append(";");
        }

//		默认message
        String message = e.getMessage();
        return new UnifyResponse(10001, errorMsg.toString(), method + " " + url);
    }

    private String formatAllErrorMessage(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error ->
                errorMsg.append(error.getDefaultMessage()).append(";")
        );
        return errorMsg.toString();
    }

    /**
     * AccessDeniedException无权限异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public UnifyResponse exceptionHandler(HttpServletRequest req, AccessDeniedException e) {
        log.warn("认证用户，没有权限访问! Message - {}", e.getMessage());
        String url = req.getRequestURI();
        String method = req.getMethod();
        return new UnifyResponse(10001, e.getMessage(), method + " " + url);
    }
}