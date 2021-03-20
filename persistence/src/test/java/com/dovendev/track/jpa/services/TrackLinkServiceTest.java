package com.dovendev.track.jpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dovendev.track.jpa.entities.TrackLink;
import com.dovendev.track.jpa.repositories.TrackLinkRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("track.finder.persistence/com.dovendev.track.jpa.services.TrackLinkService")
@DataJpaTest
class TrackLinkServiceTest {

  private TrackLinkService trackLinkService;

  private final TrackLinkRepository trackLinkRepository = Mockito.mock(TrackLinkRepository.class);

  @BeforeEach
  void setup() {
    this.trackLinkService = new TrackLinkService(trackLinkRepository);
  }

  @Test
  void findById() {
    when(trackLinkRepository.findById(1L)).thenReturn(optionalTrackLink());
    TrackLink trackLink = trackLinkService.findById(1L);
    assertNotNull(trackLink);
    assertEquals(trackLink, optionalTrackLink().orElse(null));
  }

  @Test
  void save() {
    final TrackLink trackLinkInput = trackLinkInput();
    when(trackLinkRepository.save(trackLinkInput)).thenReturn(optionalTrackLink().orElse(null));
    TrackLink trackLink = trackLinkService.save(trackLinkInput);
    assertNotNull(trackLink);
    assertNotNull(trackLink.getId());
  }

  @Test
  void delete() {
    doNothing().when(trackLinkRepository).delete(trackLinkDelete());
    TrackLink trackLink = trackLinkService.delete(trackLinkDelete());
    assertNotNull(trackLink);
    assertEquals(trackLink, trackLinkDelete());
    verify(trackLinkRepository).delete(trackLinkDelete());
  }

  private Optional<TrackLink> optionalTrackLink() {
    final TrackLink trackLink = new TrackLink();
    trackLink.setId(1L);
    trackLink.setLink("Link 1");
    trackLink.setTrackId(null);
    return Optional.of(trackLink);
  }

  private TrackLink trackLinkInput() {
    final TrackLink trackLink = new TrackLink();
    trackLink.setLink("Link 1");
    trackLink.setTrackId(null);
    return trackLink;
  }

  private TrackLink trackLinkDelete() {
    final TrackLink trackLink = new TrackLink();
    trackLink.setId(1L);
    trackLink.setLink("Link 1");
    trackLink.setTrackId(null);
    return trackLink;
  }
}
