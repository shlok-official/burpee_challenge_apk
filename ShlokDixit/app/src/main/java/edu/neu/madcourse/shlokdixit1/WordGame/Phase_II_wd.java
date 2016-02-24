package edu.neu.madcourse.shlokdixit1.WordGame;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import edu.neu.madcourse.shlokdixit1.R;

/**
 * Created by shlokdixit on 23/02/16.
 */
public class Phase_II_wd extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_wg_phase_2);
        setTitle("Word Game- Phase-II");

        final TextView timer;

        timer=(TextView) findViewById(R.id.timer3);
        new CountDownTimer(90000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time Left:" + millisUntilFinished / 1000);
            }

            public void onFinish() {


                Intent intent = new Intent(Phase_II_wd.this, WordGameCredits.class);
                startActivity(intent);
                // timer.setText("Phase-II Started");


            }

        }.start();
    }
}