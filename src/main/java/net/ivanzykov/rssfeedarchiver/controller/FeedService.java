package net.ivanzykov.rssfeedarchiver.controller;

import com.rometools.rome.feed.synd.SyndFeed;
import net.ivanzykov.rssfeedarchiver.entity.Entry;

import java.util.List;
import java.util.Set;

public interface FeedService {

    void consumeUrls(List<String> feedUrls);

    List<String> getFeedUrls();

    void addAllFeedUrls(List<String> reedUrls);

    void addFetchedFeed(SyndFeed syndFeed);

    List<SyndFeed> getFetchedFeeds();

    void addEntry(Entry entry);

    Set<Entry> getEntries();
}
