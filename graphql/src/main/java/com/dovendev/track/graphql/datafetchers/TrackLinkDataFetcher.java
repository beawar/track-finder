package com.dovendev.track.graphql.datafetchers;


import com.dovendev.track.jpa.entities.TrackLink;
import com.dovendev.track.jpa.services.TrackLinkService;
import graphql.schema.DataFetcher;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackLinkDataFetcher {
    @Autowired
    private TrackLinkService trackLinkService;

    public DataFetcher<TrackLink> getTrackLinkDataFetcher() {
        return dataFetchingEnvironment -> {
            String trackLinkId = dataFetchingEnvironment.getArgument("id");
            return trackLinkService.findById(Long.parseLong(trackLinkId));
        };
    }

    public DataFetcher<TrackLink> createTrackLinkDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, Object> trackInputMap = dataFetchingEnvironment.getArgument("track");
            TrackLink trackLink = TrackLink.fromMap(trackInputMap);
            return trackLinkService.save(trackLink);
        };
    }

    public DataFetcher<TrackLink> deleteTrackLinkDataFetcher() {
        return dataFetchingEnvironment -> {
            String trackLinkId = dataFetchingEnvironment.getArgument("id");
            return trackLinkService.delete(trackLinkService.findById(Long.parseLong(trackLinkId)));
        };
    }
}
