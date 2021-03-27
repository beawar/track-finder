package com.dovendev.track.jpa.entities;

import org.springframework.data.domain.Sort;

public enum SearchOperation {
  EQUALITY, NEGATION, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN, LESS_THAN_EQUAL, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS;

  public static SearchOperation getOperationFromSort(Sort.Direction sort) {
    return switch (sort) {
      case ASC -> GREATER_THAN_EQUAL;
      case DESC -> LESS_THAN_EQUAL;
    };
  }
}
