package edu.neu.madcourse.shlokdixit1.WordGame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.content.DialogInterface;
import edu.neu.madcourse.shlokdixit1.R;

public class Parent_Controls extends Activity implements CompoundButton.OnCheckedChangeListener {

    MediaPlayer mMediaPlayer;
    ToggleButton t;
    private AlertDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent__controls);
        setTitle("WORD GAME");
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cartoon);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        t = (ToggleButton) findViewById(R.id.togglebutton);
        t.setOnCheckedChangeListener(this);

        AlertDialog mDialog;

        Button Continue_Button = (Button) findViewById(R.id.continue_button_wg);
        Button New_Button = (Button) findViewById(R.id.new_button_wg);
        Button Quit_Button = (Button) findViewById(R.id.quit_button_wg);
        Button About_Button = (Button) findViewById(R.id.about_button_wg);
        Button Ack_Button = (Button) findViewById(R.id.ack_button_wg);


        Continue_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Parent_Controls.this, Phase_I_wg.class);
                startActivity(intent);
            }
        });


        New_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Parent_Controls.this, Phase_I_wg.class);
                startActivity(intent);
            }
        });


        Quit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Ack_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Parent_Controls.this, WordGameCredits.class);
                startActivity(intent);
            }
        });

        About_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Parent_Controls.this);
                builder.setMessage(R.string.about_text_wg);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });



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

    @Override
    public void onPause() {
        super.onPause();

        mMediaPlayer.stop();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

    }
}
