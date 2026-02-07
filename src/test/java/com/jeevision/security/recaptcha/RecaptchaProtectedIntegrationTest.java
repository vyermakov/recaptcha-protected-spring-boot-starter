package com.jeevision.security.recaptcha;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.github.mkopylec.recaptcha.validation.RecaptchaValidator;
import com.github.mkopylec.recaptcha.validation.ValidationResult;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class RecaptchaProtectedIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecaptchaValidator validator;

    @Test
    void testValidRecaptcha() throws Exception {
        when(validator.validate(anyString(), anyString())).thenReturn(new ValidationResult(true, Collections.emptyList()));

        mockMvc.perform(get("/test").param("recaptcha", "valid-response"))
            .andExpect(status().isOk())
            .andExpect(content().string("success"));
    }

    @Test
    void testMissingRecaptcha() throws Exception {
        mockMvc.perform(get("/test"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testBlankRecaptcha() throws Exception {
        mockMvc.perform(get("/test").param("recaptcha", ""))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidRecaptcha() throws Exception {
        when(validator.validate(anyString(), anyString())).thenReturn(new ValidationResult(false, Collections.emptyList()));

        mockMvc.perform(get("/test").param("recaptcha", "invalid-response"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testMethodLevelAnnotationValid() throws Exception {
        when(validator.validate(anyString(), anyString())).thenReturn(new ValidationResult(true, Collections.emptyList()));

        mockMvc.perform(get("/test-method").param("recaptcha", "valid-response"))
            .andExpect(status().isOk())
            .andExpect(content().string("method-protected"));
    }

    @Test
    void testMethodLevelAnnotationMissing() throws Exception {
        mockMvc.perform(get("/test-method"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testNoAnnotation() throws Exception {
        mockMvc.perform(get("/test-no-annotation"))
            .andExpect(status().isOk())
            .andExpect(content().string("no-protection"));
    }
}