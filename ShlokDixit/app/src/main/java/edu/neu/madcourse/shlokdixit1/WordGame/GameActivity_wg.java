/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
 ***/
package edu.neu.madcourse.shlokdixit1.WordGame;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import edu.neu.madcourse.shlokdixit1.R;
import android.widget.ToggleButton;
import android.app.AlertDialog;

public class GameActivity_wg extends Activity implements CompoundButton.OnCheckedChangeListener {
    //public static final String KEY_RESTORE = "key_restore";
    //public static final String PREF_RESTORE = "pref_restore";
    //private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private GameFragment_wg mGameFragment;

    MediaPlayer mMediaPlayer;
    ToggleButton t;
    CountDownTimer countDownTimer = null;

    TextView timer_wg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_wg_phase_1);
        startCountDownTimer();
        mGameFragment = (GameFragment_wg) getFragmentManager()
                .findFragmentById(R.id.fragment_game);

        /////////////////////////////////////////////////////////////
        t = (ToggleButton) findViewById(R.id.togglebutton1);
        t.setOnCheckedChangeListener(this);


        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cartoon);
        ///////
        TextView myText = (TextView) findViewById(R.id.phase);


        ///////////////


        ///////////////////
        /*

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);
        ///////////////

        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            if (gameData != null) {
                // mGameFragment.putState(gameData);
            }
        }}*/
        //Log.d("UT3", "restore = " + restore);
/////////////////////////////////////////////////////////////
    }

long remaining = 0;
    long total=90000;
        ///////////////
    public void startCountDownTimer() {
        timer_wg = (TextView) findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(total, 1000) {
            public void onTick(long millisUntilFinished) {


                timer_wg.setText("Time Left:00:" + millisUntilFinished / 1000);
                remaining = millisUntilFinished;
            }
            public void onFinish() {
               // resumeCountDownTimer();
                //startCountDownTimer();
                //Toast.makeText(getApplicationContext(), "PHASE-II STARTED", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(GameActivity_wg.this, Phase_II_wd.class);
                //startActivity(intent);
                //finish();
            }
        }.start();

    }

    public void resumeCountDownTimer() {
        timer_wg = (TextView) findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(remaining, 1000) {
            public void onTick(long millisUntilFinished) {
                        total = millisUntilFinished;
                        timer_wg.setText("Time Left:00:" + millisUntilFinished / 1000);
                remaining = millisUntilFinished;
                    }
                    public void onFinish() {
                      //  Toast.makeText(getApplicationContext(), "PHASE-II STARTED", Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(GameActivity_wg.this, Phase_II_wd.class);
                        //startActivity(intent);
                    //    finish();
                    }
                }.start();

    }



//////////////////////////////////////////////////////////


/*
    public void restartGame() {
        mGameFragment.restartGame();
    }

    public void reportWinner(final Tile_wg.Owner winner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
        builder.setMessage(getString(R.string.declare_winner, winner));
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok_label,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        final Dialog dialog = builder.create();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMediaPlayer = MediaPlayer.create(GameActivity_wg.this,
                        winner == Tile_wg.Owner.X ? R.raw.cartoon
                                : winner == Tile_wg.Owner.O ? R.raw.notr_loser
                                : R.raw.cartoon
                );
                mMediaPlayer.start();
                dialog.show();
            }
        }, 500);

        // Reset the board to the initial position
        mGameFragment.initGame();
    }*/
/*
    public void startThinking() {
        View thinkView = findViewById(R.id.thinking);
        thinkView.setVisibility(View.VISIBLE);
    }

    public void stopThinking() {
        View thinkView = findViewById(R.id.thinking);
        thinkView.setVisibility(View.GONE);
    }
*/
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // The toggle is enabled
            mMediaPlayer.stop();
            Toast.makeText(getApplicationContext(), "SOUND OFF", Toast.LENGTH_SHORT).show();

        } else {
            // The toggle is disabled
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cartoon);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
            Toast.makeText(getApplicationContext(), "SOUND ON", Toast.LENGTH_SHORT).show();
        }
    }

//public void pausegame(View view){
   // onPause();

//}



    public void pausegame(View view){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("GAME PAUSED")
                    .setPositiveButton("UNPAUSE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            resumeCountDownTimer();

                        }
                    })
                    .setNegativeButton("EXIT GAME", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            finish();
                        }
                    });

            // Create the AlertDialog object and return it

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
        countDownTimer.cancel();
        //onPause();
        }



    /////////////////////////////

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(), " GAME PAUSED", Toast.LENGTH_SHORT).show();
        //countDownTimer.cancel();
        //Button b= (Button)findViewById(R.id.small1);
      // b.setVisibility(View.GONE);

       }



    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        //return countDownTimer;
        //Toast.makeText(getApplicationContext(), " GAME RESUMED", Toast.LENGTH_SHORT).show();
        //countDownTimer.start();
        //startCountDownTimer();
        //countDownTimer.onFinish();
       //resumeCountDownTimer();
       // Button b= (Button)findViewById(R.id.small1);
       // b.setVisibility(View.VISIBLE);
        //startCountDownTimer();




    }

    /////////////////////////////


/*
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);  // "Hide" your current Activity
    }
*/
}
