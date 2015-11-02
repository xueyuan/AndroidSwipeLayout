package com.daimajia.swipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseAdapter;

/**
 * SwipeItemMangerImpl is a helper class to help all the adapters to maintain open status.
 */
public class SwipeItemManager {

    private Mode mode = Mode.Single;
    public final int INVALID_POSITION = -1;

    protected int mOpenPosition = INVALID_POSITION;

    protected Set<Integer> mOpenPositions = new HashSet<Integer>();
    protected Set<SwipeLayout> mShownLayouts = new HashSet<SwipeLayout>();
    RecyclerView.Adapter<?> mRecyclerViewAdapter;
    BaseAdapter mBaseAdapter;

    public SwipeItemManager() {
    }

    public void setRecyclerViewAdapter(RecyclerView.Adapter<?> recyclerViewAdapter) {
        mRecyclerViewAdapter = recyclerViewAdapter;
    }

    public void setBaseAdapter(BaseAdapter baseAdapter) {
        mBaseAdapter = baseAdapter;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        mOpenPositions.clear();
        mShownLayouts.clear();
        mOpenPosition = INVALID_POSITION;
    }

    public void bind(SwipeLayout swipeLayout, View view, int position, int resId) {
        if (swipeLayout.getTag(resId) == null) {
            OnLayoutListener onLayoutListener = new OnLayoutListener(position);
            SwipeMemory swipeMemory = new SwipeMemory(position);
            swipeLayout.addSwipeListener(swipeMemory);
            swipeLayout.addOnLayoutListener(onLayoutListener);
            swipeLayout.setTag(resId, new ValueBox(position, swipeMemory, onLayoutListener));
            mShownLayouts.add(swipeLayout);
        } else {
            ValueBox valueBox = (ValueBox)swipeLayout.getTag(resId);
            valueBox.swipeMemory.setPosition(position);
            valueBox.onLayoutListener.setPosition(position);
            valueBox.position = position;
        }
    }

    public void openItem(int position) {
        if (mode == Mode.Multiple) {
            if (!mOpenPositions.contains(position))
                mOpenPositions.add(position);
        } else {
            mOpenPosition = position;
        }
        notifyDataSetChanged();
    }

    public void closeItem(int position) {
        if (mode == Mode.Multiple) {
            mOpenPositions.remove(position);
        } else {
            if (mOpenPosition == position)
                mOpenPosition = INVALID_POSITION;
        }
        notifyDataSetChanged();
    }

    public void closeAllExcept(SwipeLayout layout) {
        for (SwipeLayout s : mShownLayouts) {
            if (s != layout)
                s.close();
        }
    }

    void notifyDataSetChanged() {
        if (mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter.notifyDataSetChanged();
        } else if (mBaseAdapter != null) {
            mBaseAdapter.notifyDataSetChanged();
        } else {
            throw new RuntimeException("Adapters are not set before the code is used");
        }
    }

    public void closeAllItems() {
        if (mode == Mode.Multiple) {
            mOpenPositions.clear();
        } else {
            mOpenPosition = INVALID_POSITION;
        }
        for (SwipeLayout s : mShownLayouts) {
            s.close();
        }
    }

    public void removeShownLayouts(SwipeLayout layout) {
        mShownLayouts.remove(layout);
    }

    public List<Integer> getOpenItems() {
        if (mode == Mode.Multiple) {
            return new ArrayList<Integer>(mOpenPositions);
        } else {
            return Collections.singletonList(mOpenPosition);
        }
    }

    public List<SwipeLayout> getOpenLayouts() {
        return new ArrayList<SwipeLayout>(mShownLayouts);
    }

    public boolean isOpen(int position) {
        if (mode == Mode.Multiple) {
            return mOpenPositions.contains(position);
        } else {
            return mOpenPosition == position;
        }
    }

    public enum Mode {
        Single, Multiple
    }

    class ValueBox {
        OnLayoutListener onLayoutListener;
        SwipeMemory swipeMemory;
        int position;

        ValueBox(int position, SwipeMemory swipeMemory, OnLayoutListener onLayoutListener) {
            this.swipeMemory = swipeMemory;
            this.onLayoutListener = onLayoutListener;
            this.position = position;
        }
    }

    class OnLayoutListener implements SwipeLayout.OnLayout {

        private int position;

        OnLayoutListener(int position) {
            this.position = position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onLayout(SwipeLayout v) {
            if (isOpen(position)) {
                v.open(false, false);
            } else {
                v.close(false, false);
            }
        }

    }

    class SwipeMemory extends SimpleSwipeListener {

        private int position;

        SwipeMemory(int position) {
            this.position = position;
        }

        @Override
        public void onClose(SwipeLayout layout) {
            if (mode == Mode.Multiple) {
                mOpenPositions.remove(position);
            } else {
                mOpenPosition = INVALID_POSITION;
            }
        }

        @Override
        public void onStartOpen(SwipeLayout layout) {
            if (mode == Mode.Single) {
                closeAllExcept(layout);
            }
        }

        @Override
        public void onOpen(SwipeLayout layout) {
            if (mode == Mode.Multiple)
                mOpenPositions.add(position);
            else {
                closeAllExcept(layout);
                mOpenPosition = position;
            }
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
