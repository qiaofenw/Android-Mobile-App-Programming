package com.example.apple.project0;
/**
 * Author: Qiaofeng Wang
 * A software to generate a light piture and a switch to control it.
 *
 * Sun Aug 28
 */

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    ImageView view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the layout
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(Color.BLACK);

        // set the basic view
        view = new ImageView(this);
        //view.setImageResource(R.drawable.on);
        rootLayout.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        //Create a switch for light
        Switch s = new Switch(this);
        LinearLayout.LayoutParams lslp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        lslp.gravity = Gravity.CENTER_HORIZONTAL;
        rootLayout.addView(s, lslp);

        setContentView(rootLayout);

        s.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        // A listener for the switch
        if(b) {
            view.clearColorFilter();
            view.setImageResource(R.drawable.on);
        }
        else
            view.setColorFilter(Color.BLACK);
    }
}
