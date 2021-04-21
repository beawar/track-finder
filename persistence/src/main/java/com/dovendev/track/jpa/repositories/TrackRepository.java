package com.dovendev.track.jpa.repositories;

import com.dovendev.track.jpa.entities.QTrack;
import com.dovendev.track.jpa.entities.Track;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface TrackRepository extends CustomRepository<Track, Long>,
    QuerydslPredicateExecutor<Track>, QuerydslBinderCustomizer<QTrack> {

  @Override
  default void customize(QuerydslBindings bindings, QTrack root) {
    bindings.bind(String.class)
        .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }
}
