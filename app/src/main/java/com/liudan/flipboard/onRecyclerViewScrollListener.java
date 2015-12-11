package com.liudan.flipboard;

import android.view.View;

import java.util.List;

/**
 * Created by liudan on 15/12/10.
 */
interface onRecyclerViewScrollListener {
    void onScrollListener(List<View> viewList,int x, int y);
}
