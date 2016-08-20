package mimi.show;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    LinearLayout buttonLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.title);
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "Tayle_B.ttf"));

        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        mHandler.sendEmptyMessage(0);

    }



android.os.Handler mHandler = new android.os.Handler(){
    public void handleMessage(Message msg){
        buttonLayout.performClick();
        mHandler.sendEmptyMessageDelayed(2500, 2500);
    };
};
    public void onButton1Clicked(View v) {

        Intent intent = new Intent(getApplicationContext(), Top3Activity.class);
        startActivity(intent);
    }

    public void onButton2Clicked(View v) {

        Intent intent = new Intent(getApplicationContext(), Today.class);
        startActivity(intent);
    }
}




