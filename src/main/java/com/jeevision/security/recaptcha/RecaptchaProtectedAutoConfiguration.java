package com.jeevision.security.recaptcha;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableConfigurationProperties(RecaptchaProtectedProperties.class)
@ConditionalOnProperty(prefix = "recaptcha", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableAspectJAutoProxy
public class RecaptchaProtectedAutoConfiguration {
}