package edu.neu.madcourse.shlokdixit1.WordGame.TwoPlayerWordGame;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
import edu.neu.madcourse.shlokdixit1.TicTacToe.Tile;
import edu.neu.madcourse.shlokdixit1.WordGame.SinglePlayerWordGame.Accumulator;

/**
 * Created by shlokdixit on 08/03/16.
 */
public class New_Phase_I_Game extends Activity implements CompoundButton.OnCheckedChangeListener {

    static private int mLargeIds[] = {R.id.large1, R.id.large2, R.id.large3,
            R.id.large4, R.id.large5, R.id.large6, R.id.large7, R.id.large8,
            R.id.large9,};
    static private int mSmallIds[] = {R.id.small1, R.id.small2, R.id.small3,
            R.id.small4, R.id.small5, R.id.small6, R.id.small7, R.id.small8,
            R.id.small9,};

    ArrayList<Integer> smallPos = new ArrayList<Integer>();
    ArrayList<Integer> largePos = new ArrayList<Integer>();

    private Tile mLargeTiles[] = new Tile[9];
    private Tile mSmallTiles[][] = new Tile[9][9];
    char[][] words = new char[9][9];
    char[][] gameWords = new char[9][9];
    ArrayList<String> selectedWord;
    String data;
    int large, small;
    int[] selectedLarge;
    int[] selectedSmall;
    int counter = 0;
    int largeOld = 99, smallOld = 99;
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

    Ringtone beep;
    ToneGenerator tone;
    int points;
    TextView points_tv;
    InputStream ins;
    BufferedReader reader;
    ArrayList<String> l_a;
    ArrayList<String> l_b;
    ArrayList<String> l_c;
    ArrayList<String> l_d_e;
    ArrayList<String> l_f_g;
    ArrayList<String> l_h_i;
    ArrayList<String> l_j_k;
    ArrayList<String> l_l_m;
    ArrayList<String> l_n_o;
    ArrayList<String> l_p_q;
    ArrayList<String> l_r;
    ArrayList<String> l_s;
    ArrayList<String> l_t;
    ArrayList<String> l_u;
    ArrayList<String> l_v_w;
    ArrayList<String> l_x_y_z;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("PHASE-I");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_wg_phase_1);

        t = (ToggleButton) findViewById(R.id.togglebutton1);
        t.setOnCheckedChangeListener(this);

        initTiles();
        playGame();
        initTileData();

        l_a = new ArrayList<String>();
        ins = getResources().openRawResource(R.raw.a_list);
        reader = new BufferedReader(new InputStreamReader(ins));
        try {
            while ((data = reader.readLine()) != null) {
                l_a.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //@match the word by calling checkword()

        Button checkword = (Button) findViewById(R.id.check_wg_1);
        checkword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                checkword();
            }
        });
    }

