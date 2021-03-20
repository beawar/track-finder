package com.dovendev.track.jpa.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("track.finder.persistence/com.dovendev.track.jpa.repositories.TrackLinkRepositoryTest")
@DataJpaTest
class TrackLinkRepositoryTest {

  @Autowired
  private TrackLinkRepository trackLinkRepository;

  @Test
  @DisplayName("test")
  void test() {
    assertNotNull(trackLinkRepository, "track link repository is not null");
  }

}
