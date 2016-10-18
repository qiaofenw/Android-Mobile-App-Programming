package edu.utah.cs4530.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Created by Qiaofeng Wang on 9/17/16.
 */
public class PaintView extends View {
//    float x,y;
//
//    Bitmap bitmap;
//    Canvas mapCanvas;
//
//    Path path;
//    Path circle;
//
//    int color = Color.TRANSPARENT;
//    Paint paintPath;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public PaintView(Context context) {
        super(context);

//        bitmap = Bitmap.createBitmap(this.getWidth(),this.getHeight(),Bitmap.Config.ARGB_8888);
//        mapCanvas = new Canvas(bitmap);

        setFocusable(true);
        setFocusableInTouchMode(true);

        Gallery.path = new Path();
        //Gallery.circle = new Path();

        Gallery.color = Color.TRANSPARENT;
        Gallery.paintPath = new Paint();
        Gallery.paintPath.setAntiAlias(true);
        Gallery.paintPath.setDither(true);
        Gallery.paintPath.setColor(Color.TRANSPARENT);
        Gallery.paintPath.setStyle(Paint.Style.STROKE);
        Gallery.paintPath.setStrokeJoin(Paint.Join.ROUND);
        Gallery.paintPath.setStrokeCap(Paint.Cap.ROUND);
        Gallery.paintPath.setStrokeWidth(12f);
        //drawPaint = new Paint();
    }

    public void setPaintColor(int p){
        Gallery.color = p;
        invalidate();
    }

    public int getPaintColor(){
        return Gallery.color;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float eventX = event.getX();
        float eventY = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(eventX,eventY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(eventX,eventY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true; //super.onTouchEvent(event);
    }

    public void touchStart(float _x, float _y){

//        Gallery.path.reset();
//        Gallery.path.moveTo(_x,_y);
//        Gallery.x = _x;
//        Gallery.y = _y;


        Gallery.undo.clear();
        Gallery.path.reset();
        Gallery.path.moveTo(_x, _y);
        Gallery.x = _x;
        Gallery.y = _y;

        Point p = new Point();
        p.x = _x;
        p.y = _y;
        //Gallery.pointList.add(p);
        //Gallery.colorPointList.add(Gallery.color);
        //animationX.add(_x);
        //animationY.add(_y);
    }

    public void touchMove(float _x, float _y){
        float tempX = Math.abs(_x - Gallery.x);
        float tempY = Math.abs(_y - Gallery.y);

        if(tempX >= 5  || tempY >=5){
            Gallery.path.quadTo(Gallery.x,Gallery.y,(Gallery.x+_x)/2,(Gallery.y+_y)/2);
//            if(!Gallery.path.equals(null)) {
//                Gallery.pathList.add(Gallery.path);
//                Gallery.colorList.add(Gallery.color);
//            }
            Gallery.x = _x;
            Gallery.y = _y;

            Point p = new Point();
            p.x = _x;
            p.y = _y;
            //Gallery.pointList.add(p);
            //Gallery.colorPointList.add(Gallery.color);
            //animationX.add(_x);
            //animationY.add(_y);

            //Gallery.circle.reset();
            //Gallery.circle.addCircle(Gallery.x,Gallery.y,15,Path.Direction.CW);
        }
    }

    public void touchUp(){
        Gallery.path.lineTo(Gallery.x,Gallery.y);
        //Gallery.circle.reset();

        //animationX.add(_x);
        //animationY.add(_y);

        //Gallery.mapCanvas.drawPath(Gallery.path,Gallery.paintPath);
//        if(!Gallery.path.equals(null)) {
//            Gallery.pathList.add(Gallery.path);
//            Gallery.colorList.add(Gallery.color);
//        }
        //Gallery.path.reset();

        //mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        //mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
        Gallery.pathList.add(Gallery.path);
        Gallery.colorList.add(Gallery.color);
        Gallery.path = new Path();

        Point p = new Point();
        p.x = Gallery.x;
        p.y = Gallery.y;
        //Gallery.pointList.add(p);
        //Gallery.colorPointList.add(Gallery.color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // original staff
        /*
        Paint paint = new Paint();
        paint.setColor(Gallery.color);
        Gallery.paintPath.setColor(Gallery.color);
        */

        //Paint paintMap = new Paint(Paint.DITHER_FLAG);
        //paintMap.setColor(color);

//        Paint paintCircle = new Paint();
//        paintCircle.setColor(Color.BLACK);
//        paintCircle.setAntiAlias(true);
//        paintCircle.setStyle(Paint.Style.STROKE);
//        paintCircle.setStrokeJoin(Paint.Join.MITER);
//        paintCircle.setStrokeWidth(5f);

        //  canvas.drawBitmap(Gallery.bitmap, 0, 0, paintMap);
        /*canvas.drawPath(Gallery.path,Gallery.paintPath);*/
        //canvas.drawPath(circle,paintCircle);

        for(Path p : Gallery.pathList){
            Paint np = new Paint(Gallery.paintPath);
            np.setColor(Gallery.colorList.get(Gallery.pathList.indexOf(p)));
            //Log.i("Color", "Color value: " + np.getColor());
            //Gallery.paintPath.setColor(Gallery.colorList.get(Gallery.pathList.indexOf(p)));
            canvas.drawPath(p,np);
        }
        Gallery.paintPath.setColor(Gallery.color);
        canvas.drawPath(Gallery.path, Gallery.paintPath);

//        for (int i = 0; i < _colorList.size(); i++) {
//            Paint paintColor = new Paint(Paint.ANTI_ALIAS_FLAG);
//            paintColor.setColor(_colorList.get(i));
//            paintColor.setStrokeWidth(5);
//            paintColor.setStyle(Paint.Style.STROKE);
//            canvas.drawPath(_pathList.get(i), paintColor);
//        }
/*
        for(int i =0;i< Gallery.pointList.size()-1;i++){
            Paint np = new Paint(Gallery.paintPath);
            np.setColor(Gallery.colorPointList.get(i));
            //canvas.drawPath(tempP,np);
            //canvas.drawPoint(tempP.x,tempP.y,np);
            float temp1 = Math.abs(Gallery.pointList.get(i).x - Gallery.pointList.get(i+1).x);
            float temp2 = Math.abs(Gallery.pointList.get(i).y - Gallery.pointList.get(i+1).y);

            if(temp1 > 5 || temp2>5)
                continue;

            canvas.drawLine(Gallery.pointList.get(i).x,Gallery.pointList.get(i).y,Gallery.pointList.get(i+1).x,Gallery.pointList.get(i+1).y,np);
        }*/
        //Gallery.paintPath.setColor(Gallery.color);
        //canvas.drawPath(Gallery.path, Gallery.paintPath);
        //canvas.drawPoint(Gallery.x,Gallery.y, Gallery.paintPath);

    }

    public void onClickUndo () {
        if (Gallery.pathList.size()>0)
        {
            Gallery.undo.add(Gallery.pathList.remove(Gallery.pathList.size()-1));
            invalidate();
        }
    }

    public void onClickRedo (){
        if (Gallery.undo.size()>0)
        {
            Gallery.pathList.add(Gallery.undo.remove(Gallery.undo.size()-1));
            invalidate();
        }
    }

    public void clear(){
        Gallery.pathList.clear();
        Gallery.colorList.clear();
        Gallery.path.reset();

        //Gallery.pointList.clear();
        //Gallery.colorPointList.clear();

        invalidate();
    }

    public void addPath(Path p, int c){
        Gallery.pathList.add(p);
        Gallery.colorList.add(c);

        invalidate();
    }

    public void recover(List<Path> p, List<Integer> c){
        Gallery.pathList = p;
        Gallery.colorList = c;
        invalidate();
    }
}
