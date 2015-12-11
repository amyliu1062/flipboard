package com.liudan.flipboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by liudan on 15/12/10.
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private List<ItemViewModel> list;
    private ImageView itemIv;

    private Paint paint;

    public ImageRecyclerAdapter(Context context, List<ItemViewModel> list) {
        this.context = context;
        this.list = list;

        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_listview, null);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        itemIv = obtainView(holder.itemView, R.id.item_listview_iv);

        ItemViewModel item = list.get(position);
        boolean shouldDraw = item.draw();

//        RecyclerViewHolder preHolder = (RecyclerViewHolder) itemIv.getTag();
//        if(null != preHolder && preHolder == holder && !shouldDraw){
//
//        }else{
        long start = System.currentTimeMillis();
            itemIv.setImageBitmap(item.getDrawBitmap());
            Log.d("[" + item.TAG + "][XXXX_SET_IMAGE_BITMAP]", "--->: " + (System.currentTimeMillis() - start) + "ms");
//        }

//        itemIv.setTag(holder);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public ItemViewModel getItem(int position) {
        return list.get(position);
    }

    public static <T extends View> T obtainView(View convertView, int id) {
        SparseArray<View> holder = (SparseArray<View>) convertView.getTag();
        if (holder == null) {
            holder = new SparseArray<View>();
            convertView.setTag(holder);
        }
        View childView = holder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            holder.put(id, childView);
        }
        return (T) childView;
    }
}
