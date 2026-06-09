package com.example.compscisummative;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void start(View v){
        Spinner spinner = findViewById(R.id.spinner);
        Intent intent;
        String choice = spinner.getSelectedItem().toString(); // get user's choice
        if(choice.equals("Choose Time Signature"))
            Toast.makeText(this, "Please choose an appropriate time signature", Toast.LENGTH_SHORT).show();
        else {
            if (choice.equals("3/4")) { // send to the appropriate intent
                intent = new Intent(Menu.this, ThreeFour.class);
                startActivity(intent);
            } else if(choice.equals("4/4")) {
                intent = new Intent(Menu.this, FourFour.class);
                startActivity(intent);
            }
        }
    }
}