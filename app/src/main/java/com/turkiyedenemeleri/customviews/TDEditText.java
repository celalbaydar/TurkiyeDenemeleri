package com.turkiyedenemeleri.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.turkiyedenemeleri.R;


/**
 * Created by safakesberk on 22/04/2017.
 */

public class TDEditText extends AppCompatEditText {

    public TDEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public TDEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public TDEditText(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TDEdittext);
            String fontName = a.getString(R.styleable.TDEdittext_etFontName);
            if (fontName != null && !isInEditMode())
            {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }

}
