package com.liudan.flipboard.other;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liudan.flipboard.R;
import com.liudan.flipboard.RecyclerViewHolder;

import java.util.List;

/**
 * Created by liudan on 15/12/11.
 */
public class FlipAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private final List<FlipViewModel> list;
    private final Context context;

    public FlipAdapter(Context context, List<FlipViewModel> list) {
        this.context = context;
        this.list = list;


    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_listview_other, null);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.item_list_view_other_iv);
        imageView.setImageResource(list.get(position).resId);
        imageView.setTranslationY(list.get(position).currentY);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public FlipViewModel getItem(int position) {
        return list.get(position);
    }
}
