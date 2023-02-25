package net.ivanzykov.rssfeedarchiver.feed;

import com.rometools.rome.feed.synd.SyndFeed;
import net.ivanzykov.rssfeedarchiver.entity.Entry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Holds data needed to consume RSS feeds, and calls other components of this app.
 */
@Service
public class Feed {

    private final List<String> feedUrls;
    private final List<SyndFeed> fetchedFeeds;
    private final Set<Entry> entries;
    private final List<Consumer> consumers;

    /**
     * Constructor of this class.
     *
     * @param feedUrls      list of strings with URLs of RSS feeds to consume
     * @param fetchedFeeds  list of {@link SyndFeed} objects to store parsed XML entries
     * @param entries       list of {@link Entry} objects to store mapped entries
     * @param consumers     list of {@link Consumer} objects that do their part of the job to consume this feed
     */
    public Feed(List<String> feedUrls, List<SyndFeed> fetchedFeeds, Set<Entry> entries, List<Consumer> consumers) {
        this.feedUrls = feedUrls;
        this.fetchedFeeds = fetchedFeeds;
        this.entries = entries;
        this.consumers = consumers;
    }

    /**
     * Calls components of this app.
     */
    public void consumeSelf() {
        for (Consumer c : consumers) {
            c.consume(this);
        }
    }

    public List<String> getFeedUrls() {
        return feedUrls;
    }

    public void addAllFeedUrls(List<String> reedUrls) {
        feedUrls.addAll(reedUrls);
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
