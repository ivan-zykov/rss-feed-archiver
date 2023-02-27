package net.ivanzykov.rssfeedarchiver.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "entry")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Entry {
    @Id
    @Column(name = "id", nullable = false, length = 2083)
    private String guid;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "description", nullable = false, length = 4000)
    private String description;

    @Column(name = "link", nullable = false, length = 2083)
    private String link;

    @Column(name = "pub_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime pubDate;

    public OffsetDateTime getPubDate() {
        return pubDate;
    }

    public void setPubDate(OffsetDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Entry entry = (Entry) o;
        return guid != null && Objects.equals(guid, entry.guid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
