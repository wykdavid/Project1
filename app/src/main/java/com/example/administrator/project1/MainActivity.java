package com.example.administrator.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView view=new GameView(this);
        setContentView(view);
    }
}
