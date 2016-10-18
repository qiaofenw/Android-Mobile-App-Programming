package edu.utah.cs4530.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.shapes.ArcShape;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Qiaofeng Wang on 9/6/16.
 */
public class KnobView extends View{
    //Gravity gravity = 0;

    float _theta = 0.0f;
    RectF _knobRect = new RectF();
    String _color = "";

    int rgb = 0;

    public KnobView(Context context, String color) {
        super(context);

        _color = color;
    }

    public void setRgb (int r){
        rgb = r;
        invalidate();
    }

    public int getRgb(){
        return rgb;
    }

//    public void setGravity(Gravity _g){
//        gravity = _g;
//    }

    public float getTheta(){
        return _theta;
    }

    public void setTheta(float theta){
        _theta = theta;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF touchPoint = new PointF();
        touchPoint.x = event.getX();
        touchPoint.y = event.getY();

        float theta = (float)Math.atan2(touchPoint.y - _knobRect.centerY(), touchPoint.x - _knobRect.centerX());
        setTheta(theta);
        //return false;
        return true; //super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //RectF knobRect = new RectF();
        _knobRect.left = getPaddingLeft() ;
        _knobRect.top = getPaddingTop();
        _knobRect.right = getWidth() - getPaddingRight();
        _knobRect.bottom = _knobRect.width();

        // Center knob
        float offset = (getHeight() - _knobRect.height()) * 0.5f;
        _knobRect.top += offset;
        _knobRect.bottom += offset;

        float radius = _knobRect.width() * 0.35f;
        //float theta = (float)Math.PI * 0.5f;

        PointF nibCenter= new PointF();
        nibCenter.x = _knobRect.centerX() + radius * (float)Math.cos((double)_theta);
        nibCenter.y = _knobRect.centerY() + radius * (float)Math.sin((double)_theta);

        float nibRadius = radius * 0.2f;

        RectF nibRect = new RectF();
        nibRect.left = nibCenter.x - nibRadius;
        nibRect.top = nibCenter.y - nibRadius;
        nibRect.right = nibCenter.x + nibRadius;
        nibRect.bottom = nibCenter.y + nibRadius;

        double degree = Math.toDegrees((double)_theta);
        if(Math.toDegrees((double)_theta) < 0)
            degree = 360d + Math.toDegrees((double)_theta);

        setRgb((int)(degree/360 * 255));

        //Paint knobPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //knobPaint.text
        //knobPaint.setColor(Color.YELLOW);
        //ArcShape arc1 = new ArcShape(0.0f, (360/256));

        Paint nibPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        nibPaint.setColor(Color.WHITE);

//        Paint colorWheel = new Paint();
//        colorWheel.setARGB(255, 255, 0, 0);
//        canvas.drawArc(_knobRect, (float)Math.toDegrees((double)1* Math.PI/4.0) ,  (float)Math.toDegrees((double)2* Math.PI/4.0), true, colorWheel);

        if(_color == "red") {

            for (int i = 0; i <= 255; i++) {
                Paint colorWheel = new Paint();
                colorWheel.setARGB(255, i, 0, 0);
                canvas.drawArc(_knobRect, (float) i * (360.0f / 256.0f), (360.0f / 256.0f)+1f, true, colorWheel);
            }
        }

        if(_color == "green") {

            for (int i = 0; i <= 255; i++) {
                Paint colorWheel = new Paint();
                colorWheel.setARGB(255, 0, i, 0);
                canvas.drawArc(_knobRect, (float) i * (float) (360d / 256d), (float) (360d / 256d)+1f, true, colorWheel);
            }
        }

        if(_color == "blue") {

            for (int i = 0; i <= 255; i++) {
                Paint colorWheel = new Paint();
                colorWheel.setARGB(255, 0, 0, i);
                canvas.drawArc(_knobRect, (float) i * (float) (360d / 256d), (float) (360d / 256d)+1f, true, colorWheel);
            }
        }

        canvas.drawOval(nibRect, nibPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);

        //setMeasuredDimension(400,400);

        int width = getSuggestedMinimumWidth();
        int height = getSuggestedMinimumHeight();

        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSpec;
            height = width;
        }

        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSpec;
            width = height;
        }

        setMeasuredDimension(resolveSize(width,widthMeasureSpec), resolveSize(height,heightMeasureSpec));
    }
}
