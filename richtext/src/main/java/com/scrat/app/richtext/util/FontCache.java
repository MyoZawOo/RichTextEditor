package com.scrat.app.richtext.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by mav on 7/13/17.
 */

public class FontCache {

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface get(String name, Context context){
        Typeface tf = fontCache.get(name);
        if (tf == null){
            tf = Typeface.createFromAsset(context.getAssets(), "fonts/"+name);
            fontCache.put(name, tf);
        }
        return tf;
    }
}
