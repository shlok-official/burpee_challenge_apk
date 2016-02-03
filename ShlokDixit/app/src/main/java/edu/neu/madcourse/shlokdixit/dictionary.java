package edu.neu.madcourse.shlokdixit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by shlokdixit on 03/02/16.
 */
public class dictionary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary);
        setTitle("Shlok Dixit");


        Button return_to_menu = (Button) findViewById(R.id.return_to_menu);
        return_to_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        Button acknowledgements = (Button) findViewById(R.id.acknowledgements);
        acknowledgements.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });




    }
}
