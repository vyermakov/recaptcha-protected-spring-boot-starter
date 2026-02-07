package com.jeevision.security.recaptcha;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.mkopylec.recaptcha.validation.RecaptchaValidator;

@Configuration
@Import(RecaptchaProtectedAutoConfiguration.class)
@ComponentScan(basePackages = "com.jeevision.security.recaptcha")
public class TestConfig {

    @Bean
    RecaptchaValidator recaptchaValidator() {
        return Mockito.mock(RecaptchaValidator.class);
    }
}