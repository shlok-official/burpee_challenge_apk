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
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
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

   ArrayList<String> lst_ab;
   ArrayList<String> lst_c;
   ArrayList<String> lst_de;
   ArrayList<String> lst_fgh;
   ArrayList<String> lst_ijkl;
   ArrayList<String> lst_mn;
   ArrayList<String> lst_op;
   ArrayList<String> lst_qr;
   ArrayList<String> lst_s;
   ArrayList<String> lst_tu;
   ArrayList<String> lst_vwxyz;

   ArrayList<String> listItems;
   ArrayAdapter<String> adapter;
   ArrayList<ArrayList> masterData;
   ArrayList<String> nineLenghtWords;
   ArrayList<String> selectedWord;

   int large, small;
   int[] selectedLarge;
   int[] selectedSmall;
   int counter=0;


   boolean[][] buttonState = new boolean[9][9];

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      // Retain this fragment across configuration changes.
      setRetainInstance(true);
      this.loadData();

      // initGame();
      mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
      mSoundX = mSoundPool.load(getActivity(), R.raw.sergenious_movex, 1);
      mSoundO = mSoundPool.load(getActivity(), R.raw.sergenious_moveo, 1);
      mSoundMiss = mSoundPool.load(getActivity(), R.raw.erkanozan_miss, 1);
      mSoundRewind = mSoundPool.load(getActivity(), R.raw.joanne_rewind, 1);
   }

   private void clearAvailable() {
      mAvailable.clear();
   }

   private void addAvailable(Tile tile) {
      tile.animate();
      mAvailable.add(tile);
   }

/*   public boolean isAvailable(Button clickedButton) {

      return true;
   }*/

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {


      View rootView =
              inflater.inflate(R.layout.fragment_game_wg, container, false);
      initViews(rootView);
      //updateAllTiles();
      return rootView;
   }

   private void initViews(View rootView) {

      this.fillTiles(rootView);
      selectedWord = new ArrayList<String>();


      for (large = 0; large < 9; large++) {
         View outer = rootView.findViewById(mLargeIds[large]);

         for (small = 0; small < 9; small++) {

            final Button inner1 = (Button) outer.findViewById(mSmallIds[small]);
            //  inner1.setText(Character.toString(nineWords[large][small]));}}
            inner1.setTag(large + ":" + small);
            inner1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  int large = Integer.parseInt(view.getTag().toString().split(":")[0]);
                  int small = Integer.parseInt(view.getTag().toString().split(":")[1]);
                  if(isButtonAvailable(large, small)) {

                     if(isAdjacent((Integer.parseInt(selectedWord.get(counter).toString().split(":")[0])),
                             (Integer.parseInt(selectedWord.get(counter).toString().split(":")[1])), large, small)) {
                        //selectedWord.add(inner1.getText().toString());
                        selectedWord.add(inner1.getTag().toString());
                        counter++;
                        System.out.println(inner1.getText().toString());

                        setButtonState(large, small, false);
                     }
                  }
                  //inner1.setEnabled(false);
                   /*for(int a=0; a<selectedWord.size();a++)
                        System.out.println(selectedWord.get(a).toString());*/
                  //setButtonState(large, small, true);
                  //  }

/*                if (isAvailable(inner1)) {
                     ((GameActivity)getActivity()).startThinking();
                     mSoundPool.play(mSoundX, mVolume, mVolume, 1, 0, 1f);

                     // makeMove(fLarge, fSmall);
                     //think();

                  } else {
                     mSoundPool.play(mSoundMiss, mVolume, mVolume, 1, 0, 1f);
                  }*/
               }
            });
         }
      }
   }

   public boolean isAdjacent(int largeOld, int smallOld, int largeNew, int smallNew)
   {
      if(largeNew == largeOld) {
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
               else return false;

            default: return false;
         }
      }
      else return false;

   }

   public boolean isButtonAvailable(int l, int s)
   {
      if (buttonState[l][s] == false)
         return false;
      else return true;
   }

   public void setButtonState(int large, int small, boolean state)
   {
      buttonState[large][small] = state;
   }

