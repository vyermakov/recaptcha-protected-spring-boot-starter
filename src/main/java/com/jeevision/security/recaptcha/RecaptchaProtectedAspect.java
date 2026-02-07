package com.jeevision.security.recaptcha;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.mkopylec.recaptcha.validation.RecaptchaValidator;

@Aspect
@Component
@ConditionalOnProperty(prefix = "recaptcha", name = "enabled", havingValue = "true")
public class RecaptchaProtectedAspect {

    private final RecaptchaValidator validator;
    private final RecaptchaProtectedProperties properties;

    public RecaptchaProtectedAspect(RecaptchaValidator validator, RecaptchaProtectedProperties properties) {
        this.validator = validator;
        this.properties = properties;
    }

    @Around("(@annotation(com.jeevision.security.recaptcha.RecaptchaProtected) || @within(com.jeevision.security.recaptcha.RecaptchaProtected)) && execution(* *(..))")
    public Object validateRecaptcha(ProceedingJoinPoint joinPoint) throws Throwable {
        var attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        var request = attributes.getRequest();

        var paramName = properties.validation().requestParamName();
        var paramValue = request.getParameter(paramName);
        var response = ofNullable(paramValue)
            .filter(not(String::isBlank))
            .orElseThrow(() -> new RecaptchaValidationException("reCAPTCHA response is missing"));

        var remoteIp = request.getRemoteAddr();
        var result = validator.validate(response, remoteIp);
        if (!result.isSuccess()) {
            throw new RecaptchaValidationException("reCAPTCHA validation failed");
        }

        return joinPoint.proceed();
    }
}