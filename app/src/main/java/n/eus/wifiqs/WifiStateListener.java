package n.eus.wifiqs;

public interface WifiStateListener {
    void onWifiStateChanged(WifiState state, String ssid, int rss);
}
