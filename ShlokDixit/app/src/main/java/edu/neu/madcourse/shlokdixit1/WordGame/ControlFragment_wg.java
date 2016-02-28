
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import edu.neu.madcourse.shlokdixit1.R;

public class ControlFragment_wg extends Fragment {

    ArrayList<String> inputWord;
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
    String data;
    InputStream ins;
    BufferedReader reader;
    GameFragment_wg gameFrg = Accumulator.getInstance().getControlObj();
    int points;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_control_wg, container, false);
        View main = rootView.findViewById(R.id.button_main);
        View restart = rootView.findViewById(R.id.restart_wg);
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
                inputWord = Accumulator.getInstance().getArl();
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
            }
            else return false;
        }


        else if (firstChar == 'b' || firstChar == 'B') {
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
            }
            else return false;
        }

        else if (firstChar == 'c' || firstChar == 'C' ) {
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
            }
            else return false;
        }

        else if (firstChar == 'D' || firstChar == 'd' || firstChar == 'E' || firstChar == 'e') {
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
            }
            else return false;
        }

        else if (firstChar == 'F' || firstChar == 'f' || firstChar == 'G' || firstChar == 'g' ) {
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
            }else return false;

        }
        else if (firstChar == 'H' || firstChar == 'h' || firstChar == 'i' || firstChar == 'I') {
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
            }else return false;
        }

        else if (firstChar == 'J' || firstChar == 'j' || firstChar == 'K' || firstChar == 'k') {
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
            }else return false;
        }

        else if (firstChar == 'L' || firstChar == 'l' || firstChar == 'm' || firstChar == 'M') {
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
            }else return false;
            }

        else if (firstChar == 'N' ||firstChar == 'n'||firstChar == 'O' ||firstChar == 'o') {
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
            }else return false;
        }

        else if (firstChar == 'P' || firstChar == 'p' || firstChar == 'Q' ||firstChar == 'q') {
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
            }else return false;
        }

        else if (firstChar == 'R' || firstChar == 'r') {
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
            }else return false;
        }

        else if (firstChar == 'S' || firstChar == 's' ) {
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
            }else return false;
        }
        else if (firstChar == 'T' || firstChar == 't' ) {
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
            }else return false;
        }
        else if ( firstChar == 'U' ||firstChar == 'u') {
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
            }else return false;
        }

        else if ( firstChar == 'V' || firstChar == 'v' || firstChar == 'W' || firstChar == 'w') {
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
            }else return false;
        }
        else if ( firstChar == 'X' || firstChar == 'x' || firstChar == 'Y' || firstChar == 'y' || firstChar == 'Z' || firstChar == 'z') {
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
            }else return false;
        }


        else return false;

    }
}
