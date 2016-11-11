package mimi.show;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class ScreenViewFlipper extends RelativeLayout implements View.OnTouchListener {

    public static int countIndexes = 4;
    LinearLayout buttonLayout;
    ImageView[] indexButtons;
    View[] views;
    ViewFlipper flipper;

    int currentIndex = 0;
    float upX,downX;


    public ScreenViewFlipper(Context context){

        super(context);

        init(context);



    }
    public ScreenViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);

    }


    public void init(final Context context){


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.screenview,this,true);

        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.setOnTouchListener(this);

        buttonLayout.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper);
                flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_out));

                LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
                flipper.showNext();
                if (currentIndex == countIndexes - 1)
                    currentIndex = 0;
                else
                    currentIndex++;

                updateIndexes();
            }
        });


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.width=40;
        params.height=40;
        params.topMargin=50;
        params.leftMargin = 50;
        this.setLayoutParams(params);

        indexButtons = new ImageView[countIndexes];
        views = new ImageView[countIndexes];

        for(int i=0; i< countIndexes;i++){
            indexButtons[i] = new ImageView(context);
            if(i==currentIndex) {
                indexButtons[i].setImageResource(R.drawable.swipe_select);

            }else{
                indexButtons[i].setImageResource(R.drawable.swipe);
            }
            buttonLayout.addView(indexButtons[i],params);

            ImageView imageSample = new ImageView(context);
            switch (i) {

                case 0 :
                    imageSample.setBackground(MainActivity.fetchImage(MainActivity.img_dir2[0]));
                    break;
                case 1 : imageSample.setBackground(MainActivity.fetchImage(MainActivity.img_dir2[1]));
                    break;
                case 2 : imageSample.setBackground(MainActivity.fetchImage(MainActivity.img_dir2[2]));
                    break;
                case 3 : imageSample.setBackground(MainActivity.fetchImage(MainActivity.img_dir2[3]));
                    break;
            }

            views[i] = imageSample;

            flipper.addView(views[i]);
        }
    }
    public void updateIndexes() {
        for(int i = 0; i < countIndexes; i++) {
            if (i == currentIndex) {
                indexButtons[i].setImageResource(R.drawable.swipe_select);

            }else{
                indexButtons[i].setImageResource(R.drawable.swipe);

            }
        }

    }




    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v != flipper) return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
            flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_in));
            flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_out));

            flipper.showNext();
            if (currentIndex == countIndexes - 1)
                currentIndex = 0;
            else
                currentIndex++;

            updateIndexes();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            upX = event.getX();

            if (upX < downX) {

                flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_out));

                flipper.showNext();
                if (currentIndex == countIndexes - 1)
                    currentIndex = 0;
                else
                    currentIndex++;

                updateIndexes();

            } else if (upX > downX) {
                flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_out));
                flipper.showPrevious();
                if (currentIndex == 0)
                    currentIndex = 2;
                else
                    currentIndex--;

                updateIndexes();

            }
        }


        return true;
    }



}