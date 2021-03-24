package com.dovendev.track.graphql.datafetchers;


import com.dovendev.track.graphql.connection.CursorUtil;
import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.services.TrackService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.schema.DataFetcher;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackDataFetcher implements GraphQLQueryResolver {
    @Autowired
    private TrackService trackService;
    private CursorUtil cursorUtil = new CursorUtil();

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
            return trackService.save(track);
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

    public DataFetcher<Connection<Track>> getAllPageable() {
        return dataFetchingEnvironment -> {
            int first = dataFetchingEnvironment.getArgument("first");
            String cursor = dataFetchingEnvironment.getArgument("after");

            List<Edge<Track>> edges = getTrackList(cursor)
                .stream()
                .map(track -> new DefaultEdge<>(track, cursorUtil.from(track.getId())))
                .collect(Collectors.toUnmodifiableList());

            var pageInfo = new DefaultPageInfo(
                cursorUtil.getFirstCursorFrom(edges),
                cursorUtil.getLastCursorFrom(edges),
                cursor != null,
                edges.size() >= first);
            return new DefaultConnection<>(edges, pageInfo);
        };
    }

    public List<Track> getTrackList(String cursor){
        if (cursor == null){
            return trackService.findAll();
        }
        return trackService.getTrackAfterCursor(Long.valueOf(cursorUtil.decode(cursor)));
    }
}
