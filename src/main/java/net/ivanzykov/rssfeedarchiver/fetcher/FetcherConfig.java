package net.ivanzykov.rssfeedarchiver.fetcher;

import com.rometools.rome.io.SyndFeedInput;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FetcherConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public SyndFeedInput syndFeedInput() {
        return new SyndFeedInput();
    }
}
