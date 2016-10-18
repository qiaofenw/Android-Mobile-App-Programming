package edu.utah.cs4530.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Qiaofeng Wang on 9/17/16.
 */
public class SplotchView extends View {
    int _splotColor = Color.WHITE;
    boolean _highLighted = false;

    // These global variables are for random shapes.
    Random r = new Random();
    int n = r.nextInt(30) + 10;
    ArrayList<Float> list1 = new ArrayList<Float>();
    ArrayList<Float> list2 = new ArrayList<Float>();

    public SplotchView(Context context) {
        super(context);

    }

    public void setSplotchColor(int color){
        _splotColor = color;
        invalidate();
    }

    public int getSplotchColor(){
        return _splotColor;
    }

    public void setHighLighted(boolean b){
        _highLighted = b;
        invalidate();
    }

    public boolean getHighLighted(){
        return _highLighted;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF splotchRect = new RectF();
        splotchRect.left = getPaddingLeft();
        splotchRect.top = getPaddingTop();
        splotchRect.right = getWidth() - getPaddingRight();
        splotchRect.bottom = getHeight() - getPaddingBottom();

        Paint splotchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        splotchPaint.setColor(_splotColor);

        canvas.drawCircle(splotchRect.centerX(), splotchRect.centerY(), splotchRect.height()/2.5f ,splotchPaint);
        //canvas.drawOval(splotchRect, splotchPaint);

        RectF highlightRect = new RectF();
        highlightRect.left = getPaddingLeft();
        highlightRect.top = getPaddingTop();
        highlightRect.right = getWidth() - getPaddingRight();
        highlightRect.bottom = getHeight() - getPaddingBottom();

        // Generate different shapes
        if(list1.size() == 0 & list2.size() == 0) {
            for(int i = 0 ;i< n; i++) {
                float temp1 = r.nextFloat() * 360.0f;
                float temp2 = r.nextFloat() * 40.0f;
                canvas.drawArc(highlightRect, temp1, temp2, true, splotchPaint);

                list1.add(temp1);
                list2.add(temp2);
            }
        }
        else {
            for(int i = 0 ;i< n; i++) {
                float temp1 = list1.get(i);
                float temp2 = list2.get(i);
                canvas.drawArc(highlightRect, temp1, temp2, true, splotchPaint);
            }
        }

        if(_highLighted){
            Paint highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

            //int max = 0xFFFFFFFF;
            highlightPaint.setColor(Color.YELLOW);
            //highlightPaint.setAlpha(250);

            // If the color is YELLOW
            if(splotchPaint.getColor() > -300 && splotchPaint.getColor() < -200)
                highlightPaint.setColor(Color.BLUE);

            highlightPaint.setStyle(Paint.Style.STROKE);
            highlightPaint.setStrokeWidth(highlightRect.height() * 0.1f);
            canvas.drawOval(highlightRect, highlightPaint);

//            Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            linePaint.setColor(Color.BLACK);
//            linePaint.setTextSize(100);
            //canvas.drawLine((splotchRect.left + splotchRect.right)/2,(splotchRect.top+splotchRect.bottom)/2,(splotchRect.left + splotchRect.right)/2 ,(splotchRect.top+splotchRect.bottom)/2 + 100,linePaint);
//            canvas.drawText("↑",splotchRect.centerX() ,splotchRect.centerY(),linePaint);
        }
    }
}
