package com.tomercouch.patternrecognition.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.tomercouch.patternrecognition.model.Point;
import com.tomercouch.patternrecognition.repository.PointRepository;
import org.springframework.web.context.request.WebRequest;
import sun.rmi.runtime.Log;

import javax.validation.Valid;
import javax.xml.ws.Response;

@RestController
public class PatternRecognitionController {

    private PointRepository PointRepository;

    public PatternRecognitionController(PointRepository PointRepository) {
        this.PointRepository = PointRepository;
    }

    @PostMapping(path= "/point", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> addPoint(@Valid @RequestBody Point point, BindingResult bindingResult) {
        Logger logger = LoggerFactory.getLogger(PatternRecognitionController.class);

        if(bindingResult.hasErrors()) {
            logger.info("Error while crating point");
            return ResponseEntity.badRequest().body("Point input is not valid");
        }else{
            logger.info(point.getX() + "y :" + point.getY());
            PointRepository.save(point);
            return ResponseEntity.ok("Point created successfully");
        }
    }

        @GetMapping("/space")
    public List<Point> getPoints() {
        return PointRepository.findAll();
    }

    // TODO: create LineSegment logic -> and create a function to find all lineSegments -> later, maybe -> lineSegments has his own logic in a different controller
    @GetMapping("/lines/{n}")
    public List<Point> getLineSegments(@RequestParam int n){
        return null;
    }

    @DeleteMapping("/space")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deletePoints() {
        PointRepository.deleteAll();
        return ResponseEntity.ok("Space is clear");
    }
}