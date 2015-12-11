package com.liudan.flipboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by liudan on 15/12/10.
 */
public class FlipItemListView extends ListView {

    private Scroller scroller;
    /**
     * 速度追踪器
     */
    private VelocityTracker velocityTracker;
    /**
     * 手指按下的X坐标的值
     */
    private int downX;
    private int downY;
    /**
     * 用户滑动的最小距离
     */
    private int mTouchSlop;
    /**
     * 当前listview position
     */
    private int slidePosition;
    private View itemView;
    private boolean isSlide;

    public FlipItemListView(Context context) {
        this(context, null);
    }

    public FlipItemListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipItemListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlipItemListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                addVelocityTracker(event);

                if (!scroller.isFinished()) {
                    return super.dispatchTouchEvent(event);
                }
                downX = (int) event.getX();
                downY = (int) event.getY();
                slidePosition = pointToPosition(downX, downY);

                itemView = getChildAt(slidePosition - getFirstVisiblePosition());

                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(getScrollVelocity()) > 600 ||
                        (Math.abs(event.getY() - downY) > mTouchSlop && Math.abs(event.getX() - downX) < mTouchSlop)) {
                    isSlide = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);
        addVelocityTracker(ev);
        final int action = ev.getAction();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                // Action为ACTION_CANCEL,ListView会取消item的点击事件，长按事件以及item的高亮
                MotionEvent cancelEvent = MotionEvent.obtain(ev);
                cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                        (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                onTouchEvent(cancelEvent);

                int deltaY = downY - y;
                downY = y;

                // 手指拖动itemView滚动, deltaY大于0向上滚动，小于0向下滚
                itemView.scrollBy(0, deltaY);

                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 获取Y方向的滑动速度，大于0向右滑动，反之向左
     *
     * @return
     */
    private double getScrollVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getYVelocity();
        return velocity;
    }

    /**
     * 添加 用户速度跟踪器
     *
     * @param event
     */
    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    /**
     * 移除 用户速度跟踪器
     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }
}
