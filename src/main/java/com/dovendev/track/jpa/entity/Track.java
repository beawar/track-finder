package com.dovendev.track.jpa.entity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "tracks")
public class Track {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private double length;
    private Time time;
    private int altitude_difference;
    private Timestamp upload_time;
    private Long activity_id;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "track_id", referencedColumnName = "id")
    private List<TrackLink> links;

    public Track() {}

    public Track(String title){
        this.title = title;
    }

    // getter
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public double getLength() {
        return length;
    }
    public Time getTime() {
        return time;
    }
    public int getAltitudeDifference() {
        return altitude_difference;
    }
    public Timestamp getUploadTime() {
        return upload_time;
    }
    public Long getActivityId() {
        return activity_id;
    }
    public List<TrackLink> getLinks() {
        return links;
    }

    //setter
    public void setTitle(String title) {
        this.title =  title;
    }
    public void setDescription(String description) {
        this.description =  description;
    }
    public void setLength(double length) {
        this.length =  length;
    }
    public void setTime(Time time) {
        this.time =  time;
    }
    public void setAltitudeDifference(int altitude_difference) {
        this.altitude_difference =  altitude_difference;
    }
    public void setUploadTime(Timestamp upload_time) {
        this.upload_time =  upload_time;
    }
    public void setActivityId(Long activity_id) {
        this.activity_id = activity_id;
    }
    public void setLinks(List<TrackLink> links) {
        this.links = links;
    }
}
