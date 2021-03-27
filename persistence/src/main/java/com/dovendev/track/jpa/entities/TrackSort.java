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
  TITLE_DESC(new Order(Direction.DESC, "title"));


  private final Sort.Order sortOrder;

  public Order getSortOrder() {
    return sortOrder;
  }

  private TrackSort(Sort.Order sortOrder) {
    this.sortOrder = sortOrder;
  }
}