/*
     mEntireBoard.setView(rootView);
      for (int large = 0; large < 9; large++) {
         View outer = rootView.findViewById(mLargeIds[large]);
         mLargeTiles[large].setView(outer);

         for (int small = 0; small < 9; small++) {
            //ImageButton inner = (ImageButton) outer.findViewById
            //      (mSmallIds[small]);
           final Button inner1 = (Button) outer.findViewById(mSmallIds[small]);
            //inner1.sette

            final int fLarge = large;
            final int fSmall = small;
            final Tile smallTile = mSmallTiles[large][small];
            smallTile.setView(inner1);
            // ...
            inner1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  smallTile.animate();
                  // ...
                  if (isAvailable(smallTile)) {
                     ((GameActivity)getActivity()).startThinking();
                     mSoundPool.play(mSoundX, mVolume, mVolume, 1, 0, 1f);

                    // makeMove(fLarge, fSmall);
                     //think();
                     inner1.setText("A");
                  } else {
                     mSoundPool.play(mSoundMiss, mVolume, mVolume, 1, 0, 1f);
                  }
               }
            });
            // ...
         }
      }
   }*/

   private void fillTiles(View rootView){

/*      char[][]words=new char[9][9];

      for (int i=0; i<9; i++)
      {
         if(nineLenghtWords.size()>i)
             words[i] = nineLenghtWords.get(i).toString().toCharArray();
      }*/

      char [][] nineWords = {{'B', 'L', 'I', 'Z', 'Z', 'A', 'R', 'D', 'S'},
              {'B', 'L', 'I', 'Z', 'Z', 'A', 'R', 'D', 'S'},
              {'B', 'L', 'I', 'Z', 'Z', 'A', 'R', 'D', 'S'},
              {'B', 'L', 'I', 'Z', 'Z', 'A', 'R', 'D', 'S'},
              {'B', 'L', 'I', 'Z', 'Z', 'A', 'R', 'D', 'S'},
              {'B', 'L', 'I', 'Z', 'Z', 'A', 'R', 'D', 'S'},
              {'B', 'L', 'I', 'Z', 'Z', 'A', 'R', 'D', 'S'},
              {'B', 'L', 'I', 'Z', 'Z', 'A', 'R', 'D', 'S'},
              {'B', 'L', 'I', 'Z', 'Z', 'A', 'R', 'D', 'S'}};

      for (int large = 0; large < 9; large++) {
         View outer = rootView.findViewById(mLargeIds[large]);

         for (int small = 0; small < 9; small++) {

            final Button inner1 = (Button) outer.findViewById(mSmallIds[small]);
            inner1.setText(Character.toString(nineWords[large][small]));
            buttonState[large][small] = true;
         }}


   }

/*   private void think() {
      mHandler.postDelayed(new Runnable() {
         @Override
         public void run() {
            if (getActivity() == null) return;
            if (mEntireBoard.getOwner() == Tile.Owner.NEITHER) {
               int move[] = new int[2];
               pickMove(move);
               if (move[0] != -1 && move[1] != -1) {
                  switchTurns();
                  mSoundPool.play(mSoundO, mVolume, mVolume,
                        1, 0, 1f);
                  makeMove(move[0], move[1]);
                  switchTurns();
               }
            }
            ((GameActivity) getActivity()).stopThinking();
         }
      }, 1000);
   }*/

