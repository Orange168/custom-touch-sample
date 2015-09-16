/*
 * Copyright (c) 2012 Wireless Designs, LLC
 *
 * See the file license.txt for copying permission.
 */
package com.examples.customtouch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.examples.customtouch.widget.CustomOverScroller;

import java.io.IOException;
import java.io.InputStream;

public class CustomOverScrollerScrollActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//        CustomOverScroller customOverScroller = new CustomOverScroller(this,null) ;
        setContentView(R.layout.over_scroller_layout);

	}

}
