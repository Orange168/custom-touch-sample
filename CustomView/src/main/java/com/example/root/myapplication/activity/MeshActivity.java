package com.example.root.myapplication.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.root.myapplication.R;
import com.example.root.myapplication.activity.frag.MeshFragment;
import com.example.root.myapplication.activity.frag.WaveFragment;

/**
 * Created by Edward Lin
 * on 1/2/16 5:19 PM.
 */
public class MeshActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mesh_activity);
		if (getIntent().getBooleanExtra("wave",false)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			getFragmentManager().beginTransaction().replace(R.id.frag,new WaveFragment()).commit();
		}else {
			getFragmentManager().beginTransaction().replace(R.id.frag, new MeshFragment()).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		return super.onMenuItemSelected(featureId, item);
	}
}
