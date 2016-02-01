package com.example.root.myapplication.activity.frag;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.myapplication.R;

/**
 * Created by Edward Lin
 * on 2/1/16 10:40 AM.
 */
public class MainDialogFrag extends DialogFragment {

    private int args = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            args = getArguments().getInt("args", 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = null ;
        switch (args) {
            case 0:
                view = inflater.inflate(R.layout.layout_scrollview, container, false);
                break;
            case 1:
                break;
        }

        return view;
    }
}
