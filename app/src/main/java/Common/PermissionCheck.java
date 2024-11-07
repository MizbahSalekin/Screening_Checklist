package Common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionCheck {

    Context context;
    PermissionCallBack permissionCallBack;
    int LOCATION_PERMISSION_CODE = 101;
    public PermissionCheck(Context context, PermissionCallBack permissionCallBack){
        this.context = context;
        this.permissionCallBack = permissionCallBack;

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
            permissionCallBack.onPermissionGranted(LOCATION_PERMISSION_CODE, true);
        }
    }

    public interface PermissionCallBack{
        void onPermissionGranted(int permission_code, boolean isGranted);
    }
}
