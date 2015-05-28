// forActivity.aidl
package com.example.root.myapplication;
import  com.example.root.myapplication.forActivity;
// Declare any non-default types here with import statements

interface forService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void registerTestCall(forActivity cb);
    void invokCallBack();
}
