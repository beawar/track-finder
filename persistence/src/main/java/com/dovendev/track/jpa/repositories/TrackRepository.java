package com.dovendev.track.jpa.repositories;

import com.dovendev.track.jpa.entities.QTrack;
import com.dovendev.track.jpa.entities.Track;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.query.Param;

public interface TrackRepository extends JpaRepository<Track, Long>,
    QuerydslPredicateExecutor<Track>, QuerydslBinderCustomizer<QTrack> {

  @Query("SELECT DISTINCT t FROM Track t WHERE UPPER(t.title) LIKE CONCAT('%',UPPER(:searchText),'%') "
      + "OR UPPER(t.description) LIKE CONCAT('%',UPPER(:searchText),'%') ORDER BY t.uploadTime DESC")
  List<Track> findByTitleDescription(@Param("searchText") String searchText);

  @Override
  default public void customize(QuerydslBindings bindings, QTrack root) {
    bindings.bind(String.class)
        .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }
}
