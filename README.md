# Recaptcha Protected Spring Boot Starter

A Spring Boot starter that provides AOP-based reCAPTCHA protection for REST controllers and methods using the `@RecaptchaProtected` annotation.

## Features

- **AOP Integration**: Uses Aspect-Oriented Programming to intercept annotated methods.
- **Flexible Protection**: Supports both class-level and method-level annotations.
- **Auto-Configuration**: Automatically configures reCAPTCHA validation when the starter is included.
- **HTTP Status Codes**: Returns `400 Bad Request` for invalid or missing reCAPTCHA responses.
- **Spring Boot 3 Compatible**: Built for modern Spring Boot applications.

## Requirements

- Java 17+
- Spring Boot 3.x
- Maven or Gradle

## Installation

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.jeevision.security.recaptcha</groupId>
    <artifactId>recapcha-protected-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

For Gradle:

```gradle
implementation 'com.jeevision.security.recaptcha:recapcha-protected-spring-boot-starter:0.0.1-SNAPSHOT'
```

## Configuration

Configure reCAPTCHA settings in your `application.yml`:

```yaml
recaptcha:
  enabled: true
  validation:
    request-param-name: recaptcha
    site-key: your-google-recaptcha-site-key
    secret-key: your-google-recaptcha-secret-key
```

### Properties

- `recaptcha.enabled`: Enable or disable reCAPTCHA validation (default: `true`)
- `recaptcha.validation.request-param-name`: The request parameter name for the reCAPTCHA response (default: `recaptcha`)
- `recaptcha.validation.site-key`: Your Google reCAPTCHA site key
- `recaptcha.validation.secret-key`: Your Google reCAPTCHA secret key

**Note**: To obtain your site key and secret key, visit the [Google reCAPTCHA Admin Console](https://www.google.com/recaptcha/admin).

## Usage

### 1. Annotate Controllers (Class-Level Protection)

Protect all methods in a controller:

```java
import com.jeevision.security.recaptcha.RecaptchaProtected;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RecaptchaProtected
public class MyController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This endpoint is protected by reCAPTCHA";
    }

    @PostMapping("/submit")
    public String submitForm() {
        return "Form submitted successfully";
    }
}
```

### 2. Annotate Individual Methods (Method-Level Protection)

Protect specific methods only:

```java
import com.jeevision.security.recaptcha.RecaptchaProtected;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This endpoint is not protected";
    }

    @PostMapping("/submit")
    @RecaptchaProtected
    public String submitForm() {
        return "Form submitted successfully";
    }
}
```

### 3. Client-Side Integration

Include the reCAPTCHA response in your requests. For example, in a form submission:

```html
<form action="/api/submit" method="post">
    <!-- Your form fields -->
    <input type="hidden" name="recaptcha" value="recaptcha-response-token">
    <button type="submit">Submit</button>
</form>
```

Or in AJAX requests:

```javascript
fetch('/api/submit', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify({
        // your data
        recaptcha: 'recaptcha-response-token'
    })
});
```

## How It Works

1. When a request hits an annotated method, the AOP aspect intercepts it.
2. The aspect checks for the reCAPTCHA response parameter.
3. If missing or blank, it throws a `RecaptchaValidationException` (HTTP 400).
4. If present, it validates the response using Google's reCAPTCHA API.
5. If invalid, it throws a `RecaptchaValidationException` (HTTP 400).
6. If valid, the method proceeds normally.

## Error Handling

Invalid or missing reCAPTCHA responses result in:
- **HTTP Status**: 400 Bad Request
- **Response Body**: Error message (e.g., "reCAPTCHA response is missing" or "reCAPTCHA validation failed")

You can customize error handling by configuring Spring's `@ControllerAdvice`.

## Example Project

See the test classes in `src/test/java` for complete examples of usage.

## Contributing

Contributions are welcome! Please submit issues and pull requests.

## License

This project is licensed under the MIT License.