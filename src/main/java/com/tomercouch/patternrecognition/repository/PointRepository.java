package com.tomercouch.patternrecognition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomercouch.patternrecognition.model.Point;

public interface PointRepository extends JpaRepository<Point, Long> {

}