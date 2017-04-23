package com.turkiyedenemeleri.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.turkiyedenemeleri.R;


/**
 * Created by safakesberk on 22/04/2017.
 */

public class TDButton extends AppCompatButton {

    public TDButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public TDButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public TDButton(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null && !isInEditMode()) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.TDButton);
            String fontName = a
                    .getString(R.styleable.TDButton_btFontName);
            if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext()
                        .getAssets(), "fonts/" + fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }

}
