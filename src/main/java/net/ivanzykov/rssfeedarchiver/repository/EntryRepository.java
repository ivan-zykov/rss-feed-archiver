package net.ivanzykov.rssfeedarchiver.repository;

import net.ivanzykov.rssfeedarchiver.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, String> {
}
