package com.custome.brokenview;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BrokenView mBrokenView;
    private Paint whitePaint;
    private BrokenViewListener brokenViewListener;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBrokenView = BrokenView.add2Window(this);
        whitePaint = new Paint();
        whitePaint.setColor(0xffffffff);
        brokenViewListener = new BrokenViewListener.Builder(mBrokenView)
                .setPaint(whitePaint).build();

        imageView = (ImageView) findViewById(R.id.image);
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
                mBrokenView.reset();
                Toast.makeText(MainActivity.this, "reset==>>", Toast.LENGTH_SHORT).show();
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnTouchListener(brokenViewListener);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
