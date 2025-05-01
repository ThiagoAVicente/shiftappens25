package com.shift;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;

public class NfcControl {

    /**
     * Tag sent from requester to target phone
     */
    private final String REQUEST_TAG = "readInfo1234";

    /**
     * Variable that marks if the phone is connected to another phone
     */
    private volatile boolean isConnected = false;

    /**
     * adapter nfc do dispositivo
     */
    private final NfcAdapter nfcAdapter;

    /**
     *
     * @param context interface for the app to interact with the android device
     * @param nfcA device nfc adapter
     */
    private NfcControl(Context context, NfcAdapter nfcA){
        this.nfcAdapter = nfcA;
    }

    /**
     * creates an instance on NfcControl if it can be created
     * @param context interface for the app to interact with the android device
     * @return a new NfcControl created from context OR null if device doesn't support nfc
     */
    public static NfcControl create(Context context){
        NfcAdapter nfcA = NfcAdapter.getDefaultAdapter(context);
        if (nfcA != null){
            return new NfcControl(context,nfcA);
        }

        // device doesn't have nfc
        return null;
    }

    /**
     * checks if nfc is supported and enabled
     * @return true if nfc is supported else false
     */
    public boolean isNfcAvailable(){ return nfcAdapter != null && nfcAdapter.isEnabled();}

    /**
     * tries to connect to a device and ensures only a connection at time
     * @param t detected tag
     * @return true if connection established else false
     */
    public synchronized boolean connectToDevice( Tag t ){

        if ( isConnected ){
            // device is already connected to another device
            Log.w(REQUEST_TAG,"Another device is already connected");
            return false;
        }

        try{

            // TODO
            return true;
        }catch (Exception e){
            Log.e(REQUEST_TAG, "Failed to connect to nfc device");
            return false;
        }

    }

}
