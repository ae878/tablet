package mimi.show;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Top3ScreenViewFlipper extends RelativeLayout implements View.OnTouchListener {

    public static int countIndexes = 3;
    RelativeLayout tagLayout;
    ImageView[] tag;
    View[] views;
    ViewFlipper flipper;
    TextView[] tagText;
    CountDownTimer timer;

    int currentIndex = 0;
    float upY,downY;


    public Top3ScreenViewFlipper(Context context){
        super(context);
        init(context);
    }
    public Top3ScreenViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init(final Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.top3screenview,this,true);


        tagLayout = (RelativeLayout) findViewById(R.id.tagLayout);
        flipper = (ViewFlipper) findViewById(R.id.top3flipper);
        flipper.setOnTouchListener(this);

        views = new ImageView[countIndexes];
        tag = new ImageView[countIndexes];
        tagText = new TextView[countIndexes];

        tag[0]= (ImageView) findViewById(R.id.tag1);
        tag[1] = (ImageView) findViewById(R.id.tag2);
        tag[2] = (ImageView) findViewById(R.id.tag3);

        tagText[0]= (TextView) findViewById(R.id.tag1text);
        tagText[1] = (TextView) findViewById(R.id.tag2text);
        tagText[2] = (TextView) findViewById(R.id.tag3text);


        timer = new CountDownTimer(10000,10000){
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                timer.cancel();
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        };
        timer .start();
        tagText[0].setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ViewFlipper flipper = (ViewFlipper) findViewById(R.id.top3flipper);
                timer.cancel();
                timer.start();
                switch(flipper.getDisplayedChild()){

                    case 0:
                         break;
                    case 1:
                    case 2:
                        flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_down_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_down_out));
                        flipper.setDisplayedChild(0);
                        currentIndex = 0;
                        updateIndexes();
                        break;
                }
            }
        });


        tagText[1].setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ViewFlipper flipper = (ViewFlipper) findViewById(R.id.top3flipper);
                timer.cancel();
                timer.start();
                switch(flipper.getDisplayedChild()){
                    case 1:
                        break;
                    case 0:
                        flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_up_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_up_out));
                        flipper.setDisplayedChild(1);
                        currentIndex = 1;
                        updateIndexes();
                        break;
                    case 2:
                        flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_down_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_down_out));
                        flipper.setDisplayedChild(1);
                        currentIndex = 1;
                        updateIndexes();
                        break;
                }
            }
        });
        tagText[2].setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ViewFlipper flipper = (ViewFlipper) findViewById(R.id.top3flipper);
                timer.cancel();
                timer.start();
                switch(flipper.getDisplayedChild()){
                    case 2:
                        break;
                    case 1:

                        flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_up_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_up_out));
                        flipper.setDisplayedChild(2);
                        currentIndex = 2;
                        updateIndexes();
                        break;
                    case 0:
                        flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_down_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_down_out));
                        flipper.setDisplayedChild(2);
                        currentIndex = 2;
                        updateIndexes();
                        break;
                }
            }
        });
        for(int i=0; i< countIndexes;i++){
                 ImageView imageSample = new ImageView(context);
            switch (i) {
                case 0 : imageSample.setImageResource(R.drawable.sample);
                    break;
                case 1 : imageSample.setImageResource(R.drawable.sample2);
                    break;
                case 2 : imageSample.setImageResource(R.drawable.sample3);
                    break;
            }

            views[i] = imageSample;

            flipper.addView(views[i]);
         }


    }
    public void updateIndexes() {
        for(int i = 0; i < countIndexes; i++) {
            if (i == currentIndex) {
                tag[i].setBackgroundResource(R.drawable.tab1);
                LayoutParams params = (LayoutParams) tag[i].getLayoutParams();
                params.width = 400;
                params.height = 120;

                tag[i].setLayoutParams(params);

                tagText[i].setTextSize(0,70);
            }else{
                tag[i].setBackgroundResource(R.drawable.tab2);
                LayoutParams params = (LayoutParams) tag[i].getLayoutParams();
                params.width = 250;
                params.height = 120;

                tag[i].setLayoutParams(params);

                tagText[i].setTextSize(0,50);
            }
        }

    }




    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v != flipper) return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            upY = event.getY();

            if (upY< downY) {

                flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_up_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_up_out));

                flipper.showNext();
                timer.cancel();
                timer.start();
                if (currentIndex == countIndexes - 1)
                    currentIndex = 0;
                else
                    currentIndex++;

                updateIndexes();

            } else if (upY>downY) {
                flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_down_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_down_out));
                flipper.showPrevious();    timer.cancel();
                timer.start();

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

