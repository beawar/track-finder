package com.dovendev.track.jpa.services;

import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.repositories.TrackRepository;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
  private final TrackRepository trackRepository;

  public TrackService(TrackRepository trackRepository) {
    this.trackRepository = trackRepository;
  }

  public Track save(Track track) {
    trackRepository.save(track);
    // TODO retrieve ID
    return track;
  }

  public Track findById(Long id) {
    return trackRepository.findById(id).orElse(null);
  }
}
