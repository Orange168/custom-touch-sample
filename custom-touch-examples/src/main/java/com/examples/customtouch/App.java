package com.examples.customtouch;

import android.app.Application;

import com.github.mmin18.layoutcast.LayoutCast;

/**
 * Created by Edward Lin
 * on 11/5/15 12:07 AM.
 */
public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		LayoutCast.init(getApplicationContext());
	}
}
