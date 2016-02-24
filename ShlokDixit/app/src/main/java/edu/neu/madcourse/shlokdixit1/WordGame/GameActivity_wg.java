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
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import edu.neu.madcourse.shlokdixit1.R;

public class GameActivity_wg extends Activity implements CompoundButton.OnCheckedChangeListener {
   public static final String KEY_RESTORE = "key_restore";
   public static final String PREF_RESTORE = "pref_restore";
   //private MediaPlayer mMediaPlayer;
   private Handler mHandler = new Handler();
   private GameFragment_wg mGameFragment;

   MediaPlayer mMediaPlayer;
   ToggleButton t;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_game_wg_phase_1);
      mGameFragment = (GameFragment_wg) getFragmentManager()
            .findFragmentById(R.id.fragment_game);
      t= (ToggleButton)findViewById(R.id.togglebutton1);
      t.setOnCheckedChangeListener(this);

      boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
      if (restore) {
         String gameData = getPreferences(MODE_PRIVATE)
               .getString(PREF_RESTORE, null);
         if (gameData != null) {
           // mGameFragment.putState(gameData);
         }
      }
      Log.d("UT3", "restore = " + restore);
/////////////////////////////////////////////////////////////
      final TextView timer;
      ///////////////
      timer=(TextView) findViewById(R.id.timer);
      new CountDownTimer(90000, 1000) {

         public void onTick(long millisUntilFinished) {
            timer.setText("Time Left:" + millisUntilFinished / 1000);
         }

         public void onFinish() {
            timer.setText("done!");
            Intent intent = new Intent(GameActivity_wg.this,Phase_II_wd.class);
            startActivity(intent);
         }
      }.start();
      //////////////////////////////////////////////////////////

   }

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
   }

   public void startThinking() {
      View thinkView = findViewById(R.id.thinking);
      thinkView.setVisibility(View.VISIBLE);
   }

   public void stopThinking() {
      View thinkView = findViewById(R.id.thinking);
      thinkView.setVisibility(View.GONE);
   }

   @Override
   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
      if (isChecked) {
         // The toggle is enabled
         mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cartoon);
         mMediaPlayer.setLooping(true);
         mMediaPlayer.start();
      } else {
         // The toggle is disabled
         mMediaPlayer.stop();

      }
   }
/*
   @Override
   protected void onResume() {
      super.onResume();
      mMediaPlayer = MediaPlayer.create(this, R.raw.cartoon);
      mMediaPlayer.setLooping(true);
      mMediaPlayer.start();
   }

   @Override
   protected void onPause() {
      super.onPause();
      mHandler.removeCallbacks(null);
      mMediaPlayer.stop();
      mMediaPlayer.reset();
      mMediaPlayer.release();
      String gameData = mGameFragment.getState();
      getPreferences(MODE_PRIVATE).edit()
            .putString(PREF_RESTORE, gameData)
            .commit();
      Log.d("UT3", "state = " + gameData);
   }
   */
}
