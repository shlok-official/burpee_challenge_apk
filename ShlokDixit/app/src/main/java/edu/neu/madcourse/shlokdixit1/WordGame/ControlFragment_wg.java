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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import edu.neu.madcourse.shlokdixit1.R;

public class ControlFragment_wg extends Fragment {

    ArrayList<String> inputWord;
    GameFragment_wg gameFrg = DataHolder.getInstance().getControlObj();
    int points;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_control_wg, container, false);
        View main = rootView.findViewById(R.id.button_main);
        View restart = rootView.findViewById(R.id.button_restart);
        View check = rootView.findViewById(R.id.check_wg);

        //View pause = rootView.findViewById(R.id.button_pause);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences myPrefs = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor;
                prefsEditor = myPrefs.edit();
                prefsEditor.putString("REFKEY", "some data");
                prefsEditor.commit();
                getActivity().finish();
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mSoundPool.play(mSoundRewind, mVolume, mVolume, 1, 0, 1f);
                // ...
                gameFrg.l1= null;
                gameFrg.l2 = null;
                gameFrg.l02= null;
                gameFrg.l3= null;
                gameFrg.l4= null;
                gameFrg.l5= null;
                gameFrg.l6= null;
                gameFrg.l7= null;
                gameFrg.l8= null;
                gameFrg.l9= null;
                gameFrg.l10 = null;
                gameFrg.l101= null;
                gameFrg.l11= null;
                gameFrg.l12= null;
                gameFrg.l13= null;
                gameFrg.l14= null;
                Intent intent = new Intent(getActivity(), GameActivity_wg.class);
                getActivity().startActivity(intent);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputWord = DataHolder.getInstance().getArl();
                String word = "";
                for (String s : inputWord)
                {
                    word += s ;
                }
                if(wordSearch(word)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Correct Word !", Toast.LENGTH_SHORT).show();
                    points = points + word.length();
                    gameFrg.initTileData();

                }
                else
                    Toast.makeText(getActivity().getApplicationContext(), "InCorrect Word !", Toast.LENGTH_SHORT).show();

            }
        });
      /*pause.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            //((GameActivity) getActivity()).restartGame();
         }
      });*/
        return rootView;
    }

    public boolean wordSearch(String key)
    {
        char firstChar = key.charAt(0);
        if (firstChar == 'a' || firstChar == 'A') {
            if (Collections.binarySearch(gameFrg.l1, key) >= 0) {
                return true;
            }
            else return false;
        }
        else if (firstChar == 'b' || firstChar == 'B') {
            if (Collections.binarySearch(gameFrg.l2, key) >= 0) {
                return true;
            }
            else return false;
        }

        else if (firstChar == 'c' || firstChar == 'C' ) {
            if (Collections.binarySearch(gameFrg.l02, key) >= 0)
            {
                return true;
            }
            else return false;
        }

        else if (firstChar == 'D' || firstChar == 'd' || firstChar == 'E' || firstChar == 'e') {
            if (Collections.binarySearch(gameFrg.l3, key) >= 0)
            {
                return true;
            }
            else return false;
        }

        else if (firstChar == 'F' || firstChar == 'f' || firstChar == 'G' || firstChar == 'g' ) {
            if (Collections.binarySearch(gameFrg.l4, key) >= 0)
            {
                return true;
            }
            else return false;
        }
        else if (firstChar == 'H' || firstChar == 'h' || firstChar == 'i' || firstChar == 'I') {
            if (Collections.binarySearch(gameFrg.l5, key) >= 0)
            {
                return true;
            }
            else return false;
        }

        else if (firstChar == 'J' || firstChar == 'j' || firstChar == 'K' || firstChar == 'k') {
            if (Collections.binarySearch(gameFrg.l6, key) >= 0)
            {
                return true;
            }
            else return false;
        }

        else if (firstChar == 'L' || firstChar == 'l' || firstChar == 'm' || firstChar == 'M') {
            if (Collections.binarySearch(gameFrg.l7, key) >= 0)
            {
                return true;
            }
            else return false;
        }

        else if (firstChar == 'N' ||firstChar == 'n'||firstChar == 'O' ||firstChar == 'o') {
            if (Collections.binarySearch(gameFrg.l8, key) >= 0)
            {
                return true;
            }
            else return false;
        }

        else if (firstChar == 'P' || firstChar == 'p' || firstChar == 'Q' ||firstChar == 'q') {
            if (Collections.binarySearch(gameFrg.l9, key) >= 0)
            {
                return true;
            }
            else return false;
        }

        else if (firstChar == 'R' || firstChar == 'r') {
            if (Collections.binarySearch(gameFrg.l10, key) >= 0)
            {
                return true;
            }
            else return false;

        }

        else if (firstChar == 'S' || firstChar == 's' ) {
            if (Collections.binarySearch(gameFrg.l101, key) >= 0)
            {
                return true;
            }
            else return false;
        }
        else if (firstChar == 'T' || firstChar == 't' ) {
            if (Collections.binarySearch(gameFrg.l11, key) >= 0)
            {
                return true;
            }
            else if ( firstChar == 'U' ||firstChar == 'u') {
                if (Collections.binarySearch(gameFrg.l12, key) >= 0)
                {
                    return true;
                }
                else return false;
            }
            else return false;
        }
        else if (firstChar == 'T' || firstChar == 't' || firstChar == 'U' ||firstChar == 'u') {
            if (Collections.binarySearch(gameFrg.l13, key) >= 0)
            {
                return true;
            }
            else return false;
        }
        else if ( firstChar == 'U' ||firstChar == 'u') {
            if (Collections.binarySearch(gameFrg.l14, key) >= 0)
            {
                return true;
            }
            else return false;
        }
        else if ( firstChar == 'V' || firstChar == 'v' || firstChar == 'W' || firstChar == 'w') {
            if (Collections.binarySearch(gameFrg.l14, key) >= 0)
            {
                return true;
            }
            else return false;
        }
        else if ( firstChar == 'X' || firstChar == 'x' || firstChar == 'Y' || firstChar == 'y' || firstChar == 'Z' || firstChar == 'z') {
            if (Collections.binarySearch(gameFrg.l14, key) >= 0)
            {
                return true;
            }
            else return false;
        }


        else return false;

    }
}