/*   private void pickMove(int move[]) {
      Tile.Owner opponent = mPlayer == Tile.Owner.X ? Tile.Owner.O : Tile
            .Owner.X;
      int bestLarge = -1;
      int bestSmall = -1;
      int bestValue = Integer.MAX_VALUE;
      for (int large = 0; large < 9; large++) {
         for (int small = 0; small < 9; small++) {
            Tile smallTile = mSmallTiles[large][small];
            if (isAvailable(smallTile)) {
               // Try the move and get the score
               Tile newBoard = mEntireBoard.deepCopy();
               newBoard.getSubTiles()[large].getSubTiles()[small]
                     .setOwner(opponent);
               int value = newBoard.evaluate();
               Log.d("UT3",
                       "Moving to " + large + ", " + small + " gives value " +
                               "" + value
               );
               if (value < bestValue) {
                  bestLarge = large;
                  bestSmall = small;
                  bestValue = value;
               }
            }
         }
      }
      move[0] = bestLarge;
      move[1] = bestSmall;
      Log.d("UT3", "Best move is " + bestLarge + ", " + bestSmall);
   }*/

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
      //mEntireBoard.setOwner(winner);
      updateAllTiles();
      if (winner != Tile.Owner.NEITHER) {
         ((GameActivity)getActivity()).reportWinner(winner);
      }
   }

   public void restartGame() {
      mSoundPool.play(mSoundRewind, mVolume, mVolume, 1, 0, 1f);
      // ...
      initGame();
      initViews(getView());
      updateAllTiles();
   }

   public void initGame() {

/*      Log.d("UT3", "init game");
      mEntireBoard = new Tile(this);
      // Create all the tiles
      for (int large = 0; large < 9; large++) {
         mLargeTiles[large] = new Tile(this);
         for (int small = 0; small < 9; small++) {
            mSmallTiles[large][small] = new Tile(this);
         }
         mLargeTiles[large].setSubTiles(mSmallTiles[large]);
      }
      mEntireBoard.setSubTiles(mLargeTiles);

      // If the player moves first, set which spots are available
      mLastSmall = -1;
      mLastLarge = -1;
      setAvailableFromLastMove(mLastSmall);*/
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
    //  mEntireBoard.updateDrawableState();
      for (int large = 0; large < 9; large++) {
         mLargeTiles[large].updateDrawableState();
         for (int small = 0; small < 9; small++) {
            mSmallTiles[large][small].updateDrawableState();
         }
      }
   }

   /** Create a string containing the state of the game. */
   public String getState() {
/*      StringBuilder builder = new StringBuilder();
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
      return builder.toString();*/
      return "dummy val";
   }

   public void loadData()
   {
      try {
         String data;
         InputStream ins;
         BufferedReader reader;

         this.lst_ab =  new ArrayList<String>();
         this.lst_c =  new ArrayList<String>();
         this.lst_de =  new ArrayList<String>();
         this.lst_fgh =  new ArrayList<String>();
         this.lst_ijkl = new ArrayList<String>();
         this.lst_mn = new ArrayList<String>();
         this.lst_op = new ArrayList<String>();
         this.lst_qr = new ArrayList<String>();
         this.lst_s = new ArrayList<String>();
         this.lst_tu = new ArrayList<String>();
         this.lst_vwxyz = new ArrayList<String>();
         this.masterData = new ArrayList<ArrayList>();

         ins = getResources().openRawResource(R.raw.a_list);
         reader = new BufferedReader(new InputStreamReader(ins));
         while ((data = reader.readLine()) != null) {
            lst_ab.add(data);
         }

         ins = getResources().openRawResource(R.raw.b_list);
         reader = new BufferedReader(new InputStreamReader(ins));
         while ((data = reader.readLine()) != null) {
            lst_c.add(data);
         }

         ins = getResources().openRawResource(R.raw.c_list);
         reader = new BufferedReader(new InputStreamReader(ins));
         while ((data = reader.readLine()) != null) {
            lst_de.add(data);
         }

         ins = getResources().openRawResource(R.raw.d_e_list);
         reader = new BufferedReader(new InputStreamReader(ins));
         while ((data = reader.readLine()) != null) {
            lst_fgh.add(data);
         }

         ins = getResources().openRawResource(R.raw.f_g_list);
         reader = new BufferedReader(new InputStreamReader(ins));
         while ((data = reader.readLine()) != null) {
            lst_ijkl.add(data);
         }

         ins = getResources().openRawResource(R.raw.h_i_list);
         reader = new BufferedReader(new InputStreamReader(ins));
         while ((data = reader.readLine()) != null) {
            lst_mn.add(data);
         }

         ins = getResources().openRawResource(R.raw.j_k_list);
         reader = new BufferedReader(new InputStreamReader(ins));
         while ((data = reader.readLine()) != null) {
            lst_op.add(data);
         }

         ins = getResources().openRawResource(R.raw.l_m_list);
         reader = new BufferedReader(new InputStreamReader(ins));
         while ((data = reader.readLine()) != null) {
            lst_qr.add(data);
         }

         ins = getResources().openRawResource(R.raw.n_o_list);
         reader = new BufferedReader(new InputStreamReader(ins));
         while ((data = reader.readLine()) != null) {
            lst_s.add(data);
         }

         ins = getResources().openRawResource(R.raw.p_q_list);
         reader = new BufferedReader(new InputStreamReader(ins));
         while ((data = reader.readLine()) != null) {
            lst_tu.add(data);
         }

         ins = getResources().openRawResource(R.raw.r_list);
         reader = new BufferedReader(new InputStreamReader(ins));
         while ((data = reader.readLine()) != null) {
            lst_vwxyz.add(data);
         }

         // masterData.addAll(Arrays.asList(lst_ab, lst_c, lst_de, lst_fgh, lst_ijkl, lst_mn, lst_op,lst_qr, lst_s,lst_tu, lst_vwxyz ));
         //masterData.add(lst_ab);
         //    this.getNineLengthWords(lst_ab);

      } catch (IOException e) {

         e.printStackTrace();
      }
   }

   public void getNineLengthWords(ArrayList ar)
   {
      int i=0, j=0;
      this.nineLenghtWords=ar;

      for (i=0; i<9; i++)
      {
         ArrayList innerAr = (ArrayList) ar.get(i);
         for (j=0; i<innerAr.size(); i++)
         {
            if (innerAr.get(j).toString().length()==9)
               nineLenghtWords.add(innerAr.get(j).toString());
         }

      }
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