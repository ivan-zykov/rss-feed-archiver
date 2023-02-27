package net.ivanzykov.rssfeedarchiver.services.validator;

import net.ivanzykov.rssfeedarchiver.controller.FeedService;
import net.ivanzykov.rssfeedarchiver.services.Consumer;
import net.ivanzykov.rssfeedarchiver.services.FeedVO;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
public class ValidatorService implements Consumer {

    private final UrlValidator urlValidator;

    /**
     * Constructor of this class.
     *
     * @param urlValidator  urlValidator object which handles validation
     */
    public ValidatorService(UrlValidator urlValidator) {
        this.urlValidator = urlValidator;
    }

    /**
     * Validates feed URLs in the {@link FeedService} object
     *
     * @param feed feedVO object with data needed for this method. Result is also saved there
     */
    @Override
    public void consume(FeedVO feed) {
        for (String url : feed.getFeedUrls()) {
            if (!urlValidator.isValid(url)) {
                throw new InvalidFeedUrlException("Following feed URL is invalid: " + url);
            }
        }
    }
}
