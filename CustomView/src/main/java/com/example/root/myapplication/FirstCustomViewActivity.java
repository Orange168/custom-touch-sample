package com.example.root.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;


public class FirstCustomViewActivity extends AppCompatActivity {
    LinearLayout rootLayout =  null ;

    static CustomView mCustomView ;
    static int radius ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout = (LinearLayout)findViewById(R.id.main_container) ;
//        mCustomView = (CustomView)findViewById(R.id.custom_view) ;
//
//        new Thread(mCustomView).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//
//                        radius = radius < 200 ? (radius +10) : 0 ;
//                        mHandler.obtainMessage().sendToTarget();
//                        Thread.sleep(40);
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start() ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new MyHandler(this) ;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify landscape_mountain parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    static class MyHandler extends Handler {
        WeakReference<Activity> mActivity;

        MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            mCustomView.setRadius(radius);

        }
    }
}
