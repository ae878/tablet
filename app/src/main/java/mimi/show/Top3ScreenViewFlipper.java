package mimi.show;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;


import com.androidquery.AQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Top3ScreenViewFlipper extends RelativeLayout implements View.OnTouchListener {
    private AQuery aq = new AQuery( this );


    public static int countIndexes = 3;
    RelativeLayout tagLayout;
    ImageView[] tag;
    View[] views;
    ViewFlipper flipper;
    TextView[] heartLike;
    TextView[] tagText;
    CountDownTimer timer;
    int likeInt[];
    String likeString;

    ImageView heart;
    CountDownTimer timer2=null;

    int currentIndex = 0;
    float upY,downY;


    public Top3ScreenViewFlipper(Context context){


        super(context);

        // send();
        init(context);
    }
    public Top3ScreenViewFlipper(Context context, AttributeSet attrs) {

        super(context, attrs);

        // send();
        init(context);
    }


    public void init(final Context context){


        likeInt = new int[3];

        for(int i=0;i<3;i++){

            likeInt[i] = Integer.parseInt(MainActivity.likeTop3[0]);
            likeInt[i]++;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.top3screenview,this,true);


        tagLayout = (RelativeLayout) findViewById(R.id.tagLayout);
        flipper = (ViewFlipper) findViewById(R.id.top3flipper);
        flipper.setOnTouchListener(this);


        views = new ImageView[countIndexes];
        tag = new ImageView[countIndexes];
        tagText = new TextView[countIndexes];

        heartLike = new TextView[countIndexes];

        heart = new ImageView(context);


        heart=(ImageView)findViewById(R.id.like);
        tag[0]= (ImageView) findViewById(R.id.tag1);
        tag[1] = (ImageView) findViewById(R.id.tag2);
        tag[2] = (ImageView) findViewById(R.id.tag3);

        tagText[0]= (TextView) findViewById(R.id.tag1text);
        tagText[1] = (TextView) findViewById(R.id.tag2text);
        tagText[2] = (TextView) findViewById(R.id.tag3text);

        heartLike[0] = (TextView)findViewById(R.id.liketext1) ;
        heartLike[1] = (TextView)findViewById(R.id.liketext2);
        heartLike[2] = (TextView)findViewById(R.id.liketext3);


        timer = new CountDownTimer(10000,10000){
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                timer.cancel();
                Intent intent = new Intent(context, NewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);//MainActivity.class로 돌아가면서 스택에 있는 activity들을 모두 삭제하고 돌아간다.
                context.startActivity(intent);

            }
        };
        timer .start();
        tagText[0].setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ViewFlipper flipper = (ViewFlipper) findViewById(R.id.top3flipper);
                timer.cancel();
                timer.start();
                heartLike[0].setText(MainActivity.likeTop3[0]);
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
                heartLike[1].setText(MainActivity.likeTop3[1]);
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
                heartLike[2].setText(MainActivity.likeTop3[2]);
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

                case 0 :  aq.id(imageSample).image(MainActivity.img_dir2[0]);
                    heartLike[0].setText(MainActivity.likeTop3[0]);

                    break;
                case 1 : imageSample.setImageDrawable(MainActivity.fetchImage(MainActivity.img_dir2[1]));;
                    heartLike[1].setText(MainActivity.likeTop3[1]);
                    break;
                case 2 : imageSample.setImageDrawable(MainActivity.fetchImage(MainActivity.img_dir[2]));;
                    heartLike[2].setText(MainActivity.likeTop3[2]);
                    break;
            }

            views[i] = imageSample;

            flipper.addView(views[i]);
        }

        heart.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v){

                timer.cancel();
                timer.start();

                heartPush(0);

                timer2 = new CountDownTimer(400,400){
                    public void onTick(long millisUntilFinished) {

                    }
                    public void onFinish() {
                        heart.setImageResource(R.drawable.like2);
                        System.out.println("끝");
                    }
                };

                timer2 .start();
                heart.setImageResource(R.drawable.like2push);

                ImageView smallheart = (ImageView)findViewById(R.id.smalllike);
                smallheart.setVisibility(View.VISIBLE);
                smallheart.startAnimation(AnimationUtils.loadAnimation(context,R.anim.heart_up));
                smallheart.setVisibility(View.INVISIBLE);



                switch (currentIndex)
                {
                    case 0:

                        likeString = String.valueOf(likeInt[0]);
                        heartLike[0].setText(likeString);
                        likeInt[0]++;
                        break;
                    case 1:

                        likeString = String.valueOf(likeInt[1]);
                        heartLike[1].setText(likeString);
                        likeInt[1]++;
                        break;
                    case 2:
                          likeString = String.valueOf(likeInt[2]);
                    heartLike[2].setText(likeString);
                    likeInt[2]++;
                    break;
                }
            }
        });
    }
    public void updateIndexes() {
        for(int i = 0; i < countIndexes; i++) {
            if (i == currentIndex) {

                LayoutParams params = (LayoutParams) tag[i].getLayoutParams();
                params.width = 400;
                params.height = 120;

                tag[i].setLayoutParams(params);

                tagText[i].setTextSize(0,70);
            }else{

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

                heartLike[currentIndex].setText(MainActivity.likeTop3[currentIndex]);
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

                heartLike[currentIndex].setText(MainActivity.likeTop3[currentIndex]);
                updateIndexes();

            }
        }


        return true;
    }




    public static void heartPush(final int num) {
int i;
        String sendData = null;
        final String itemName[] = new String[50];
       for(i=0;i<3;i++) {
           itemName[i] = MainActivity.itemNameTop3[i];
       }
        new Thread() {
            public void run() {
                try {

                    URL url = new URL("http://52.78.68.136/get_item_data_sorted_by_liked");       // URL 설정
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
                        jObj.put("asd", itemName[num]);


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
