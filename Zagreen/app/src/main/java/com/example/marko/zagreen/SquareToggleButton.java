package com.example.marko.zagreen;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;

/**
 * Klasa koja postavlja dimenzije buttona u kvadratičan oblik
 *
 * @author Collude
 * @version 2015.0502
 * @since 1.0
 */
public class SquareToggleButton extends ToggleButton {

    public SquareToggleButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public SquareToggleButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareToggleButton(Context context)
    {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        w = Math.min(w, h);
        h = w;

        setMeasuredDimension(w, h);
    }


}
