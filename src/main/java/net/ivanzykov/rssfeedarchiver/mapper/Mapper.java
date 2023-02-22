package net.ivanzykov.rssfeedarchiver.mapper;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import net.ivanzykov.rssfeedarchiver.entity.Entry;
import net.ivanzykov.rssfeedarchiver.feed.Consumer;
import net.ivanzykov.rssfeedarchiver.feed.Feed;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class Mapper implements Consumer {

    private final ModelMapper modelMapper;

    /**
     * Constructor of this class.
     *
     * @param objectMapper  objectMapper object mapping XML entries to entities
     */
    public Mapper(ModelMapper objectMapper) {
        this.modelMapper = objectMapper;
    }

    /**
     * Maps XML entries from the supplied {@link Feed} object to entities of and saves them in the same object.
     *
     * @param feed  feed object holding fetched items. Result is also saved there
     */
    @Override
    public void consume(Feed feed) {
        for (SyndFeed syndFeed : feed.getFetchedFeeds()) {
            for (SyndEntry syndEntry : syndFeed.getEntries()) {
                Entry entry = mapToEntity(syndEntry);
                feed.addEntry(entry);
            }
        }
    }

    private Entry mapToEntity(SyndEntry syndEntry) {
        Converter<Date, OffsetDateTime> toOffsetDateTime = prop ->
                ZonedDateTime.ofInstant(prop.getSource().toInstant(), ZoneId.systemDefault()).toOffsetDateTime();
        modelMapper.typeMap(SyndEntryImpl.class, Entry.class).addMappings(mapper -> {
            mapper.map(SyndEntry::getUri, Entry::setGuid);
            mapper.map(src -> src.getDescription().getValue(), Entry::setDescription);
            mapper.using(toOffsetDateTime).map(SyndEntry::getPublishedDate, Entry::setPubDate);
        });
        return modelMapper.map(syndEntry, Entry.class);
    }
}
