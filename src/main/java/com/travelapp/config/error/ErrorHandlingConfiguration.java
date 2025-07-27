package com.travelapp.config.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.UUID;

@Configuration
public class ErrorHandlingConfiguration {

    @Bean
    public static MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    @Primary
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

                // Remove sensitive information
                errorAttributes.remove("exception");
                errorAttributes.remove("trace");

                // Add trace ID
                errorAttributes.put("traceId", UUID.randomUUID().toString().substring(0, 8));

                return errorAttributes;
            }
        };
    }
}
