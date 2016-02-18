/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
***/
package edu.neu.madcourse.shlokdixit1.Scraggle;

import android.app.Fragment;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.HashSet;
import java.util.Set;

import edu.neu.madcourse.shlokdixit1.R;
public class GameFragment_wg extends Fragment {
   static private int mLargeIds[] = {R.id.large1, R.id.large2, R.id.large3,
         R.id.large4, R.id.large5, R.id.large6, R.id.large7, R.id.large8,
         R.id.large9,};
   static private int mSmallIds[] = {R.id.small1, R.id.small2, R.id.small3,
         R.id.small4, R.id.small5, R.id.small6, R.id.small7, R.id.small8,
         R.id.small9,};
   private Handler mHandler = new Handler();
   private Tile_wg mEntireBoard = new Tile_wg(this);
   private Tile_wg mLargeTileWgs[] = new Tile_wg[9];
   private Tile_wg mSmallTileWgs[][] = new Tile_wg[9][9];
   private Tile_wg.Owner mPlayer = Tile_wg.Owner.X;
   private Set<Tile_wg> mAvailable = new HashSet<Tile_wg>();
   private int mSoundX, mSoundO, mSoundMiss, mSoundRewind;
   private SoundPool mSoundPool;
   private float mVolume = 1f;
   private int mLastLarge;
   private int mLastSmall;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      // Retain this fragment across configuration changes.
      setRetainInstance(true);
      initGame();
      mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
      mSoundX = mSoundPool.load(getActivity(), R.raw.sergenious_movex, 1);
      mSoundO = mSoundPool.load(getActivity(), R.raw.sergenious_moveo, 1);
      mSoundMiss = mSoundPool.load(getActivity(), R.raw.erkanozan_miss, 1);
      mSoundRewind = mSoundPool.load(getActivity(), R.raw.joanne_rewind, 1);
   }

   private void clearAvailable() {
      mAvailable.clear();
   }

   private void addAvailable(Tile_wg tileWg) {
      tileWg.animate();
      mAvailable.add(tileWg);
   }

   public boolean isAvailable(Tile_wg tileWg) {
      return mAvailable.contains(tileWg);
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View rootView =
            inflater.inflate(R.layout.large_board_wg, container, false);
      initViews(rootView);
      updateAllTiles();
      return rootView;
   }

   private void initViews(View rootView) {
      mEntireBoard.setView(rootView);
      for (int large = 0; large < 9; large++) {
         View outer = rootView.findViewById(mLargeIds[large]);
         mLargeTileWgs[large].setView(outer);

         for (int small = 0; small < 9; small++) {
            ImageButton inner = (ImageButton) outer.findViewById
                  (mSmallIds[small]);
            final int fLarge = large;
            final int fSmall = small;
            final Tile_wg smallTileWg = mSmallTileWgs[large][small];
            smallTileWg.setView(inner);
            // ...
            inner.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  smallTileWg.animate();
                  // ...
                  if (isAvailable(smallTileWg)) {
                     ((GameActivity_wg)getActivity()).startThinking();
                     mSoundPool.play(mSoundX, mVolume, mVolume, 1, 0, 1f);
                     makeMove(fLarge, fSmall);
                     think();
                  } else {
                     mSoundPool.play(mSoundMiss, mVolume, mVolume, 1, 0, 1f);
                  }
               }
            });
            // ...
         }
      }
   }

   private void think() {
      mHandler.postDelayed(new Runnable() {
         @Override
         public void run() {
            if (getActivity() == null) return;
            if (mEntireBoard.getOwner() == Tile_wg.Owner.NEITHER) {
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
            ((GameActivity_wg) getActivity()).stopThinking();
         }
      }, 1000);
   }

   private void pickMove(int move[]) {
      Tile_wg.Owner opponent = mPlayer == Tile_wg.Owner.X ? Tile_wg.Owner.O : Tile_wg
            .Owner.X;
      int bestLarge = -1;
      int bestSmall = -1;
      int bestValue = Integer.MAX_VALUE;
      for (int large = 0; large < 9; large++) {
         for (int small = 0; small < 9; small++) {
            Tile_wg smallTileWg = mSmallTileWgs[large][small];
            if (isAvailable(smallTileWg)) {
               // Try the move and get the score
               Tile_wg newBoard = mEntireBoard.deepCopy();
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
   }

   private void switchTurns() {
      mPlayer = mPlayer == Tile_wg.Owner.X ? Tile_wg.Owner.O : Tile_wg
            .Owner.X;
   }

   private void makeMove(int large, int small) {
      mLastLarge = large;
      mLastSmall = small;
      Tile_wg smallTileWg = mSmallTileWgs[large][small];
      Tile_wg largeTileWg = mLargeTileWgs[large];
      smallTileWg.setOwner(mPlayer);
      setAvailableFromLastMove(small);
      Tile_wg.Owner oldWinner = largeTileWg.getOwner();
      Tile_wg.Owner winner = largeTileWg.findWinner();
      if (winner != oldWinner) {
         largeTileWg.animate();
         largeTileWg.setOwner(winner);
      }
      winner = mEntireBoard.findWinner();
      mEntireBoard.setOwner(winner);
      updateAllTiles();
      if (winner != Tile_wg.Owner.NEITHER) {
         ((GameActivity_wg)getActivity()).reportWinner(winner);
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
      Log.d("UT3", "init game");
      mEntireBoard = new Tile_wg(this);
      // Create all the tiles
      for (int large = 0; large < 9; large++) {
         mLargeTileWgs[large] = new Tile_wg(this);
         for (int small = 0; small < 9; small++) {
            mSmallTileWgs[large][small] = new Tile_wg(this);
         }
         mLargeTileWgs[large].setSubTiles(mSmallTileWgs[large]);
      }
      mEntireBoard.setSubTiles(mLargeTileWgs);

      // If the player moves first, set which spots are available
      mLastSmall = -1;
      mLastLarge = -1;
      setAvailableFromLastMove(mLastSmall);
   }

   private void setAvailableFromLastMove(int small) {
      clearAvailable();
      // Make all the tiles at the destination available
      if (small != -1) {
         for (int dest = 0; dest < 9; dest++) {
            Tile_wg tileWg = mSmallTileWgs[small][dest];
            if (tileWg.getOwner() == Tile_wg.Owner.NEITHER)
               addAvailable(tileWg);
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
            Tile_wg tileWg = mSmallTileWgs[large][small];
            if (tileWg.getOwner() == Tile_wg.Owner.NEITHER)
               addAvailable(tileWg);
         }
      }
   }

   private void updateAllTiles() {
      mEntireBoard.updateDrawableState();
      for (int large = 0; large < 9; large++) {
         mLargeTileWgs[large].updateDrawableState();
         for (int small = 0; small < 9; small++) {
            mSmallTileWgs[large][small].updateDrawableState();
         }
      }
   }

   /** Create a string containing the state of the game. */
   public String getState() {
      StringBuilder builder = new StringBuilder();
      builder.append(mLastLarge);
      builder.append(',');
      builder.append(mLastSmall);
      builder.append(',');
      for (int large = 0; large < 9; large++) {
         for (int small = 0; small < 9; small++) {
            builder.append(mSmallTileWgs[large][small].getOwner().name());
            builder.append(',');
         }
      }
      return builder.toString();
   }

   /** Restore the state of the game from the given string. */
   public void putState(String gameData) {
      String[] fields = gameData.split(",");
      int index = 0;
      mLastLarge = Integer.parseInt(fields[index++]);
      mLastSmall = Integer.parseInt(fields[index++]);
      for (int large = 0; large < 9; large++) {
         for (int small = 0; small < 9; small++) {
            Tile_wg.Owner owner = Tile_wg.Owner.valueOf(fields[index++]);
            mSmallTileWgs[large][small].setOwner(owner);
         }
      }
      setAvailableFromLastMove(mLastSmall);
      updateAllTiles();
   }
}

