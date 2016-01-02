package com.example.root.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.root.myapplication.R;
import com.example.root.myapplication.view.AnimListView;

/**
 * Created by Edward Lin
 * on 12/28/15 10:07 PM.
 */
public class AnimListActivity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animlist_layout);

		AnimListView animListView = (AnimListView) findViewById(R.id.main_alv);
		animListView.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_anim_list, null);
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public int getCount() {
				return 100;
			}
		});
	}
}
