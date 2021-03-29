package com.dovendev.track.jpa.entities;

import java.util.Map;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "activities")
public class Activity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  public Activity() {}

  public Activity(String name) {
    this.name = name;
  }

  public static Activity fromMap(Map<String, Object> map) {
    if (map != null) {
      Activity activity = new Activity();
      activity.setId(map.get("id") != null ? Long.parseLong(String.valueOf(map.get("id"))) : null);
      activity.setName(Objects.toString(map.get("name"), null));
      return activity;
    }
    return null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
