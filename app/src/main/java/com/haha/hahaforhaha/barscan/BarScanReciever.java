package com.haha.hahaforhaha.barscan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.haha.hahaforhaha.utils.StringUtils;

public class BarScanReciever extends BroadcastReceiver {
    private static BarScanListener barScanListener=null;
    private static final String lachesisBarScancode = "lachesis_barcode_value_notice_broadcast";
    private static final String lachesisBarScancodeExtra = "lachesis_barcode_value_notice_broadcast_data_string";

    @Override
    public void onReceive(Context context, Intent intent) {
        String strBarcode = "";
        String action = intent.getAction();
        if (action.equals(lachesisBarScancode)) {
            strBarcode = intent.getStringExtra(lachesisBarScancodeExtra);
        }
        if (StringUtils.isStringEmpty(strBarcode)) return;
        barScanListener.onBarScanEvent(strBarcode);
    }

    public interface BarScanListener {
        void onBarScanEvent(String strBarCode);
    }

    public void SetOnbarScanListener(BarScanListener barScanListener) {
        this.barScanListener = barScanListener;
    }
}
