
package edu.neu.madcourse.shlokdixit1.WordGame;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import edu.neu.madcourse.shlokdixit1.R;

public class MainActivity_wg extends Activity implements CompoundButton.OnCheckedChangeListener {

    MediaPlayer mMediaPlayer;
    ToggleButton t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_main_wg);
        TextView title = (TextView) findViewById(R.id.title1);
        title.setText(" Word Game ");
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cartoon);
        mMediaPlayer.start();
        t = (ToggleButton) findViewById(R.id.togglebutton);
        t.setOnCheckedChangeListener(this);
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

        }
    }
}