//checkword()

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
            }
            //else {
            //}
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


    // word_finder , loading of arraylist and word search happens here
    public boolean word_finder(String key) {


        this.l_a = new ArrayList<String>();
        this.l_b = new ArrayList<String>();
        this.l_c = new ArrayList<String>();
        this.l_d_e = new ArrayList<String>();
        this.l_f_g = new ArrayList<String>();
        this.l_h_i = new ArrayList<String>();
        this.l_j_k = new ArrayList<String>();
        this.l_l_m = new ArrayList<String>();
        this.l_n_o = new ArrayList<String>();
        this.l_p_q = new ArrayList<String>();
        this.l_r = new ArrayList<String>();
        this.l_s = new ArrayList<String>();
        this.l_t = new ArrayList<String>();
        this.l_u = new ArrayList<String>();
        this.l_v_w = new ArrayList<String>();
        this.l_x_y_z = new ArrayList<String>();
        char firstChar = key.charAt(0);

        if (firstChar == 'a' || firstChar == 'A') {
            ins = getResources().openRawResource(R.raw.a_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_a.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (Collections.binarySearch(l_a, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'b' || firstChar == 'B') {
            ins = getResources().openRawResource(R.raw.b_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_b.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_b, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'c' || firstChar == 'C') {
            ins = getResources().openRawResource(R.raw.c_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_c.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_c, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'D' || firstChar == 'd' || firstChar == 'E' || firstChar == 'e') {
            ins = getResources().openRawResource(R.raw.d_e_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_d_e.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_d_e, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'F' || firstChar == 'f' || firstChar == 'G' || firstChar == 'g') {
            ins = getResources().openRawResource(R.raw.f_g_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_f_g.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_f_g, key) >= 0) {
                return true;
            } else return false;

        } else if (firstChar == 'H' || firstChar == 'h' || firstChar == 'i' || firstChar == 'I') {
            ins = getResources().openRawResource(R.raw.h_i_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_h_i.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_h_i, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'J' || firstChar == 'j' || firstChar == 'K' || firstChar == 'k') {
            ins = getResources().openRawResource(R.raw.j_k_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_j_k.add(data);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_j_k, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'L' || firstChar == 'l' || firstChar == 'm' || firstChar == 'M') {
            ins = getResources().openRawResource(R.raw.l_m_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_l_m.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_l_m, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'N' || firstChar == 'n' || firstChar == 'O' || firstChar == 'o') {
            ins = getResources().openRawResource(R.raw.n_o_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_n_o.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_n_o, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'P' || firstChar == 'p' || firstChar == 'Q' || firstChar == 'q') {
            ins = getResources().openRawResource(R.raw.p_q_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_p_q.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_p_q, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'R' || firstChar == 'r') {
            ins = getResources().openRawResource(R.raw.r_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_r.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_r, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'S' || firstChar == 's') {
            ins = getResources().openRawResource(R.raw.s_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_s.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_s, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'T' || firstChar == 't') {
            ins = getResources().openRawResource(R.raw.t_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_t.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_t, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'U' || firstChar == 'u') {
            ins = getResources().openRawResource(R.raw.u_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_u.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_u, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'V' || firstChar == 'v' || firstChar == 'W' || firstChar == 'w') {
            ins = getResources().openRawResource(R.raw.v_w_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_v_w.add(data);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_v_w, key) >= 0) {
                return true;
            } else return false;
        } else if (firstChar == 'X' || firstChar == 'x' || firstChar == 'Y' || firstChar == 'y' || firstChar == 'Z' || firstChar == 'z') {
            ins = getResources().openRawResource(R.raw.x_y_z_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            try {
                while ((data = reader.readLine()) != null) {
                    l_x_y_z.add(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Collections.binarySearch(l_x_y_z, key) >= 0) {
                return true;
            } else return false;
        } else return false;

    }

    //begin the game play
    public void playGame() {
        for (large = 0; large < 9; large++) {
            View largeblock = this.findViewById(mLargeIds[large]);

            for (small = 0; small < 9; small++) {

                final Button smallblock = (Button) largeblock.findViewById(mSmallIds[small]);
                smallblock.setTag(large + ":" + small);
                smallblock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //***********my code
                        int large = Integer.parseInt(view.getTag().toString().split(":")[0]);
                        int small = Integer.parseInt(view.getTag().toString().split(":")[1]);
                        int smOld = smallPos.get(smallPos.size() - 1);
                        int lrOld = largePos.get(largePos.size() - 1);

                        if (Word_Selection(lrOld, smOld, large, small)) {
                            smallblock.setTextColor(Color.BLACK);
                            //letterClicked[large][small] = true;
                            inputWord.add(smallblock.getText().toString());
                            smallPos.add(small);
                            largePos.add(large);
                           // tone.startTone(ToneGenerator.TONE_PROP_BEEP);

                        } else if (smOld == small && lrOld == large) {
                            inputWord.remove(inputWord.size() - 1);
                            smallPos.remove(smallPos.size() - 1);
                            largePos.remove(largePos.size() - 1);
                            smallblock.setTextColor(Color.WHITE);
                            //letterClicked[large][small] = false;

                        } else {
                            Toast.makeText(getApplicationContext(), "Not an adjacent letter", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }
    }


    //set words in tiles
    public void initTiles() {
///large01
        View largeblock00 = this.findViewById(mLargeIds[0]);
        final Button smallblock01 = (Button) largeblock00.findViewById(mSmallIds[0]);
        smallblock01.setText("B");
        final Button smallblock02 = (Button) largeblock00.findViewById(mSmallIds[1]);
        smallblock02.setText("L");
        final Button smallblock03 = (Button) largeblock00.findViewById(mSmallIds[2]);
        smallblock03.setText("I");
        final Button smallblock04 = (Button) largeblock00.findViewById(mSmallIds[3]);
        smallblock04.setText("Z");
        final Button smallblock05 = (Button) largeblock00.findViewById(mSmallIds[4]);
        smallblock05.setText("Z");
        final Button smallblock06 = (Button) largeblock00.findViewById(mSmallIds[5]);
        smallblock06.setText("A");
        final Button smallblock07 = (Button) largeblock00.findViewById(mSmallIds[6]);
        smallblock07.setText("R");
        final Button smallblock08 = (Button) largeblock00.findViewById(mSmallIds[7]);
        smallblock08.setText("D");
        final Button smallblock09 = (Button) largeblock00.findViewById(mSmallIds[8]);
        smallblock09.setText("S");


        //large02
        View largeblock01 = this.findViewById(mLargeIds[1]);
        final Button smallblock11 = (Button) largeblock01.findViewById(mSmallIds[0]);
        smallblock11.setText("A");
        final Button smallblock12 = (Button) largeblock01.findViewById(mSmallIds[1]);
        smallblock12.setText("B");
        final Button smallblock13 = (Button) largeblock01.findViewById(mSmallIds[2]);
        smallblock13.setText("C");
        final Button smallblock14 = (Button) largeblock01.findViewById(mSmallIds[3]);
        smallblock14.setText("D");
        final Button smallblock15 = (Button) largeblock01.findViewById(mSmallIds[4]);
        smallblock15.setText("E");
        final Button smallblock16 = (Button) largeblock01.findViewById(mSmallIds[5]);
        smallblock16.setText("F");
        final Button smallblock17 = (Button) largeblock01.findViewById(mSmallIds[6]);
        smallblock17.setText("G");
        final Button smallblock18 = (Button) largeblock01.findViewById(mSmallIds[7]);
        smallblock18.setText("H");
        final Button smallblock19 = (Button) largeblock01.findViewById(mSmallIds[8]);
        smallblock19.setText("I");


        //large03
        View largeblock02 = this.findViewById(mLargeIds[2]);
        final Button smallblock21 = (Button) largeblock02.findViewById(mSmallIds[0]);
        smallblock21.setText("A");
        final Button smallblock22 = (Button) largeblock02.findViewById(mSmallIds[1]);
        smallblock22.setText("B");
        final Button smallblock23 = (Button) largeblock02.findViewById(mSmallIds[2]);
        smallblock23.setText("C");
        final Button smallblock24 = (Button) largeblock02.findViewById(mSmallIds[3]);
        smallblock24.setText("D");
        final Button smallblock25 = (Button) largeblock02.findViewById(mSmallIds[4]);
        smallblock25.setText("E");
        final Button smallblock26 = (Button) largeblock02.findViewById(mSmallIds[5]);
        smallblock26.setText("F");
        final Button smallblock27 = (Button) largeblock02.findViewById(mSmallIds[6]);
        smallblock27.setText("T");
        final Button smallblock28 = (Button) largeblock02.findViewById(mSmallIds[7]);
        smallblock28.setText("I");
        final Button smallblock29 = (Button) largeblock02.findViewById(mSmallIds[8]);
        smallblock29.setText("C");


        //large04
        View largeblock03 = this.findViewById(mLargeIds[3]);
        final Button smallblock31 = (Button) largeblock03.findViewById(mSmallIds[0]);
        smallblock31.setText("A");
        final Button smallblock32 = (Button) largeblock03.findViewById(mSmallIds[1]);
        smallblock32.setText("B");
        final Button smallblock33 = (Button) largeblock03.findViewById(mSmallIds[2]);
        smallblock33.setText("C");
        final Button smallblock34 = (Button) largeblock03.findViewById(mSmallIds[3]);
        smallblock34.setText("D");
        final Button smallblock35 = (Button) largeblock03.findViewById(mSmallIds[4]);
        smallblock35.setText("E");
        final Button smallblock36 = (Button) largeblock03.findViewById(mSmallIds[5]);
        smallblock36.setText("F");
        final Button smallblock37 = (Button) largeblock03.findViewById(mSmallIds[6]);
        smallblock37.setText("G");
        final Button smallblock38 = (Button) largeblock03.findViewById(mSmallIds[7]);
        smallblock38.setText("H");
        final Button smallblock39 = (Button) largeblock03.findViewById(mSmallIds[8]);
        smallblock39.setText("I");


        //large05
        View largeblock04 = this.findViewById(mLargeIds[4]);
        final Button smallblock41 = (Button) largeblock04.findViewById(mSmallIds[0]);
        smallblock41.setText("Z");
        final Button smallblock42 = (Button) largeblock04.findViewById(mSmallIds[1]);
        smallblock42.setText("B");
        final Button smallblock43 = (Button) largeblock04.findViewById(mSmallIds[2]);
        smallblock43.setText("C");
        final Button smallblock44 = (Button) largeblock04.findViewById(mSmallIds[3]);
        smallblock44.setText("D");
        final Button smallblock45 = (Button) largeblock04.findViewById(mSmallIds[4]);
        smallblock45.setText("E");
        final Button smallblock46 = (Button) largeblock04.findViewById(mSmallIds[5]);
        smallblock46.setText("F");
        final Button smallblock47 = (Button) largeblock04.findViewById(mSmallIds[6]);
        smallblock47.setText("G");
        final Button smallblock48 = (Button) largeblock04.findViewById(mSmallIds[7]);
        smallblock48.setText("H");
        final Button smallblock49 = (Button) largeblock04.findViewById(mSmallIds[8]);
        smallblock49.setText("I");


        //large06
        View largeblock05 = this.findViewById(mLargeIds[5]);
        final Button smallblock51 = (Button) largeblock05.findViewById(mSmallIds[0]);
        smallblock51.setText("U");
        final Button smallblock52 = (Button) largeblock05.findViewById(mSmallIds[1]);
        smallblock52.setText("B");
        final Button smallblock53 = (Button) largeblock05.findViewById(mSmallIds[2]);
        smallblock53.setText("C");
        final Button smallblock54 = (Button) largeblock05.findViewById(mSmallIds[3]);
        smallblock54.setText("D");
        final Button smallblock55 = (Button) largeblock05.findViewById(mSmallIds[4]);
        smallblock55.setText("E");
        final Button smallblock56 = (Button) largeblock05.findViewById(mSmallIds[5]);
        smallblock56.setText("F");
        final Button smallblock57 = (Button) largeblock05.findViewById(mSmallIds[6]);
        smallblock57.setText("G");
        final Button smallblock58 = (Button) largeblock05.findViewById(mSmallIds[7]);
        smallblock58.setText("H");
        final Button smallblock59 = (Button) largeblock05.findViewById(mSmallIds[8]);
        smallblock59.setText("I");


        //large07
        View largeblock06 = this.findViewById(mLargeIds[6]);
        final Button smallblock61 = (Button) largeblock06.findViewById(mSmallIds[0]);
        smallblock61.setText("X");
        final Button smallblock62 = (Button) largeblock06.findViewById(mSmallIds[1]);
        smallblock62.setText("B");
        final Button smallblock63 = (Button) largeblock06.findViewById(mSmallIds[2]);
        smallblock63.setText("C");
        final Button smallblock64 = (Button) largeblock06.findViewById(mSmallIds[3]);
        smallblock64.setText("D");
        final Button smallblock65 = (Button) largeblock06.findViewById(mSmallIds[4]);
        smallblock65.setText("E");
        final Button smallblock66 = (Button) largeblock06.findViewById(mSmallIds[5]);
        smallblock66.setText("F");
        final Button smallblock67 = (Button) largeblock06.findViewById(mSmallIds[6]);
        smallblock67.setText("G");
        final Button smallblock68 = (Button) largeblock06.findViewById(mSmallIds[7]);
        smallblock68.setText("H");
        final Button smallblock69 = (Button) largeblock06.findViewById(mSmallIds[8]);
        smallblock69.setText("I");


        //large08
        View largeblock07 = this.findViewById(mLargeIds[7]);
        final Button smallblock71 = (Button) largeblock07.findViewById(mSmallIds[0]);
        smallblock71.setText("M");
        final Button smallblock72 = (Button) largeblock07.findViewById(mSmallIds[1]);
        smallblock72.setText("B");
        final Button smallblock73 = (Button) largeblock07.findViewById(mSmallIds[2]);
        smallblock73.setText("C");
        final Button smallblock74 = (Button) largeblock07.findViewById(mSmallIds[3]);
        smallblock74.setText("D");
        final Button smallblock75 = (Button) largeblock07.findViewById(mSmallIds[4]);
        smallblock75.setText("E");
        final Button smallblock76 = (Button) largeblock07.findViewById(mSmallIds[5]);
        smallblock76.setText("F");
        final Button smallblock77 = (Button) largeblock07.findViewById(mSmallIds[6]);
        smallblock77.setText("G");
        final Button smallblock78 = (Button) largeblock07.findViewById(mSmallIds[7]);
        smallblock78.setText("H");
        final Button smallblock79 = (Button) largeblock07.findViewById(mSmallIds[8]);
        smallblock79.setText("I");


        //large09
        View largeblock08 = this.findViewById(mLargeIds[8]);
        final Button smallblock81 = (Button) largeblock08.findViewById(mSmallIds[0]);
        smallblock81.setText("N");
        final Button smallblock82 = (Button) largeblock08.findViewById(mSmallIds[1]);
        smallblock82.setText("B");
        final Button smallblock83 = (Button) largeblock08.findViewById(mSmallIds[2]);
        smallblock83.setText("C");
        final Button smallblock84 = (Button) largeblock08.findViewById(mSmallIds[3]);
        smallblock84.setText("D");
        final Button smallblock85 = (Button) largeblock08.findViewById(mSmallIds[4]);
        smallblock85.setText("E");
        final Button smallblock86 = (Button) largeblock08.findViewById(mSmallIds[5]);
        smallblock86.setText("F");
        final Button smallblock87 = (Button) largeblock08.findViewById(mSmallIds[6]);
        smallblock87.setText("G");
        final Button smallblock88 = (Button) largeblock08.findViewById(mSmallIds[7]);
        smallblock88.setText("H");
        final Button smallblock89 = (Button) largeblock08.findViewById(mSmallIds[8]);
        smallblock89.setText("I");

    }

    /**
     * Create a string containing the state of the game.
     */


    /*
    public String getState() {
        StringBuilder builder = new StringBuilder();
        builder.append(mLastLarge);
        builder.append(',');
        builder.append(mLastSmall);
        builder.append(',');
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                builder.append(mSmallTiles[large][small].getOwner().name());
                builder.append(',');
            }
        }
        return builder.toString();
    }
*/
    /**
     * Restore the state of the game from the given string.
     */

    /*
    public void putState(String gameData) {
        String[] fields = gameData.split(",");
        int index = 0;
        mLastLarge = Integer.parseInt(fields[index++]);
        mLastSmall = Integer.parseInt(fields[index++]);
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile.Owner owner = Tile.Owner.valueOf(fields[index++]);
                mSmallTiles[large][small].setOwner(owner);
            }
        }
        setAvailableFromLastMove(mLastSmall);
        updateAllTiles();
    }

*/
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
