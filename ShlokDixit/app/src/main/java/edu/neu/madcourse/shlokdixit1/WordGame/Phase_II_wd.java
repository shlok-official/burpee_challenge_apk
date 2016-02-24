package edu.neu.madcourse.shlokdixit1.WordGame;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import edu.neu.madcourse.shlokdixit1.R;

/**
 * Created by shlokdixit on 23/02/16.
 */
public class Phase_II_wd extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_wg_phase_2);


        final TextView timer;

        timer = (TextView) findViewById(R.id.timer3);
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time Left:" + millisUntilFinished / 1000);
            }

            public void onFinish() {

                Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Phase_II_wd.this, Final_score_wd.class);
                startActivity(intent);

            }

        }.start();
    }
}