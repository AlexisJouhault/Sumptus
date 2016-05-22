package com.frusby.sumptusmagnus.core;

import android.content.Context;
import android.widget.TextView;

import com.frusby.sumptusmagnus.BaseComponents.BaseView;
import com.frusby.sumptusmagnus.BaseComponents.BaseViewContainer;
import com.frusby.sumptusmagnus.BaseComponents.SumptusException;
import com.frusby.sumptusmagnus.BaseComponents.ViewInfo;
import com.frusby.sumptusmagnus.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexisjouhault on 5/21/16.
 */
public class ReceiptContainer extends BaseViewContainer {

    private Logger LOGGER = LoggerFactory.getLogger(ReceiptContainer.class);

    private int titleViewId = R.id.receipt_title;
    private int valueViewId = R.id.receipt_value;

    public ReceiptContainer(ViewInfo viewInfo, Receipt receipt) {
        super(viewInfo, receipt);
    }

    @Override
    public void setUpUIContent(int position) {
        try {
            Receipt receipt = (Receipt) this.object;

            if (receipt == null) {
                throw new SumptusException("Trying to show view with null content");
            }

            TextView title = (TextView) view.findViewById(titleViewId);
            TextView value = (TextView) view.findViewById(valueViewId);

            title.setText(receipt.getIssuer());
            value.setText(receipt.getValue());
        } catch (SumptusException e) {
            LOGGER.debug(e.getMessage());
        }
    }

    //Todo find a way to make this a generic method in BaseViewContainer
    public static List<BaseView> generateList(Context context, List<Receipt> items, int itemLayoutId) {
        List<BaseView> baseViewList = new ArrayList<>();
        ViewInfo viewInfo = new ViewInfo(context, itemLayoutId);

        for (Receipt item : items) {
            ReceiptContainer receiptContainer = new ReceiptContainer(viewInfo, item);
            baseViewList.add(receiptContainer);
        }

        return baseViewList;
    }
}
