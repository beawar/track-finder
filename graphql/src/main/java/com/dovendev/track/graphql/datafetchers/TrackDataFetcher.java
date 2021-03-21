package com.dovendev.track.graphql.datafetchers;


import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.services.TrackService;
import graphql.schema.DataFetcher;
import java.util.List;
import java.util.Map;
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
}
