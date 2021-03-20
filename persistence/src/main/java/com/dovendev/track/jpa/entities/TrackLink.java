package com.dovendev.track.jpa.entities;

import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "track_links")
public class TrackLink {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "track_id")
    private Long trackId;

    private String link;

    public TrackLink() { }

    public TrackLink(String link) {
        this.link = link;
    }

    public static TrackLink fromMap(@NotNull Map<String, Object> map) {
        TrackLink trackLink = new TrackLink();
        trackLink.setTrackId(map.get("track_id") != null ? Long.valueOf(String.valueOf(map.get("track_id"))) : null);
        trackLink.setLink(String.valueOf(map.get("link")));

        return trackLink;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrackLink trackLink = (TrackLink) o;
        return Objects.equals(id, trackLink.id) && Objects
            .equals(trackId, trackLink.trackId) && Objects.equals(link, trackLink.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trackId);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}