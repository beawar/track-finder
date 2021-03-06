package com.dovendev.track.jpa.repository;

import com.dovendev.track.jpa.entity.TracksResources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackLinkRepository extends JpaRepository<TracksResources, Integer> {
}

