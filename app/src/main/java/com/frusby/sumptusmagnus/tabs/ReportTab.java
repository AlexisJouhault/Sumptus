package com.frusby.sumptusmagnus.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frusby.sumptusmagnus.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.ButterKnife;

/**
 * Created by alexisjouhault on 4/24/16.
 */
public class ReportTab extends LandingPageTabFragment {

    private Logger LOGGER = LoggerFactory.getLogger(ReportTab.class);

    public ReportTab() {
        this.title = "Report";
    }

    @Override
    public void onAttach(Context context) {
        TAG = context.getString(R.string.fragment_report_title);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, mainView);
        return mainView;
    }
}
