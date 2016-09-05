package mimi.show;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    LinearLayout buttonLayout;
    public static today_data Today_DATA = new today_data();

    int num = 50;
    static String[] img_dir;
    static String myResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.title);
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "Tayle_B.ttf"));
        Today_DATA.img_url = "http://icon.daumcdn.net/w/icon/1606/30/105915014.png";
        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        mHandler.sendEmptyMessage(0);

    }

    public static Drawable fetchImage(final String urlstr) {
        final int[] i = {0};
        final String str = urlstr;
        final Bitmap[] img = new Bitmap[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url;
                    url = new URL(str);
                    HttpURLConnection c = (HttpURLConnection) url.openConnection();
                    c.setDoInput(true);
                    c.connect();
                    InputStream is = c.getInputStream();
                    img[0] = BitmapFactory.decodeStream(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i[0] = 1;
            }
        });
        thread.start();
        while (i[0] == 0) {
        }
        return new BitmapDrawable(img[0]);

    }


    public static void send(int x) {
        new Thread() {
            public void run() {
                try {

                    System.out.println("himijin");

                    URL url = new URL("http://52.78.68.136/get_todayable_page_data");       // URL 설정
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
                    //--------------------------
                    //   전송 모드 설정 - 기본적인 설정이다
                    //--------------------------
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);                         // 서버에서 읽기 모드 지정
                    http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
                    http.setRequestMethod("POST");

                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                    //--------------------------
                    //   서버로 값 전송
                    //--------------------------
                    StringBuffer buffer = new StringBuffer();

                    OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "EUC-KR"); //넘어감
                    PrintWriter writer = new PrintWriter(outStream);
                    writer.write(buffer.toString());
                    writer.flush();

                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                        builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                    }
                    myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
                    System.out.println(myResult);

                    doJSONParser();

                } catch (MalformedURLException e) {
                    //
                } catch (IOException e) {
                    System.out.println(e);
                }
            }

        }.start();
    }




   static public void doJSONParser(){

       img_dir=new String[50];

       try {
            JSONArray jarray = new JSONArray(myResult);   // JSONArray 생성
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                System.out.println(jObject);
                img_dir[i] ="http://52.78.68.136/"+ jObject.getString("img_dir");
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
}



    android.os.Handler mHandler = new android.os.Handler(){
    public void handleMessage(Message msg){
        buttonLayout.performClick();
        mHandler.sendEmptyMessageDelayed(2500, 2500);
    };
};
    public void onButton3Clicked(View v) {

        Intent intent = new Intent(getApplicationContext(), NewActivity.class);
        startActivity(intent);
    }
    //오늘의메뉴, 메인메뉴, 베스트
    public static class today_data implements Serializable{
        int like;
        String img_url;
        public today_data()
        {
            like=0;
        }
    }
}




