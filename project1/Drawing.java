package edu.utah.cs4530.project1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 10/4/16.
 */
public class Drawing {
    List<Stroke> _strokes = new ArrayList<Stroke>();

    int getStrokeCount() {
        return _strokes.size();
    }

    public Stroke getStroke(int strokeIndex) {
        return _strokes.get(strokeIndex);
    }

    public void addStroke(Stroke stroke) {
        _strokes.add(stroke);
    }
}
