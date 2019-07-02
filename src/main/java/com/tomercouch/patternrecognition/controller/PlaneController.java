package com.tomercouch.patternrecognition.controller;

import com.tomercouch.patternrecognition.model.LineSegment;
import com.tomercouch.patternrecognition.model.Point;
import com.tomercouch.patternrecognition.repository.LineSegmentRepository;
import com.tomercouch.patternrecognition.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class PlaneController {

    @Autowired
    private PointRepository PointRepository;

    @Autowired
    private LineSegmentRepository LineRepository;

    // It adds the point to any line that intersects with the point, and creates a new line with each the remaining points.
    // sameLinePoints -> Points that belong to lines that intersect with the new point.
    // newLinePoints ->  Points that do not have an intersecting line with the new point. (e.g. all points - sameLinePoints)
    public void updatePlane(Point newPoint){
        Set<Point> sameLinePoints = new HashSet();
        sameLinePoints.add(newPoint);

        for(LineSegment line: LineRepository.findAll()){
            boolean isPointAddedToLine = line.addPointIfIntersects(newPoint);

            if(isPointAddedToLine){
                Set<Point> linePoints = line.getPoints();
                sameLinePoints.addAll(linePoints);

                LineRepository.save(line);
            }
        }

        Set<Point> newLinePoints = new HashSet(PointRepository.findAll());
        newLinePoints.removeAll(sameLinePoints);

        this.createNewLines(newPoint, newLinePoints);
    }

    // It creates a new line between the newPoint and each of the given points
    private void createNewLines(Point newPoint, Set<Point> newLinePoints){
        for(Point point : newLinePoints){
            if(!LineSegment.isValidLine(point, newPoint)){
                continue;
            }
            LineSegment line = new LineSegment();
            Set<Point> points = new HashSet(Arrays.asList(point, newPoint));
            line.setPoints(points);
            LineRepository.save(line);
        };
    }
}
