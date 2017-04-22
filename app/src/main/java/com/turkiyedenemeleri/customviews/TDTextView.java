package com.turkiyedenemeleri.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.turkiyedenemeleri.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by safakesberk on 22/04/2017.
 */

public class TDTextView extends AppCompatTextView {
    public static Map<String, Typeface> typefaceCache = new HashMap<String, Typeface>();

    public TDTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, context);
    }

    public TDTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);

    }

    public TDTextView(Context context) {
        super(context);
        init(null, context);
    }

    private void init(AttributeSet attrs, Context context) {
        if (attrs != null) {
            setTypeface(attrs, context);
            setLineSpacing(0, 1.2f);
        }
    }

    public static Typeface getFontOrGenerateOne(String typefaceName, Context context) {
        Typeface typeface = null;
        if (typefaceCache.containsKey(typefaceName)) {
            return typefaceCache.get(typefaceName);
        } else {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + typefaceName);
            } catch (Exception e) {
                return typeface;
            }

            typefaceCache.put(typefaceName, typeface);
        }
        return typeface;
    }


    private void setTypeface(AttributeSet attrs, Context context) {
        TypedArray values = context.obtainStyledAttributes(attrs,
                R.styleable.TDTextView, 0, 0);
        String typefaceName = values.getString(R.styleable.TDTextView_tvFontName);

        setTypeface(getFontOrGenerateOne(typefaceName, context));
        values.recycle();

    }
}
