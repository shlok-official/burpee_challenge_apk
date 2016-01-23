package edu.neu.madcourse.shlokdixit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {

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
        String about;
        about = ((Button) view).getText().toString();

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);


    }

}
