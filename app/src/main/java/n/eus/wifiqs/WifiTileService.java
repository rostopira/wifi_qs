package n.eus.wifiqs;

import android.graphics.drawable.Icon;
import android.net.wifi.WifiManager;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.os.Build;

public class WifiTileService extends TileService implements WifiStateListener {
    WifiStateReceiver wifiStateReceiver;

    @Override
    public void onStartListening() {
        super.onStartListening();
        if (wifiStateReceiver != null) {
            return;
        }
        wifiStateReceiver = WifiStateReceiver.createAndRegister(this, this);
        // Get current wifi state
        wifiStateReceiver.onReceive(this, null);
    }

    @Override
    public void onWifiStateChanged(WifiState state, String ssid) {
        final Tile tile = getQsTile();
        switch (state) {
            case DISABLED:
                tile.setIcon(Icon.createWithResource(this, R.drawable.disabled));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    tile.setLabel("Wi-Fi");
                    tile.setSubtitle(("Off"));
                } else {
                    tile.setLabel("Off");
                }
                tile.setState(Tile.STATE_INACTIVE);
                break;
            case ENABLED:
                tile.setIcon(Icon.createWithResource(this, R.drawable.enabled));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    tile.setLabel("Wi-Fi");
                    tile.setSubtitle(("Disconnected"));
                } else {
                    tile.setLabel("Disconnected");
                }
                tile.setState(Tile.STATE_ACTIVE);
                break;
            case CONNECTED:
                tile.setIcon(Icon.createWithResource(this, R.drawable.connected));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    tile.setLabel(("Wi-Fi"));
                    tile.setSubtitle(ssid != null ? ssid : "Connected");
                } else {
                    tile.setLabel(ssid != null ? ssid : "Connected");
                }
                tile.setState(Tile.STATE_ACTIVE);
                break;
        }
        tile.updateTile();
    }

    @Override
    public void onClick() {
        super.onClick();
        final WifiManager wifiMan = getSystemService(WifiManager.class);
        wifiMan.setWifiEnabled(!wifiMan.isWifiEnabled());
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        if (wifiStateReceiver != null) {
            unregisterReceiver(wifiStateReceiver);
            wifiStateReceiver = null;
        }
    }

}
