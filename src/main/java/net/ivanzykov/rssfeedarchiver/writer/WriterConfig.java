package net.ivanzykov.rssfeedarchiver.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WriterConfig {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(Writer.class);
    }
}
