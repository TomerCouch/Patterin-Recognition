package com.tomercouch.patternrecognition.model;


import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
import javax.validation.constraints.*;

import static java.lang.Integer.parseInt;


// TODO: separate Point model from PointDTO
@Entity
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Digits(integer=10, fraction = 0, message = "X is not an integer type")
    private int x;

    @Digits(integer=10, fraction = 0, message = "Y is not an integer type")
    @NotNull
    private int y;

    public Point(){

    }
    public Point(long id, Integer x, Integer y) {
        super();

        this.id = id;
        this.x = x;
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(String x){
            this.x = parseInt(x);
    }

    public int getY() {
        return y;
    }

    public void setY(String y){
            this.y = parseInt(y);
    }
}
