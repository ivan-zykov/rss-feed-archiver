package net.ivanzykov.rssfeedarchiver.controller;

import net.ivanzykov.rssfeedarchiver.feed.Feed;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeedController {

    private final Feed feed;

    /**
     * Constructor of this class.
     *
     * @param feed  feed object holding data needed to consume RSS feeds, and calls other services of this app.
     */
    public FeedController(Feed feed) {
        this.feed = feed;
    }

    @PostMapping("/analyse/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void analyseNew(@RequestBody final List<String> feedUrls) {
        feed.addAllFeedUrls(feedUrls);
        feed.consumeSelf();
    }
}
