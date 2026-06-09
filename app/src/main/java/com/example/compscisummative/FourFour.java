// please read the ThreeFour.java comments, code and comments is pretty much identical
package com.example.compscisummative;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import java.util.concurrent.TimeUnit;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FourFour extends AppCompatActivity {

    private Button startButton, stopButton;
    private EditText BPM;
    private volatile boolean isRunning;
    private Thread continuousThread;
    private boolean[][] status = new boolean[4][4];
    private int[][] ids =  {{R.id.B00,R.id.B01,R.id.B02,R.id.B03},
                            {R.id.B10,R.id.B11,R.id.B12,R.id.B13},
                            {R.id.B20,R.id.B21,R.id.B22,R.id.B23},
                            {R.id.B30,R.id.B31,R.id.B32,R.id.B33}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four_four);

        startButton = findViewById(R.id.BStart);
        startButton.setBackgroundColor(Color.RED);

        stopButton = findViewById(R.id.BStop);
        stopButton.setBackgroundColor(Color.BLACK);


        BPM = findViewById(R.id.BIn);

        status = new boolean[4][4];
        for(int i[]:ids){
            for(int j:i){
                findViewById(j).setBackgroundColor(Color.BLACK);
            }
        }
    }
    public void start(View v){
        if (continuousThread != null && continuousThread.isAlive()) {
            Toast.makeText(this, "Process already running", Toast.LENGTH_SHORT).show();
            return;
        }

        MediaPlayer bass = MediaPlayer.create(FourFour.this,R.raw.bass),
                    hihat = MediaPlayer.create(FourFour.this,R.raw.hihat),
                    snare = MediaPlayer.create(FourFour.this,R.raw.snare),
                    tom = MediaPlayer.create(FourFour.this,R.raw.tom);
        int BPM;
        if(this.BPM.getText().toString().matches("")||Integer.parseInt(this.BPM.getText().toString())<=0)
            Toast.makeText(this, "Please choose an appropriate BPM", Toast.LENGTH_SHORT).show();
        else{
            BPM = Integer.parseInt(this.BPM.getText().toString());
            isRunning = true;
            continuousThread = new Thread(new Runnable() {
                @Override
                public void run() {
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
                        i=(i==3)?0:i+1;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FourFour.this, "Running...", Toast.LENGTH_SHORT).show();
                            }
                        });

                        try {
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
    public void stopContinuousProcess(View view) {
        isRunning = false;
        if (continuousThread != null) {
            continuousThread.interrupt();
            continuousThread = null;
        }
        Toast.makeText(this, "Process stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
        if (continuousThread != null) {
            continuousThread.interrupt();
        }
    }
    public void feedback(View v){
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