package com.liudan.flipboard.other;

import android.animation.ObjectAnimator;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liudan.flipboard.R;

/**
 * Created by liudan on 15/12/14.
 */
public class CocoView extends LinearLayout {

    private Canvas canvas;
    private Paint paint;
    private boolean isChangeColor;
    private Rect currentRect;
    private TextView tickTv;

    public CocoView(Context context) {
        this(context, null);
    }

    public CocoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CocoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        initCocoView(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initCocoView(Context context) {
        canvas = new Canvas();
        paint = new Paint();
        paint.setColor(0x88FF0000);
        final View view = View.inflate(getContext(), R.layout.item_coco, this);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateView(v);
            }
        });
        tickTv = (TextView) view.findViewById(R.id.item_list_view_other_tick_tv);
        propertyValuesHolder(tickTv, isChangeColor);

    }


    private void updateView(View view) {
        isChangeColor = !isChangeColor;
        startAnimation();
        postInvalidate();

        propertyValuesHolder(tickTv, isChangeColor);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void propertyValuesHolder(final View view, final boolean isScaleLarger) {
        final ObjectAnimator animatorScale = isScaleLarger ? ObjectAnimator.ofFloat(view, "scale", 0.0f, 2.0f, 1.0f).setDuration(500) :
                ObjectAnimator.ofFloat(view, "scale", 1.0f, 0.0f).setDuration(500);
        animatorScale.start();
        animatorScale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatorValue = (float)animatorScale.getAnimatedValue();
                view.setAlpha(animatorValue);
                view.setScaleX(animatorValue);
                view.setScaleY(animatorValue);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CocoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentRect != null) {
            canvas.drawRect(currentRect, paint);
        } else {
            currentRect = new Rect(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight() / 2);
            canvas.drawRect(currentRect, paint);
            startAnimation();
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void startAnimation() {
        Rect startRect = isChangeColor ? new Rect(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight() / 2) : new Rect(0, 0, getWidth(), getHeight());
        Rect endRect = isChangeColor ? new Rect(0, 0, getWidth(), getHeight()) : new Rect(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight() / 2);
        ValueAnimator anim = ValueAnimator.ofObject(new RectEvaluator(), startRect, endRect);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentRect = (Rect) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(500);
        anim.start();
    }

}
