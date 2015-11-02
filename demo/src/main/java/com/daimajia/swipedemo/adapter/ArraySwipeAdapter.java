package com.daimajia.swipedemo.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.daimajia.swipe.SwipeItemManager;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipedemo.R;

/**
 * Sample usage of ArraySwipeAdapter.
 *
 * @param <T>
 */
public class ArraySwipeAdapter<T> extends ArrayAdapter {
    SwipeItemManager mItemManager;

    public ArraySwipeAdapter(Context context, SwipeItemManager mgr, int resource) {
        super(context, resource);
        mItemManager = mgr;
        mItemManager.setBaseAdapter(this);
    }

    public ArraySwipeAdapter(Context context, SwipeItemManager mgr, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        mItemManager = mgr;
    }

    public ArraySwipeAdapter(Context context, SwipeItemManager mgr, int resource, Object[] objects) {
        super(context, resource, objects);
        mItemManager = mgr;
    }

    public ArraySwipeAdapter(Context context, SwipeItemManager mgr, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
        mItemManager = mgr;
    }

    public ArraySwipeAdapter(Context context, SwipeItemManager mgr, int resource, List objects) {
        super(context, resource, objects);
        mItemManager = mgr;
    }

    public ArraySwipeAdapter(Context context, SwipeItemManager mgr, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
        mItemManager = mgr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(R.id.swipe);
        mItemManager.bind(swipeLayout, v, position, R.id.swipe);
        return v;
    }
}
