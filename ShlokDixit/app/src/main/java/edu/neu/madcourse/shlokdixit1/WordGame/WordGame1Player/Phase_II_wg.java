package edu.neu.madcourse.shlokdixit1.WordGame.WordGame1Player;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import edu.neu.madcourse.shlokdixit1.R;


/**
 * Created by shlokdixit on 23/02/16.
 */
public class Phase_II_wg extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    MediaPlayer mMediaPlayer;
    ToggleButton t;
    ArrayList<String> l1;
    ArrayList<String> l2;
    ArrayList<String> l02;
    ArrayList<String> l3;
    ArrayList<String> l4;
    ArrayList<String> l5;
    ArrayList<String> l6;
    ArrayList<String> l7;
    ArrayList<String> l8;
    ArrayList<String> l9;
    ArrayList<String> l10;
    ArrayList<String> l101;
    ArrayList<String> l11;
    ArrayList<String> l12;
    ArrayList<String> l13;
    ArrayList<String> l14;
    InputStream ins;
    String data;
    BufferedReader reader;
    //CountDownTimer countDownTimer = null;

    static private int mLargeIds[] = {R.id.large1, R.id.large2, R.id.large3,
            R.id.large4, R.id.large5, R.id.large6, R.id.large7, R.id.large8,
            R.id.large9,};
    static private int mSmallIds[] = {R.id.small1, R.id.small2, R.id.small3,
            R.id.small4, R.id.small5, R.id.small6, R.id.small7, R.id.small8,
            R.id.small9,};

    int large, small, points, bonusPoints;
    ArrayList<Integer> smallPos = new ArrayList<Integer>();
    ArrayList<Integer> largePos = new ArrayList<Integer>();
    ArrayList<String> inputWord = new ArrayList<>();
    boolean[][] letterClicked = new boolean[9][9];
    //TextView Bonus;
    TextView points_tv;
    ToneGenerator tone;

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
        points_tv = (TextView) findViewById(R.id.score_II);
        tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        smallPos.add(99);
        largePos.add(99);
        tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        ////////////////////////////////////////////////////////
        //TextView myText = (TextView) findViewById(R.id.phase);
/*
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);*/
        ////////////////////////////////////////////////////////


        // @fill PHASE-II WORD GAME tiles as per data received from PHASE-I
        bonusPoints = Accumulator.getInstance().getBonusPoints();
        points = Accumulator.getInstance().getPoints();
        fillStage2Tiles(Accumulator.getInstance().getWords(), Accumulator.getInstance().getCorrectClicks(), points);

        //@play PHASE-II WORD GAME
        playGame();

        Button button = (Button) findViewById(R.id.check_wg);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                checkword();
            }
        });


        //@takes the game to home screen
        Button mainmenu = (Button) findViewById(R.id.button_main);
        mainmenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                finish();
            }
        });
//@resets the game
        Button reboot = (Button) findViewById(R.id.restart_wg);
        reboot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Set_List_To_Null();
