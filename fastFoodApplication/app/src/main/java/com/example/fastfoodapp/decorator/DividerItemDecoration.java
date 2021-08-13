package com.example.fastfoodapp.decorator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.R;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable divider;
    private static final int SIDE_PADDING = 30;

    public DividerItemDecoration(Context context) {
        divider = ContextCompat.getDrawable(context, R.drawable.item_divider);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int right = parent.getWidth() - SIDE_PADDING;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            int top = child.getBottom();
            int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(SIDE_PADDING, top, right, bottom);
            divider.draw(c);
        }
    }
}

