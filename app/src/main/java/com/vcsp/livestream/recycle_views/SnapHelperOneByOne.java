package com.vcsp.livestream.recycle_views;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SnapHelperOneByOne extends PagerSnapHelper {

    final float marginDp = 6f, scaleRatio = 1.3f, notFocusAlpha = 0.6f;
    final int animateDuration = 150;
    private float marginPx;

    public SnapHelperOneByOne(Resources res) {
        marginPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginDp, res.getDisplayMetrics());
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {


        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }

        View currentView;

        currentView = findSnapView(layoutManager);

        if (currentView == null) {
            return RecyclerView.NO_POSITION;
        }

        LinearLayoutManager myLayoutManager = (LinearLayoutManager) layoutManager;

        int position1 = myLayoutManager.findFirstVisibleItemPosition();
        int position2 = myLayoutManager.findLastVisibleItemPosition();

        int currentPosition = layoutManager.getPosition(currentView);
        int nextPosition;

        if (velocityX > 400) {
            nextPosition = position2;
            View nextView = myLayoutManager.findViewByPosition(nextPosition);
            if (nextView != null) {
                startAnimate(nextView, currentView);
            }

        } else if (velocityX < 400) {
            nextPosition = position1;
            View nextView = myLayoutManager.findViewByPosition(nextPosition);
            if (nextView != null) {
                startAnimate(nextView, currentView);
            }
        } else {
            nextPosition = currentPosition;
        }

        return nextPosition;
    }

    private int calculateMarginWhenScaleUp(View focusView) {
        return (int) ((Math.ceil((focusView.getMeasuredWidth() * (scaleRatio - 1)) / 2)) + marginPx);
    }

    public void initFirstView(View focusView) {
        int margin = calculateMarginWhenScaleUp(focusView);
        RecyclerView.LayoutParams focusViewLayoutParams = (RecyclerView.LayoutParams) focusView.getLayoutParams();
        focusViewLayoutParams.rightMargin = margin;
        focusViewLayoutParams.leftMargin = margin;
        focusView.setScaleX(scaleRatio);
        focusView.setScaleY(scaleRatio);
        focusView.requestLayout();
        focusView.setAlpha(1);
    }

    public void startAnimate(View nextView, View currentView) {

        if (currentView != null) {
            RecyclerView.LayoutParams currentViewLayoutParams = (RecyclerView.LayoutParams) currentView.getLayoutParams();
            currentView.setScaleX(1);
            currentView.setScaleY(1);
            currentView.setAlpha(notFocusAlpha);
            currentViewLayoutParams.rightMargin = (int) marginPx;
            currentViewLayoutParams.leftMargin = (int) marginPx;
            currentView.setLayoutParams(currentViewLayoutParams);
        }

        AnimatorSet focusAnimate = new AnimatorSet();

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) nextView.getLayoutParams();

        ValueAnimator expandWidth = ValueAnimator.ofInt(0, calculateMarginWhenScaleUp(nextView));
        ValueAnimator scaleUp = ValueAnimator.ofFloat(1, scaleRatio);
        ValueAnimator alphaUp = ValueAnimator.ofFloat(notFocusAlpha, 1);

        expandWidth.addUpdateListener(valueAnimator -> {
            layoutParams.leftMargin = (int) ((Integer) valueAnimator.getAnimatedValue() + marginPx);
            layoutParams.rightMargin = (int) ((Integer) valueAnimator.getAnimatedValue() + marginPx);
            nextView.setLayoutParams(layoutParams);
        });
        scaleUp.addUpdateListener(valueAnimator -> {
            nextView.setScaleX((Float) valueAnimator.getAnimatedValue());
            nextView.setScaleY((Float) valueAnimator.getAnimatedValue());
        });
        alphaUp.addUpdateListener(valueAnimator -> nextView.setAlpha((Float) valueAnimator.getAnimatedValue()));

        scaleUp.setDuration(animateDuration);
        expandWidth.setDuration(animateDuration);
        focusAnimate.play(expandWidth).with(scaleUp).with(alphaUp);
        focusAnimate.start();
    }
}

