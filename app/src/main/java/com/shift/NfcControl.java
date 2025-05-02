package com.shift;

import android.app.Activity;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.util.Log;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.PendingIntent;

import java.util.Arrays;

public class NfcControl {

    private static final String REQUEST_TAG = "readInfo1234";
    private volatile boolean isConnected = false;
    private final NfcAdapter nfcAdapter;
    private final Context context;

    private NfcControl(Context context, NfcAdapter nfcA) {
        this.nfcAdapter = nfcA;
        this.context = context;
    }

    public static NfcControl create(Context context) {
        NfcAdapter nfcA = NfcAdapter.getDefaultAdapter(context);
        if (nfcA != null) {
            return new NfcControl(context, nfcA);
        }
        Log.e(REQUEST_TAG, "Device does not support NFC");
        return null;
    }

    public boolean isNfcAvailable() {
        return nfcAdapter != null && nfcAdapter.isEnabled();
    }

    public synchronized boolean connectToDevice(Tag t, NdefMessage ndefMessage) {
        if (isConnected) {
            Log.w(REQUEST_TAG, "Another device is already connected");
            return false;
        }

        try {
            if (ndefMessage != null) {
                NdefRecord[] records = ndefMessage.getRecords();
                for (NdefRecord record : records) {
                    if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                            Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                        byte[] payload = record.getPayload();
                        String textEncoding = ((payload[0] & 0x80) == 0) ? "UTF-8" : "UTF-16";
                        int languageCodeLength = payload[0] & 0x3F;
                        String text = new String(payload, languageCodeLength + 1,
                                payload.length - languageCodeLength - 1, textEncoding);
                        Log.d(REQUEST_TAG, "Connected to peer device with message: " + text);
                        if (REQUEST_TAG.equals(text)) {
                            Log.d(REQUEST_TAG, "Peer device identified as " + REQUEST_TAG);
                        }
                    }
                }
            }
            isConnected = true;
            return true;
        } catch (Exception e) {
            Log.e(REQUEST_TAG, "Failed to connect to NFC device: ", e);
            return false;
        }
    }

    public synchronized void disconnect() {
        isConnected = false;
        Log.d(REQUEST_TAG, "Disconnected from NFC device");
    }

    public synchronized boolean scan(Activity activity) {
        if (!isNfcAvailable()) {
            Log.e(REQUEST_TAG, "NFC is not available at the moment.");
            return false;
        }

        if (activity == null) {
            Log.e(REQUEST_TAG, "A foreground activity must be provided for initiating scan");
            return false;
        }

        try {
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    new Intent(context, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                    PendingIntent.FLAG_MUTABLE
            );

            IntentFilter[] intentFilters = new IntentFilter[]{};
            String[][] techLists = new String[][]{};

            nfcAdapter.enableForegroundDispatch(activity, pendingIntent, intentFilters, techLists);
            Log.d(REQUEST_TAG, "NFC scan initiated");
            return true;
        } catch (Exception e) {
            Log.e(REQUEST_TAG, "Failed to initiate NFC scan", e);
            return false;
        }
    }

    public void stopScan(Activity activity) {
        if (nfcAdapter != null && activity != null) {
            nfcAdapter.disableForegroundDispatch(activity);
            Log.d(REQUEST_TAG, "Scan disabled");
        }
    }

    public boolean nfcBackgroundRunning() {
        if (!isNfcAvailable()) {
            Log.e(REQUEST_TAG, "NFC is not available on this device.");
            return false;
        }

        try {
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
