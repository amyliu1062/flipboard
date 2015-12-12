package com.liudan.flipboard.other;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.liudan.flipboard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudan on 15/12/11.
 */
public class OtherActivity extends Activity {

    private RecyclerView recyclerView;
    private int itemHeight;
    private int imageViewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Point screenPoint = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenPoint);
        final float scale = getResources().getDisplayMetrics().density;
        itemHeight = (int) (300 * scale + 0.5f);
        imageViewHeight = (int) (600 * scale + 0.5f);

        List<FlipViewModel> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new FlipViewModel(R.mipmap.item_bg_e));
        }

        recyclerView = (RecyclerView) findViewById(R.id.activity_main_content_rv);
        final FlipAdapter adapter = new FlipAdapter(this, list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("dy:", dy + "");
                int firstPos = layoutManager.findFirstVisibleItemPosition();
                int lastPos = layoutManager.findLastVisibleItemPosition();
                for (int i = firstPos; i <= lastPos; i++) {

                    FlipViewModel flipViewModel = adapter.getItem(i);
                    if (Math.abs(imageViewHeight- itemHeight)/2 - flipViewModel.currentY > 0 || dy < 0) {
                        flipViewModel.currentY += dy > 0 ? Math.ceil(((double)dy) / 5) : Math.floor(((double)dy) / 5);
                        Log.d("currentY:", ""+ flipViewModel.currentY);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

    }
}
