package com.dovendev.track.jpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.repositories.TrackRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@DisplayName("track.finder.persistence/com.dovendev.track.jpa.services.TrackService")
@DataJpaTest
class TrackServiceTest {

  private TrackService trackService;

  private final TrackRepository trackRepository = Mockito.mock(TrackRepository.class);

  @BeforeEach
  void setup() {
    this.trackService = new TrackService(trackRepository);
  }

  @Test
  void findById() {
    when(trackRepository.findById(1L)).thenReturn(optionalTrack());
    Track track = trackService.findById(1L);
    assertNotNull(track);
    assertEquals(track, optionalTrack().orElse(null));
  }

  @Test
  void save() {
    final Track trackInput = trackInput();
    when(trackRepository.save(trackInput)).thenReturn(optionalTrack().orElse(null));
    Track track = trackService.save(trackInput);
    assertNotNull(track);
    assertNotNull(track.getId());
  }

  @Test
  void delete() {
    doNothing().when(trackRepository).delete(trackDelete());
    Track track = trackService.delete(trackDelete());
    assertNotNull(track);
    assertEquals(track, trackDelete());
    verify(trackRepository).delete(trackDelete());
  }

  @Test
  void deleteWithNull() {
    doNothing().when(trackRepository).delete(trackDelete());
    Track track = trackService.delete(null);
    assertNull(track);
    verify(trackRepository, times(0)).delete(null);
  }

  @Test
  void findAll() {
    when(trackRepository.findAll(Sort.by(Direction.DESC, "uploadTime"))).thenReturn(trackList());
    List<Track> trackList = trackService.findAll();
    assertNotNull(trackList);
    assertEquals(trackList, trackList());
  }

  private Optional<Track> optionalTrack() {
    final Track track = new Track();
    track.setId(1L);
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

  private List<Track> trackList() {
    final List<Track> trackList = new ArrayList<Track>();
    final Track trackOne = new Track();
    final Track trackTwo = new Track();
    final Track trackThree = new Track();
    trackOne.setId(1L);
    trackTwo.setId(2L);
    trackThree.setId(3L);
    trackOne.setTitle("Track 1");
    trackTwo.setTitle("Track 2");
    trackThree.setTitle("Track 3");
    trackOne.setDescription("Description track 1");
    trackTwo.setDescription("Description track 2");
    trackThree.setDescription("Description track 3");
    trackList.add(trackOne);
    trackList.add(trackTwo);
    trackList.add(trackThree);
    return trackList;
  }
}
