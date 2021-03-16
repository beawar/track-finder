package com.dovendev.track.jpa.entities;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "tracks")
public class Track {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private double length;
    private OffsetTime time;
    @Column(name = "altitude_difference")
    private int altitudeDifference;
    private OffsetDateTime upload_time;
    @OneToOne
    private Activity activity;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "track_id", referencedColumnName = "id")
    private List<TrackLink> links;

    public static Track fromMap(@NotNull Map<String, Object> map) {
        Track track = new Track();
        track.setTitle(String.valueOf(map.get("title")));
        track.setDescription(Objects.toString(map.get("description"), null));
        track.setLength(Double.parseDouble(String.valueOf(map.getOrDefault("length", 0))));
        LocalTime parsedDate = LocalTime.parse("23:14:36", DateTimeFormatter.ISO_LOCAL_TIME);
        track.setTime(OffsetTime.of(parsedDate, ZoneOffset.UTC));
        track.setAltitudeDifference(Integer.parseInt(String.valueOf(map.getOrDefault("altitude_difference", 0))));
        track.setUpload_time(OffsetDateTime.now());
        track.setLinks(new ArrayList<>());
        if (map.containsKey("links") && map.get("links") instanceof List<?>) {
            List<TrackLink> links = ((List<?>) map.get("links"))
                    .stream()
                    .map(link -> {
                        if (link instanceof Map){
                            Map<String, Object> linkMap = ((Map<?, ?>) link).entrySet()
                                .stream()
                                .collect(Collectors.toMap(lk -> String.valueOf(lk.getKey()),
                                    Entry::getValue));
                            return TrackLink.fromMap(linkMap);
                        }
                        return null;
                    })
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

    public OffsetTime getTime() {
        return time;
    }

    public void setTime(OffsetTime time) {
        this.time = time;
    }

    public int getAltitudeDifference() {
        return altitudeDifference;
    }

    public void setAltitudeDifference(int altitudeDifference) {
        this.altitudeDifference = altitudeDifference;
    }

    public OffsetDateTime getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(OffsetDateTime upload_time) {
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
