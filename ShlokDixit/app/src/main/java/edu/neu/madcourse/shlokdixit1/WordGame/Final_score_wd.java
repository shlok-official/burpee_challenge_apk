package edu.neu.madcourse.shlokdixit1.WordGame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.neu.madcourse.shlokdixit1.R;

/**
 * Created by shlokdixit on 23/02/16.
 */
public class Final_score_wd extends AppCompatActivity {

    private int points;
    private int bonusPoints;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_score_wd);
        points = Accumulator.getInstance().getPoints();
        TextView points_tv = (TextView) findViewById(R.id.score_stats);
        points_tv.setText("Score: " + Integer.toString(points));
        setTitle("GAME STATISTICS");
    }
}
