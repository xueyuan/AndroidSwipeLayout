package com.daimajia.swipedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daimajia.swipe.SwipeItemManager;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipedemo.R;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    SwipeItemManager mItemManager;

    public GridViewAdapter(Context Context, SwipeItemManager itemManager) {
        this.mContext = Context;
        mItemManager = itemManager;
        mItemManager.setBaseAdapter(this);
    }

    View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
    }

    void fillValues(int position, View convertView) {
        TextView t = (TextView)convertView.findViewById(R.id.position);
        t.setText((position + 1) + ".");
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = generateView(position, parent);
        }
        SwipeLayout v2 = (SwipeLayout)v.findViewById(R.id.swipe);
        mItemManager.bind(v2, v, position, R.id.swipe);
        fillValues(position, v);
        return v;
    }
}
