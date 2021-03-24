package com.dovendev.track.jpa.services;

import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.repositories.TrackRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
  private final TrackRepository trackRepository;

  public TrackService(TrackRepository trackRepository) {
    this.trackRepository = trackRepository;
  }

  public Track create(Track track) {
    return trackRepository.save(track);
  }

  public Track findById(Long id) {
    return trackRepository.findById(id).orElse(null);
  }

  public Track delete(Track track){
    if (track != null) {
      trackRepository.delete(track);
    }

    return track;
  }

  public List<Track> findAll(){
    return trackRepository.findAll(Sort.by(Direction.DESC, "uploadTime"));
  }

  public List<Track> findByTitleDescription(String searchText) {
    return trackRepository.findByTitleDescription(searchText);
  }

  public Track update(Track track){
    return trackRepository.save(track);
  }
}
