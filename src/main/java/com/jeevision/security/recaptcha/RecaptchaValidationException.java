package com.jeevision.security.recaptcha;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class RecaptchaValidationException extends ResponseStatusException {
    public RecaptchaValidationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public RecaptchaValidationException(String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, cause);
    }
}