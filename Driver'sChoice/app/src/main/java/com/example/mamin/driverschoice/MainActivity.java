package com.example.mamin.driverschoice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
public Button but1;
    public void init(){
        but1 = (Button)findViewById(R.id.but1);
       but1.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               Intent scr1 = new Intent(MainActivity.this,ActivitySecond.class );
               startActivity(scr1);
           }
       });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}
