package com.frusby.sumptusmagnus.BaseComponents;

import android.view.View;

/**
 * Created by alexisjouhault on 5/21/16.
 */
public interface BaseView {

    public ViewInfo getViewInfo();
    public void setUpUIContent(int position);
    public View getView(int position);
    public boolean isRemoved();
}
