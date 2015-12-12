package com.liudan.flipboard;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by liudan on 15/12/10.
 */
public class ItemViewModel {
    public final String TAG = "[" + System.identityHashCode(this) + "]";
    private int resId;
    private int deltaY;
    private Bitmap bitmap; // 要显示的原始bitmap
    private Bitmap drawBitmap; // 要绘制的bitmap
        private Canvas canvas;
    private Rect srcRect = new Rect(); // 绘制的bitmap区域
    private Rect drawRect = new Rect();
    private int bw;
    private int bh;
    private int iw;
    private int ih;

//    private int currentOffsetTop;
    private Paint paint = new Paint();

    int[] pixels;

    public ItemViewModel() {
    }

    public ItemViewModel(Bitmap bitmap, int resId, int iw, int ih) {
        this.bitmap = bitmap;
        bw = bitmap.getWidth();
        bh = bitmap.getHeight();
        pixels = new int[bw * bh];
        bitmap.getPixels(pixels, 0, bw, 0, 0, bw, bh);
        this.iw = iw;
        this.ih = ih;
        this.resId = resId;
        srcRect.set(0, Math.abs(bh - ih), iw, bh);
        drawRect.set(0, 0, iw, ih);
//        currentOffsetTop = bh - ih;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }

    private void ensureInitialized() {
        if (null == drawBitmap) {
            drawBitmap = Bitmap.createBitmap(iw, ih, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(drawBitmap);
        }
    }

    public boolean draw() {
        ensureInitialized();
//        currentY = currentY > 0 ? 1 : -1;
        if (srcRect.top - deltaY < 0 || srcRect.bottom - deltaY > bh) {
            return false;
        }
        srcRect.set(srcRect.left, srcRect.top - deltaY, srcRect.right, srcRect.bottom - deltaY);

        long start = System.currentTimeMillis();
        canvas.drawBitmap(bitmap, srcRect, drawRect, paint);
        Log.d(TAG, "srcTop: " + srcRect.top + ", srcBottom: " + srcRect.bottom);
        Log.d("[" + TAG + "][XXXX_SET_PIXELS]", "--->: " + (System.currentTimeMillis() - start) + "ms");
        return true;
    }


    public Bitmap getDrawBitmap() {
        return drawBitmap;
    }
}
