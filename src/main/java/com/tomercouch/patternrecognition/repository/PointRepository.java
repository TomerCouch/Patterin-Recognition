package com.tomercouch.patternrecognition.repository;

import com.tomercouch.patternrecognition.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {

    // It returns a point corresponding the given coordinators.
    Point findByXAndY(int x, int y);
}