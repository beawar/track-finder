package com.dovendev.track.jpa.repository;

import com.dovendev.track.jpa.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Integer> {
}
