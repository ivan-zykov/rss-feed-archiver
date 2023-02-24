package net.ivanzykov.rssfeedarchiver.writer;

import net.ivanzykov.rssfeedarchiver.entity.Entry;
import net.ivanzykov.rssfeedarchiver.feed.Consumer;
import net.ivanzykov.rssfeedarchiver.feed.Feed;
import net.ivanzykov.rssfeedarchiver.repository.EntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

@Service
public class Writer implements Consumer {

    private final EntryRepository entryRepository;
    private final Logger logger = LoggerFactory.getLogger(Writer.class);

    /**
     * Constructor of this class.
     *
     * @param entryRepository entryRepository object of JPA repository for the {@link Entry} entity
     */
    public Writer(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    /**
     * Gets {@link Entry} objects from the supplied {@link Feed} object and tries to persist each entry. If persisting
     * an entry fails, error is logged and persisting of the left entries continues.
     *
     * @param feed feed object with data needed for this method
     */
    @Override
    public void consume(Feed feed) {
        for (Entry entry : feed.getEntries()) {
            try {
                entryRepository.save(entry);
            } catch (JpaSystemException | DataIntegrityViolationException e) {
                logger.error("Entry with guid: {} is missing at least one required property. {}",
                        entry.getGuid(), e.getMessage());
            }
        }
    }
}