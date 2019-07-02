package com.tomercouch.patternrecognition.controller;

import com.tomercouch.patternrecognition.model.LineSegment;
import com.tomercouch.patternrecognition.model.Point;
import com.tomercouch.patternrecognition.repository.LineSegmentRepository;
import com.tomercouch.patternrecognition.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class PatternRecognitionController {

    @Autowired
    private PointRepository PointRepository;
    @Autowired
    private LineSegmentRepository LineRepository;

    @Autowired PlaneController PlaneController;

    @PostMapping(path= "/point")
    public ResponseEntity<Object> addPoint(@Valid @RequestBody Point point, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new PointNotValidResponse("Point input is not valid: "+ bindingResult.getFieldError()));
        }

        boolean isPointExists = PointRepository.findByXAndY(point.getX(),point.getY()) != null;

        if (isPointExists) {
                return ResponseEntity.unprocessableEntity().body(new PointNotValidResponse("Point already exists on the plane"));
            }

            PlaneController.updatePlane(point);

        return ResponseEntity.ok("Point created successfully");
    }

    // A basic handler for request params exceptions from REST controller's methods
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
        public ResponseEntity<Object> planeResponseEntityExceptionHandler(RuntimeException ex) {
            return new ResponseEntity(Collections.singletonMap("message", ex.getLocalizedMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

    // It returns all lines that contain at list N intersecting points
    @GetMapping("/lines/{n}")
    public List<LineSegment> getLineSegments(@PathVariable int n){
        if(n<2){
            return new ArrayList();
        }
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