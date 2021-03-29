package com.dovendev.track.graphql.datafetchers;

import com.dovendev.track.graphql.utils.Utils;
import com.dovendev.track.jpa.entities.Activity;
import com.dovendev.track.jpa.services.ActivityService;
import graphql.GraphqlErrorException;
import graphql.schema.DataFetcher;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityDataFetcher {

  @Autowired
  private ActivityService activityService;

  private enum Errors {
    NAME_EMPTY("ACTIVITY_NAME_EMPTY", "Activity name must not be an empty string. Received");

    public GraphqlErrorException error;

    Errors(String code, String description) {
      this.error = Utils.buildError(code, description, null);
    }
  }

  public DataFetcher<List<Activity>> getActivities() {
    return dataFetchingEnvironment -> activityService.findAll();
  }

  public DataFetcher<Optional<Activity>> getActivity() {
    return dataFetchingEnvironment -> {
      Long id = Utils.parseId(dataFetchingEnvironment.getArgument("id"));
      return activityService.findById(id);
    };
  }

  public DataFetcher<Activity> createActivity() {
    return dataFetchingEnvironment -> {
      final String name = Objects.requireNonNull(dataFetchingEnvironment.getArgument("name"));
      if (name.isBlank()) {
        throw Errors.NAME_EMPTY.error;
      }
      final Activity activity = new Activity();
      activity.setName(name);
      return activityService.create(activity);
    };
  }

  public DataFetcher<Optional<Activity>> updateActivity() {
    return dataFetchingEnvironment -> {
      final Long id = Utils.parseId(dataFetchingEnvironment.getArgument("id"));
      final String name = Objects.requireNonNull(dataFetchingEnvironment.getArgument("name"));
      if (name.isBlank()) {
        throw Errors.NAME_EMPTY.error;
      }
      return activityService.update(id, name);
    };
  }

  public DataFetcher<Optional<Activity>> deleteActivity() {
    return dataFetchingEnvironment -> {
      final Long id = Utils.parseId(dataFetchingEnvironment.getArgument("id"));
      return activityService.delete(id);
    };
  }

}