//                countDownTimer.cancel();
                finish();
                Intent intent = new Intent(Phase_II_wg.this, Phase_II_wg.class);
                startActivity(intent);


            }
        });
    }


    public void fillStage2Tiles(char[][] words, boolean[][] correctClicks, int points) {
        points_tv.setText("Score: " + Integer.toString(points));

        for (int large = 0; large < 9; large++) {
            View outer = this.findViewById(mLargeIds[large]);

            for (int small = 0; small < 9; small++) {

                final Button inner1 = (Button) outer.findViewById(mSmallIds[small]);
                inner1.setText(Character.toString(words[large][small]));
                if (correctClicks[large][small] == false) {
                    inner1.setText("");
                    inner1.setEnabled(false);
                }

            }
        }

    }

    long remaining = 0;
    long total = 90000;
    TextView timer1;
    CountDownTimer countDownTimer = null;

    public void startCountDownTimer() {
        timer1 = (TextView) findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(total, 1000) {
            public void onTick(long millisUntilFinished) {
                timer1.setText("Time:00:" + millisUntilFinished / 1000);
                if (millisUntilFinished < 10000) Blink_Timer();
                remaining = millisUntilFinished;
            }

            public void onFinish() {
                Set_List_To_Null();
                startnextphase();
            }
        }.start();
    }

    public void resumeCountDownTimer() {
        timer1 = (TextView) findViewById(R.id.timer);
        countDownTimer = new CountDownTimer(remaining, 1000) {
            public void onTick(long millisUntilFinished) {
                total = millisUntilFinished;
                timer1.setText("Time:00:" + millisUntilFinished / 1000);
                remaining = millisUntilFinished;
                if (millisUntilFinished < 10000)
                    Blink_Timer();
            }

            public void onFinish() {
                Set_List_To_Null();
                startnextphase();
            }
        }.start();

    }

    public void Blink_Timer() {
        TextView myText = (TextView) findViewById(R.id.timer);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);
        myText.setTextColor(Color.RED);

    }

    public void Set_List_To_Null() {

        l1 = null;
        l2 = null;
        l02 = null;
        l3 = null;
        l4 = null;
        l5 = null;
        l6 = null;
        l7 = null;
        l8 = null;
        l9 = null;
        l10 = null;
        l101 = null;
        l11 = null;
        l12 = null;
        l13 = null;
        l14 = null;

    }


    public void startnextphase() {
        Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Phase_II_wg.this, Final_score_wd.class);
        startActivity(intent);
        finish();
    }

    ////////////////////////////////
    public void pausegame(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("GAME PAUSED")
                .setPositiveButton("UNPAUSE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        resumeCountDownTimer();
                        GridLayout view1 = (GridLayout) findViewById(R.id.largeboard);
                        view1.setVisibility(View.VISIBLE);
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
        GridLayout view1 = (GridLayout) findViewById(R.id.largeboard);
        view1.setVisibility(View.INVISIBLE);
        countDownTimer.cancel();
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


    private void playGame() {

        for (large = 0; large < 9; large++) {
            View outer = this.findViewById(mLargeIds[large]);

            for (small = 0; small < 9; small++) {

                final Button inner1 = (Button) outer.findViewById(mSmallIds[small]);
                inner1.setTag(large + ":" + small);
                inner1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //***********my code
                        int large = Integer.parseInt(view.getTag().toString().split(":")[0]);
                        int small = Integer.parseInt(view.getTag().toString().split(":")[1]);
                        int smOld = smallPos.get(smallPos.size() - 1);
                        int lrOld = largePos.get(largePos.size() - 1);

                        if (Word_Selection(lrOld, smOld, large, small)) {
                            inner1.setTextColor(Color.BLACK);
                            inputWord.add(inner1.getText().toString());
                            smallPos.add(small);
                            largePos.add(large);
                            tone.startTone(ToneGenerator.TONE_PROP_BEEP);

                        } else if (smOld == small && lrOld == large) {
                            inputWord.remove(inputWord.size() - 1);
                            smallPos.remove(smallPos.size() - 1);
                            largePos.remove(largePos.size() - 1);
                            inner1.setTextColor(Color.WHITE);
                            letterClicked[large][small] = false;

                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid selection !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }
    }

    public boolean Word_Selection(int lrOld, int smOld, int largeNew, int smallNew) {
        if (lrOld != largeNew) {
            return true;
        }
        return false;

    }


    public boolean word_finder(String key) {


        this.l1 = new ArrayList<String>();
        this.l2 = new ArrayList<String>();
        this.l02 = new ArrayList<String>();
        this.l3 = new ArrayList<String>();
        this.l4 = new ArrayList<String>();
        this.l5 = new ArrayList<String>();
        this.l6 = new ArrayList<String>();
        this.l7 = new ArrayList<String>();
        this.l8 = new ArrayList<String>();
        this.l9 = new ArrayList<String>();
        this.l10 = new ArrayList<String>();
        this.l101 = new ArrayList<String>();
        this.l11 = new ArrayList<String>();
        this.l12 = new ArrayList<String>();
        this.l13 = new ArrayList<String>();
        this.l14 = new ArrayList<String>();
        char firstChar = key.charAt(0);

        if (firstChar == 'a' || firstChar == 'A') {
            ins = getResources().openRawResource(R.raw.a_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l1.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (Collections.binarySearch(l1, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'b' || firstChar == 'B') {
            ins = getResources().openRawResource(R.raw.b_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l2.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l2, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'c' || firstChar == 'C') {
            ins = getResources().openRawResource(R.raw.c_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l02.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l02, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'D' || firstChar == 'd' || firstChar == 'E' || firstChar == 'e') {
            ins = getResources().openRawResource(R.raw.d_e_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l3.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l3, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'F' || firstChar == 'f' || firstChar == 'G' || firstChar == 'g') {
            ins = getResources().openRawResource(R.raw.f_g_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l4.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l4, key) >= 0) {
                return true;
            } else return false;

        } else if (firstChar == 'H' || firstChar == 'h' || firstChar == 'i' || firstChar == 'I') {
            ins = getResources().openRawResource(R.raw.h_i_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l5.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l5, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'J' || firstChar == 'j' || firstChar == 'K' || firstChar == 'k') {
            ins = getResources().openRawResource(R.raw.j_k_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l6.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l6, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'L' || firstChar == 'l' || firstChar == 'm' || firstChar == 'M') {
            ins = getResources().openRawResource(R.raw.l_m_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l7.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l7, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'N' || firstChar == 'n' || firstChar == 'O' || firstChar == 'o') {
            ins = getResources().openRawResource(R.raw.n_o_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l8.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l8, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'P' || firstChar == 'p' || firstChar == 'Q' || firstChar == 'q') {
            ins = getResources().openRawResource(R.raw.p_q_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l9.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l9, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'R' || firstChar == 'r') {
            ins = getResources().openRawResource(R.raw.r_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l10.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l10, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'S' || firstChar == 's') {
            ins = getResources().openRawResource(R.raw.s_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l101.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l101, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'T' || firstChar == 't') {
            ins = getResources().openRawResource(R.raw.t_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l11.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l11, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'U' || firstChar == 'u') {
            ins = getResources().openRawResource(R.raw.u_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l12.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l12, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'V' || firstChar == 'v' || firstChar == 'W' || firstChar == 'w') {
            ins = getResources().openRawResource(R.raw.v_w_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l3.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l13, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'X' || firstChar == 'x' || firstChar == 'Y' || firstChar == 'y' || firstChar == 'Z' || firstChar == 'z') {
            ins = getResources().openRawResource(R.raw.x_y_z_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l14.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l14, key) >= 0) {
                return true;
            } else return false;
        } else return false;

    }

    public void checkword() {
        if (inputWord != null) {
            String word = "";
            for (String s : inputWord) {
                word += s;
            }

            if (word.length() > 0) {
                if (word_finder(word)) {
                    Toast.makeText(this.getApplicationContext(), "Correct Word !", Toast.LENGTH_SHORT).show();
                    points = points + word.length();
                    if (word.length() == 9)
                        bonusPoints = bonusPoints + 1;
                    Accumulator.getInstance().setPoints(points);
                    Accumulator.getInstance().setBonusPoints(bonusPoints);
                    points_tv.setText("Points: " + Integer.toString(points));
                    //points_tv.setText("Points: " + Integer.toString(points));
                } else
                    Toast.makeText(this.getApplicationContext(), "InCorrect Word !", Toast.LENGTH_SHORT).show();
            } else {
            }
        } else {
            Toast.makeText(this.getApplicationContext(), "Please select a word !", Toast.LENGTH_SHORT).show();
        }


    }
}