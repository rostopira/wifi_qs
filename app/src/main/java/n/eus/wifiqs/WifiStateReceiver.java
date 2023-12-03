package n.eus.wifiqs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiStateReceiver extends BroadcastReceiver {
    final WifiStateListener listener;

    WifiStateReceiver(WifiStateListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("ForwardCompatibility")
    @Override
    public void onReceive(Context context, Intent _) {
        final WifiManager wifiMan = context.getSystemService(WifiManager.class);
        final boolean isEnabled = wifiMan.isWifiEnabled();
        if (!isEnabled) {
            listener.onWifiStateChanged(WifiState.DISABLED, null, -1);
            return;
        }
        final WifiInfo wifiInfo = wifiMan.getConnectionInfo();
        if (wifiInfo.getNetworkId() != -1) {
            String ssid = wifiInfo.getSSID();
            if (WifiManager.UNKNOWN_SSID.equals(ssid)) {
                ssid = null;
            } else {
                // Remove quotes
                ssid = ssid.substring(1, ssid.length()-1);
            }
            final int rss = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 7);
            listener.onWifiStateChanged(WifiState.CONNECTED, ssid, rss);
        } else {
            listener.onWifiStateChanged(WifiState.ENABLED, null, -1);
        }
    }

    static WifiStateReceiver createAndRegister(Context context, WifiStateListener listener) {
        final WifiStateReceiver receiver = new WifiStateReceiver(listener);
        final IntentFilter intentFilter = new IntentFilter();
        /// We don't care about deprecation, because doesn't register this receiver in manifest
        //noinspection deprecation
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        context.registerReceiver(receiver, intentFilter);
        return receiver;
    }
}
