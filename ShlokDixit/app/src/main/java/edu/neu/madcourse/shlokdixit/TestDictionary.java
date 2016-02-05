package edu.neu.madcourse.shlokdixit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;



/**
 * Created by shlokdixit on 03/02/16.
 */
public class TestDictionary extends AppCompatActivity {

    ArrayList<String> l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14;
    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   setContentView(R.layout.dictionary);
        listview = (ListView)findViewById(R.id.listview);
        editText=(EditText) findViewById(R.id.txtsearch);
        initList();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().equals("")){
                    //reset LV
                    initList();
                }
                else {


                    searchitem(s.toString());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void searchitem(String textTosearch){

for(String item:items){

    if(item.contains(textTosearch)){

        listitems.remove(item);
    }
}
adapter.notifyDataSetChanged();
    }
    public void initList(){

        items = new String[]{"canada","cannibal","cartoon"};
        listitems=new ArrayList<>(Arrays.asList(items));
        adapter=new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtitem,listitems);
        listview.setAdapter(adapter);
    }*/
        this.loadData();


        final EditText listview_search = (EditText) findViewById(R.id.dict_list);
        listview_search.addTextChangedListener(new TextWatcher() {


            TextView text_view = (TextView) findViewById(R.id.);
            String key;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                   //
                    if (s.length() > 2) {
                        if (firstchar == 'A' || firstchar == 'a') {

                            if (Collections.binarySearch(l1, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'B' || firstchar == 'b' || firstchar == 'C' || firstchar == 'c') {

                            if (Collections.binarySearch(l2, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'D' || firstchar == 'd' || firstchar == 'E' || firstchar == 'e') {

                            if (Collections.binarySearch(l3, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'F' || firstchar == 'f' || firstchar == 'G' || firstchar == 'g') {

                            if (Collections.binarySearch(l4, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'H' || firstchar == 'h' || firstchar == 'I' || firstchar == 'i') {

                            if (Collections.binarySearch(l5, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'J' || firstchar == 'j' || firstchar == 'K' || firstchar == 'k') {

                            if (Collections.binarySearch(l6, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'L' || firstchar == 'l' || firstchar == 'M' || firstchar == 'm') {

                            if (Collections.binarySearch(l7, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'N' || firstchar == 'n' || firstchar == 'O' || firstchar == 'o') {

                            if (Collections.binarySearch(l8, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'P' || firstchar == 'p' || firstchar == 'Q' || firstchar == 'q') {

                            if (Collections.binarySearch(l9, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'R' || firstchar == 'r' || firstchar == 'S' || firstchar == 's') {

                            if (Collections.binarySearch(l10, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'T' || firstchar == 't') {

                            if (Collections.binarySearch(l11, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'U' || firstchar == 'u') {

                            if (Collections.binarySearch(l12, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'V' || firstchar == 'v' || firstchar == 'W' || firstchar == 'w') {

                            if (Collections.binarySearch(l13, key) >= 0)
                                text_view.setText(key);
                        } else if (firstchar == 'X' || firstchar == 'x' || firstchar == 'Y' || firstchar == 'y' || firstchar == 'Z' || firstchar == 'z') {

                            if (Collections.binarySearch(l14, key) >= 0)
                                text_view.setText(key);
                        }

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void loadData() {
        try {

            String data;
            InputStream ins;
            BufferedReader reader;
            this.l1 = new ArrayList<String>();
            this.l2 = new ArrayList<String>();
            this.l3 = new ArrayList<String>();
            this.l4 = new ArrayList<String>();
            this.l5 = new ArrayList<String>();
            this.l6 = new ArrayList<String>();
            this.l7 = new ArrayList<String>();
            this.l8 = new ArrayList<String>();
            this.l9 = new ArrayList<String>();
            this.l10 = new ArrayList<String>();
            this.l11 = new ArrayList<String>();
            this.l12 = new ArrayList<String>();
            this.l13 = new ArrayList<String>();
            this.l14 = new ArrayList<String>();

            ins = getResources().openRawResource(R.raw.aA_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l1.add(data);
            }
            ins = getResources().openRawResource(R.raw.bB_Cc_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l2.add(data);
            }
            ins = getResources().openRawResource(R.raw.dD_Ee_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l3.add(data);
            }
            ins = getResources().openRawResource(R.raw.fF_Gg_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l4.add(data);
            }
            ins = getResources().openRawResource(R.raw.hH_Ii_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l5.add(data);
            }
            ins = getResources().openRawResource(R.raw.jJ_Kk_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l6.add(data);
            } ins = getResources().openRawResource(R.raw.lL_Mm_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l7.add(data);
            }
            ins = getResources().openRawResource(R.raw.nN_Oo_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l8.add(data);
            }
            ins = getResources().openRawResource(R.raw.pP_Qq_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l9.add(data);
            }
            ins = getResources().openRawResource(R.raw.rR_Ss_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l10.add(data);
            }
            ins = getResources().openRawResource(R.raw.tT_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l11.add(data);
            }
            ins = getResources().openRawResource(R.raw.uU_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l12.add(data);
            }
            ins = getResources().openRawResource(R.raw.uU_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l3.add(data);
            }
            ins = getResources().openRawResource(R.raw.vV_Ww_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l14.add(data);
            }
            ins = getResources().openRawResource(R.raw.xX_Yy_Zz_list);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((data = reader.readLine()) != null) {
                l14.add(data);

        }



    }
        catch (IOException e) {
            e.printStackTrace();
        }
}}


