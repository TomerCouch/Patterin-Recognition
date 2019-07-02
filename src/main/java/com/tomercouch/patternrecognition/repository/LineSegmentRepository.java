package com.tomercouch.patternrecognition.repository;

import com.tomercouch.patternrecognition.model.LineSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LineSegmentRepository extends JpaRepository<LineSegment, Long> {

    // It returns all lines with N or more points
    @Query("select line from LineSegment line where size(line.points)>=?1")
    List<LineSegment> findByPointCount(int n);
}
