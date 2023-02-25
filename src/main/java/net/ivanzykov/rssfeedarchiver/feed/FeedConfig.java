package net.ivanzykov.rssfeedarchiver.feed;

import com.rometools.rome.feed.synd.SyndFeed;
import net.ivanzykov.rssfeedarchiver.entity.Entry;
import net.ivanzykov.rssfeedarchiver.fetcher.Fetcher;
import net.ivanzykov.rssfeedarchiver.mapper.Mapper;
import net.ivanzykov.rssfeedarchiver.writer.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class FeedConfig {

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

    @Bean
    public List<Consumer> consumers(
            @Autowired Fetcher fetcher,
            @Autowired Mapper mapper,
            @Autowired Writer writer) {
        return List.of(fetcher, mapper, writer);
    }
}
