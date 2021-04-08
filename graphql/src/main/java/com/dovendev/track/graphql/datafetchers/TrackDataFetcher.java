package com.dovendev.track.graphql.datafetchers;

import com.dovendev.track.graphql.connection.CursorUtil;
import com.dovendev.track.graphql.utils.Utils;
import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.entities.TrackSort;
import com.dovendev.track.jpa.services.TrackService;
import graphql.relay.Connection;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import graphql.schema.DataFetcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackDataFetcher {
  @Autowired private TrackService trackService;

  public DataFetcher<Track> getTrack() {
    return dataFetchingEnvironment -> {
      String trackId = dataFetchingEnvironment.getArgument("id");
      return trackService.findById(Long.parseLong(trackId));
    };
  }

  public DataFetcher<Track> createTrack() {
    return dataFetchingEnvironment -> {
      Map<String, Object> trackInputMap = dataFetchingEnvironment.getArgument("track");
      Track track = Track.fromMap(trackInputMap);
      return trackService.create(track);
    };
  }

  public DataFetcher<Track> deleteTrack() {
    return dataFetchingEnvironment -> {
      String trackId = dataFetchingEnvironment.getArgument("id");
      return trackService.delete(trackService.findById(Long.parseLong(trackId)));
    };
  }

  public DataFetcher<Connection<Track>> getTracks() {
    return dataFetchingEnvironment -> {
      int limit = dataFetchingEnvironment.getArgument("limit");
      final Long cursor = Utils.parseId(dataFetchingEnvironment.getArgument("after"));
      String searchText = dataFetchingEnvironment.getArgument("searchText");
      List<String> sortNames = dataFetchingEnvironment.getArgument("sort");

      List<TrackSort> trackSorts = new ArrayList<>();
      if (sortNames!=null && !sortNames.isEmpty()) {
          trackSorts.addAll(sortNames.stream()
              .filter(sortName -> !sortName.isBlank())
              .map(TrackSort::valueOf).collect(Collectors.toList()));
      } else {
          // if sort list is empty, default sort is by upload time desc
          trackSorts.add(TrackSort.UPLOAD_TIME_DESC);
      }

      List<Track> tracks = getTrackList(limit + 1, cursor, trackSorts, searchText);
      List<Edge<Track>> edges =
          tracks.subList(0, Math.min(tracks.size(), limit)).stream()
              .map(
                  track ->
                      new DefaultEdge<>(
                          track, new DefaultConnectionCursor(track.getId().toString())))
              .collect(Collectors.toUnmodifiableList());

      PageInfo pageInfo =
          new DefaultPageInfo(
              CursorUtil.getFirstCursorFrom(edges),
              CursorUtil.getLastCursorFrom(edges),
              cursor != null,
              tracks.size() > limit);
      return new DefaultConnection<>(edges, pageInfo);
    };
  }

  public List<Track> getTrackList(int limit, Long cursor, List<TrackSort> trackSorts, String searchText) {
    return trackService.findAll(limit, cursor, trackSorts, searchText);
  }

    public DataFetcher<Track> updateTrack() {
        return dataFetchingEnvironment -> {
            Map<String, Object> trackUpdateMap = dataFetchingEnvironment.getArgument("track");
            Track track = Track.fromMap(trackUpdateMap);
            track.setId(Long.valueOf(dataFetchingEnvironment.getArgument("id")));
            return trackService.update(track);
        };
    }
}
