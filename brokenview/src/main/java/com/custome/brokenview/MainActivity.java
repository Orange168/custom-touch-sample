package com.custome.brokenview;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BrokenView mBrokenView;
    private Paint whitePaint;
    private BrokenViewListener brokenViewListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBrokenView = BrokenView.add2Window(this);
        whitePaint = new Paint();
        brokenViewListener = new BrokenViewListener.Builder(mBrokenView)
                .setPaint(whitePaint).build();

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setOnTouchListener(brokenViewListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                Toast.makeText(MainActivity.this, "reset==>>", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
