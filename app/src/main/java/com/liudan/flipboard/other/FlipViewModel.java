package com.liudan.flipboard.other;

import java.io.Serializable;

/**
 * Created by liudan on 15/12/11.
 */
public class FlipViewModel implements Serializable {

    public int resId;
    public float currentY;

    public FlipViewModel() {
    }

    public FlipViewModel(int resId) {
        this.resId = resId;

    }
}
