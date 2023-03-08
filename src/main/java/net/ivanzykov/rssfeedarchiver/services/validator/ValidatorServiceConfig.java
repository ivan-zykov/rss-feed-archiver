package net.ivanzykov.rssfeedarchiver.services.validator;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorServiceConfig {

    @Bean
    public UrlValidator urlValidator() {
        return new UrlValidator(new String[]{"http","https"}, UrlValidator.ALLOW_LOCAL_URLS);
    }
}
