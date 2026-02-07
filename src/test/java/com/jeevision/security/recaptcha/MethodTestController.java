package com.jeevision.security.recaptcha;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MethodTestController {

    @GetMapping("/test-method")
    @RecaptchaProtected
    public String testMethod() {
        return "method-protected";
    }

    @GetMapping("/test-no-annotation")
    public String testNoAnnotation() {
        return "no-protection";
    }
}