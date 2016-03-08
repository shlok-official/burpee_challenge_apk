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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import edu.neu.madcourse.shlokdixit1.R;
import edu.neu.madcourse.shlokdixit1.TicTacToe.Tile;

public class Phase_I_wg extends Activity implements CompoundButton.OnCheckedChangeListener {

    //private GameFragment_wg mGameFragment;
    int bonusPoints;
    MediaPlayer mMediaPlayer;
    ToggleButton t;
    CountDownTimer countDownTimer = null;
    TextView timer_wg;
    ArrayList<String> inputWord = new ArrayList<>();


    boolean[] wordSelected = new boolean[9]; // set true if a word is found in a 3x3 tile
    boolean[][] letterClicked = new boolean[9][9]; // if a letter was clicked in 9x9 board
    boolean[][] correctClicks = new boolean[9][9];
    long miliSecsLeft;
    //int large, small;
    ArrayList<Integer> smallPos = new ArrayList<Integer>();
    ArrayList<Integer> largePos = new ArrayList<Integer>();
    ////////////@Declaration-starting

    static private int mLargeIds[] = {R.id.large1, R.id.large2, R.id.large3,
            R.id.large4, R.id.large5, R.id.large6, R.id.large7, R.id.large8,
            R.id.large9,};
    static private int mSmallIds[] = {R.id.small1, R.id.small2, R.id.small3,
            R.id.small4, R.id.small5, R.id.small6, R.id.small7, R.id.small8,
            R.id.small9,};


    private Handler mHandler = new Handler();
    // private Tile mEntireBoard = new Tile(this);
    private Tile mLargeTiles[] = new Tile[9];
    private Tile mSmallTiles[][] = new Tile[9][9];
    private Tile.Owner mPlayer = Tile.Owner.X;
    private Set<Tile> mAvailable = new HashSet<Tile>();
    char[][] words = new char[9][9];
    char[][] gameWords = new char[9][9];

    InputStream ins;

    BufferedReader reader;
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
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ArrayList<ArrayList> masterData;
    ArrayList<String> nineLenghtWords;
    ArrayList<String> selectedWord;
    String data;
    int large, small;
    int[] selectedLarge;
    int[] selectedSmall;
    int counter = 0;
    int largeOld = 99, smallOld = 99;

    Ringtone beep;
    ToneGenerator tone;
    int points;
    TextView points_tv;


    boolean[][] buttonState = new boolean[9][9];

