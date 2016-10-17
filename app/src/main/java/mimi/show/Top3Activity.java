package mimi.show;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


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

public class Top3Activity extends AppCompatActivity {
    TextView textView;
    CountDownTimer timer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.top3_main);
        textView = (TextView) findViewById(R.id.top3Title);
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "pencil.ttf"));

        textView = (TextView) findViewById(R.id.tag1text);
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "pencil.ttf"));


        textView = (TextView) findViewById(R.id.tag2text);
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "pencil.ttf"));

        textView = (TextView) findViewById(R.id.tag3text);
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "pencil.ttf"));


        textView = (TextView) findViewById(R.id.liketext1);
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "pencil.ttf"));



    }


    public void onButtonBackClicked(View v){

        finish();
    }


}
