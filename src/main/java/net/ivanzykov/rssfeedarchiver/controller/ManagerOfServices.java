package net.ivanzykov.rssfeedarchiver.controller;

import java.util.List;

public interface ManagerOfServices {

    void consumeUrls(List<String> feedUrls);
}
