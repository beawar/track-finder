package com.dovendev.track.jpa.repositories;

import com.dovendev.track.jpa.entities.TracksResources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackResourceRepository extends JpaRepository<TracksResources, Long> {
}
