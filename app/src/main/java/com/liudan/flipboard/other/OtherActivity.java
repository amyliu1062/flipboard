package com.liudan.flipboard.other;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.liudan.flipboard.ImageRecyclerAdapter;
import com.liudan.flipboard.ItemViewModel;
import com.liudan.flipboard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudan on 15/12/11.
 */
public class OtherActivity extends Activity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<ItemViewModel> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
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

                    adapter.getItem(i).setDeltaY(dy > 0 ? 1 : -1);

                }
                adapter.notifyDataSetChanged();
            }
        });

    }
}
