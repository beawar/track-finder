package com.dovendev.track.jpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dovendev.track.jpa.entities.Track;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;


@DataJpaTest
public class TrackServiceIntegrationTest {

  @Autowired
  private TrackService trackService;

  @Test
  @Sql({"/test-data.sql"})
  void update() {
    Track trackToUpdate = trackUpdate();
    Optional<Track> track = trackService.update(trackToUpdate);
    assertTrue(track.isPresent());
    assertNotNull(track.get().getId());
    assertEquals(track.get().getTitle(), "Track 2");
    assertEquals(track.get().getDescription(), "Facile escursione ad anello con partenza da Case Bertot. Si scende fino al torrente per attraversarlo e risalire dal versante oppposto della gola. Lungo la via del rientro si passa attraverso il Bus del Buson, insenatura scavata dal vecchio flusso del torrente, successivamente deviato, che permette di ammirare conformazioni rocciose tipiche di una gola scavata dall''acqua, di rilevanza storica per le tracce fossili che ne sono emerse");

  }

  private Track trackUpdate() {
    final Track track = new Track();
    track.setId(1L);
    track.setTitle("Track 2");
    track.setDescription("Facile escursione ad anello con partenza da Case Bertot. Si scende fino al torrente per attraversarlo e risalire dal versante oppposto della gola. Lungo la via del rientro si passa attraverso il Bus del Buson, insenatura scavata dal vecchio flusso del torrente, successivamente deviato, che permette di ammirare conformazioni rocciose tipiche di una gola scavata dall''acqua, di rilevanza storica per le tracce fossili che ne sono emerse");
    return track;
  }
}
