package com.dovendev.track.jpa.services;

import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.repositories.TrackRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    when(trackRepository.findById(1L)).thenReturn(track());
    Track track = trackService.findById(1L);
    assertNotNull(track);
    assertEquals(track, track().orElse(null));
    //verify(trackRepository).save(eq(track));
  }

  @Test
  void save() {
    final Track trackInput = trackInput();
    when(trackRepository.save(trackInput)).thenReturn(track().orElse(null));
    Track track = trackService.save(trackInput);
    assertNotNull(track);
    assertNotNull(track.getId());
  }

  private Optional<Track> track() {
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

}
