package com.games.tictactoe.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by maorsisso on 31/05/2017.
 */

public class XView extends View {

    private int mXColor;
    private float mStrokeWidth = 6;

    public XView(Context context) {
        super(context);
    }

    public XView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    public XView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs){
        TypedArray typedArray;
        typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.XView);
        mXColor = typedArray.getColor(R.styleable.XView_x_color, Color.WHITE);
        mStrokeWidth = typedArray.getFloat(R.styleable.XView_x_stroke_width, 6);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = getMeasuredWidth();
        float height = getMeasuredHeight();
        Paint paint = new Paint();
        paint.setColor(mXColor);
        paint.setStrokeWidth(mStrokeWidth);
        canvas.drawLine(0,0,width,height,paint);
        canvas.drawLine(0,height,width,0,paint);
    }
}
