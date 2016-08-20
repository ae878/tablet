package mimi.show;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Top3Activity extends AppCompatActivity implements View.OnTouchListener {

    private ViewFlipper flipper;
    private float upY, downY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.top3_main);

        TextView title = (TextView)findViewById(R.id.top3Title);
        title.setTypeface(Typeface.createFromAsset(getAssets(),"Tayle_B.ttf"));


        flipper = (ViewFlipper) findViewById(R.id.top3flipper);
        flipper.setOnTouchListener(this);

        RelativeLayout sub1 = (RelativeLayout) View.inflate(this, R.layout.top3, null);
        RelativeLayout sub2 = (RelativeLayout) View.inflate(this, R.layout.top3_2, null);
        RelativeLayout sub3 = (RelativeLayout) View.inflate(this, R.layout.top3_1, null);


        flipper.addView(sub1);
        flipper.addView(sub2);
        flipper.addView(sub3);


        TextView textView = (TextView)findViewById(R.id.first);
        textView.setTypeface(Typeface.createFromAsset(getAssets(),"Tayle_B.ttf"));

        textView = (TextView)findViewById(R.id.second);
        textView.setTypeface(Typeface.createFromAsset(getAssets(),"Tayle_B.ttf"));

        textView = (TextView)findViewById(R.id.third);
        textView.setTypeface(Typeface.createFromAsset(getAssets(),"Tayle_B.ttf"));

        ImageView tag1 = (ImageView)findViewById(R.id.tag1);


    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v != flipper) return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            upY = event.getY();

            if (upY > downY) {
                flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_down_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_down_out));
                flipper.showNext();
            } else if (upY < downY) {
                flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_out));
                flipper.showPrevious();
            }
        }
        return true;


    }
}




