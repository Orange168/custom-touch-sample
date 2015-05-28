package com.example.root.myapplication.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.root.myapplication.R;

/**
 * Created by Edward Lin
 * on 5/25/15 4:58 PM.
 */
public class StartActivityTest extends AppCompatActivity implements View.OnClickListener{
    Button start ;
    private static int index = 0 ;
    private static final String LAG = "StartActivityTest " ;
    private String append ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.start_activity);
        index ++ ;
        append = "\tindex=" + index + "\t";
        start = (Button)findViewById(R.id.start) ;
        start.setOnClickListener(this);
        Log.e(LAG, append + "onCreate") ;
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, StartActivityTest.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(LAG, append+"onCreate") ;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(LAG, append + "onNewIntent") ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(LAG, append + "onResume") ;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(LAG, append + "onPause") ;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(LAG,append + "onRestart") ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(LAG,append + "onStop") ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(LAG,append + "onDestroy") ;
    }


}
