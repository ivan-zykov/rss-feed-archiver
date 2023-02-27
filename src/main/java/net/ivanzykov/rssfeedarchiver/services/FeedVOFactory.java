package net.ivanzykov.rssfeedarchiver.services;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class FeedVOFactory {

    public FeedVO create(List<String> feedUrls) {
        return new FeedVOImpl(feedUrls, new ArrayList<>(), new HashSet<>());
    }
}
