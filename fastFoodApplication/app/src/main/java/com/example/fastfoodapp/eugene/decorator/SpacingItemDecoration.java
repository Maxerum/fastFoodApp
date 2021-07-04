package com.example.fastfoodapp.eugene.decorator;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int spacing;
    private final int spanCount;

    public SpacingItemDecoration(int spacing, int spanCount) {
        this.spanCount = spanCount;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect rect, @NonNull View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        int column = position % spanCount;

        rect.left = spacing - spacing * column / spanCount;
        rect.right = (column + 1) * spacing / spanCount;

        if ((position / spanCount) == 0) {
            rect.top = spacing;
        } else {
            rect.top = 0;
        }
        rect.bottom = spacing;
    }
}
