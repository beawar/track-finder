package com.dovendev.track.jpa.services;

import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.repositories.TrackRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class TrackService{
  private final TrackRepository trackRepository;

  public TrackService(TrackRepository trackRepository) {
    this.trackRepository = trackRepository;
  }

  public Track save(Track track) {
    return trackRepository.save(track);
  }

  public Track findById(Long id) {
    return trackRepository.findById(id).orElse(null);
  }

  public Track delete(Track track){
    trackRepository.delete(track);
    return track;
  }

  public List<Track> findAll(){
    List<Track> tracks = trackRepository.findAll(Sort.by(Direction.DESC, "uploadTime"));
    return tracks;
  }

  public Page<Track> getAllPageable(int limit, int page){
    Pageable firstPage = PageRequest.of(page, limit, Sort.by(Direction.DESC, "uploadTime"));
    // multiple page sort
    /*Pageable pageable = PageRequest.of(0, 10, Sort.by("age").descending()
        .and(Sort.by("lastName").ascending()));*/
    Pageable lastPage = PageRequest.of(page, limit);
    Page<Track> tracks = trackRepository.findAll(firstPage);

    return tracks;
  }

  public List<Track> getTrackAfterCursor(Long id){
    return trackRepository.findAll().stream()
        .dropWhile(track -> track.getId().compareTo(id) != 1)
        .collect(Collectors.toUnmodifiableList());
  }
}
