package edu.utah.cs4530.project1;

import android.graphics.Color;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 10/4/16.
 */
public class Stroke {



    List<Point> points = new ArrayList<Point>();
    int color = Color.TRANSPARENT;


    int getPointCount() {
        return points.size();
    }

    Point getPoint (int pointIndex) {
        return points.get(pointIndex);
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public void setColor(int color) {
        color = color;
    }

    public int getColor() {
        return color;
    }
}
