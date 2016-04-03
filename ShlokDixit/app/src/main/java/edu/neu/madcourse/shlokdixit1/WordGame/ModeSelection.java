package edu.neu.madcourse.shlokdixit1.WordGame;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import edu.neu.madcourse.shlokdixit1.R;
import edu.neu.madcourse.shlokdixit1.WordGame.SinglePlayerWordGame.Phase_I_wg;
import edu.neu.madcourse.shlokdixit1.WordGame.TwoPlayerWordGame.Phase_I_wg_2Player;

public class ModeSelection extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_selection);
        Button singleplay = (Button)findViewById(R.id.singlePlayerMain);
        Button multiplay = (Button)findViewById(R.id.twoPlayerMain);

        singleplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeSelection.this, Phase_I_wg.class);
                startActivity(intent);
            }
        });

        multiplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeSelection.this, Phase_I_wg_2Player.class);
                startActivity(intent);
            }
        });
    }

}
