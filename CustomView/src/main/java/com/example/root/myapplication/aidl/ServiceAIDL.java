package com.example.root.myapplication.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.root.myapplication.forActivity;
import com.example.root.myapplication.forService;

/**
 * Created by Edward Lin
 * on 5/25/15 11:49 PM.
 */
public class ServiceAIDL extends Service {

    private static final String TAG = "ServiceAIDL" ;
    private forActivity callBack ;
    private void  Log(String log){
        Log.i(TAG, log) ;
    }

    @Override
    public void onCreate() {
        Log("on Create") ;
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log("on Start Command") ;
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log("on Bind");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        Log("on Destroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log("onRebind");
        super.onRebind(intent);
    }

    private  final forService.Stub mBinder = new forService.Stub() {
        @Override
        public void registerTestCall(forActivity cb) throws RemoteException {
            callBack = cb ;
        }

        @Override
        public void invokCallBack() throws RemoteException {
            callBack.performAction();
        }
    } ;
}
