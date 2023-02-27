package net.ivanzykov.rssfeedarchiver.services;

import net.ivanzykov.rssfeedarchiver.controller.ManagerOfServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerOfServicesImpl implements ManagerOfServices {

    private final List<Consumer> consumers;
    private final FeedVOFactory feedVOFactory;

    /**
     * Constructor of this class.
     *
     * @param consumers     list of {@link Consumer} objects that do their part of the job to consume this feed
     * @param feedVOFactory feedVOFactory crating objects holding data for consumers
     */
    public ManagerOfServicesImpl(List<Consumer> consumers, FeedVOFactory feedVOFactory) {
        this.consumers = consumers;
        this.feedVOFactory = feedVOFactory;
    }

    /**
     * Calls components of this app.
     *
     * @param feedUrls  list of strings with URLs of the feeds to consume
     */
    @Override
    public void consumeUrls(List<String> feedUrls) {
        FeedVO feedVO = feedVOFactory.create(feedUrls);
        for (Consumer c : consumers) {
            c.consume(feedVO);
        }
    }
}
