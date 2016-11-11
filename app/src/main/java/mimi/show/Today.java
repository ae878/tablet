package mimi.show;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Today extends AppCompatActivity {
    CountDownTimer timer = null;
    CountDownTimer timer2 = null;
    int likeInt;
    String likeString;
    int i=0;

    Animation zigZagAnimation[]= new ZigZagAnimation[100];

    ImageView[] smallheart = new ImageView[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.today);



        for(int j=0;j<100;j++)
        {        smallheart[j]= (ImageView)findViewById(R.id.smallliketoday);

            zigZagAnimation[j]=new ZigZagAnimation();
        }

        likeInt = Integer.parseInt(MainActivity.like);
        likeInt++;

        ImageView test = (ImageView) findViewById(R.id.test);

        test.setImageDrawable(MainActivity.fetchImage(MainActivity.img_dir[0]));


        TextView liketext = (TextView)findViewById(R.id.liketodaytext);

        liketext.setTypeface(Typeface.createFromAsset(getAssets(), "pencil.ttf"));
        liketext.setText(MainActivity.like);

        TextView title = (TextView) findViewById(R.id.todayTitle);
        title.setTypeface(Typeface.createFromAsset(getAssets(), "pencil.ttf"));


        timer = new CountDownTimer(10000,10000){
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {


                System.out.println("투데이 끝");
                MainActivity.send();
                timer.cancel();
                finish();
            }
        };



        timer .start();

        View view1 = (View) findViewById(R.id.today);
        view1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    timerReset();
                }
                return true;
            }
        });
    }

    private void timerReset() {
        timer.cancel();
        timer.start();
    }

    public void onButtonBackClicked(View v){
        MainActivity.send();
        timer.cancel();
        finish();
    }



    public void onButtonLikeToday(View v){

        final ImageView heart=(ImageView)findViewById(R.id.liketoday);
        timerReset();

        send();
        timer2 = new CountDownTimer(400,400){
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {

                heart.setBackgroundResource(R.drawable.like2);
            }
        };

        timer2 .start();

        heart.setBackgroundResource(R.drawable.like2push);
        smallheart[i].setVisibility(View.VISIBLE);
        if(i<100) {
            smallheart[i].setAnimation(zigZagAnimation[i++]);
        }
        else
            smallheart[i].setAnimation(zigZagAnimation[i=0]);

        smallheart[i++].setVisibility(View.INVISIBLE);


        likeString = String.valueOf(likeInt);


        TextView liketext = (TextView)findViewById(R.id.liketodaytext);
        liketext.setText(likeString);

        likeInt++;
    }


    public static void send() {

        String sendData = null;
        final String itemName = MainActivity.itemName;
        new Thread() {
            public void run() {
                try {

                    URL url = new URL("http://52.78.68.136/liked");       // URL 설정
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
                    //--------------------------
                    //   전송 모드 설정 - 기본적인 설정이다
                    //--------------------------
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);                         // 서버에서 읽기 모드 지정
                    http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
                    http.setRequestMethod("POST");

                    http.setRequestProperty("content-type", "application/json");
                    //--------------------------
                    //   서버로 값 전송
                    //--------------------------
                    StringBuffer buffer = new StringBuffer();

                    JSONObject jObj = new JSONObject();
                    try {
                                       jObj.put("asd", itemName);


                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                    OutputStream os = http.getOutputStream();
                    os.write(jObj.toString().getBytes());



                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                        builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                    }
                    // 전송결과를 전역 변수에 저장


                } catch (MalformedURLException e) {
                    //
                } catch (IOException e) {
                    System.out.println(e);
                }
            }

        }.start();
    }
}


class ZigZagAnimation extends Animation {
    private PathMeasure pm;
    float[] pos = new float[2];

    public ZigZagAnimation() {
        Path p = new Path();
        p.moveTo(0f, 0f);
        p.quadTo(0f,0f,100f,-300f);
        p.quadTo(100f,-300f,-100f,-600f);
        p.quadTo(-100f,-600f,100f,-900f);
        p.quadTo(100f,-900f,-100f,-1200f);
        p.quadTo(-100f,-1200f,100f,-1500f);
        pm = new PathMeasure(p, false);
        setDuration(2000);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float distance = pm.getLength() * interpolatedTime;
        pm.getPosTan(distance, pos, null);
        t.getMatrix().postTranslate(pos[0], pos[1]);
    }
}