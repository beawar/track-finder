package com.dovendev.track.jpa.services;

import com.dovendev.track.jpa.entities.Track;
import com.dovendev.track.jpa.repositories.TrackRepository;
import com.querydsl.core.support.OrderedQueryMetadata;
import java.util.List;
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
    return trackRepository.findAll(Sort.by(Direction.DESC, "uploadTime"));
  }

  public List<Track> findByTitleDescription(String searchText) {
    return trackRepository.findByTitleDescription(searchText);
  }

  public List<Track> findAll(int limit, Long cursor){
    Pageable pageable = PageRequest.of(0, limit, Sort.by(Direction.DESC, "uploadTime"));
    if(cursor == null){
      return trackRepository.findBy(pageable);
    }
    return trackRepository.findByIdAfter(cursor, pageable);
  }

 /* public List<Track> findOrdered(int limit, List<Sort> ){

  }*/
}
