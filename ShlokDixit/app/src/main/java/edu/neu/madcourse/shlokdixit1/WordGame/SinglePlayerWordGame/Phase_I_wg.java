/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
 ***/
package edu.neu.madcourse.shlokdixit1.WordGame.SinglePlayerWordGame;


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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.neu.madcourse.shlokdixit1.R;
import edu.neu.madcourse.shlokdixit1.TicTacToe.Tile;


public class Phase_I_wg extends Activity implements CompoundButton.OnCheckedChangeListener {
    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    public static final String MyPREFERENCES = "MyPrefs" ;


    SharedPreferences sharedpreferences;
    int points;
    TextView points_tv;
    int bonusPoints;
    MediaPlayer mMediaPlayer;
    ToggleButton t;
    CountDownTimer countDownTimer = null;
    TextView timer_wg;
    ArrayList<String> inputWord = new ArrayList<>();



    boolean[] wordSelected = new boolean[9];
    boolean[][] letterClicked = new boolean[9][9];
    boolean[][] correctClicks = new boolean[9][9];
    long miliSecsLeft;

    ArrayList<Integer> smallPos = new ArrayList<Integer>();
    ArrayList<Integer> largePos = new ArrayList<Integer>();


    static private int mLargeIds[] = {R.id.large1, R.id.large2, R.id.large3,
            R.id.large4, R.id.large5, R.id.large6, R.id.large7, R.id.large8,
            R.id.large9,};
    static private int mSmallIds[] = {R.id.small1, R.id.small2, R.id.small3,
            R.id.small4, R.id.small5, R.id.small6, R.id.small7, R.id.small8,
            R.id.small9,};



    private Tile mLargeTiles[] = new Tile[9];//no use
    private Tile mSmallTiles[][] = new Tile[9][9];//no use
    private Tile.Owner mPlayer = Tile.Owner.X;//no use
    private Set<Tile> mAvailable = new HashSet<Tile>();//no use
    char[][] words = new char[9][9];//fetch 9 letter words
    char[][] gameWords = new char[9][9];
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
    int largeLatest, smallLatest;
    int i;
    FileOutputStream outputStream;

    Ringtone beep;
    ToneGenerator tone;


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


    private Firebase mRef;
    boolean[][] buttonState = new boolean[9][9];

    private Context mContext;
////////////@Declaration-end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("PHASE-I");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_wg_phase_1);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean contFlag = sharedpreferences.getBoolean("ContinueFlag", false);

        if (contFlag == true) {

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("ContinueFlag", false);
            editor.commit();
            restoredata();
            points_tv = (TextView) findViewById(R.id.score_I);
            points_tv.setText("Score: " + Integer.toString(points));
        }

        else
        {

        }


        //Firebase ref
        Firebase.setAndroidContext(this);
        //String hello= "hey how are you";
        mRef = new Firebase("https://shining-inferno-2019.firebaseio.com/");
        mRef.setValue(points);
        // Sync data to firebase
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });
        mRef.keepSynced(false);
        //// Sync data to firebase ending

        //Music begin
        tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cartoon);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        //Music ending

        // Array list to populate words
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
        // Array list to populate words ends here


        //methods which are starting on OnCreate()
        startCountDownTimer();
        fillTiles();
        initTileData();
        initDataHolder();
        playGame();


        points_tv = (TextView) findViewById(R.id.score_I);
        points_tv.setText("Score: " + Integer.toString(points));

        t = (ToggleButton) findViewById(R.id.togglebutton1);
        t.setOnCheckedChangeListener(this);


///@match the word by calling checkword()

        Button checkword = (Button) findViewById(R.id.check_wg_1);
        checkword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                checkword();
            }
        });
//@takes the game to home screen
        Button mainmenu = (Button) findViewById(R.id.button_main_1);
        mainmenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();

            }
        });
        //@resets the game
        Button reboot = (Button) findViewById(R.id.restart_wg_1);
        reboot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Set_List_To_Null();
