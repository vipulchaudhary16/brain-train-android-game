package com.veercreation.braingame;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button startBtn, button0, button1, button2, button3, playAgainButton;
    TextView sumTextView, timerText, scoreTextView;
    CountDownTimer countDownTimer;
    MediaPlayer correctSound, wrongSound, gameOverSound;
    int a, b, score, numberOfTryPassed = 0;
    ArrayList<Integer> answerList;
    Random rand = new Random();
    int locationForCorrectAnswer;
    androidx.gridlayout.widget.GridLayout optionGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sumTextView = findViewById((R.id.sumText));
        timerText = findViewById(R.id.timerText);
        scoreTextView = findViewById(R.id.scoreText);
        startBtn = findViewById(R.id.goButton);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        playAgainButton = findViewById(R.id.playAgainButton);
        optionGridLayout = findViewById(R.id.optionGridLayout);

        correctSound = MediaPlayer.create(getApplicationContext(), R.raw.success);
        wrongSound = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
        gameOverSound = MediaPlayer.create(getApplicationContext(), R.raw.gameover);

        timerText.setVisibility(View.INVISIBLE);
        sumTextView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
        optionGridLayout.setVisibility(View.INVISIBLE);
    }

    public void startGame(View view) {
        timerText.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        optionGridLayout.setVisibility(View.VISIBLE);
        startBtn.setVisibility(View.INVISIBLE);
        playAgain(scoreTextView);
    }

    public void playAgain(View view) {
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        score = 0;
        scoreTextView.setText("0/0");
        timerText.setText("30s");
        generateSum();
        countDownTimer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long l) {
                int remainingTime = (int) l / 1000;
                String remainingTimeString = Integer.toString(remainingTime) + "s";
                if (remainingTime < 10)
                    remainingTimeString = "0" + remainingTimeString;
                timerText.setText(remainingTimeString);
            }

            @Override
            public void onFinish() {
                gameOverSound.start();
                button0.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                sumTextView.setVisibility(View.INVISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
                playAgainButton.animate().translationY(1000).setDuration(1000);
            }
        }.start();
    }

    public void generateSum() {
        answerList = new ArrayList<Integer>();
        a = rand.nextInt(21);//make correctText invisible
        b = rand.nextInt(21);
        sumTextView.setText(Integer.toString(a) + "+" + Integer.toString(b));
        locationForCorrectAnswer = rand.nextInt(4);
        for (int i = 0; i < 4; i++) {
            if (i == locationForCorrectAnswer) {
                answerList.add(a + b);
            } else {
                int wrongAnswer = rand.nextInt(41);
                while (wrongAnswer == a + b) {
                    wrongAnswer = rand.nextInt(41);
                }
                answerList.add(wrongAnswer);
            }
        }
        button0.setText(Integer.toString(answerList.get(0)));
        button1.setText(Integer.toString(answerList.get(1)));
        button2.setText(Integer.toString(answerList.get(2)));
        button3.setText(Integer.toString(answerList.get(3)));
    }

    public void chooseAnswer(View view) {
        if (Integer.toString(locationForCorrectAnswer).equals(view.getTag().toString())) {
            correctSound.start();
            score++;
            generateSum();
        } else if (!(Integer.toString(locationForCorrectAnswer).equals(view.getTag().toString()))) {
            wrongSound.start();
        }
        numberOfTryPassed++;
        scoreTextView.setText(Integer.toString(score) + "/" + numberOfTryPassed);
    }


}