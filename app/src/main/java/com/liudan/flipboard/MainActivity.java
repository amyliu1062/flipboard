package com.liudan.flipboard;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Point screenPoint = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenPoint);
        int iw = screenPoint.x;
        final float scale = getResources().getDisplayMetrics().density;
        int ih = (int) (300 * scale + 0.5f);


        List<ItemViewModel> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(
                    new ItemViewModel(
                            BitmapFactory.decodeResource(this.getResources(), R.mipmap.item_bg),
                            R.mipmap.item_bg,
                            iw,
                            ih
                    )
            );
        }

        recyclerView = (RecyclerView) findViewById(R.id.activity_main_content_rv);
        final ImageRecyclerAdapter adapter = new ImageRecyclerAdapter(this, list);
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
                Log.d("delta Y", "y: " + dy);
                int firstPos = layoutManager.findFirstVisibleItemPosition();
                int lastPos = layoutManager.findLastVisibleItemPosition();
                for (int i = firstPos; i <= lastPos; i++) {

//                    adapter.getItem(i).setDeltaY(dy > 0 ? (int) Math.ceil(dy / 2.35f) : (int) Math.floor(dy / 2.35f));

                    adapter.getItem(i).setDeltaY(dy > 0 ? 1 : -1);

                }
//                adapter.notifyItemRangeChanged(firstPos, lastPos);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
