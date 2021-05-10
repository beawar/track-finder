package com.dovendev.track.jpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dovendev.track.jpa.entities.QTrack;
import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.entities.TrackSort;
import com.dovendev.track.jpa.repositories.TrackRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DisplayName("track.finder.persistence/com.dovendev.track.jpa.services.TrackService")
@DataJpaTest
class TrackServiceTest {

  private final TrackRepository trackRepository = Mockito.mock(TrackRepository.class);

  private TrackService trackService;

  @BeforeEach
  void setup() {
    this.trackService = new TrackService(trackRepository);
  }

  @Test
  void findById() {
    when(trackRepository.findById(1L)).thenReturn(optionalTrack(1L));
    Track track = trackService.findById(1L);
    assertNotNull(track);
    assertEquals(track, optionalTrack(1L).orElse(null));
  }

  @Test
  void create() {
    final Track trackInput = trackInput();
    when(trackRepository.save(trackInput)).thenReturn(optionalTrack(1L).orElse(null));
    Track track = trackService.create(trackInput);
    assertNotNull(track);
    assertNotNull(track.getId());
  }

  @Test
  void delete() {
    doNothing().when(trackRepository).delete(trackDelete());
    Track track = trackService.delete(trackDelete().getId());
    assertNotNull(track);
    assertEquals(track, trackDelete());
    verify(trackRepository).delete(trackDelete());
  }

  @Test
  void deleteWithNull() {
    doNothing().when(trackRepository).delete(trackDelete());
    Track track = trackService.delete(null);
    assertNull(track);
    verify(trackRepository, times(1)).delete(null);
  }

  @Test
  void findAllFirstPage() {
    int limit = 5;
    List<TrackSort> trackSort = trackSortList();
    List<Sort.Order> sortOrders =
        trackSort.stream().map(TrackSort::getSortOrder).collect(Collectors.toList());
    sortOrders.add(TrackSort.ID_ASC.getSortOrder());
    Pageable pageable = PageRequest.of(0, limit, Sort.by(sortOrders));
    BooleanExpression predicates = Expressions.asBoolean(true).isTrue();
    when(trackRepository.findAll(predicates, pageable)).thenReturn(pageTracks(9));
    List <Track> trackList = trackService.findAll(limit, null, trackSort, null);
    assertNotNull(trackList);
    assertEquals(trackList, trackList(9));
  }

  @Test
  void findAllWithCursor() {
    int limit = 4;
    Long cursor = 4L;
    List<TrackSort> trackSort = trackSortList();
    List<Sort.Order> sortOrders =
        trackSort.stream().map(TrackSort::getSortOrder).collect(Collectors.toList());
    sortOrders.add(TrackSort.ID_ASC.getSortOrder());
    Page<Track> page = pageTracks(3);
    Pageable pageable = PageRequest.of(0, limit, Sort.by(sortOrders));
    BooleanExpression predicates = Expressions.asBoolean(true).isTrue();
    predicates = predicates.and(QTrack.track.uploadTime.loe(OffsetDateTime.of(2021, 04, 21, 20, 15, 45, 34587500, ZoneOffset.of("+02:00"))))
        .and(QTrack.track.id.ne(4L));
    when(trackRepository.findAll(predicates, pageable)).thenReturn(page);
    when(trackRepository.findById(cursor)).thenReturn(optionalCursorTrack(cursor));
    List <Track> trackList = trackService.findAll(limit, cursor, trackSort, null);
    assertNotNull(trackList);
    assertEquals(trackList, page.toList());
  }

  private Optional<Track> optionalTrack(Long id) {
    final Track track = new Track();
    track.setId(id);
    track.setTitle("Track 1");
    track.setDescription("Description 1");
    return Optional.of(track);
  }

  private Track trackInput() {
    final Track track = new Track();
    track.setTitle("Track 1");
    track.setDescription("Description 1");
    return track;
  }

  private Track trackDelete() {
    final Track track = new Track();
    track.setId(1L);
    track.setTitle("Track 1");
    track.setDescription("Description track 1");
    return track;
  }

  private Optional<Track> optionalCursorTrack(Long id) {
    final Track track = new Track();
    track.setId(id);
    track.setTitle("Track number: " + id);
    track.setDescription("Super-track " + id);
    track.setUploadTime(OffsetDateTime.of(2021, 04, 21, 20, 15, 45, 34587500, ZoneOffset.of("+02:00")));
    return Optional.of(track);
  }

  private Page<Track> pageTracks(int max){
    List<Track> listTracks = trackList(max);
    return new PageImpl<Track>(listTracks);
  }

  private List<TrackSort> trackSortList(){
    final List<TrackSort> trackSort = new ArrayList<>();
    trackSort.add(TrackSort.UPLOAD_TIME_DESC);
    return trackSort;
  }

  private List<Track> trackList(int max) {
    final List<Track> trackList = new ArrayList<Track>();

    for(int i = 1; i <= max; i++){
      Track track = new Track();
      track.setId((long) i);
      track.setTitle("Track number: " + i);
      track.setDescription("Super-track " + i);
      track.setUploadTime(OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES));

      trackList.add(track);
    }

    return trackList;
  }
}
