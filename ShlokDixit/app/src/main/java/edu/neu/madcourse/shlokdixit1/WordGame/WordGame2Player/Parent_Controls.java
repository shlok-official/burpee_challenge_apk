package edu.neu.madcourse.shlokdixit1.WordGame.WordGame2Player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.content.DialogInterface;

import edu.neu.madcourse.shlokdixit1.R;
import edu.neu.madcourse.shlokdixit1.WordGame.*;
import edu.neu.madcourse.shlokdixit1.WordGame.WordGame1Player.Communication;
import edu.neu.madcourse.shlokdixit1.WordGame.WordGame1Player.Phase_I_wg;

public class Parent_Controls extends Activity implements CompoundButton.OnCheckedChangeListener {

    MediaPlayer mMediaPlayer;
    ToggleButton t;
    private AlertDialog mDialog;
    Phase_I_wg datarestore = new Phase_I_wg();
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    //public boolean setflag;


    //SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordGameHomeScreen);


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
        Button communication = (Button) findViewById(R.id.communications);


        Continue_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edu.neu.madcourse.shlokdixit1.WordGame.WordGame1Player.Parent_Controls.this, Phase_I_wg.class);
                //------>>>>>>
                //intent.putExtra(Phase_I_wg_2Player.KEY_RESTORE, true);
               // datarestore.restoredata();
               //datarestore.setSetflag(true);

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("ContinueFlag", true);
                editor.commit();


                startActivity(intent);
            }
        });


        New_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edu.neu.madcourse.shlokdixit1.WordGame.WordGame1Player.Parent_Controls.this, Phase_I_wg.class);
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

                Intent intent = new Intent(edu.neu.madcourse.shlokdixit1.WordGame.WordGame1Player.Parent_Controls.this, WordGameCredits.class);
                startActivity(intent);
            }
        });

        About_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(edu.neu.madcourse.shlokdixit1.WordGame.WordGame1Player.Parent_Controls.this);
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


        communication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edu.neu.madcourse.shlokdixit1.WordGame.WordGame1Player.Parent_Controls.this, Communication.class);
                startActivity(intent);
            }
        });




    }
    /*
    // Initialize the Amazon Cognito credentials provider
    CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
            getApplicationContext(),
            "us-east-1:014c6543-2744-4d74-9919-cedf56883cc4", // Identity Pool ID
            Regions.US_EAST_1 // Region
    );
    // Initialize the Cognito Sync client
    CognitoSyncManager syncClient = new CognitoSyncManager(
            getApplicationContext(),
            Regions.US_EAST_1, // Region
            credentialsProvider);
/*
    // Create a record in a dataset and synchronize with the server
    Dataset dataset = syncClient.openOrCreateDataset("myDataset");
    dataset.put("myKey", "myValue");
    dataset.synchronize(new DefaultSyncCallback() {
        @Override
        public void onSuccess(Dataset dataset, List newRecords) {
            //Your handler code here
        }
    });*/


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
