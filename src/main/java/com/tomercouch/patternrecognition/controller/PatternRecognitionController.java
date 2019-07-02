package com.tomercouch.patternrecognition.controller;

import com.tomercouch.patternrecognition.model.LineSegment;
import com.tomercouch.patternrecognition.model.Point;
import com.tomercouch.patternrecognition.repository.LineSegmentRepository;
import com.tomercouch.patternrecognition.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PatternRecognitionController {

    @Autowired
    private PointRepository PointRepository;
    @Autowired
    private LineSegmentRepository LineRepository;

    @Autowired PlaneController PlaneController;

    // TODO: produces app/json not text
    @PostMapping(path= "/point", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> addPoint(@Valid @RequestBody Point point, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Point input is not valid");
        }

        boolean isPointExists = PointRepository.findByXAndY(point.getX(),point.getY()) != null;

        if (isPointExists) {
                return ResponseEntity.unprocessableEntity().body("Point already exists on the plane");
            }

            PlaneController.updatePlane(point);

        return ResponseEntity.ok("Point created successfully");
    }


    // It returns all lines that contain at list N intersecting points
    @GetMapping("/lines/{n}")
    public List<LineSegment> getLineSegments(@PathVariable int n){
        return LineRepository.findByPointCount(n);
    }

    // It removes all points from the plane (i.e. removes points from space)
    @DeleteMapping("/space")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deletePoints() {
        LineRepository.deleteAll();
        PointRepository.deleteAll();
        return ResponseEntity.ok("Space is clear");
    }

    // It returns all points on the plane (i.e. space)
    @GetMapping("/space")
    public List<Point> getPoints() {
        return PointRepository.findAll();
    }
}