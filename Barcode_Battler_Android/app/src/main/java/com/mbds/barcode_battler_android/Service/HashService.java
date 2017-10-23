package com.mbds.barcode_battler_android.Service;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by Karl on 23/10/2017.
 */

public class HashService {

    public static String hash(String value) {

        String sha1 = "";

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(value.getBytes("utf8"));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("SHA1", value + " --> " + sha1);

        return sha1;
    }
}
