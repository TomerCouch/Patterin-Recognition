package com.tomercouch.patternrecognition.model;

import javax.persistence.*;
import java.util.Iterator;
import java.util.Set;

@Entity
public class LineSegment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="linePoint",
                joinColumns = {@JoinColumn(name="pointID")}, // Explicit : The foreign key the points to the owner entity.
                inverseJoinColumns = {@JoinColumn(name="lineID")}) // Explicit: The foreign key that points to the primary table, which is not the owner entity
    private Set<Point> points;

    private double m; // The slope of the line according two at least two points => (y2-y1)/(x2-x1) = m

    private double n; // The value where the line is intersecting with the Y axis.

    public LineSegment() {
    }

    public LineSegment(long id, Point pointA, Point pointB) {
        super();
        this.id = id;

        if (pointA != null && pointB != null) {
            this.calculateLine(pointA, pointB);
        }
    }

    public LineSegment(long id, Set<Point> points){
        super();
        this.id= id;

        this.points = points;
    }

    public long getLineID() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Point> getPoints() {
        return this.points;
    }

    // It sets the new points -> selecting the first two points and calculates the line
    public void setPoints(Set<Point> points) {
        if(points != null && points.size()>=2){
            Iterator<Point> iterator = points.iterator();
            Point pointA = iterator.next();
            Point pointB = iterator.next();

            if(LineSegment.isValidLine(pointA, pointB)) {
                this.points = points;
                this.calculateLine(pointA, pointB);
            }
        }
    }

    // It calculates the slope(m) and the Y axis intersection(n) according to the following equations:
    // M = (y2-y1)/(x2-x1)
    // N = Y - MX
    private void calculateLine(Point pointA, Point pointB) {
        this.m = (pointB.getY() - pointA.getY()) / (pointB.getX() - pointA.getX());
        this.n = pointA.getY() - this.m * pointA.getX();
    }

    // It checks whether the equation y = mx+ n is true for the given point
    private boolean isPointIntersection(Point point){
        return point.getY() == this.m * point.getX() + this.n;
    }

    // It adds the given point if the points intersects with the line slope
    public boolean addPointIfIntersects(Point newPoint) {
        if (newPoint != null && this.isPointIntersection(newPoint)) {
            this.points.add(newPoint);
            return true;
        }

        return false;
    }

    // It checks if the first two nodes of the line are valid and and the slope if not parallel to the Y-axis
    public static boolean isValidLine(Point pointA, Point pointB){
        return pointA != null && pointB != null && pointB.getX() - pointA.getX() !=0;
    }
}
