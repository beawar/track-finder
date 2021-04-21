package com.dovendev.track.jpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dovendev.track.jpa.entities.Activity;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
public class ActivityServiceIntegrationTest {

    @Autowired
    private ActivityService activityService;

    @Test
    @Sql({"/test-data.sql"})
    void update() {
      Optional<Activity> activity = activityService.update(32L, "Trekking");
      assertTrue(((Optional<?>) activity).isPresent());
      assertNotNull(activity.get().getId());
      assertEquals(activity.get().getName(), "Trekking");
    }
}
