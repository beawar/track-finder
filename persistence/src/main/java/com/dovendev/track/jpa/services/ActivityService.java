package com.dovendev.track.jpa.services;

import com.dovendev.track.jpa.entities.Activity;
import com.dovendev.track.jpa.repositories.ActivityRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
  private final ActivityRepository activityRepository;

  public ActivityService(ActivityRepository activityRepository) {
    this.activityRepository = activityRepository;
  }

  public List<Activity> findAll() {
    return activityRepository.findAll();
  }

  public Optional<Activity> findById(Long id) {
    return activityRepository.findById(id);
  }

  public Activity create(Activity activity) {
    return activityRepository.save(activity);
  }

  public Optional<Activity> delete(Long id) {
    final Optional<Activity> activity = findById(id);
    if (activity.isPresent()) {
      activityRepository.deleteById(id);
    }
    return activity;
  }

  public Optional<Activity> update(Long id, String name) {
    final Optional<Activity> activity = findById(id);
    if (activity.isPresent()) {
      activity.get().setName(name);
      Activity updatedActivity = activityRepository.save(activity.get());
      return Optional.of(updatedActivity);
    }
    return activity;
  }
}
