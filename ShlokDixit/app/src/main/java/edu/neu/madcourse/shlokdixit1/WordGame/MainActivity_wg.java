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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import edu.neu.madcourse.shlokdixit1.R;

public class MainActivity_wg extends Activity implements CompoundButton.OnCheckedChangeListener  {

   MediaPlayer mMediaPlayer;
   ToggleButton t;
   // ...

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.ativity_main_wg);
      TextView title = (TextView) findViewById(R.id.title1);
      title.setText(" Word Game ");
       t= (ToggleButton)findViewById(R.id.togglebutton);
       t.setOnCheckedChangeListener(this);
   }
/*
   @Override
   protected void onResume() {
      super.onResume();
      mMediaPlayer = MediaPlayer.create(this, R.raw.cartoon);
      mMediaPlayer.setVolume(0.5f, 0.5f);
      mMediaPlayer.setLooping(true);
      mMediaPlayer.start();
   }

   @Override
   protected void onPause() {
      super.onPause();
      mMediaPlayer.stop();
      mMediaPlayer.reset();
      mMediaPlayer.release();
   }

*/
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

    }
