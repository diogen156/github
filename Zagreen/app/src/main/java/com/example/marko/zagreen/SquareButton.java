package com.example.marko.zagreen;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

/**
 * Created by Marko on 24.4.2015..
 */
public class SquareButton extends ToggleButton {

    public SquareButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public SquareButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareButton(Context context)
    {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //Get canvas width and height
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        w = Math.min(w, h);
        h = w;

        setMeasuredDimension(w, h);
    }


}
