package com.frusby.sumptusmagnus.BaseComponents;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexisjouhault on 5/21/16.
 */
public class SumptusAdapter<T> extends BaseAdapter {

    List<BaseViewContainer> views;

    public SumptusAdapter(List<BaseViewContainer> views) {
        super();
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object getItem(int position) {
        return views.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return views.get(position).getView(position);
    }
}
