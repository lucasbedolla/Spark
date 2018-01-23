package cool.lucasbedolla.swish.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import cool.lucasbedolla.swish.core.UnderTheHoodActivity;


public class FooterReturnBehavior extends CoordinatorLayout.Behavior<View> {
    private int mTotalDyDistance;
    private boolean hide = false;
    private int childHeight;

    public FooterReturnBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        childHeight = child.getHeight();
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        if (dy > 0 && mTotalDyDistance < 0 || dy < 0 && mTotalDyDistance > 0) {
            mTotalDyDistance = 0;
        }

        mTotalDyDistance += dy;

        if (!hide && mTotalDyDistance > child.getHeight()) {
            hideView(child);
            hide = true;
        } else if (hide && mTotalDyDistance < -child.getHeight()) {
            showView(child);
            hide = false;
        }
    }

    private void hideView(final View child) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(child, "translationY", 0, childHeight);
        animator.setDuration(300);
        animator.start();
    }

    private void showView(final View child) {
        ((UnderTheHoodActivity) child.getContext()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        ObjectAnimator animator = ObjectAnimator.ofFloat(child, "translationY", childHeight, 0);
        animator.setDuration(300);
        animator.start();
    }
}