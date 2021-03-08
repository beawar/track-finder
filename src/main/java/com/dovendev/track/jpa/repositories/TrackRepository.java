package com.dovendev.track.jpa.repositories;

import com.dovendev.track.jpa.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
