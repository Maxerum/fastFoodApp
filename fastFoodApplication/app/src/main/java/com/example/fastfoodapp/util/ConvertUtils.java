package com.example.fastfoodapp.util;

import android.util.Log;

public class ConvertUtils {

    private static final String TAG = "ConvertUtils";

    public static int extractAmount(String amount) {
        String amountDimensionless = amount.substring(0, amount.length() - 2);
        String commaReplaced = amountDimensionless.replaceAll(",", ".");
        double amountDouble = 0;
        try {
            amountDouble = Double.parseDouble(commaReplaced);
        } catch (NumberFormatException e) {
            Log.d(TAG, "Failed to parse string " + e);
        }
        int result = (int) (amountDouble * 100);
        return result;
    }
}
