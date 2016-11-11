package mimi.show;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.params.Face;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URLEncoder;
import java.util.Timer;

public class NewActivity extends AppCompatActivity {

    TextView textView1,textView2,textView3,textView4,textView5,textView6;
    CountDownTimer timer;
    ImageView best;
    ImageView today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_new);
        MainActivity.send();
        MainActivity.bestsend();
        MainActivity.likesend();
        MainActivity.facesend();

        best = (ImageView)findViewById(R.id.facebestimg);
        today= (ImageView)findViewById(R.id.facetodayimg);


        best.setBackground(MainActivity.fetchImage(MainActivity.img_dir4[0]));



        if((MainActivity.img_dir4[1])==null)
       today.setBackground(MainActivity.fetchImage(MainActivity.img_dir4[0]));
        else
            today.setBackground(MainActivity.fetchImage(MainActivity.img_dir4[1]));

        textView1 = (TextView) findViewById(R.id.todaymenu);
        textView1.setTypeface(Typeface.createFromAsset(getAssets(), "pencil.ttf"));


        textView2 = (TextView) findViewById(R.id.best);
        textView2.setTypeface(Typeface.createFromAsset(getAssets(), "pencil.ttf"));


        textView3 = (TextView) findViewById(R.id.nanum);
        textView3.setTypeface(Typeface.createFromAsset(getAssets(), "NanumSquareB.ttf"));


        textView4 = (TextView) findViewById(R.id.nanum1);
        textView4.setTypeface(Typeface.createFromAsset(getAssets(), "NanumSquareB.ttf"));

        if((MainActivity.faceitemName[1])==null)
            textView4.setText(MainActivity.faceitemName[0]);
        else
            textView4.setText(MainActivity.faceitemName[1]);

        textView3.setText(MainActivity.faceitemName[0]);


        textView5 = (TextView) findViewById(R.id.hm);
        textView5.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));


        textView6 = (TextView) findViewById(R.id.hm2);
        textView6.setTypeface(Typeface.createFromAsset(getAssets(), "HMFMPYUN.TTF"));

        if((MainActivity.faceitemNameEng[1])==null)
            textView6.setText(MainActivity.faceitemNameEng[0]);
        else
            textView6.setText(MainActivity.faceitemNameEng[1]);

        textView5.setText(MainActivity.faceitemNameEng[0]);
        ;

        timer = new CountDownTimer(10000,10000){
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                finish();

                System.out.println("new 끝");
            }
        };
        View view1 = (View) findViewById(R.id.newscreen);
        view1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    timer.cancel();
                    timer.start();
                }
                return true;
            }
        });

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                timer.start();
            }
        });
        timer .start();
    }


    public void onButton1Clicked(View v) {

        timer.cancel();
        Intent intent = new Intent(getApplicationContext(), Top3Activity.class);
        startActivity(intent);
    }

    public void onButton2Clicked(View v) {

        timer.cancel();
        Intent intent = new Intent(getApplicationContext(), Today.class);
        startActivity(intent);
    }
}