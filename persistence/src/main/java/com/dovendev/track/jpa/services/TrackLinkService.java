package com.dovendev.track.jpa.services;

import com.dovendev.track.jpa.repositories.TrackLinkRepository;
import org.springframework.stereotype.Service;

@Service
public class TrackLinkService {

    private final TrackLinkRepository trackLinkRepository;

    public TrackLinkService(TrackLinkRepository trackLinkRepository) {
        this.trackLinkRepository = trackLinkRepository;
    }
}
