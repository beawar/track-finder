package com.dovendev.track.jpa.entities;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public enum TrackSort {
  ID_ASC(new Order(Direction.ASC, "id")),
  ID_DESC(new Order(Direction.DESC, "id")),
  UPLOAD_TIME_ASC(new Order(Direction.ASC, "uploadTime")),
  UPLOAD_TIME_DESC(new Order(Direction.DESC, "uploadTime")),
  TITLE_ASC(new Order(Direction.ASC, "title")),
  TITLE_DESC(new Order(Direction.DESC, "title")),
  TIME_ASC(new Order(Direction.ASC, "time")),
  TIME_DESC(new Order(Direction.DESC, "time")),
  LENGTH_ASC(new Order(Direction.ASC, "length")),
  LENGTH_DESC(new Order(Direction.DESC, "length")),
  ALT_DIFF_ASC(new Order(Direction.ASC, "altitudeDifference")),
  ALT_DIFF_DESC(new Order(Direction.DESC, "altitudeDifference")),
  ACTIVITY_NAME_ASC(new Order(Direction.ASC, "activity")),
  ACTIVITY_NAME_DESC(new Order(Direction.DESC,"activity"));

  private final Sort.Order sortOrder;

  public Order getSortOrder() {
    return sortOrder;
  }

  private TrackSort(Sort.Order sortOrder) {
    this.sortOrder = sortOrder;
  }
}
