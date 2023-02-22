package net.ivanzykov.rssfeedarchiver.feed;

import com.rometools.rome.feed.synd.SyndFeed;
import net.ivanzykov.rssfeedarchiver.entity.Entry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Holds URLs of RSS feeds and other data needed to consume the feeds, and calls other components of this app.
 */
@Service
public class Feed {

    private final List<String> feedUrls;
    private final List<SyndFeed> fetchedFeeds;
    private final Set<Entry> entries;

    /**
     * Constructor of this class.
     *
     * @param feedUrls  list of strings with URLs of RSS feeds to consume
     */
    public Feed(List<String> feedUrls) {
        this.feedUrls = feedUrls;
        fetchedFeeds = new ArrayList<>();
        entries = new HashSet<>();
    }

    public List<String> getFeedUrls() {
        return feedUrls;
    }

    public void addFetchedFeed(SyndFeed feed) {
        fetchedFeeds.add(feed);
    }

    public List<SyndFeed> getFetchedFeeds() {
        return fetchedFeeds;
    }

    public Set<Entry> getEntries() {
        return entries;
    }

    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }
}
