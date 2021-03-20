package com.dovendev.track.jpa.services;

import com.dovendev.track.jpa.entities.TrackLink;
import com.dovendev.track.jpa.repositories.TrackLinkRepository;
import org.springframework.stereotype.Service;

@Service
public class TrackLinkService {

    private final TrackLinkRepository trackLinkRepository;

    public TrackLinkService(TrackLinkRepository trackLinkRepository) {
        this.trackLinkRepository = trackLinkRepository;
    }

    public TrackLink save(TrackLink trackLink) {
        return trackLinkRepository.save(trackLink);
    }

    public TrackLink findById(Long id) {
        return trackLinkRepository.findById(id).orElse(null);
    }

    public TrackLink delete(TrackLink trackLink) {
        trackLinkRepository.delete(trackLink);
        return trackLink;
    }
}
