package net.ivanzykov.rssfeedarchiver.services;

import com.rometools.rome.feed.synd.SyndFeed;
import net.ivanzykov.rssfeedarchiver.entity.Entry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class FeedServiceConfig {

    @Bean
    public List<String> feedUrls() {
        return new ArrayList<>();
    }

    @Bean
    public List<SyndFeed> fetchedFeeds() {
        return new ArrayList<>();
    }

    @Bean
    public Set<Entry> entries() {
        return new HashSet<>();
    }
}
