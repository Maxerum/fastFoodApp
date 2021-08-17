package com.example.fastfoodapp.util;

import android.util.Log;

import java.util.Iterator;
import java.util.Map;

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

    public static String convertMapToString(Map<String, Integer> map) {
        StringBuilder builder = new StringBuilder();

        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String itemName = it.next();
            builder.append(itemName)
                    .append(" x")
                    .append(map.get(itemName));

            if (it.hasNext()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}
