package net.ivanzykov.rssfeedarchiver.mapper;

import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import net.ivanzykov.rssfeedarchiver.entity.Entry;
import net.ivanzykov.rssfeedarchiver.feed.Feed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
class MapperTest {

    @Autowired
    private Mapper mapper;

    @Test
    void consume() {
        String url1 = "/testUrl1";
        var feed = new Feed(List.of(url1));

        var sEntry = new SyndEntryImpl();
        String guid = "testUri";
        sEntry.setUri(guid);
        String title = "testTitle";
        sEntry.setTitle(title);
        String description = "testDescription";
        var sContent = new SyndContentImpl();
        sContent.setValue(description);
        sEntry.setDescription(sContent);
        String link = "testLink";
        sEntry.setLink(link);
        ZonedDateTime pubDate = ZonedDateTime.of(2023, 2, 22, 12, 0, 0,
                0, ZoneId.of("CET"));
        sEntry.setPublishedDate(Date.from(pubDate.toInstant()));

        var sFeed = new SyndFeedImpl();
        sFeed.setEntries(List.of(sEntry));
        feed.addFetchedFeed(sFeed);

        mapper.consume(feed);

        var entry = new Entry();
        entry.setGuid(guid);
        entry.setTitle(title);
        entry.setDescription(description);
        entry.setLink(link);
        entry.setPubDate(pubDate.toOffsetDateTime());

        assertEquals(Set.of(entry), feed.getEntries());
    }
}
