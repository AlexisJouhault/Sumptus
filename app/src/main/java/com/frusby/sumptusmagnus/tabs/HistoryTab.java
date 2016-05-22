package com.frusby.sumptusmagnus.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.frusby.sumptusmagnus.BaseComponents.SumptusAdapter;
import com.frusby.sumptusmagnus.BaseComponents.ViewInfo;
import com.frusby.sumptusmagnus.R;
import com.frusby.sumptusmagnus.core.Receipt;
import com.frusby.sumptusmagnus.core.ReceiptContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexisjouhault on 4/24/16.
 */
public class HistoryTab extends LandingPageTabFragment {

    private Logger LOGGER = LoggerFactory.getLogger(HistoryTab.class);

    public HistoryTab() {
        this.title = "History";
    }

    @Bind(R.id.receipt_list)
    ListView receiptListView;

    @Override
    public void onAttach(Context context) {
        TAG = context.getString(R.string.fragment_history_title);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, mainView);
        setUpUI();
        return mainView;
    }

    private void setUpUI() {
        LOGGER.debug("Setting up {} UI", TAG);
        List<Receipt> receipts = Receipt.listAll(Receipt.class);
        SumptusAdapter sumptusAdapter = new SumptusAdapter(ReceiptContainer.generateList(getActivity(), receipts, R.layout.receipt_layout));
        receiptListView.setAdapter(sumptusAdapter);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        //TODO update view instead of setting it up again (too long)
        setUpUI();
    }
}
