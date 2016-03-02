package edu.neu.madcourse.shlokdixit1.WordGame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("PHASE-II");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_wg_phase_2);
        t = (ToggleButton) findViewById(R.id.togglebutton1);
        t.setOnCheckedChangeListener(this);
        startCountDownTimer();
        TextView text = (TextView) findViewById(R.id.phase);
        text.setText("PHASE-II");

        ////////////////////////////////////////////////////////
        TextView myText = (TextView) findViewById(R.id.phase);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);
        ////////////////////////////////////////////////////////
        }
    long remaining = 0;
    long total = 90000;
         TextView timer1;
    CountDownTimer countDownTimer = null;
    public void startCountDownTimer() {
        timer1 = (TextView) findViewById(R.id.timer);
        new CountDownTimer(total, 1000) {
            public void onTick(long millisUntilFinished) {
                timer1.setText("Time Left:00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                startnextphase();
                //finish();
            }
        }.start();
    }
    public void resumeCountDownTimer() {
        timer1 = (TextView) findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(remaining, 1000) {
            public void onTick(long millisUntilFinished) {
                total = millisUntilFinished;
                timer1.setText("Time Left:00:" + millisUntilFinished / 1000);
                remaining = millisUntilFinished;
            }

            public void onFinish() {
               startnextphase();
            }
        }.start();

    }


    public void startnextphase(){
        Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Phase_II_wd.this, WordGameCredits.class);
        startActivity(intent);
        finish();
        //setContentView(R.layout.activity_game_wg_phase_2);
    }




    public void pausegame(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("GAME PAUSED")
                .setPositiveButton("UNPAUSE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      // resumeCountDownTimer();
                    }
                })
                .setNegativeButton("EXIT GAME", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        finish();
                    }
                }).setCancelable(false);
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
      //-->  countDownTimer.cancel();
        //onPause();
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