/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
 ***/
package edu.neu.madcourse.shlokdixit1.WordGame;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import edu.neu.madcourse.shlokdixit1.R;
import edu.neu.madcourse.shlokdixit1.TicTacToe.GameActivity;
import edu.neu.madcourse.shlokdixit1.TicTacToe.Tile;

public class GameFragment_wg extends Fragment {
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
    private int mSoundX, mSoundO, mSoundMiss, mSoundRewind;
    private SoundPool mSoundPool;
    private float mVolume = 1f;
    private int mLastLarge;
    private int mLastSmall;
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
    ArrayList<Integer> smallPos;
    ArrayList<Integer> largePos;
    Ringtone beep;
    ToneGenerator tone;

    boolean[][] buttonState = new boolean[9][9];

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        //  this.loadData();
        Accumulator.getInstance().setControlObj(this);
        tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        // initGame();
       // mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
       // mSoundX = mSoundPool.load(getActivity(), R.raw.sergenious_movex, 1);
        //mSoundO = mSoundPool.load(getActivity(), R.raw.sergenious_moveo, 1);
        //mSoundMiss = mSoundPool.load(getActivity(), R.raw.erkanozan_miss, 1);
        //mSoundRewind = mSoundPool.load(getActivity(), R.raw.joanne_rewind, 1);

