package net.ivanzykov.rssfeedarchiver.services;

import net.ivanzykov.rssfeedarchiver.controller.FeedService;

public interface Consumer {

    /**
     * Perform operations on the feed in order to consume it.
     *
     * @param feed feed object with data needed for this method. Result is also saved there
     */
    void consume(FeedService feed);
}
