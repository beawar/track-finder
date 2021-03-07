package com.dovendev.track.jpa.repositories;

import com.dovendev.track.jpa.entities.TracksResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TrackLinkRepository extends JpaRepository<TracksResources, Integer> {
}

