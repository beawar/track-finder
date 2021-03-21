package com.dovendev.track.jpa.entities;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "tracks")
public class Track {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;
  @Lob private String description;
  private double length;
  private Duration time;

  private int altitudeDifference;

  private OffsetDateTime uploadTime;

  @OneToOne private Activity activity;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "trackId", referencedColumnName = "id")
  private List<TrackLink> links;

  public static Track fromMap(@NotNull Map<String, Object> map) {
    Track track = new Track();
    track.setTitle(String.valueOf(map.get("title")));
    track.setDescription(Objects.toString(map.get("description"), null));
    track.setLength(Double.parseDouble(String.valueOf(map.getOrDefault("length", 0))));
    track.setTime(map.containsKey("time") ? Duration.parse(String.valueOf(map.get("time"))) : null);
    track.setAltitudeDifference(
        Integer.parseInt(String.valueOf(map.getOrDefault("altitudeDifference", 0))));
    track.setUploadTime(OffsetDateTime.now());
    track.setLinks(new ArrayList<>());
    if (map.containsKey("links") && map.get("links") instanceof List<?>) {
      List<TrackLink> links =
          ((List<?>) map.get("links"))
              .stream()
                  .map(
                      link -> {
                        if (link instanceof Map) {
                          Map<String, Object> linkMap =
                              ((Map<?, ?>) link)
                                  .entrySet().stream()
                                      .collect(
                                          Collectors.toMap(
                                              lk -> String.valueOf(lk.getKey()), Entry::getValue));
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

  public Duration getTime() {
    return time;
  }

  public void setTime(Duration time) {
    this.time = time;
  }

  public int getAltitudeDifference() {
    return altitudeDifference;
  }

  public void setAltitudeDifference(int altitudeDifference) {
    this.altitudeDifference = altitudeDifference;
  }

  public OffsetDateTime getUploadTime() {
    return uploadTime;
  }

  public void setUploadTime(OffsetDateTime uploadTime) {
    this.uploadTime = uploadTime;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Track track = (Track) o;

    if (Double.compare(track.length, length) != 0) {
      return false;
    }
    if (altitudeDifference != track.altitudeDifference) {
      return false;
    }
    if (!Objects.equals(id, track.id)) {
      return false;
    }
    if (!Objects.equals(title, track.title)) {
      return false;
    }
    if (!Objects.equals(description, track.description)) {
      return false;
    }
    if (!Objects.equals(time, track.time)) {
      return false;
    }
    if (!Objects.equals(uploadTime, track.uploadTime)) {
      return false;
    }
    if (!Objects.equals(activity, track.activity)) {
      return false;
    }
    return Objects.equals(links, track.links);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
