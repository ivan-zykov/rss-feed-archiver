package net.ivanzykov.rssfeedarchiver.feed;

import com.rometools.rome.feed.synd.SyndFeed;

import java.util.ArrayList;
import java.util.List;

public class Feed {

    private final List<String> feedUrls;
    private final List<SyndFeed> fetchedFeeds;

    public Feed(List<String> feedUrls) {
        this.feedUrls = feedUrls;
        fetchedFeeds = new ArrayList<>();
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
}
