package com.shift;

import android.app.Activity;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.PendingIntent;

public class NfcControl {

    /**
     * Tag sent from requester to target phone
     */
    private static final String REQUEST_TAG = "readInfo1234";

    /**
     * Variable that marks if the phone is connected to another phone
     */
    private volatile boolean isConnected = false;

    /**
     * device nfc adapter
     */
    private final NfcAdapter nfcAdapter;

    /**
     * interface for the app to interact with the android device
     */
    private final Context context;

    /**
     *
     * @param context interface for the app to interact with the android device
     * @param nfcA device nfc adapter
     */
    private NfcControl(Context context, NfcAdapter nfcA){
        this.nfcAdapter = nfcA;
        this.context = context;
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
        Log.e(REQUEST_TAG,"Device does not support NFC");
        return null;
    }

    /**
     * checks if nfc is supported and enabled
     * @return true if nfc is supported else false
     */
    public boolean isNfcAvailable(){
        return nfcAdapter != null && nfcAdapter.isEnabled();
    }

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
            Log.d(REQUEST_TAG, "connected");
            // TODO
            isConnected = true;
            return true;
        }catch (Exception e){
            Log.e(REQUEST_TAG, "Failed to connect to nfc device: ",e);
            return false;
        }

    }

    /**
     * Disconnects from current nfc device
     */
    public synchronized void disconnect(){
        isConnected = false;
        Log.d(REQUEST_TAG,"Disconnect from nfc device");
    }



    /**
     * @param activity The foreground activity initiating the scan
     * @return true if scan was initiated else false
     * */
    public synchronized boolean scan(Activity activity){
        if ( !isNfcAvailable() ){
            Log.e(REQUEST_TAG,"NFC is not available at the moment.");
            return false;
        }

        if ( activity == null ){
            Log.e(REQUEST_TAG, "A foreground activity must be provided for initiating scan");
            return false;
        }

        try {
            // Create a PendingIntent to handle NFC events
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    new Intent(context, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                    PendingIntent.FLAG_MUTABLE
            );

            // Set up an intent filter for NFC tags
            IntentFilter[] intentFilters = new IntentFilter[]{};
            String[][] techLists = new String[][]{};

            // Enable foreground dispatch for the activity
            nfcAdapter.enableForegroundDispatch(activity, pendingIntent, intentFilters, techLists);
            Log.d(REQUEST_TAG, "NFC scan initiated");
            return true;
        } catch (Exception e) {
            Log.e(REQUEST_TAG, "Failed to initiate NFC scan", e);
            return false;
        }


    }

    /**
     * @param activity foreground app
     */
    public void stopScan(Activity activity){
        if ( nfcAdapter != null && activity != null ){
            nfcAdapter.disableForegroundDispatch(activity);
            Log.d(REQUEST_TAG, "Scan disabled");
        }
    }

    /**
     * Starts background nfc service
     * @return true if background NFC service is running, else false
     */
    public boolean nfcBackgroundRunning(){
        if (!isNfcAvailable()) {
            Log.e(REQUEST_TAG, "NFC is not available on this device.");
            return false;
        }

        try {
            // Check if NFC is enabled and available
            if (nfcAdapter.isEnabled()) {
                Log.d(REQUEST_TAG, "NFC background service is running");
                return true;
            } else {
                Log.e(REQUEST_TAG, "NFC is disabled. Please enable it in settings.");
                return false;
            }
        } catch (Exception e) {
            Log.e(REQUEST_TAG, "Error checking NFC background service", e);
            return false;
        }
    }

}
