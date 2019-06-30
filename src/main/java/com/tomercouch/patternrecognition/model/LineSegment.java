package com.tomercouch.patternrecognition.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LineSegment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Point[] points;

    public LineSegment() {
    }

    public LineSegment(long id, Point[] points) {
        super();
        this.id = id;
        this.points = points;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Point[] getPoints(){
        return this.points;
    }

    // TODO: manage adding a point , removing a point, and copying by
    public void setPoints() {

    }
}
