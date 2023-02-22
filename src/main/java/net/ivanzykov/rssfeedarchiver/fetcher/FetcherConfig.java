package net.ivanzykov.rssfeedarchiver.fetcher;

import com.rometools.rome.io.SyndFeedInput;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FetcherConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SyndFeedInput syndFeedInput() {
        return new SyndFeedInput();
    }

    @Bean
    public XmlReaderRomeFactory xmlReaderRomeFactory() {
        return new XmlReaderRomeFactory();
    }
}
