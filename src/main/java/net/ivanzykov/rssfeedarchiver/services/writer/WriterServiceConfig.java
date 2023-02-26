package net.ivanzykov.rssfeedarchiver.services.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WriterServiceConfig {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(WriterService.class);
    }
}
