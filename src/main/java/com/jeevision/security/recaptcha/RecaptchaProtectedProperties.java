package com.jeevision.security.recaptcha;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "recaptcha")
public record RecaptchaProtectedProperties(boolean enabled, Validation validation) {
	
	public RecaptchaProtectedProperties() {
		this(true, new Validation());
	}

	public record Validation(String requestParamName, String siteKey, String secretKey) {
		public Validation() {
			this("recaptcha", null, null);
		}
	}
}