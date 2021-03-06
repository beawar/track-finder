package com.dovendev.track.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "track_links")
public class TrackLink {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "track_id")
    private long trackId;

    String link;

    public TrackLink(String link) {
        this.link = link;
    }

    //getter
    public Long getId() {
        return id;
    }
    public String getLink() {
        return link;
    }

    // setter
    public void setId(Long id) {
        this.id = id;
    }
    public void setLink(String link) {
        this.link = link;
    }
}