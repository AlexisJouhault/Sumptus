package com.frusby.sumptusmagnus.tabs;

import android.app.Activity;
import android.content.Context;

import com.frusby.sumptusmagnus.BaseComponents.BaseFragment;
import com.frusby.sumptusmagnus.LandingPage;

/**
 * Created by alexisjouhault on 4/24/16.
 */
public class LandingPageTabFragment extends BaseFragment {

    protected LandingPage parentActivity;
    protected String title;

    @Override
    public void onAttach(Context context) {
        this.parentActivity = (LandingPage) context;
        super.onAttach(context);
    }

    public String getTitle() {
        return title;
    }
}
