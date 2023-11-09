package com.fetchrewards.codingexercise;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class BorderItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacingInDp;
    private final float density;
    private final Paint borderPaint;

    public BorderItemDecoration(Context context) {
        this.spacingInDp = 8;
        this.density = context.getResources().getDisplayMetrics().density;
        borderPaint = new Paint();
        borderPaint.setColor(context.getResources().getColor(R.color.white));
        borderPaint.setStrokeWidth(0);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spacingInPixels = (int) (spacingInDp * density + 0.5f);
        outRect.top = spacingInPixels;
        outRect.left = spacingInPixels;
        outRect.right = spacingInPixels;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            child.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            int left = child.getLeft();
            int right = child.getRight();
            int top = child.getTop();
            int bottom = child.getBottom();
            c.drawRect(left, top, right, bottom, borderPaint);
        }
    }
}
