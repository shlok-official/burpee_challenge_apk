package edu.neu.madcourse.shlokdixit1.WordGame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import edu.neu.madcourse.shlokdixit1.R;


/**
 * Created by shlokdixit on 23/02/16.
 */
public class Phase_II_wd extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    MediaPlayer mMediaPlayer;
    ToggleButton t;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_wg_phase_2);
        t = (ToggleButton) findViewById(R.id.togglebutton1);
        t.setOnCheckedChangeListener(this);

        ////////////////////////////////////////////////////////
        TextView myText = (TextView) findViewById(R.id.phaseII);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);
        ////////////////////////////////////////////////////////

        final TextView timer;

        timer = (TextView) findViewById(R.id.timer3);
        new CountDownTimer(90000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText("Time Left:00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Phase_II_wd.this, Final_score_wd.class);
                startActivity(intent);
                //finish();
            }
        }.start();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // The toggle is enabled
            mMediaPlayer.stop();

        } else {
            // The toggle is disabled
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cartoon);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
            finish();
        }
    }

}