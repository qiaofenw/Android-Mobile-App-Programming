package edu.utah.cs4530.project1;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.SizeF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by Qiaofeng Wang on 9/17/16.
 */
public class CircleLayout extends ViewGroup implements Serializable {
    public CircleLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //int avoid = getChildCount();

        for(int c =0;c < getChildCount(); c++){
            float theta = (float)(1.485 * Math.PI) / (float)(getChildCount() + 0) * (float)c + (float)(0.45 * Math.PI);
            //float radius = getWidth() * 0.5f;

            float density = getResources().getDisplayMetrics().density;

            float childWidth = 0.2f * 160.0f * density;
            float childHeight = 0.15f * 160.0f * density;

            PointF childCenter = new PointF();
            childCenter.x = getWidth() * 0.5f + (getWidth() * 0.5f - childWidth * 0.7f) * (float)Math.cos(theta);
            childCenter.y = getHeight() * 0.5f + (getHeight() * 0.5f - childHeight * 0.7f) * (float)Math.sin(theta);

            Rect childRect = new Rect();
            childRect.left = (int)(childCenter.x - childWidth * 0.5f);
            childRect.right = (int)(childCenter.x + childWidth * 0.5f);
            childRect.top = (int)(childCenter.y - childHeight * 0.5f);
            childRect.bottom = (int)(childCenter.y + childHeight * 0.5f);

            View childView  = getChildAt(c);
            childView.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
        }
    }
}
