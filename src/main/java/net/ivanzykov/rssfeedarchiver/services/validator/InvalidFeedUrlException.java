package net.ivanzykov.rssfeedarchiver.services.validator;

/**
 * For invalid feed URL.
 */
public class InvalidFeedUrlException extends RuntimeException {
    public InvalidFeedUrlException(String messase) {
        super(messase);
    }
}
