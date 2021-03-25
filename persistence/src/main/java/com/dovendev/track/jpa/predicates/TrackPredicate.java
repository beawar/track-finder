package com.dovendev.track.jpa.predicates;

import com.dovendev.track.jpa.entities.Track;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

public class TrackPredicate {

  private SearchCriteria criteria;

  public TrackPredicate() {
  }

  public TrackPredicate(SearchCriteria criteria) {
    this.criteria = criteria;
  }

  public BooleanExpression getPredicate() {
    PathBuilder<Track> entityPath = new PathBuilder<>(Track.class, "track");

    if (isNumeric(criteria.getValue().toString())) {
      NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
      int value = Integer.parseInt(criteria.getValue().toString());
      switch (criteria.getOperation()) {
        case ":":
          return path.eq(value);
        case ">":
          return path.goe(value);
        case "<":
          return path.loe(value);
      }
    }
    else {
      StringPath path = entityPath.getString(criteria.getKey());
      if (criteria.getOperation().equalsIgnoreCase(":")) {
        return path.containsIgnoreCase(criteria.getValue().toString());
      }
    }
    return null;
  }

  public boolean isNumeric(String value){
    if(value == null){
      return false;
    }
    try {
      Double result = Double.parseDouble(value);
    }catch (NumberFormatException ne){
      return false;
    }
    return true;
  }
}