    private Context mContext;
////////////@Declaration-end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle("PHASE-I");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_wg_phase_1);
        startCountDownTimer();
        tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        //mContext = this.getApplicationContext();
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cartoon);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        l1 = new ArrayList<String>();
        ins = getResources().openRawResource(R.raw.a_list);
        reader = new BufferedReader(new InputStreamReader(ins));
        try {
            while ((data = reader.readLine()) != null) {
                l1.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillTiles();
        initTileData();
        initDataHolder();
        playGame();
        // points = 0;
        points_tv = (TextView) findViewById(R.id.score_I);
        points_tv.setText("Score: " + Integer.toString(points));

        t = (ToggleButton) findViewById(R.id.togglebutton1);
        t.setOnCheckedChangeListener(this);

       // mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cartoon);

/////////////////////////@match the word by calling checkword()

        Button checkword = (Button) findViewById(R.id.check_wg_1);
        checkword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                checkword();
            }
        });


    }


    //////checkword()

    public void checkword() {
        //inputWord = DataHolder.getInstance().getArl();
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
                    for (int i = 1; i < largePos.size(); i++) {
                        correctClicks[largePos.get(i)][smallPos.get(i)] = true;
                    }

                    wordSelected[largePos.get(1)] = true;
                    updateAllTiles(gameWords, wordSelected, letterClicked);
                    initTileData();

                } else {
                    Toast.makeText(this.getApplicationContext(), "InCorrect Word !", Toast.LENGTH_SHORT).show();
                    for (int i = 1; i < largePos.size(); i++) {
                        letterClicked[largePos.get(i)][smallPos.get(i)] = false;
                    }
                    updateAllTiles(gameWords, wordSelected, letterClicked);
                    initTileData();
                }
            } else {
            }
        } else {
            Toast.makeText(this.getApplicationContext(), "Please select a word !", Toast.LENGTH_SHORT).show();
        }


    }//checkword() !!! end

    private void updateAllTiles(char[][] words, boolean[] wordSelected, boolean[][] letterClicked) // words here refer to gameWords
    {
        for (int large = 0; large < 9; large++) {
            View outer = this.findViewById(mLargeIds[large]);

            for (int small = 0; small < 9; small++) {

                final Button inner1 = (Button) outer.findViewById(mSmallIds[small]);
                inner1.setText(Character.toString(words[large][small]));

                if (letterClicked[large][small] == true)
                    inner1.setTextColor(Color.BLACK);
                else
                    inner1.setTextColor(Color.WHITE);

                if (wordSelected[large] == true) {
                    inner1.setEnabled(false);
                    inner1.setBackgroundColor(Color.CYAN);
                }

            }
        }
    }

    // word_finder , loading of arraylist and word search happens here
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


    private void clearAvailable() {
        mAvailable.clear();
    }

    private void addAvailable(Tile tile) {
        tile.animate();
        mAvailable.add(tile);
    }


    public void initDataHolder() {
        Accumulator.getInstance().setWords(null);
        Accumulator.getInstance().setCorrectClicks(null);
        Accumulator.getInstance().setPoints(0);
        Accumulator.getInstance().setBonusPoints(0);
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
                            letterClicked[large][small] = true;
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
                            Toast.makeText(getApplicationContext(), "Not an adjacent letter", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }
    }

    public void initTileData() {
        inputWord = null;
        smallPos = null;
        largePos = null;
        inputWord = new ArrayList<String>();
        smallPos = new ArrayList<Integer>();
        largePos = new ArrayList<Integer>();
        smallPos.add(99);
        largePos.add(99);
    }

    public boolean Word_Selection(int largeOld, int smallOld, int largeNew, int smallNew) {
        if ((largeNew == largeOld) || (largeOld == 99)) {
            switch (smallOld) {

                case 0:
                    if (smallNew == 1 || smallNew == 4 || smallNew == 3)
                        return true;
                    else return false;

                case 1:
                    if (smallNew == 2 || smallNew == 4 || smallNew == 3 || smallNew == 5 || smallNew == 0)
                        return true;
                    else return false;

                case 2:
                    if (smallNew == 5 || smallNew == 4 || smallNew == 1)
                        return true;
                    else return false;

                case 3:
                    if (smallNew == 7 || smallNew == 4 || smallNew == 6 || smallNew == 0 || smallNew == 1)
                        return true;
                    else return false;

                case 4:
                    if (smallNew == 7 || smallNew == 8 || smallNew == 5 || smallNew == 6 || smallNew == 3 || smallNew == 0 || smallNew == 1 || smallNew == 2)
                        return true;
                    else return false;

                case 5:
                    if (smallNew == 1 || smallNew == 2 || smallNew == 4 || smallNew == 7 || smallNew == 8)
                        return true;
                    else return false;

                case 6:
                    if (smallNew == 3 || smallNew == 7 || smallNew == 4)
                        return true;
                    else return false;

                case 7:
                    if (smallNew == 3 || smallNew == 5 || smallNew == 4 || smallNew == 6 || smallNew == 8)
                        return true;
                    else return false;

                case 8:
                    if (smallNew == 7 || smallNew == 5 || smallNew == 4)
                        return true;

                case 99:
                    return true;

                default:
                    return false;
            }
        } else return false;

    }


    public boolean isButtonAvailable(int l, int s) {
        if (buttonState[l][s] == false)
            return false;
        else return true;
    }

    public void setButtonState(int large, int small, boolean state) {
        buttonState[large][small] = state;
    }


    private void fillTiles() {

        ArrayList<String> nine = this.getNineLengthWords(l1);
        int min = 0;
        int max = 30;
        int ran = 0;
        Random rn = new Random();

        for (int i = 0; i < 9; i++) {
            ran = rn.nextInt(max - min + 1) + min;
            words[i] = nine.get(i).toString().toCharArray();
        }


        //pattern 1
        View outer0 = this.findViewById(mLargeIds[0]);
        final Button inner00 = (Button) outer0.findViewById(mSmallIds[0]);
        gameWords[0][0] = words[0][0];
        inner00.setText(Character.toString(words[0][0]));

        final Button inner01 = (Button) outer0.findViewById(mSmallIds[1]);
        gameWords[0][1] = words[0][5];
        inner01.setText(Character.toString(words[0][5]));
        final Button inner02 = (Button) outer0.findViewById(mSmallIds[2]);
        gameWords[0][2] = words[0][6];
        inner02.setText(Character.toString(words[0][6]));
        final Button inner03 = (Button) outer0.findViewById(mSmallIds[3]);
        gameWords[0][3] = words[0][1];
        inner03.setText(Character.toString(words[0][1]));
        final Button inner04 = (Button) outer0.findViewById(mSmallIds[4]);
        gameWords[0][4] = words[0][4];
        inner04.setText(Character.toString(words[0][4]));
        final Button inner05 = (Button) outer0.findViewById(mSmallIds[5]);
        gameWords[0][5] = words[0][7];
        inner05.setText(Character.toString(words[0][7]));
        final Button inner06 = (Button) outer0.findViewById(mSmallIds[6]);
        gameWords[0][6] = words[0][2];
        inner06.setText(Character.toString(words[0][2]));
        final Button inner07 = (Button) outer0.findViewById(mSmallIds[7]);
        gameWords[0][7] = words[0][3];
        inner07.setText(Character.toString(words[0][3]));
        final Button inner08 = (Button) outer0.findViewById(mSmallIds[8]);
        gameWords[0][8] = words[0][8];
        inner08.setText(Character.toString(words[0][8]));

        //pattern 2
        View outer1 = this.findViewById(mLargeIds[1]);
        final Button inner10 = (Button) outer1.findViewById(mSmallIds[0]);
        gameWords[1][0] = words[1][8];
        inner10.setText(Character.toString(words[1][8]));
        final Button inner11 = (Button) outer1.findViewById(mSmallIds[1]);
        gameWords[1][1] = words[1][5];
        inner11.setText(Character.toString(words[1][5]));
        final Button inner12 = (Button) outer1.findViewById(mSmallIds[2]);
        gameWords[1][2] = words[1][4];
        inner12.setText(Character.toString(words[1][4]));
        final Button inner13 = (Button) outer1.findViewById(mSmallIds[3]);
        gameWords[1][3] = words[1][7];
        inner13.setText(Character.toString(words[1][7]));
        final Button inner14 = (Button) outer1.findViewById(mSmallIds[4]);
        gameWords[1][4] = words[1][6];
        inner14.setText(Character.toString(words[1][6]));
        final Button inner15 = (Button) outer1.findViewById(mSmallIds[5]);
        gameWords[1][5] = words[1][3];
        inner15.setText(Character.toString(words[1][3]));
        final Button inner16 = (Button) outer1.findViewById(mSmallIds[6]);
        gameWords[1][6] = words[1][0];
        inner16.setText(Character.toString(words[1][0]));
        final Button inner17 = (Button) outer1.findViewById(mSmallIds[7]);
        gameWords[1][7] = words[1][1];
        inner17.setText(Character.toString(words[1][1]));
        final Button inner18 = (Button) outer1.findViewById(mSmallIds[8]);
        gameWords[1][8] = words[1][2];
        inner18.setText(Character.toString(words[1][2]));

        //pattern 3
        View outer2 = this.findViewById(mLargeIds[2]);
        final Button inner20 = (Button) outer2.findViewById(mSmallIds[0]);
        gameWords[2][0] = words[2][2];
        inner20.setText(Character.toString(words[2][2]));
        final Button inner21 = (Button) outer2.findViewById(mSmallIds[1]);
        gameWords[2][1] = words[2][3];
        inner21.setText(Character.toString(words[2][3]));
        final Button inner22 = (Button) outer2.findViewById(mSmallIds[2]);
        gameWords[2][2] = words[2][4];
        inner22.setText(Character.toString(words[2][4]));
        final Button inner23 = (Button) outer2.findViewById(mSmallIds[3]);
        gameWords[2][3] = words[2][1];
        inner23.setText(Character.toString(words[2][1]));
        final Button inner24 = (Button) outer2.findViewById(mSmallIds[4]);
        gameWords[2][4] = words[2][0];
        inner24.setText(Character.toString(words[2][0]));
        final Button inner25 = (Button) outer2.findViewById(mSmallIds[5]);
        gameWords[2][5] = words[2][5];
        inner25.setText(Character.toString(words[2][5]));
        final Button inner26 = (Button) outer2.findViewById(mSmallIds[6]);
        gameWords[2][6] = words[2][8];
        inner26.setText(Character.toString(words[2][8]));
        final Button inner27 = (Button) outer2.findViewById(mSmallIds[7]);
        gameWords[2][7] = words[2][7];
        inner27.setText(Character.toString(words[2][7]));
        final Button inner28 = (Button) outer2.findViewById(mSmallIds[8]);
        gameWords[2][8] = words[2][6];
        inner28.setText(Character.toString(words[2][6]));

        //pattern 3
        View outer3 = this.findViewById(mLargeIds[3]);
        final Button inner30 = (Button) outer3.findViewById(mSmallIds[0]);
        gameWords[3][0] = words[3][2];
        inner30.setText(Character.toString(words[3][2]));
        final Button inner31 = (Button) outer3.findViewById(mSmallIds[1]);
        gameWords[3][1] = words[3][3];
        inner31.setText(Character.toString(words[3][3]));
        final Button inner32 = (Button) outer3.findViewById(mSmallIds[2]);
        gameWords[3][2] = words[3][4];
        inner32.setText(Character.toString(words[3][4]));
        final Button inner33 = (Button) outer3.findViewById(mSmallIds[3]);
        gameWords[3][3] = words[3][1];
        inner33.setText(Character.toString(words[3][1]));
        final Button inner34 = (Button) outer3.findViewById(mSmallIds[4]);
        gameWords[3][4] = words[3][0];
        inner34.setText(Character.toString(words[3][0]));
        final Button inner35 = (Button) outer3.findViewById(mSmallIds[5]);
        gameWords[3][5] = words[3][5];
        inner35.setText(Character.toString(words[3][5]));
        final Button inner36 = (Button) outer3.findViewById(mSmallIds[6]);
        gameWords[3][6] = words[3][8];
        inner36.setText(Character.toString(words[3][8]));
        final Button inner37 = (Button) outer3.findViewById(mSmallIds[7]);
        gameWords[3][7] = words[3][7];
        inner37.setText(Character.toString(words[3][7]));
        final Button inner38 = (Button) outer3.findViewById(mSmallIds[8]);
        gameWords[3][8] = words[3][6];
        inner38.setText(Character.toString(words[3][6]));

        //pattern 1
        View outer4 = this.findViewById(mLargeIds[4]);
        final Button inner40 = (Button) outer4.findViewById(mSmallIds[0]);
        gameWords[4][0] = words[4][0];
        inner40.setText(Character.toString(words[4][0]));
        final Button inner41 = (Button) outer4.findViewById(mSmallIds[1]);
        gameWords[4][1] = words[4][5];
        inner41.setText(Character.toString(words[4][5]));
        final Button inner42 = (Button) outer4.findViewById(mSmallIds[2]);
        gameWords[4][2] = words[4][6];
        inner42.setText(Character.toString(words[4][6]));
        final Button inner43 = (Button) outer4.findViewById(mSmallIds[3]);
        gameWords[4][3] = words[4][1];
        inner43.setText(Character.toString(words[4][1]));
        final Button inner44 = (Button) outer4.findViewById(mSmallIds[4]);
        gameWords[4][4] = words[4][4];
        inner44.setText(Character.toString(words[4][4]));
        final Button inner45 = (Button) outer4.findViewById(mSmallIds[5]);
        gameWords[4][5] = words[4][7];
        inner45.setText(Character.toString(words[4][7]));
        final Button inner46 = (Button) outer4.findViewById(mSmallIds[6]);
        gameWords[4][6] = words[4][2];
        inner46.setText(Character.toString(words[4][2]));
        final Button inner47 = (Button) outer4.findViewById(mSmallIds[7]);
        gameWords[4][7] = words[4][3];
        inner47.setText(Character.toString(words[4][3]));
        final Button inner48 = (Button) outer4.findViewById(mSmallIds[8]);
        gameWords[4][8] = words[4][8];
        inner48.setText(Character.toString(words[4][8]));

        //pattern 2
        View outer5 = this.findViewById(mLargeIds[5]);
        final Button inner50 = (Button) outer5.findViewById(mSmallIds[0]);
        gameWords[5][0] = words[5][8];
        inner50.setText(Character.toString(words[5][8]));
        final Button inner51 = (Button) outer5.findViewById(mSmallIds[1]);
        gameWords[5][1] = words[5][5];
        inner51.setText(Character.toString(words[5][5]));
        final Button inner52 = (Button) outer5.findViewById(mSmallIds[2]);
        gameWords[5][2] = words[5][4];
        inner52.setText(Character.toString(words[5][4]));
        final Button inner53 = (Button) outer5.findViewById(mSmallIds[3]);
        gameWords[5][3] = words[5][7];
        inner53.setText(Character.toString(words[5][7]));
        final Button inner54 = (Button) outer5.findViewById(mSmallIds[4]);
        gameWords[5][4] = words[5][6];
        inner54.setText(Character.toString(words[5][6]));
        final Button inner55 = (Button) outer5.findViewById(mSmallIds[5]);
        gameWords[5][5] = words[5][3];
        inner55.setText(Character.toString(words[5][3]));
        final Button inner56 = (Button) outer5.findViewById(mSmallIds[6]);
        gameWords[5][6] = words[5][0];
        inner56.setText(Character.toString(words[5][0]));
        final Button inner57 = (Button) outer5.findViewById(mSmallIds[7]);
        gameWords[5][7] = words[5][1];
        inner57.setText(Character.toString(words[5][1]));
        final Button inner58 = (Button) outer5.findViewById(mSmallIds[8]);
        gameWords[5][8] = words[5][2];
        inner58.setText(Character.toString(words[5][2]));


        //pattern 2
        View outer6 = this.findViewById(mLargeIds[6]);
        final Button inner60 = (Button) outer6.findViewById(mSmallIds[0]);
        gameWords[6][0] = words[6][8];
        inner60.setText(Character.toString(words[6][8]));
        final Button inner61 = (Button) outer6.findViewById(mSmallIds[1]);
        gameWords[6][1] = words[6][5];
        inner61.setText(Character.toString(words[6][5]));
        final Button inner62 = (Button) outer6.findViewById(mSmallIds[2]);
        gameWords[6][2] = words[6][4];
        inner62.setText(Character.toString(words[6][4]));
        final Button inner63 = (Button) outer6.findViewById(mSmallIds[3]);
        gameWords[6][3] = words[6][7];
        inner63.setText(Character.toString(words[6][7]));
        final Button inner64 = (Button) outer6.findViewById(mSmallIds[4]);
        gameWords[6][4] = words[6][6];
        inner64.setText(Character.toString(words[6][6]));
        final Button inner65 = (Button) outer6.findViewById(mSmallIds[5]);
        gameWords[6][5] = words[6][3];
        inner65.setText(Character.toString(words[6][3]));
        final Button inner66 = (Button) outer6.findViewById(mSmallIds[6]);
        gameWords[6][6] = words[6][0];
        inner66.setText(Character.toString(words[6][0]));
        final Button inner67 = (Button) outer6.findViewById(mSmallIds[7]);
        gameWords[6][7] = words[6][1];
        inner67.setText(Character.toString(words[6][1]));
        final Button inner68 = (Button) outer6.findViewById(mSmallIds[8]);
        gameWords[6][8] = words[6][2];
        inner68.setText(Character.toString(words[6][2]));


        //pattern 1
        View outer7 = this.findViewById(mLargeIds[7]);
        final Button inner70 = (Button) outer7.findViewById(mSmallIds[0]);
        gameWords[7][0] = words[7][0];
        inner70.setText(Character.toString(words[7][0]));
        final Button inner71 = (Button) outer7.findViewById(mSmallIds[1]);
        gameWords[7][1] = words[7][5];
        inner71.setText(Character.toString(words[7][5]));
        final Button inner72 = (Button) outer7.findViewById(mSmallIds[2]);
        gameWords[7][2] = words[7][6];
        inner72.setText(Character.toString(words[7][6]));
        final Button inner73 = (Button) outer7.findViewById(mSmallIds[3]);
        gameWords[7][3] = words[7][1];
        inner73.setText(Character.toString(words[7][1]));
        final Button inner74 = (Button) outer7.findViewById(mSmallIds[4]);
        gameWords[7][4] = words[7][4];
        inner74.setText(Character.toString(words[7][4]));
        final Button inner75 = (Button) outer7.findViewById(mSmallIds[5]);
        gameWords[7][5] = words[7][7];
        inner75.setText(Character.toString(words[7][7]));
        final Button inner76 = (Button) outer7.findViewById(mSmallIds[6]);
        gameWords[7][6] = words[7][2];
        inner76.setText(Character.toString(words[7][2]));
        final Button inner77 = (Button) outer7.findViewById(mSmallIds[7]);
        gameWords[7][7] = words[7][3];
        inner77.setText(Character.toString(words[7][3]));
        final Button inner78 = (Button) outer7.findViewById(mSmallIds[8]);
        gameWords[7][8] = words[7][8];
        inner78.setText(Character.toString(words[7][8]));


        //pattern 3
        View outer8 = this.findViewById(mLargeIds[8]);
        final Button inner80 = (Button) outer8.findViewById(mSmallIds[0]);
        gameWords[8][0] = words[8][2];
        inner80.setText(Character.toString(words[8][2]));
        final Button inner81 = (Button) outer8.findViewById(mSmallIds[1]);
        gameWords[8][1] = words[8][3];
        inner81.setText(Character.toString(words[8][3]));
        final Button inner82 = (Button) outer8.findViewById(mSmallIds[2]);
        gameWords[8][2] = words[8][4];
        inner82.setText(Character.toString(words[8][4]));
        final Button inner83 = (Button) outer8.findViewById(mSmallIds[3]);
        gameWords[8][3] = words[8][1];
        inner83.setText(Character.toString(words[8][1]));
        final Button inner84 = (Button) outer8.findViewById(mSmallIds[4]);
        gameWords[8][4] = words[8][0];
        inner84.setText(Character.toString(words[8][0]));
        final Button inner85 = (Button) outer8.findViewById(mSmallIds[5]);
        gameWords[8][5] = words[8][5];
        inner85.setText(Character.toString(words[8][5]));
        final Button inner86 = (Button) outer8.findViewById(mSmallIds[6]);
        gameWords[8][6] = words[8][8];
        inner86.setText(Character.toString(words[8][8]));
        final Button inner87 = (Button) outer8.findViewById(mSmallIds[7]);
        gameWords[8][7] = words[8][7];
        inner87.setText(Character.toString(words[8][7]));
        final Button inner88 = (Button) outer8.findViewById(mSmallIds[8]);
        gameWords[8][8] = words[8][6];
        inner88.setText(Character.toString(words[8][6]));


    }

    public ArrayList getNineLengthWords(ArrayList ar) {
        int i = 0, j = 0;
        // this.nineLenghtWords=ar;

        ArrayList<String> nine = new ArrayList<String>();
        for (j = 0; j < ar.size(); j++) {
            if (ar.get(j).toString().length() == 9)
                nine.add(ar.get(j).toString());
        }
        return nine;

    }


    public void restartGame() {

    }

    public void initGame() {

    }

    private void setAvailableFromLastMove(int small) {
        clearAvailable();
        // Make all the tiles at the destination available
        if (small != -1) {
            for (int dest = 0; dest < 9; dest++) {
                Tile tile = mSmallTiles[small][dest];
                if (tile.getOwner() == Tile.Owner.NEITHER)
                    addAvailable(tile);
            }
        }
        // If there were none available, make all squares available
        if (mAvailable.isEmpty()) {
            setAllAvailable();
        }
    }

    private void setAllAvailable() {
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile tile = mSmallTiles[large][small];
                if (tile.getOwner() == Tile.Owner.NEITHER)
                    addAvailable(tile);
            }
        }
    }

    private void updateAllTiles() {
        // mEntireBoard.updateDrawableState();
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large].updateDrawableState();
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small].updateDrawableState();
            }
        }
    }


    public String getState() {

        return "dummy val";
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


    long remaining = 0;
    long total = 90000;

    public void startCountDownTimer() {
        timer_wg = (TextView) findViewById(R.id.timer_1);
        countDownTimer = new CountDownTimer(total, 1000) {
            public void onTick(long millisUntilFinished) {
                timer_wg.setText("Time:00:" + millisUntilFinished / 1000);
                if (millisUntilFinished < 10000) Blink_Timer();
                remaining = millisUntilFinished;
            }

            public void onFinish() {
                Accumulator.getInstance().setWords(gameWords);
                Accumulator.getInstance().setCorrectClicks(correctClicks);
                Set_List_To_Null();
                startnextphase();
            }
        }.start();

    }

    public void resumeCountDownTimer() {
        timer_wg = (TextView) findViewById(R.id.timer_1);
        countDownTimer = new CountDownTimer(remaining, 1000) {
            public void onTick(long millisUntilFinished) {
                total = millisUntilFinished;
                timer_wg.setText("Time:00:" + millisUntilFinished / 1000);
                remaining = millisUntilFinished;
                if (millisUntilFinished < 10000)
                    Blink_Timer();
            }

            public void onFinish() {
                Accumulator.getInstance().setWords(gameWords);
                Accumulator.getInstance().setCorrectClicks(correctClicks);
                Set_List_To_Null();
                startnextphase();
            }
        }.start();

    }

    public void Blink_Timer() {
        TextView myText = (TextView) findViewById(R.id.timer_1);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);
        myText.setTextColor(Color.RED);

    }

    public void startnextphase() {

        Toast.makeText(getApplicationContext(), "PHASE-II STARTED", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Phase_I_wg.this, Phase_II_wg.class);
        startActivity(intent);
        finish();
    }

    /////////////////////
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

    public void exit_1(View view) {
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
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

    }

}
