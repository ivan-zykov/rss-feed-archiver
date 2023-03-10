package net.ivanzykov.rssfeedarchiver.services.fetcher;

public class FetcherException extends RuntimeException {

    /**
     * For errors while fetching a feed.
     *
     * @param message string with text describing the error
     */
    public FetcherException(String message) {
        super(message);
    }
}
