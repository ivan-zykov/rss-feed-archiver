package net.ivanzykov.rssfeedarchiver.services;

import com.rometools.rome.feed.synd.SyndFeed;
import net.ivanzykov.rssfeedarchiver.entity.Entry;

import java.util.List;
import java.util.Set;

/**
 * Holds data required to consume an RSS feed.
 */
public interface FeedVO {

    List<String> getFeedUrls();

    void addFetchedFeed(SyndFeed syndFeed);

    List<SyndFeed> getFetchedFeeds();

    void addEntry(Entry entry);

    Set<Entry> getEntries();
}
