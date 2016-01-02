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
            "PorterDuff View","DisInView","StartActivity 4 Launcher",
            "AIDL","MaskFilter","ECGView","ReflectView","DreamEffectView",
		    "ShaderView","MatrixView","AnimList"
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
                startActivity(new Intent(this, DisInViewActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, StartActivityTest.class));
                break;
            case 5:
                startActivity(new Intent(this, mAIDLActivity.class));
                break ;
	        case 6:
		        startActivity(new Intent(this,MaskFilterActivity.class));
		        break;
	        case 7:
		        startActivity(new Intent(this,ECGViewActivity.class));
		        break;
	        case 8:
		        startActivity(new Intent(this,ReflectViewActivity.class));
		        break;
	        case 9:
		        startActivity(new Intent(this,DreamEffectViewActivity.class));
		        break;
	        case 10:
		        startActivity(new Intent(this,ShaderViewActivity.class));
		        break;
	        case 11:
		        startActivity(new Intent(this,MatrixViewActivity.class));
		        break;
	        case 12:
		        startActivity(new Intent(this,AnimListActivity.class));
		        break;
            default:
                break ;
        }
    }
}
