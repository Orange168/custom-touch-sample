package com.example.root.myapplication;

import android.app.Application;

import com.github.mmin18.layoutcast.LayoutCast;

/**
 * Created by Edward Lin
 * on 9/17/15 7:10 AM.
 */
public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		LayoutCast.init(this);
	}
}
