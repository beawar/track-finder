package com.dovendev.track.jpa.entities;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "tracks")
public class Track {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private double length;
    private Time time;
    @Column(name = "altitude_difference")
    private int altitudeDifference;
    private Timestamp upload_time;
    @OneToOne
    private Activity activity;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "track_id", referencedColumnName = "id")
    private List<TrackLink> links;

    public static Track fromMap(@NotNull Map<String, Object> map) {
        Track track = new Track();
        track.setTitle(String.valueOf(map.get("title")));
        track.setDescription(Objects.toString(map.get("description")));
        track.setLength(Double.parseDouble(String.valueOf(map.getOrDefault("length", 0))));
        track.setTime(new Time(Integer.parseInt(String.valueOf(map.getOrDefault("time", 0)))));
        track.setAltitudeDifference(Integer.parseInt(String.valueOf(map.getOrDefault("altitude_difference", 0))));
        track.setLinks(new ArrayList<>());
        if (map.containsKey("links") && map.get("links") instanceof List<?>) {
            List<TrackLink> links = ((List<?>) map.get("links"))
                    .stream()
                    .map(link -> new TrackLink(String.valueOf(link)))
                    .collect(Collectors.toList());
            track.getLinks().addAll(links);
        }
        return track;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getAltitudeDifference() {
        return altitudeDifference;
    }

    public void setAltitudeDifference(int altitudeDifference) {
        this.altitudeDifference = altitudeDifference;
    }

    public Timestamp getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Timestamp upload_time) {
        this.upload_time = upload_time;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<TrackLink> getLinks() {
        return links;
    }

    public void setLinks(List<TrackLink> links) {
        this.links = links;
    }
}
