package com.dovendev.track.jpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dovendev.track.jpa.entities.Activity;
import com.dovendev.track.jpa.repositories.ActivityRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ActivityServiceTest {
  private ActivityService activityService;

  private final ActivityRepository activityRepository = Mockito.mock(ActivityRepository.class);

  @BeforeEach
  void setup() {
    this.activityService = new ActivityService(activityRepository);
  }

  @Test
  void findById() {
    when(activityRepository.findById(1L)).thenReturn(optionalActivity());
    Optional<Activity> activity = activityService.findById(1L);
    assertTrue(activity.isPresent());
    assertNotNull(activity.get());
    assertEquals(activity.get(), optionalActivity().orElse(null));
  }

  @Test
  void create() {
    final Activity activityInput = activityInput();
    when(activityRepository.save(activityInput)).thenReturn(optionalActivity().orElse(null));
    Activity activity = activityService.create(activityInput);
    assertNotNull(activity);
    assertNotNull(activity.getId());
  }

  @Test
  void delete() {
    Optional<Activity> actDeleted = activityDelete();
    assertTrue(activityDelete().isPresent());
    when(activityRepository.findById(1L)).thenReturn(actDeleted);
    doNothing().when(activityRepository).deleteById(1L);
    Optional<Activity> activity = activityService.delete(activityDelete().get().getId());
    assertTrue(activity.isPresent());
    assertEquals(activity.get(), activityDelete().orElse(null));
    verify(activityRepository).deleteById(activity.get().getId());
  }

  @Test
  void deleteNotPresent() {
    // this test verify wrong synchronous delete of same entity. First it delete a specific activity,
    // then it tried to delete the same activity which is no longer in the available activities

    assertTrue(activityDelete().isPresent());
    when(activityRepository.findById(1L)).thenReturn(activityDelete());
    doNothing().when(activityRepository).deleteById(1L);
    Optional<Activity> activityDeleted = activityService.delete(activityDelete().get().getId());
    assertTrue(activityDeleted.isPresent());
    assertEquals(activityDeleted.get(), activityDelete().orElse(null));

    Optional<Activity> fakeDelete = Optional.of(new Activity());
    when(activityRepository.findById(1L)).thenReturn(fakeDelete);
    doNothing().when(activityRepository).deleteById(fakeDelete.get().getId());
    Optional<Activity> activity = activityService.delete(activityDeleted.get().getId());
    assertTrue(activity.isPresent());
    assertNull(activity.get().getId());
    verify(activityRepository, times(0)).deleteById(null);
  }

  private Optional<Activity> optionalActivity() {
    final Activity activity = new Activity();
    activity.setId(1L);
    activity.setName("Activity 1");
    return Optional.of(activity);
  }

  private Activity activityInput() {
    final Activity activity = new Activity();
    activity.setName("Activity 1");
    return activity;
  }

  private Optional<Activity> activityDelete() {
    final Activity activity = new Activity();
    activity.setId(1L);
    activity.setName("Activity 1");
    return Optional.of(activity);
  }
}
