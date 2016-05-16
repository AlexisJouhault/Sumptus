package com.frusby.sumptusmagnus;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.frusby.sumptusmagnus.BaseComponents.SumptusPagerAdapter;
import com.frusby.sumptusmagnus.tabs.HistoryTab;
import com.frusby.sumptusmagnus.tabs.ReportTab;
import com.frusby.sumptusmagnus.tabs.RecentScansTab;
import com.frusby.sumptusmagnus.tools.ActivityResultCodes;

import org.opencv.android.OpenCVLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LandingPage extends FragmentActivity {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumptus);

        if (!OpenCVLoader.initDebug()) {
            Log.e("Init", "OpenCVLoader.initDebug(), not working.");
        } else {
            Log.d("Init", "OpenCVLoader.initDebug(), working.");
        }

        ButterKnife.bind(this);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SumptusPagerAdapter adapter = new SumptusPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecentScansTab());
        adapter.addFragment(new HistoryTab());
        adapter.addFragment(new ReportTab());
        viewPager.setAdapter(adapter);
    }

    public void startScanning() {
        Intent i = new Intent(this, ReceiptDetectionActivity.class);
        startActivityForResult(i, ActivityResultCodes.GET_IMAGES_FROM_SCAN);
    }
}
