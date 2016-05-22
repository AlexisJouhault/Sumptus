package com.frusby.sumptusmagnus.tabs;

import android.content.Context;

import com.frusby.sumptusmagnus.BaseComponents.BaseFragment;
import com.frusby.sumptusmagnus.LandingPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alexisjouhault on 4/24/16.
 */
public class LandingPageTabFragment extends BaseFragment {

    private Logger LOGGER = LoggerFactory.getLogger(LandingPageTabFragment.class);

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
