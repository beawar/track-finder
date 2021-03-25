package com.dovendev.track.jpa.predicates;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TrackPredicatesBuilder {

  private final List<SearchCriteria> params;

  public TrackPredicatesBuilder() {
    params = new ArrayList<>();
  }

  public TrackPredicatesBuilder with(final String key, final String operation, final Object value) {
    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  public BooleanExpression build() {
    if (params.size() == 0) {
      return null;
    }

    List<BooleanExpression> predicates = params.stream().map(param -> {
      TrackPredicate predicate = new TrackPredicate(param);
      return predicate.getPredicate();
    }).filter(Objects::nonNull).collect(Collectors.toList());

    BooleanExpression result = Expressions.asBoolean(true).isTrue();
    for (BooleanExpression predicate : predicates) {
      result = result.and(predicate);
    }
    return result;
  }
}
