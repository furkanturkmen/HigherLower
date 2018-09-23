package com.hva.m2mobi.higherlower;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Score> adapterScores;
    private ArrayList arrayList;
    private TextView currentScoreTV;
    private TextView highScoreTV;
    private ArrayAdapter mAdapter;
    private ListView scoreList;
    private int random;
    private FloatingActionButton buttonUp;
    private FloatingActionButton buttonDown;
    private int highScore;
    private int hits=0;
    private ImageView randomImg;
    public static final Random RANDOM = new Random();
    private int highHits;
    private Score prevScore;
    private Score currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initalize the variables
        scoreList = findViewById(R.id.scoreList);
        currentScoreTV = findViewById(R.id.currentScoreTV);
        highScoreTV = findViewById(R.id.highScoreTV);

        buttonUp = findViewById(R.id.buttonUp);
        buttonDown = findViewById(R.id.buttonDown);

        adapterScores = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adapterScores);
        scoreList.setAdapter(mAdapter);

        random = new Random().nextInt(6) + 1;
        highScore = 0;
        randomImg = findViewById(R.id.diceImages);
        // Random on start
        prevScore = new Score(0);


        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentScore = new Score(randomValues());

                mAdapter.notifyDataSetChanged();
                adapterScores.add(currentScore);

                if(currentScore.getScore() < prevScore.getScore()){

                    hits++;
                    currentScoreTV.setText("Current score: " + hits);
                    if (hits > highHits) {
                        highHits = hits;
                        highScoreTV.setText("HighScore: " + highHits);
                    }
                }else if (currentScore.getScore() != prevScore.getScore()){
                    hits = 0;
                    showSnackbar(v, "You lose!");
                    currentScoreTV.setText("Current score: " + hits);
                    adapterScores.clear();

                }
                Log.i("getScore", "is: " + prevScore.getScore() + "-" + currentScore.getScore());
                prevScore = currentScore;
//                random = new Random().nextInt(6 - 1) + 1;
                updatedListToBottom();
                updateDice(randomImg, currentScore.getScore());

            }
        });


        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentScore = new Score(randomValues());
                mAdapter.notifyDataSetChanged();
                adapterScores.add(currentScore);

                if(currentScore.getScore() > prevScore.getScore()){
                    hits++;
                    currentScoreTV.setText("Current score: " + hits);
                    if (hits > highHits) {
                        highHits = hits;
                        highScoreTV.setText("HighScore: " + highHits);
                    }
                }else if (currentScore.getScore() != prevScore.getScore()){
                    hits = 0;
                    showSnackbar(v, "You lose!");
                    currentScoreTV.setText("Current score: " + hits);
                    adapterScores.clear();
                }
                Log.i("getScore", "is: " + prevScore.getScore() + "-" + currentScore.getScore());
                prevScore = currentScore;
                // Randomize image
//                random = new Random().nextInt(6 -1) + 1;
                updateDice(randomImg, currentScore.getScore());

                // Stick to bottom in list
                updatedListToBottom();


            }
        });

    }

    private void showSnackbar(View v, String s) {
        Snackbar.make(v, s, Snackbar.LENGTH_SHORT).show();
    }

    public static int randomValues(){
        return RANDOM.nextInt(6 - 1) + 1;
    }

    public void updateDice(ImageView img, int randomImg){
        int res = getResources().getIdentifier("d"+ randomImg, "drawable", "com.hva.m2mobi.higherlower");
        img.setImageResource(res);
    }

    private void updatedListToBottom() {
        scoreList.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                scoreList.setSelection(mAdapter.getCount() - 1);
            }
        });
    }
}
