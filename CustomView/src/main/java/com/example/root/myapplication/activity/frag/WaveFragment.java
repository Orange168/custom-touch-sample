package com.example.root.myapplication.activity.frag;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.root.myapplication.R;
import com.example.root.myapplication.view.WaveView;

/**
 * Created by Edward Lin
 * on 1/4/16 10:20 PM.
 */
public class WaveFragment extends Fragment {

	private LinearLayout layout;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.frag_wave, container, false);
		layout = (LinearLayout) view.findViewById(R.id.container);
		WaveView mWaveView = new WaveView(getActivity());
		layout.addView(mWaveView);
		return view;
	}


}
