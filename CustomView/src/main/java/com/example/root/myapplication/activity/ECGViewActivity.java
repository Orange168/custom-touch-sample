package com.example.root.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.root.myapplication.R;

/**
 * Created by Edward Lin
 * on 12/30/15 8:37 AM.
 */
public class ECGViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ecg_view_layout);
	}
}
