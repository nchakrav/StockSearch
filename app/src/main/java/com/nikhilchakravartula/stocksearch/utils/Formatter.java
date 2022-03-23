package com.nikhilchakravartula.stocksearch.utils;
import java.text.DecimalFormat;

public class Formatter {
    public static String format(double val) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(val);
    }

    public static String format(double val, int decimalPlaces) {
        String prefix = "#,##0";
        String suffix = "";
        while (decimalPlaces != 0) {
            suffix += "0";
            decimalPlaces--;
        }
        String pattern = prefix + "." + suffix;
        DecimalFormat formatter = new DecimalFormat(pattern);
        return formatter.format(val);
    }
}