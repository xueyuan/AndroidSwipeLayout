package com.daimajia.swipedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeItemManager;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipedemo.R;

public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    SwipeItemManager mItemManager;

    public ListViewAdapter(Context context, SwipeItemManager itemManager) {
        this.mContext = context;
        mItemManager = itemManager;
        mItemManager.setBaseAdapter(this);
    }

    View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(R.id.swipe);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = generateView(position, parent);
        }
        View v2 = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
        SwipeLayout swipeLayout = (SwipeLayout)v2.findViewById(R.id.swipe);
        mItemManager.bind(swipeLayout, v, position, R.id.swipe);
        fillValues(position, v);
        return v;
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
}
