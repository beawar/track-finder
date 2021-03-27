package com.dovendev.track.jpa.predicates;

import com.dovendev.track.jpa.entities.Track;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class TrackPredicate {

  private SearchCriteria criteria;

  public TrackPredicate() {
  }

  public TrackPredicate(SearchCriteria criteria) {
    this.criteria = criteria;
  }

  public BooleanExpression getPredicate() {
    PathBuilder<Track> entityPath = new PathBuilder<>(Track.class, "track");
    final String criteriaValue = criteria.getValue().toString();
    if (isLong(criteriaValue)) {
      NumberPath<Long> path = entityPath.getNumber(criteria.getKey(), Long.class);
      Long value = Long.valueOf(criteria.getValue().toString());
      return switch (criteria.getOperation()) {
        case EQUALITY -> path.eq(value);
        case NEGATION -> path.ne(value);
        case GREATER_THAN -> path.gt(value);
        case GREATER_THAN_EQUAL -> path.goe(value);
        case LESS_THAN -> path.lt(value);
        case LESS_THAN_EQUAL -> path.loe(value);
        default -> null;
      };

    } else if (isDouble(criteriaValue)) {
      NumberPath<Double> path = entityPath.getNumber(criteria.getKey(), Double.class);
      Double value = Double.valueOf(criteria.getValue().toString());
      return switch (criteria.getOperation()) {
        case EQUALITY -> path.eq(value);
        case NEGATION -> path.ne(value);
        case GREATER_THAN -> path.gt(value);
        case GREATER_THAN_EQUAL -> path.goe(value);
        case LESS_THAN -> path.lt(value);
        case LESS_THAN_EQUAL -> path.loe(value);
        default -> null;
      };
    } else if (isDateTime(criteriaValue)) {
      DateTimePath<OffsetDateTime> path = entityPath.getDateTime(criteria.getKey(), OffsetDateTime.class);
      OffsetDateTime value = OffsetDateTime.parse(criteriaValue);
      return switch (criteria.getOperation()) {
        case EQUALITY -> path.eq(value);
        case NEGATION -> path.ne(value);
        case GREATER_THAN -> path.gt(value);
        case GREATER_THAN_EQUAL -> path.goe(value);
        case LESS_THAN -> path.lt(value);
        case LESS_THAN_EQUAL -> path.loe(value);
        default -> null;
      };
    } else if (isDuration(criteriaValue)) {
      ComparablePath<Duration> path = entityPath.getComparable(criteria.getKey(), Duration.class);
      Duration value = Duration.parse(criteriaValue);
      return switch (criteria.getOperation()) {
        case EQUALITY -> path.eq(value);
        case NEGATION -> path.ne(value);
        case GREATER_THAN -> path.gt(value);
        case GREATER_THAN_EQUAL -> path.goe(value);
        case LESS_THAN -> path.lt(value);
        case LESS_THAN_EQUAL -> path.loe(value);
        default -> null;
      };
    } else {
      StringPath path = entityPath.getString(criteria.getKey());
      String value = criteria.getValue().toString();
      return switch (criteria.getOperation()) {
        case EQUALITY -> path.equalsIgnoreCase(value);
        case NEGATION -> path.notEqualsIgnoreCase(value);
        case GREATER_THAN -> path.gt(value);
        case GREATER_THAN_EQUAL -> path.goe(value);
        case LESS_THAN -> path.lt(value);
        case LESS_THAN_EQUAL -> path.loe(value);
        case LIKE -> path.likeIgnoreCase(value);
        case CONTAINS -> path.containsIgnoreCase(value);
        case STARTS_WITH -> path.startsWithIgnoreCase(value);
        case ENDS_WITH -> path.endsWithIgnoreCase(value);
      };
    }
  }

  public boolean isLong(String value){
    if(value == null){
      return false;
    }
    try {
      Long result = Long.parseLong(value);
      return true;
    } catch (NumberFormatException ne){
      return false;
    }
  }
  public boolean isDouble(String value){
    if(value == null){
      return false;
    }
    try {
      Double result = Double.parseDouble(value);
      return true;
    } catch (NumberFormatException ne){
      return false;
    }
  }

  public boolean isDateTime(String value) {
    if (value == null) {
      return false;
    }
    try {
      OffsetDateTime.parse(value);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public boolean isDuration(String value) {
    if (value == null) {
      return false;
    }
    try {
      Duration.parse(value);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }
}
