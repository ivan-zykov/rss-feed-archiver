package net.ivanzykov.rssfeedarchiver.writer;

import net.ivanzykov.rssfeedarchiver.entity.Entry;
import net.ivanzykov.rssfeedarchiver.feed.FeedServiceImpl;
import net.ivanzykov.rssfeedarchiver.repository.EntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(OutputCaptureExtension.class)
class WriterServiceTest {

    @Autowired
    private WriterService writerService;

    @Autowired
    private EntryRepository entryRepository;

    @Test
    void consume_oneBadAndOneOkEntries_goodIsPersistedAndBadIsLogged(CapturedOutput output) {
        var entryOk = new Entry();
        String guid  = "1";
        entryOk.setGuid(guid);
        entryOk.setLink("testLink");
        entryOk.setTitle("testTitle");
        entryOk.setDescription("testDescr");
        entryOk.setPubDate(ZonedDateTime.now().toOffsetDateTime());
        var entryBad = new Entry();

        var feed = new FeedServiceImpl(List.of("/testUrl"), new ArrayList<>(), new HashSet<>(), new ArrayList<>());
        feed.addEntry(entryBad);
        feed.addEntry(entryOk);

        assertTrue(entryRepository.findAll().isEmpty());

        writerService.consume(feed);

        List<Entry> allSaved = entryRepository.findAll();

        assertAll(
                () -> assertEquals(1, allSaved.size()),
                () -> assertEquals(entryOk.getGuid(), allSaved.get(0).getGuid()),
                () -> assertEquals(entryOk.getPubDate(), allSaved.get(0).getPubDate()),
                () -> assertEquals(entryOk.getLink(), allSaved.get(0).getLink()),
                () -> assertEquals(entryOk.getDescription(), allSaved.get(0).getDescription()),
                () -> assertEquals(entryOk.getTitle(), allSaved.get(0).getTitle()),
                () -> assertTrue(output.getOut().contains("Entry with guid: null is missing at least one required property."))
        );

        // Clean up
        entryRepository.deleteAll();
    }
}