        mContext = getActivity().getApplicationContext();
        ArrayList<String> l1;
        this.l1 = new ArrayList<String>();

    }


    private void clearAvailable() {
        mAvailable.clear();
    }

    private void addAvailable(Tile tile) {
        tile.animate();
        mAvailable.add(tile);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView =
                inflater.inflate(R.layout.large_board_wg, container, false);
        initViews(rootView);
        //updateAllTiles();
        return rootView;

    }

    private void initViews(View rootView) {

        this.fillTiles(rootView);
        initTileData();
        for (large = 0; large < 9; large++) {
            View outer = rootView.findViewById(mLargeIds[large]);

            for (small = 0; small < 9; small++) {

                final Button inner1 = (Button) outer.findViewById(mSmallIds[small]);
                inner1.setTag(large + ":" + small);
                inner1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        int large = Integer.parseInt(view.getTag().toString().split(":")[0]);
                        int small = Integer.parseInt(view.getTag().toString().split(":")[1]);
                        int smOld = smallPos.get(smallPos.size() - 1);
                        int lrOld = largePos.get(largePos.size() - 1);

                        if (isAdjacent(lrOld, smOld, large, small)) {


                            inner1.setTextColor(Color.RED);
                            //inner1.setBackgroundColor(Color.RED);
                            // inner1.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
                            //inner1.setVisibility (View.VISIBLE);
                            selectedWord.add(inner1.getText().toString());
                            smallPos.add(small);
                            largePos.add(large);
                            tone.startTone(ToneGenerator.TONE_PROP_BEEP);
                            Accumulator.getInstance().setArl(selectedWord);

                        } else if (smOld == small && lrOld == large) {
                            selectedWord.remove(selectedWord.size() - 1);
                            smallPos.remove(smallPos.size() - 1);
                            largePos.remove(largePos.size() - 1);
                            inner1.setTextColor(Color.WHITE);
                            //inner1.setBackgroundColor(Color.parseColor("#fb8c00"));

                        } else {
                            Toast.makeText(mContext, "Not an adjacent letter", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
        }
    }

    public void initTileData() {
        selectedWord = null;
        smallPos = null;
        largePos = null;
        selectedWord = new ArrayList<String>();
        smallPos = new ArrayList<Integer>();
        largePos = new ArrayList<Integer>();
        smallPos.add(99);
        largePos.add(99);
    }

    public boolean isAdjacent(int largeOld, int smallOld, int largeNew, int smallNew) {
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



    private void fillTiles(View rootView) {

        ins = getResources().openRawResource(R.raw.a_list);
        reader = new BufferedReader(new InputStreamReader(ins));
        try {
            while ((data = reader.readLine()) != null) {
                l1.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[][] words = new char[9][9];
        ArrayList<String> nine = this.getNineLengthWords(l1);
        int min = 0;
        int max = 30;
        int ran = 0;
        Random rn = new Random();

        for (int i = 0; i < 9; i++) {
            ran = rn.nextInt(max - min + 1) + min;
            words[i] = nine.get(ran).toString().toCharArray();
        }
        words = this.swapChars(words);

        for (int large = 0; large < 9; large++) {
            View outer = rootView.findViewById(mLargeIds[large]);

            for (int small = 0; small < 9; small++) {

                final Button inner1 = (Button) outer.findViewById(mSmallIds[small]);
                inner1.setText(Character.toString(words[large][small]));
                buttonState[large][small] = true;
            }
        }


    }


    private void switchTurns() {
        mPlayer = mPlayer == Tile.Owner.X ? Tile.Owner.O : Tile
                .Owner.X;
    }

    private void makeMove(int large, int small) {
        mLastLarge = large;
        mLastSmall = small;
        Tile smallTile = mSmallTiles[large][small];
        Tile largeTile = mLargeTiles[large];
        smallTile.setOwner(mPlayer);
        setAvailableFromLastMove(small);
        Tile.Owner oldWinner = largeTile.getOwner();
        Tile.Owner winner = largeTile.findWinner();
        if (winner != oldWinner) {
            largeTile.animate();
            largeTile.setOwner(winner);
        }
        //winner = mEntireBoard.findWinner();
        //  mEntireBoard.setOwner(winner);
        updateAllTiles();
        if (winner != Tile.Owner.NEITHER) {
            ((GameActivity) getActivity()).reportWinner(winner);
        }
    }

/*   public void restartGame() {
      mSoundPool.play(mSoundRewind, mVolume, mVolume, 1, 0, 1f);
      // ...
      initGame();
      initViews(getView());
      updateAllTiles();
   }*/

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

    // public void vanish(View view) {
    //ToggleButton p= (ToggleButton)findViewById(R.id.pause);
    //p.setVisibility(View.GONE);

    // }


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

    public void loadData() {
        try {
            String data;
            InputStream ins;
            BufferedReader reader;

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
            this.masterData = new ArrayList<ArrayList>();

            ins = getResources().openRawResource(R.raw.a_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l1.add(data);
            }

            ins = getResources().openRawResource(R.raw.b_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l2.add(data);
            }

            ins = getResources().openRawResource(R.raw.c_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l02.add(data);
            }

            ins = getResources().openRawResource(R.raw.d_e_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l3.add(data);
            }

            ins = getResources().openRawResource(R.raw.f_g_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l4.add(data);
            }

            ins = getResources().openRawResource(R.raw.h_i_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l5.add(data);
            }

            ins = getResources().openRawResource(R.raw.j_k_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l6.add(data);
            }

            ins = getResources().openRawResource(R.raw.l_m_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l7.add(data);
            }

            ins = getResources().openRawResource(R.raw.n_o_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l8.add(data);
            }

            ins = getResources().openRawResource(R.raw.p_q_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l9.add(data);
            }

            ins = getResources().openRawResource(R.raw.r_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l10.add(data);
            }
            ins = getResources().openRawResource(R.raw.s_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l101.add(data);
            }
            ins = getResources().openRawResource(R.raw.t_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l11.add(data);
            }
            ins = getResources().openRawResource(R.raw.u_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l12.add(data);
            }
            ins = getResources().openRawResource(R.raw.v_w_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l13.add(data);
            }
            ins = getResources().openRawResource(R.raw.x_y_z_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l14.add(data);
            }



        } catch (IOException e) {

            e.printStackTrace();
        }
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

    public char[][] swapChars(char[][] chr) {
        char temp;
        ArrayList<String> nine = new ArrayList<String>();
        for (int j = 0; j < chr.length; j++) {
            temp = chr[j][3];
            chr[j][3] = chr[j][5];
            chr[j][5] = temp;
        }
        return chr;
    }

    /** Restore the state of the game from the given string. */
   /*public void putState(String gameData) {
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
   }*/
}