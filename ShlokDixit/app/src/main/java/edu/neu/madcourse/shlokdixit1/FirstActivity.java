package edu.neu.madcourse.shlokdixit1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.shlokdixit1.Dictionary.TestDictionary;
import edu.neu.madcourse.shlokdixit1.TrickestPart.TrickestPart;
import edu.neu.madcourse.shlokdixit1.WordGame.SinglePlayerWordGame.Parent_Controls;
import edu.neu.madcourse.shlokdixit1.TicTacToe.MainActivity;


public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        setTitle("Shlok Dixit");


        Button exit_button = (Button) findViewById(R.id.exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


//        Button Gen_error = (Button) findViewById(R.id.Gen_Err);
//        Gen_error.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                int a= 1/0;
//
//            }
//        });

    }
    public void ABOUT(View view) {

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);


    }
    public void dict(View view) {

        Intent intent = new Intent(this, TestDictionary.class);
        startActivity(intent);


    }
    public void uttt(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
//    public void wordgame(View view) {
//
//        Intent intent = new Intent(this, Parent_Controls.class);
//        startActivity(intent);
//
//
//    }
//    public void communication(View view) {
//
//        Intent intent = new Intent(this, TrickestPart.class);
//        startActivity(intent);
//
//
//    }
}
