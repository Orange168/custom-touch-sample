package com.example.root.myapplication.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.root.myapplication.aidl.mAIDLActivity;

/**
 * http://blog.csdn.net/column/details/androidcustomview.html
 */
public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private  static final String ITEMS[] = {
            "SimpleCustomView", "Filter Picture View",
            "PorterDuff View","StartActivity 4 Launcher",
            "AIDL"

    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ITEMS) ;
        getListView().setAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0:
                startActivity(new Intent(this, FirstCustomViewActivity.class));
                break ;
            case 1:
                startActivity(new Intent(this, FilterPictureActivity.class));
                break ;
            case 2:
                startActivity(new Intent(this, PorterDuffActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, StartActivityTest.class));
                break;
            case 4:
                startActivity(new Intent(this, mAIDLActivity.class));
                break ;
            default:
                break ;
        }
    }
}
