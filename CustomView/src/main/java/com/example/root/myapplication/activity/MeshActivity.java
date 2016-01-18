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

	private WaveFragment mWaveFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mesh_activity);
		mWaveFragment = new WaveFragment();
		if (getIntent().getBooleanExtra("wave",false)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			getFragmentManager().beginTransaction().replace(R.id.frag, mWaveFragment).commit();
		}else if(getIntent().getBooleanExtra("poly",false)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			Bundle bundle = new Bundle();
			bundle.putBoolean("isPoly",true);
			mWaveFragment.setArguments(bundle);
			getFragmentManager().beginTransaction().replace(R.id.frag, mWaveFragment).commit();
		}else if (getIntent().getBooleanExtra("save",false)){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			Bundle bundle = new Bundle();
			bundle.putBoolean("save",true);
			mWaveFragment.setArguments(bundle);
			getFragmentManager().beginTransaction().replace(R.id.frag, mWaveFragment).commit();
		} else{
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
