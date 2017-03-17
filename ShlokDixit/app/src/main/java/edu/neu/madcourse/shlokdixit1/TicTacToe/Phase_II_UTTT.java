package edu.neu.madcourse.shlokdixit1.TicTacToe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.neu.madcourse.shlokdixit1.FirstActivity;
import edu.neu.madcourse.shlokdixit1.R;

/**
 * Created by shlokdixit on 23/02/16.
 */
public class Phase_II_UTTT extends AppCompatActivity {
    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private GameFragment mGameFragment;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_phase_2);
        mGameFragment = (GameFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game);
        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            if (gameData != null) {
                mGameFragment.putState(gameData);
            }
        }

        final TextView timer;
        timer=(TextView) findViewById(R.id.timer2);
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time Left:" + millisUntilFinished / 1000);
            }

            public void onFinish() {


                Intent intent = new Intent(Phase_II_UTTT.this, FirstActivity.class);
                startActivity(intent);

            }

        }.start();

    }

    public void restartGame() {
        mGameFragment.restartGame();
    }
}