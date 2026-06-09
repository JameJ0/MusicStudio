package com.example.compscisummative;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class ThreeFour extends AppCompatActivity {

    private Button startButton, stopButton;
    private EditText BPM;
    private Thread continuousThread;
    private volatile boolean isRunning;
    private boolean[][] status = new boolean[4][3];
    private int[][] ids =  {{R.id.b00,R.id.b01,R.id.b02},
                            {R.id.b10,R.id.b11,R.id.b12},
                            {R.id.b20,R.id.b21,R.id.b22},
                            {R.id.b30,R.id.b31,R.id.b32}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three_four);

        // setup for colours and buttons
        stopButton = findViewById(R.id.bStop);
        stopButton.setBackgroundColor(Color.BLACK);

        startButton = findViewById(R.id.bStart);
        startButton.setBackgroundColor(Color.RED);

        BPM = findViewById(R.id.bIn);

        status = new boolean[4][3];
        for(int i[]:ids){
            for(int j:i){
                findViewById(j).setBackgroundColor(Color.BLACK);
            }
        }
    }

    public void start(View v){ // depending on what buttons user presses, it will loop those sounds on repeat depending on BPM
        if (continuousThread != null && continuousThread.isAlive()) {
            Toast.makeText(this, "Process already running", Toast.LENGTH_SHORT).show();
            return;
        }

        MediaPlayer bass = MediaPlayer.create(ThreeFour.this,R.raw.bass),
                hihat = MediaPlayer.create(ThreeFour.this,R.raw.hihat),
                snare = MediaPlayer.create(ThreeFour.this,R.raw.snare),
                tom = MediaPlayer.create(ThreeFour.this,R.raw.tom);
        int BPM;
        if(this.BPM.getText().toString().matches("")||Integer.parseInt(this.BPM.getText().toString())<=0)
            Toast.makeText(this, "Please choose an appropriate BPM", Toast.LENGTH_SHORT).show();
        else{
            BPM = Integer.parseInt(this.BPM.getText().toString());
            isRunning = true;
            continuousThread = new Thread(new Runnable() {
                @Override
                public void run() { // the thing I want to run on repeat
                    int i=0;
                    while (isRunning) {
                        if(status[0][i])
                            hihat.start();
                        if(status[1][i])
                            snare.start();
                        if(status[2][i])
                            tom.start();
                        if(status[3][i])
                            bass.start();
                        i=(i==2)?0:i+1;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ThreeFour.this, "Running...", Toast.LENGTH_SHORT).show();
                            }
                        });

                        try { // the delay
                            Thread.sleep(30000/BPM-3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            continuousThread.start();
        }
    }

    public void stopContinuousProcess(View view) { // onClick for the stop button
        isRunning = false;
        if (continuousThread != null) {
            continuousThread.interrupt();
            continuousThread = null;
        }
        Toast.makeText(this, "Process stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() { // overrides the default onDestroy so that our threat doesn't leak memory
        super.onDestroy();
        isRunning = false;
        if (continuousThread != null) {
            continuousThread.interrupt();
        }
    }
    public void feedback(View v){ // changes the button's color if clicked
        int buttonId = v.getId();
        for(int i=0; i<ids.length; i++){
            for(int j=0; j<ids[0].length; j++){
                if(ids[i][j]==buttonId){
                    status[i][j]=!status[i][j];
                    if(status[i][j])
                        findViewById(buttonId).setBackgroundColor(Color.RED);
                    else
                        findViewById(buttonId).setBackgroundColor(Color.BLACK);
                }
            }
        }
    }

}