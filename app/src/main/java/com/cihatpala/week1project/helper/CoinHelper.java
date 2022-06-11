package com.cihatpala.week1project.helper;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.cihatpala.week1project.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CoinHelper {

    public static String coinPriceDoubleFormat(double currencyPrice) {
        NumberFormat format = new DecimalFormat("##.#####");
        return format.format(currencyPrice);
    }

    public static Drawable changeDrawableColor(Context context, int color) {

        return ContextCompat.getDrawable(context, color);
    }

}
