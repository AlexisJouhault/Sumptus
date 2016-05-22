package com.frusby.sumptusmagnus.BaseComponents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by alexisjouhault on 5/21/16.
 */
public class BaseViewContainer implements BaseView {

    protected ViewInfo viewInfo;
    protected View view = null;

    protected Object object;

    private boolean isRemoved = false;

    public BaseViewContainer(ViewInfo viewInfo, Object object) {
        this.object = object;
        this.viewInfo = viewInfo;
    }

    @Override
    public ViewInfo getViewInfo() {
        return viewInfo;
    }

    /***
     * Overide in ElemContainer to setup UI
     * @param position
     */
    @Override
    public void setUpUIContent(int position) {

    }

    @Override
    public View getView(int position) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) viewInfo.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(viewInfo.getLayoutId(), null);
            setUpUIContent(position);
        }
        return view;
    }

    @Override
    public boolean isRemoved() {
        return isRemoved;
    }
}
