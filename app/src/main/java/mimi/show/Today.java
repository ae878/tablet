package mimi.show;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class Today extends AppCompatActivity {
    CountDownTimer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.today);

        TextView title = (TextView) findViewById(R.id.todayTitle);
        title.setTypeface(Typeface.createFromAsset(getAssets(), "Tayle_B.ttf"));


        timer = new CountDownTimer(10000,10000){
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
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
        timer.cancel();
        finish();
    }
}