//               countDownTimer.cancel();
                finish();
                Intent intent = new Intent(Phase_I_wg.this, Phase_I_wg.class);
                startActivity(intent);


            }
        });


    }
    private String file = "mydata";

    public void fetchdata(){

        
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("points_I_wg", points);
        editor.putInt("bonusPoints", bonusPoints);
        editor.putInt("large", large);
        editor.putInt("small", small);
        editor.putInt("largeLatest", largeLatest);
        editor.putInt("smallLatest", smallLatest);
        Set<String> set = new HashSet<String>();
        set.addAll(inputWord);
        editor.putStringSet("inputWord", set);

        editor.putInt("array_size", letterClicked.length);
        for(int i=0;i<letterClicked.length; i++)
            for(int j=0;j<letterClicked[i].length; j++)
            editor.putBoolean("array_" + i +"_"+ j, letterClicked[i][j]);

        editor.putInt("array_size", correctClicks.length);
        for(int i=0;i<correctClicks.length; i++)
            for(int j=0;j<correctClicks[j].length; j++)
            editor.putBoolean("array_" + i +"_"+ j, correctClicks[i][j]);

        editor.putInt("array_size", gameWords.length);
        for(int i=0;i<gameWords.length; i++)
            editor.putString("array_" + i, gameWords[i].toString());

        editor.putInt("array_size", wordSelected.length);
        for(int i=0;i<wordSelected.length; i++)
            editor.putBoolean("array_" + i, wordSelected[i]);



        editor.commit();
        Toast.makeText(this.getApplicationContext(), " GAME STATE SAVED !", Toast.LENGTH_SHORT).show();


        }


    public void restoredata(){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        points = sharedpreferences.getInt("points_I_wg", 0);
        bonusPoints=sharedpreferences.getInt("bonusPoints", 0);
        large=sharedpreferences.getInt("large", 0);
        small=sharedpreferences.getInt("small", 0);
        largeLatest=sharedpreferences.getInt("largeLatest", 0);
        smallLatest=sharedpreferences.getInt("smallLatest", 0);

        Set<String> set = sharedpreferences.getStringSet("inputWord", null);
    }

    //@ checkword()

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
                    points_tv.setText("Points: " + Integer.toString(points));
                    //--->
                    for (int i = 1; i < largePos.size(); i++) {
                        correctClicks[largePos.get(i)][smallPos.get(i)] = true;
                    }
                    //--->
                    wordSelected[largePos.get(1)] = true;
                    updateAllTiles(gameWords, wordSelected, letterClicked);
                    initTileData();

                } else {
                    Toast.makeText(this.getApplicationContext(), "InCorrect Word !", Toast.LENGTH_SHORT).show();
                    for (int i = 1; i < largePos.size(); i++) {
                        //--->
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


    }//checkword() ends here

    Button inner1;

    private void updateAllTiles(char[][] words, boolean[] wordSelected, boolean[][] letterClicked) // words here refer to gameWords
    {
        for (int large = 0; large < 9; large++) {
            View outer = this.findViewById(mLargeIds[large]);

            for (int small = 0; small < 9; small++) {

                inner1 = (Button) outer.findViewById(mSmallIds[small]);
                inner1.setText(Character.toString(words[large][small]));

                if (letterClicked[large][small] == true)
                    inner1.setTextColor(Color.BLACK);
                else
                    inner1.setTextColor(Color.WHITE);

                if (wordSelected[large] == true) {
                    inner1.setEnabled(false);
                    inner1.setBackgroundColor(Color.CYAN);
                    //savecolor = wordSelected[large]
                }

            }
        }

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
            if (Collections.binarySearch(l_j_k, key) >= 0) {
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
       // Accumulator.getInstance().setPoints(0);
       // Accumulator.getInstance().setBonusPoints(0);
    }

    //String s1;

    private void playGame() {

        for (large = 0; large < 9; large++) {
            View outer = this.findViewById(mLargeIds[large]);

            for (small = 0; small < 9; small++) {
                final Button inner1 = (Button) outer.findViewById(mSmallIds[small]);

                inner1.setTag(large + ":" + small);//not clear TA help needed
                inner1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        largeLatest = Integer.parseInt(view.getTag().toString().split(":")[0]);//not clear TA help needed
                        smallLatest = Integer.parseInt(view.getTag().toString().split(":")[1]);//not clear TA help needed
                        int smOld = smallPos.get(smallPos.size() - 1);
                        int lrOld = largePos.get(largePos.size() - 1);

                        if (Word_Selection(lrOld, smOld, largeLatest, smallLatest)) {
                            inner1.setTextColor(Color.BLACK);
                            System.out.print("button pressed");
                            //s1= Boolean.toString(letterClicked[largeLatest][smallLatest]);
                            //i = Integer.parseInt(s1);
                            inputWord.add(inner1.getText().toString());
                            smallPos.add(smallLatest);
                            largePos.add(largeLatest);
                            tone.startTone(ToneGenerator.TONE_PROP_BEEP);
                            letterClicked[largeLatest][smallLatest] = true;

                        } else if (smOld == smallLatest && lrOld == largeLatest) {
                            inputWord.remove(inputWord.size() - 1);
                            smallPos.remove(smallPos.size() - 1);
                            largePos.remove(largePos.size() - 1);
                            inner1.setTextColor(Color.WHITE);
                            System.out.print("button released");
                            //  letterClicked[largeLatest][smallLatest] = false;

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


    public void Set_List_To_Null() {

        l_a = null;
        l_b = null;
        l_c = null;
        l_d_e = null;
        l_f_g = null;
        l_h_i = null;
        l_j_k = null;
        l_l_m = null;
        l_n_o = null;
        l_p_q = null;
        l_r = null;
        l_s = null;
        l_t = null;
        l_u = null;
        l_v_w = null;
        l_x_y_z = null;

    }


    long remaining;
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
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();

        fetchdata();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
