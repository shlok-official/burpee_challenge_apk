package edu.neu.madcourse.shlokdixit1.WordGame.SinglePlayerWordGame;

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
    int Bonus;
    private int bonusPoints;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_score_wd);
        points = Accumulator.getInstance().getPoints();
        TextView points_tv = (TextView) findViewById(R.id.score_stats);
        points_tv.setText("Score: " + Integer.toString(points));
        Bonus = Accumulator.getInstance().getBonusPoints();

        TextView Bonus = (TextView) findViewById(R.id.bonus);
        Bonus.setText("Bonus Points: " + Integer.toString(bonusPoints));
        TextView NetScore = (TextView) findViewById(R.id.bonus);

        NetScore.setText("NET SCORE: " + Integer.toString(bonusPoints) + Integer.toString(points));
        setTitle("GAME STATISTICS");
    }
}
