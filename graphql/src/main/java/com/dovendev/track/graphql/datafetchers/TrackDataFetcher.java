package com.dovendev.track.graphql.datafetchers;

import com.dovendev.track.graphql.connection.CursorUtil;
import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.entities.TrackSort;
import com.dovendev.track.jpa.services.TrackService;
import graphql.GraphQLException;
import graphql.GraphqlErrorException;
import graphql.relay.Connection;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.schema.DataFetcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackDataFetcher {
    @Autowired
    private TrackService trackService;

    public DataFetcher<Track> getTrackDataFetcher() {
        return dataFetchingEnvironment -> {
            String trackId = dataFetchingEnvironment.getArgument("id");
            return trackService.findById(Long.parseLong(trackId));
        };
    }

    public DataFetcher<Track> createTrackDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, Object> trackInputMap = dataFetchingEnvironment.getArgument("track");
            Track track = Track.fromMap(trackInputMap);
            return trackService.create(track);
        };
    }

    public DataFetcher<Track> deleteTrackDataFetcher() {
        return dataFetchingEnvironment -> {
            String trackId = dataFetchingEnvironment.getArgument("id");
            return trackService.delete(trackService.findById(Long.parseLong(trackId)));
        };
    }

    public DataFetcher<List<Track>> findAllTrackDataFetcher() {
        return dataFetchingEnvironment -> trackService.findAll();
    }

    public DataFetcher updateTrackDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, Object> trackUpdateMap = dataFetchingEnvironment.getArgument("track");
            Track track = Track.fromMap(trackUpdateMap);
            track.setId(Long.valueOf(dataFetchingEnvironment.getArgument("id")));
            return trackService.update(track);
        };
    }
  public DataFetcher<Connection<Track>> getAllPageable() {
    return dataFetchingEnvironment -> {
      int limit = dataFetchingEnvironment.getArgument("limit");
      final Object cursorObj = dataFetchingEnvironment.getArgument("after");
      Long cursor = null;

      if (cursorObj != null) {
        try {
          cursor = Long.valueOf(String.valueOf(cursorObj));
        } catch (NumberFormatException ne) {
          GraphqlErrorException.Builder errorBuilder = GraphqlErrorException.newErrorException();
          errorBuilder.message(ne.getMessage());
          Map<String, Object> errorData = new HashMap<>();
          errorData.put("error_code", "INVALID_CURSOR");
          errorData.put("error_message", "Invalid cursor");
          errorBuilder.extensions(errorData);
          throw errorBuilder.build();
        }
      }

      List<Track> tracks = getTrackList(limit + 1, cursor);
      List<Edge<Track>> edges =
          tracks.subList(0, Math.min(tracks.size(), limit)).stream()
              .map(
                  track ->
                      new DefaultEdge<>(
                          track, new DefaultConnectionCursor(track.getId().toString())))
              .collect(Collectors.toUnmodifiableList());

      var pageInfo =
          new DefaultPageInfo(
              CursorUtil.getFirstCursorFrom(edges),
              CursorUtil.getLastCursorFrom(edges),
              cursor != null,
              tracks.size() > limit);
      return new DefaultConnection<>(edges, pageInfo);
    };
  }

  public List<Track> getTrackList(int limit, Long cursor) {
    List<TrackSort> trackSorts = new ArrayList<>();
    trackSorts.add(TrackSort.UPLOAD_TIME_DESC);
    return trackService.findAll(limit, cursor, trackSorts);
  }

  public DataFetcher<List<Track>> findByTitleDescriptionDataFetcher() {
    return dataFetchingEnvironment -> {
      String searchText = dataFetchingEnvironment.getArgument("searchText");
      return trackService.findByTitleDescription(searchText);
    };
  }
}
