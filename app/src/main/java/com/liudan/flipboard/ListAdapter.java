package com.liudan.flipboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by liudan on 15/12/10.
 */

class ListAdapter extends BaseAdapter {

    private Context context;
    private List<ItemViewModel> list;

    public ListAdapter(Context context, List<ItemViewModel> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private ImageView itemIv;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView){
            convertView = View.inflate(context, R.layout.item_listview, null);
            itemIv = (ImageView) convertView.findViewById(R.id.item_listview_iv);
        }else{
            itemIv.setBackgroundResource(list.get(position).getResId());
        }
        return convertView;
    }

}
