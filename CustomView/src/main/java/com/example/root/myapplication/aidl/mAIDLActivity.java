package com.example.root.myapplication.aidl;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.root.myapplication.R;
import com.example.root.myapplication.forActivity;
import com.example.root.myapplication.forService;

/**
 * Created by Edward Lin
 * on 5/25/15 11:48 PM.
 */
public class mAIDLActivity extends Activity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aidl);


    }

    private forActivity mCallback = new forActivity.Stub() {

        @Override
        public void performAction() throws RemoteException {
            Toast.makeText(mAIDLActivity.this, "this toast is called from service",
                    Toast.LENGTH_LONG).show();
        }
    } ;

    private forService mService ;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = forService.Stub.asInterface(service) ;
            try {
                mService.registerTestCall(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
             mService = null ;
        }
    } ;

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.start:
                Bundle bundle = new Bundle();
                Intent _Intent = new Intent("android.intent.action.AIDLService") ;
                _Intent.putExtras(bundle) ;
                bindService(_Intent, mConnection, Context.BIND_AUTO_CREATE) ;
//                startActivity(_Intent);
                break ;
            case R.id.stop :
                unbindService(mConnection);
                break ;
            case R.id.call_back:
                if (mService != null) {
                    try {
                        mService.invokCallBack();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break ;

        }
    }
}
