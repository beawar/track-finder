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
            return trackService.findById(trackId);
        };
    }

    public DataFetcher<Track> createTrackDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, Object> trackInputMap = dataFetchingEnvironment.getArgument("track");
            Track track = Track.fromMap(trackInputMap);
            return trackService.save(track);
        };
    }

}
