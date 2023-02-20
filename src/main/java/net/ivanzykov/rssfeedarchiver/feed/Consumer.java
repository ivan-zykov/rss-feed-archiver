package net.ivanzykov.rssfeedarchiver.feed;

public interface Consumer {

    /**
     * Perform operations on the feed in order to consume it.
     *
     * @param feed feed object with data needed for this method. Result is also saved there
     */
    void consume(Feed feed);
}
