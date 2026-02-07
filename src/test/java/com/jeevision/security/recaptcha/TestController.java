package com.jeevision.security.recaptcha;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RecaptchaProtected
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "success";
    }
}