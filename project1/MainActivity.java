package edu.utah.cs4530.project1;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

/*
    Author: Qiaofeng Wang

    Project 1 - Palette Paint

    Note: 1. DO NOT DRAW ON SOMEWHERE EXCEPT FOR THE CANVAS, NOTHING WILL HAPPEN.
          2. FOR THE REMOVE BUTTON. THE FILE SHOWS THAT WE NEED TO REMOVE THE COLOR WHICH IS THE MOST RECENTLY ADD TO THE PALETTE.
             THIS IS TOO WEIRD. USER SHOULD BE ABLE TO CHOOSE WHICH COLOR TO REMOVE. SO THIS APP WILL REMOVE THE HIGHLIGHT COLOR.
          3. REMOVE COLOR OR TOUCH ON SOMEWHERE NOT A COLOR WILL CAUSE NON-HIGHLIGHT SITUATION.
             IF THERE IS NO COLOR IS HIGHLIGHTED, USER WILL DRAW NOTHING ON THE CANVAS. THEN USER CAN TRY MOVE FINGER ON THE CANVAS.

    Ten Reasons For Extra Credit: 1. A REAL WOOD-MADE CANVAS VIEW.
                                  2. THE KNOBS ARE FILLED IN THE EXACTLY COLOR WHERE THE NIB IS. USER CAN EASILY SEE WHICH COLOR THEY ARE PICKING UP.
                                  3. REMOVE BUTTON WILL REMOVE THE HIGHLIGHT COLOR, NOT THE COLOR WHICH IS MOST RECENTLY ADDED INTO THE PALETTE.
                                  4. A REAL WOOD-MADE PALETTE VIEW.
                                  5. THE RANDOM SHAPE COLORS ON THE PALETTE. THEY ARE LIKE REAL NOW!
                                  6. THEIR SHAPES WILL NOT CHANGE EVEN ADD OR REMOVE FUNCTION ARE CALLED AND THEN THE PALETTE IS REDRAWED.
                                  7. COLORS ON THE PALETTE ALL HAVE REASONABLE LOCATION.
                                  8. I PREFER A CLEAN AND NEAT USER EXPERIENCE. SO THE BACKGROUND IS CLEAN, WHITE AND GOOD-LOOKING.
                                  9. EVERY COLOR CAN BE HIGHLIGHTED AND USER CAN SEE WHICH ONE EVEN IT IS A YELLOW OR YELLOW-LIKE COLOR (BLUE HIGHLIGHT STYLE).
                                  10. U R AWESOME.

 */
public class MainActivity extends AppCompatActivity implements ValueAnimator.AnimatorUpdateListener{

    PaintView paintView = null;
    List<Path> tempPath ;
    List<Integer> tempColor;

    //List<Point> tempPoints ;
    //List<Integer> tempColorPoints;

    Button play ;
    //Button pause = new Button(this);
    Button back ;
    Button forward ;
    Button p ;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        // Set menu
        LinearLayout menuView = new LinearLayout(this);
        menuView.setOrientation(LinearLayout.HORIZONTAL);

        play = new Button(this);
        //Button pause = new Button(this);
        back = new Button(this);
        forward = new Button(this);
        p = new Button(this);

        // animation
        //final ValueAnimator animator = new ValueAnimator();
        //animator.setDuration(5000);
        //animator.setFloatValues(0.0f,100.0f);

        play.setText("play");
        play.setTextSize(17);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(play.getText() == "play"){
                    back.setEnabled(false);
                    forward.setEnabled(false);
                    p.setEnabled(false);

                    play.setText("pause");

                    tempPath = new ArrayList<Path>(Gallery.pathList);
                    tempColor = new ArrayList<Integer>(Gallery.colorList);

                    //tempPoints = new ArrayList<Point>(Gallery.pointList);
                    //tempColorPoints = new ArrayList<Integer>(Gallery.colorPointList);

                    paintView.clear();
                    animate();
                }
                else if (play.getText() == "pause"){

                    //animator.pause();

                    play.setText("play");
                    animation.pause();
                }

            }
        });

        back.setText("<<");
        back.setTextSize(17);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paintView.onClickUndo();
            }
        });

        forward.setText(">>");
        forward.setTextSize(17);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paintView.onClickRedo();

            }
        });

        p.setText("Palette");
        p.setTextSize(17);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });

        menuView.addView(play, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        //menuView.addView(pause, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        menuView.addView(back, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        menuView.addView(forward, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        menuView.addView(p, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        rootLayout.addView(menuView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        // SET CURRENT COLOR TO HIGHLIGHT
        //SplotchView current = new SplotchView(this);

        // Set something funny first
        View viewTop = new View(this);
        viewTop.setBackground(getDrawable(R.drawable.canvas1));
        rootLayout.addView(viewTop, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        // Set Paint View
        paintView = new PaintView(this);
        paintView.setBackgroundColor(Color.WHITE);

        rootLayout.addView(paintView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 4));

        // Set something funny
        View viewDown = new View(this);
        viewDown.setBackground(getDrawable(R.drawable.canvas2));

        rootLayout.addView(viewDown, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2));

        // Set palette

        //paletteLayout.setBackgroundColor(Color.WHITE);
