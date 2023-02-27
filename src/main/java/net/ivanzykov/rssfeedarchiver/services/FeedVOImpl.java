package net.ivanzykov.rssfeedarchiver.services;

import com.rometools.rome.feed.synd.SyndFeed;
import net.ivanzykov.rssfeedarchiver.entity.Entry;

import java.util.List;
import java.util.Set;

public class FeedVOImpl implements FeedVO {

    private final List<String> feedUrl;
    private final List<SyndFeed> fetchedFeeds;
    private final Set<Entry> entries;

    /**
     * Constructor of this class.
     *
     * @param feedUrl       list of Strings with URLs of feeds to consume
     * @param fetchedFeeds  list of SyndFeed objects to store fetched and parsed feeds
     * @param entries       set of Entries to write to the database
     */
    public FeedVOImpl(List<String> feedUrl, List<SyndFeed> fetchedFeeds, Set<Entry> entries) {
        this.feedUrl = feedUrl;
        this.fetchedFeeds = fetchedFeeds;
        this.entries = entries;
    }

    @Override
    public List<String> getFeedUrls() {
        return feedUrl;
    }

    @Override
    public void addFetchedFeed(SyndFeed syndFeed) {
        fetchedFeeds.add(syndFeed);
    }

    @Override
    public List<SyndFeed> getFetchedFeeds() {
        return fetchedFeeds;
    }

    @Override
    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    @Override
    public Set<Entry> getEntries() {
        return entries;
    }
}
