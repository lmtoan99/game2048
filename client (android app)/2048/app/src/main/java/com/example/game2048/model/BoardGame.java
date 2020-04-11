package com.example.game2048.model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class BoardGame extends GridView {
    public BoardGame(Context context) {
        super(context);
    }

    public BoardGame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BoardGame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int side = getMeasuredWidth();
        setMeasuredDimension(side,side);
    }
}
