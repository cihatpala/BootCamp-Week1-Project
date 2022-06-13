package com.cihatpala.week1project.helper;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

public class CoinHelper {

    public static String coinPriceDoubleFormat(double currencyPrice) {
        NumberFormat format = new DecimalFormat("##.#####");
        return format.format(currencyPrice);
    }

    public static HashMap<Integer, Integer> colors;

}
