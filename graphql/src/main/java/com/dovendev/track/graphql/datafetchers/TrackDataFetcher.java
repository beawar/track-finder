package com.dovendev.track.graphql.datafetchers;


import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.services.TrackService;
import graphql.schema.DataFetcher;
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
            return trackService.save(track);
        };
    }

    public DataFetcher<Track> deleteTrackDataFetcher() {
        return dataFetchingEnvironment -> {
            String trackId = dataFetchingEnvironment.getArgument("id");
            return trackService.delete(trackService.findById(Long.parseLong(trackId)));
        };
    }
}
