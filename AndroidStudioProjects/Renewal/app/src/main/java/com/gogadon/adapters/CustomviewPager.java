package com.gogadon.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomviewPager extends ViewPager {
    public CustomviewPager(@NonNull Context context) {
        super(context);
    }

    public CustomviewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
