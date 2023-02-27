package net.ivanzykov.rssfeedarchiver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeedController {

    private final ManagerOfServices managerOfServices;

    /**
     * Constructor of this class.
     *
     * @param managerOfServices managerOfServices object triggering services
     */
    public FeedController(ManagerOfServices managerOfServices) {
        this.managerOfServices = managerOfServices;
    }

    @PostMapping("/analyse/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void analyseNew(@RequestBody final List<String> feedUrls) {
        managerOfServices.consumeUrls(feedUrls);
    }
}
