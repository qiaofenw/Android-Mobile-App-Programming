package edu.utah.cs4530.project1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 10/3/16.
 */
public class Gallery {

    static Gallery _Instance = null;

    public static Gallery getInstance(){
        if(_Instance == null)
            _Instance = new Gallery();

        return _Instance;
    }

    private Gallery(){
        //TODO: setup
    }

    public static void setInstance(Gallery gallery) {
        _Instance = gallery;
    }

//    List<Drawing> drawingList = new ArrayList<>();
//
//    public int Count() {
//        return drawingList.size();
//    }
//
//    public Drawing getDrawing(int drawingIndex) {
//        return drawingList.get(drawingIndex);
//    }
//
//    public void addDrawing(Drawing d) {
//        drawingList.add(d);
//    }

//    public void removeDrawing(int drawingIndex) {
//        drawingList.remove(drawingIndex);
//    }
//
//    public void addStroke(int i, Stroke stroke) {
//        drawingList.get(i).addStroke(stroke);
//    }

    static float x,y;

//    static Bitmap bitmap;
//    static Canvas mapCanvas;

    static Path path;
    static int color;// = Color.TRANSPARENT;
    static Paint paintPath;

    static List<Path> pathList = new ArrayList<Path>();
    static List<Path> undo = new ArrayList<Path>();
    static List<Integer> colorList = new ArrayList<Integer>();

    //static List<Point> pointList = new ArrayList<Point>();
    //static List<Integer> colorPointList = new ArrayList<Integer>();

    static boolean flag = false;
    static CircleLayout paletteLayout;
}
