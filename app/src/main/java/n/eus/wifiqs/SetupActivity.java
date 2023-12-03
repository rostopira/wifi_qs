package n.eus.wifiqs;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.StatusBarManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class SetupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_activity);
        if (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            hidePermissionBlock();
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            final ViewGroup addTileBlock = findViewById(R.id.add_tile_block);
            addTileBlock.setVisibility(View.GONE);
        }
    }

    private void hidePermissionBlock() {
        final ViewGroup permissionBlock = findViewById(R.id.location_permission_block);
        permissionBlock.setVisibility(View.GONE);
    }

    public void onPermissionClicked(View button) {
        requestPermissions(new String[] { ACCESS_FINE_LOCATION }, 1);
    }

    /** @noinspection NullableProblems*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            hidePermissionBlock();
        }
    }

    @TargetApi(Build.VERSION_CODES.TIRAMISU)
    public void onAddTileClicked(View button) {
        @SuppressLint("WrongConstant")
        final StatusBarManager sbm = (StatusBarManager) getSystemService(Context.STATUS_BAR_SERVICE);
        sbm.requestAddTileService(
            new ComponentName(BuildConfig.APPLICATION_ID, BuildConfig.APPLICATION_ID + ".WifiTileService"),
            getString(R.string.wifi),
            Icon.createWithResource(this, R.drawable.rss_4),
            getMainExecutor(),
            resultCode -> {}
        );
    }

}
