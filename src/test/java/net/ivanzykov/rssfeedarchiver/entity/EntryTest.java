package net.ivanzykov.rssfeedarchiver.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EntryTest {

    @Autowired
    private TestEntityManager entityManager;

    private Entry entry;
    private String guid;

    @BeforeEach
    void setUp() {
        entry = new Entry();
        guid = "1";
        entry.setGuid(guid);
        entry.setLink("testLink");
        entry.setTitle("testTitle");
        entry.setDescription("testDescr");
        entry.setPubDate(ZonedDateTime.now().toOffsetDateTime());
    }

    @Test
    void saveOneEntry_andGetTheSameOne() {
        entityManager.persistAndFlush(entry);

        Entry saved = entityManager.find(Entry.class, guid);

        assertAll(
                () -> assertEquals(entry.getGuid(), saved.getGuid()),
                () -> assertEquals(entry.getLink(), saved.getLink()),
                () -> assertEquals(entry.getTitle(), saved.getTitle()),
                () -> assertEquals(entry.getDescription(), saved.getDescription()),
                () -> assertEquals(entry.getPubDate(), saved.getPubDate())
        );
    }

    @Test
    void guidMissing_throwsException() {
        entry.setGuid(null);
        assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(entry));
    }

    @Test
    void linkMissing_throwsException() {
        entry.setLink(null);
        assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(entry));
    }

    @Test
    void titleMissing_throwsException() {
        entry.setTitle(null);
        assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(entry));
    }

    @Test
    void descriptionMissing_throwsException() {
        entry.setDescription(null);
        assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(entry));
    }
}
