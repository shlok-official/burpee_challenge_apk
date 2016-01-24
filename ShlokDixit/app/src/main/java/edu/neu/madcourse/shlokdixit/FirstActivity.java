package edu.neu.madcourse.shlokdixit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.shlokdixit.TicTacToe.MainActivity;

public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_first);




        setTitle("Shlok Dixit");
        Button exit_button = (Button) findViewById(R.id.exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });




    }
    public void ABOUT(View view) {

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);


    }

    public void uttt(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
}
