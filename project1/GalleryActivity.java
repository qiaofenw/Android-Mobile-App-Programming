package edu.utah.cs4530.project1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by apple on 10/3/16.
 */
public class GalleryActivity extends AppCompatActivity {
    //CircleLayout paletteLayout;
    //boolean flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        final PaintView paintView = new PaintView(this);
        paintView.setBackgroundColor(Color.WHITE);

        // set Back button
        Button back = new Button(this);
        back.setText("BACK TO YOUR PAINT");
        back.setTextSize(50);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //serialize("");
                //Gallery.paletteLayout = paletteLayout;
                rootLayout.removeView(Gallery.paletteLayout);
                Gallery.flag = true;
                finish();
            }
        });

        rootLayout.addView(back,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));

        // Set palette

        if(Gallery.flag == true) {
            //paletteLayout = new CircleLayout(this);
            //paletteLayout = Gallery.paletteLayout;
            int i=0;
            i++;
        }
        else {
            //paletteLayout.setBackgroundColor(Color.WHITE);
            Gallery.paletteLayout = new CircleLayout(this);
            //Gallery.paletteLayout = new CircleLayout(this);
            Gallery.paletteLayout.setBackground(getDrawable(R.drawable.palette));

        /*
            Here are some test cases for palette.
         */

            SplotchView s1 = new SplotchView(this);
            s1.setSplotchColor(Color.RED);
            Gallery.paletteLayout.addView(s1);

            SplotchView s2 = new SplotchView(this);
            s2.setSplotchColor(Color.GREEN);
            Gallery.paletteLayout.addView(s2);

            SplotchView s3 = new SplotchView(this);
            s3.setSplotchColor(Color.BLUE);
            Gallery.paletteLayout.addView(s3);

            SplotchView s4 = new SplotchView(this);
            s4.setSplotchColor(Color.YELLOW);
            Gallery.paletteLayout.addView(s4);

            SplotchView s5 = new SplotchView(this);
            s5.setSplotchColor(Color.rgb(128, 0, 128));
            Gallery.paletteLayout.addView(s5);
//
//        SplotchView s6 = new SplotchView(this);
//        s6.setSplotchColor(Color.YELLOW);
//        paletteLayout.addView(s6);
//
//        SplotchView s7 = new SplotchView(this);
//        s7.setSplotchColor(Color.GRAY);
//        paletteLayout.addView(s7);
//
//        SplotchView s8 = new SplotchView(this);
//        s8.setSplotchColor(Color.DKGRAY);
//        paletteLayout.addView(s8);
        }

        Gallery.paletteLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                boolean b = false;

                for (int i = 0; i < Gallery.paletteLayout.getChildCount(); i++) {
                    SplotchView splotch = (SplotchView) Gallery.paletteLayout.getChildAt(i);

                    if (x >= splotch.getLeft() && x <= splotch.getRight() && y >= splotch.getTop() && y <= splotch.getBottom()) {
                        splotch.setHighLighted(true);
                        Gallery.color = splotch.getSplotchColor();
                        //paintView.setPaintColor(splotch.getSplotchColor());
                        b = true;
                        //break;
                    } else {
                        splotch.setHighLighted(false);
                        if (!b)
                            //Gallery.color = Color.TRANSPARENT;
                            paintView.setPaintColor(Color.TRANSPARENT);
                    }

                }
                return true;
            }
        });
        //Gallery.paletteLayout.pare
        rootLayout.addView(Gallery.paletteLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        // Set Knob
        LinearLayout knobLayout = new LinearLayout(this);

        final KnobView knobR = new KnobView(this, "red");
        knobR.setBackgroundColor(Color.WHITE);
        knobLayout.addView(knobR, new LinearLayout.LayoutParams(400, 400, 0));

        final KnobView knobG = new KnobView(this, "green");
        knobG.setBackgroundColor(Color.WHITE);
        knobLayout.addView(knobG, new LinearLayout.LayoutParams(400, 400, 0));

        final KnobView knobB = new KnobView(this, "blue");
        knobB.setBackgroundColor(Color.WHITE);
        knobLayout.addView(knobB, new LinearLayout.LayoutParams(400, 400, 0));

        // Set + & - Buttons
        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.VERTICAL);

        Button plus = new Button(this);
        plus.setText("+");
        plus.setTextSize(30);

        plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int r, g, b;
                r = knobR.getRgb();
                g = knobB.getRgb();
                b = knobB.getRgb();

                // Debug View constructor.
                SplotchView newSplotch = new SplotchView(view.getContext());

                // Set current Highlight
                for (int i = 0; i < Gallery.paletteLayout.getChildCount(); i++) {
                    SplotchView temp = (SplotchView) Gallery.paletteLayout.getChildAt(i);
                    temp.setHighLighted(false);
                }

                newSplotch.setHighLighted(true);

                Color newColor = new Color();
                newColor.alpha(255);
                newColor.red(r);
                newColor.green(g);
                newColor.blue(b);

                newSplotch.setSplotchColor(newColor.rgb(r, g, b));
                Gallery.paletteLayout.addView(newSplotch);
                //Gallery.color = newSplotch.getSplotchColor();
                paintView.setPaintColor(newSplotch.getSplotchColor());
            }
        });

        Button minus = new Button(this);
        minus.setText("-");
        minus.setTextSize(30);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < Gallery.paletteLayout.getChildCount(); i++) {
                    SplotchView temp = (SplotchView) Gallery.paletteLayout.getChildAt(i);

                    if (temp.getHighLighted() && Gallery.paletteLayout.getChildCount() > 1) {
                        Gallery.paletteLayout.removeView(temp);
                        //Gallery.color = Color.TRANSPARENT;
                        paintView.setPaintColor(Color.TRANSPARENT);
                    }
                }
            }
        });

        buttonLayout.addView(plus, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        buttonLayout.addView(minus, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));

        // Add buttons to knob view
        knobLayout.addView(buttonLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));

        rootLayout.addView(knobLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400, 0));
        setContentView(rootLayout);
    }
}
