package net.ivanzykov.rssfeedarchiver.services;

public interface Consumer {

    /**
     * Perform operations on the feed in order to consume it.
     *
     * @param feed feedVO object with data needed for this method. Result is also saved there
     */
    void consume(FeedVO feed);
}
