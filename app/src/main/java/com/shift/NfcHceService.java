package com.shift;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class NfcHceService extends HostApduService {
    private static final String TAG = "NfcHceService";
    private static final byte[] SELECT_AID_APDU = {
            (byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00
    };
    private static final String NDEF_MESSAGE = "readInfo1234";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "NfcHceService created");
    }

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.d(TAG, "Received APDU: " + bytesToHex(commandApdu));

        // Check if the APDU is a SELECT AID command
        if (isSelectAidApdu(commandApdu)) {
            Log.d(TAG, "SELECT AID APDU received");
            return createNdefResponse();
        }

        // Check if the APDU matches the expected NDEF message
        String receivedMessage = new String(commandApdu, StandardCharsets.UTF_8);
        Log.d(TAG, "Received Message: " + receivedMessage);

        if (NDEF_MESSAGE.equals(receivedMessage)) {
            Log.d(TAG, "Received valid request: " + NDEF_MESSAGE);
            return createDummyResponse();
        }

        Log.d(TAG, "Invalid APDU received");
        return new byte[]{(byte) 0x6A, (byte) 0x82}; // Status word for "File not found"
    }

    private boolean isSelectAidApdu(byte[] apdu) {
        if (apdu.length < SELECT_AID_APDU.length) return false;
        return Arrays.equals(Arrays.copyOf(apdu, SELECT_AID_APDU.length), SELECT_AID_APDU);
    }

    private byte[] createDummyResponse() {
        try {
            String dummyMessage = "Hello, World!";
            NdefRecord textRecord = NdefRecord.createTextRecord("en", dummyMessage);
            NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{textRecord});
            return ndefMessage.toByteArray();
        } catch (Exception e) {
            Log.e(TAG, "Error creating NDEF response", e);
            return new byte[]{(byte) 0x6F, (byte) 0x00}; // Status word for "Unknown error"
        }
    }

    private byte[] createNdefResponse() {
        String text = NDEF_MESSAGE;
        byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
        byte[] ndefMessage = new byte[textBytes.length + 5];
        ndefMessage[0] = (byte) 0xD1; // NDEF record header
        ndefMessage[1] = (byte) 0x01; // TNF_WELL_KNOWN, RTD_TEXT
        ndefMessage[2] = (byte) textBytes.length; // Payload length
        ndefMessage[3] = (byte) 0x00; // Status byte (no language code)
        System.arraycopy(textBytes, 0, ndefMessage, 4, textBytes.length);
        ndefMessage[ndefMessage.length - 1] = (byte) 0xFE; // Terminator
        return ndefMessage;
    }

    @Override
    public void onDeactivated(int reason) {
        Log.d(TAG, "HCE deactivated: " + reason);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
