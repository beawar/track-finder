package com.dovendev.track.jpa.repositories;

import com.dovendev.track.jpa.entities.Track;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrackRepository extends JpaRepository<Track, Long> {

  // sabgio replaced by the method that follows which is more understandable
  List<Track> findDistinctByTitleIgnoreCaseContainingOrDescriptionIgnoreCaseContainingOrderByUploadTimeDesc(String title, String description);

  @Query("SELECT DISTINCT t FROM Track t WHERE UPPER(t.title) LIKE CONCAT('%',UPPER(:searchText),'%') "
      + "OR UPPER(t.description) LIKE CONCAT('%',UPPER(:searchText),'%') ORDER BY t.uploadTime DESC")
  List<Track> findByTitleDescription(@Param("searchText") String searchText);
}