//        final CircleLayout paletteLayout = new CircleLayout(this);
//        paletteLayout.setBackground(getDrawable(R.drawable.palette));

        /*
            Here are some test cases for palette.
         */
        /*
        SplotchView s1 = new SplotchView(this);
        s1.setSplotchColor(Color.RED);
        paletteLayout.addView(s1);

        SplotchView s2 = new SplotchView(this);
        s2.setSplotchColor(Color.GREEN);
        paletteLayout.addView(s2);

        SplotchView s3 = new SplotchView(this);
        s3.setSplotchColor(Color.BLUE);
        paletteLayout.addView(s3);

        SplotchView s4 = new SplotchView(this);
        s4.setSplotchColor(Color.YELLOW);
        paletteLayout.addView(s4);

        SplotchView s5 = new SplotchView(this);
        s5.setSplotchColor(Color.rgb(128, 0, 128));
        paletteLayout.addView(s5);
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

        // TODO: move to another activity
        //rootLayout.addView(paletteLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2));

        paletteLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                boolean b = false;

                for (int i = 0; i < paletteLayout.getChildCount(); i++) {
                    SplotchView splotch = (SplotchView) paletteLayout.getChildAt(i);

                    if (x >= splotch.getLeft() && x <= splotch.getRight() && y >= splotch.getTop() && y <= splotch.getBottom()) {
                        splotch.setHighLighted(true);
                        paintView.setPaintColor(splotch.getSplotchColor());
                        b = true;
                        //break;
                    } else {
                        splotch.setHighLighted(false);
                        if (!b)
                            paintView.setPaintColor(Color.TRANSPARENT);
                    }

                }
                return true;
            }
        });

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
                for (int i = 0; i < paletteLayout.getChildCount(); i++) {
                    SplotchView temp = (SplotchView) paletteLayout.getChildAt(i);
                    temp.setHighLighted(false);
                }

                newSplotch.setHighLighted(true);

                Color newColor = new Color();
                newColor.alpha(255);
                newColor.red(r);
                newColor.green(g);
                newColor.blue(b);

                newSplotch.setSplotchColor(newColor.rgb(r, g, b));
                paletteLayout.addView(newSplotch);
                paintView.setPaintColor(newSplotch.getSplotchColor());
            }
        });

        Button minus = new Button(this);
        minus.setText("-");
        minus.setTextSize(30);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < paletteLayout.getChildCount(); i++) {
                    SplotchView temp = (SplotchView) paletteLayout.getChildAt(i);

                    if (temp.getHighLighted() && paletteLayout.getChildCount() > 1) {
                        paletteLayout.removeView(temp);
                        paintView.setPaintColor(Color.TRANSPARENT);
                    }
                }
            }
        });

        buttonLayout.addView(plus, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        buttonLayout.addView(minus, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        */

        // Add buttons to knob view
        //knobLayout.addView(buttonLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));

        // TODO: move to another activity
        // rootLayout.addView(knobLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400, 0));

        // ***********************************************************************************************************************************************

        setContentView(rootLayout);
    }

    ValueAnimator animation = new ValueAnimator();

    private void animate() {
        animation.setDuration(5000);
        animation.setIntValues(0, tempPath.size()-1);
        animation.addUpdateListener(this);
        //paintView.clear();
        animation.start();
//        if (!animation.isRunning()) {
//            _animatorMode = false;
//        }
//        else {
//            _animatorMode = true;
//        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        //float animationValue = (Float)animation.getAnimatedValue();
        //paintView.setX(animationValue);

        //paintView.drawNewPath(_animatorPathList.get(animationValue), _animatorColorList.get(animationValue));
        //c.drawPoint(animationValue,animationValue,new Paint());
        //Matrix translateMatrix = Matrix.translateM(animationValue);
        //_viewProperty = animationValue;

        int animationValue = (int) animation.getAnimatedValue();
        //Log.i("Animation", "Current Animation value: " + animationValue);

        paintView.addPath(tempPath.get(animationValue), tempColor.get(animationValue));
        //paintView.addPoint(tempPoints.get(animationValue), tempColorPoints.get(animationValue));
//        Gallery.pointList.add(animationValue, tempPoints.get(animationValue));
//        Gallery.colorPointList.add(animationValue, tempColorPoints.get(animationValue));

        if(animationValue == tempPath.size()-1){
//            Gallery.pathList = tempPath;
//            Gallery.colorList = tempColor;
            paintView.recover(tempPath, tempColor);
            play.setText("play");
            back.setEnabled(true);
            forward.setEnabled(true);
            p.setEnabled(true);
        }

    }
}
