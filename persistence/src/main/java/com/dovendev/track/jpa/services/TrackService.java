package com.dovendev.track.jpa.services;

import com.dovendev.track.jpa.entities.QTrack;
import com.dovendev.track.jpa.entities.SearchOperation;
import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.entities.TrackSort;
import com.dovendev.track.jpa.predicates.SearchCriteria;
import com.dovendev.track.jpa.predicates.TrackPredicatesBuilder;
import com.dovendev.track.jpa.repositories.TrackRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
  private final TrackRepository trackRepository;

  public TrackService(TrackRepository trackRepository) {
    this.trackRepository = trackRepository;
  }

  public Track save(Track track) {
    return trackRepository.save(track);
  }

  public Track findById(Long id) {
    return trackRepository.findById(id).orElse(null);
  }

  public Track delete(Track track) {
    trackRepository.delete(track);
    return track;
  }

  public List<Track> findAll() {
    return trackRepository.findAll(Sort.by(Direction.DESC, "uploadTime"));
  }

  public List<Track> findByTitleDescription(String searchText) {
    return trackRepository.findByTitleDescription(searchText);
  }

  //  public List<Track> findAll(int limit, Long cursor){
  //    Pageable pageable = PageRequest.of(0, limit, Sort.by(Direction.DESC, "uploadTime"));
  //    if(cursor == null){
  //      return trackRepository.findBy(pageable);
  //    }
  //    return trackRepository.findByIdAfter(cursor, pageable);
  //  }

  public List<Track> findAll(int limit, Long cursor, List<TrackSort> sorts) {
    List<Sort.Order> sortOrders =
        sorts.stream().map(TrackSort::getSortOrder).collect(Collectors.toList());
    // as final sort, if every other comparator is equal, order by id asc
    // so that we can exclude current cursor, which otherwise will be returned as 1st result
    sortOrders.add(TrackSort.ID_ASC.getSortOrder());
    Pageable pageable = PageRequest.of(0, limit, Sort.by(sortOrders));
    if (cursor == null) {
      return trackRepository.findBy(pageable);
    }
    final Track edge = findById(cursor);
    if (edge != null) {
      final TrackPredicatesBuilder builder = new TrackPredicatesBuilder();

      sorts.forEach(
          sort -> {
            final String field = sort.getSortOrder().getProperty();
            try {
              final SearchOperation searchOperation =
                  SearchOperation.getOperationFromSort(sort.getSortOrder().getDirection());
              builder.with(field, searchOperation, edge.getFieldValue(field));
            } catch (IllegalAccessException e) {
              System.err.println("Field not accessible: " + field);
            }
          });

      BooleanExpression predicates = builder.build();
      // exclude the edge from the results
      predicates = predicates.and(QTrack.track.id.ne(cursor));
      Page<Track> page = trackRepository.findAll(predicates, pageable);
      return page.toList();
    }
    return new ArrayList<>();
  }
}
