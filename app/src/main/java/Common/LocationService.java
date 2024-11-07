package Common;

public class LocationService {
//    private final FusedLocationProviderClient fusedLocationClient;
//    LocationManager locationManager;
//    private final LocationRequest locationRequest;
//    private final LocationCallback locationCallback;
//    Context context;
//
//    public LocationService(Context ctx, LocationCallback locationCallback) {
//        this.context = ctx;
//        this.locationCallback = locationCallback;
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
//
//        locationRequest = LocationRequest.create();
//        locationRequest.setInterval(30000);
//        locationRequest.setFastestInterval(1000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
//
//        if (isLocationEnabled()){
//            startLocationUpdates(context);
//        }else {
//            new GpsUtils(context, locationRequest).turnGPSOn(new GpsUtils.onGpsListener() {
//                @Override
//                public void gpsStatus(boolean isGPSEnable) {
//                    if (isGPSEnable){
//                        startLocationUpdates(context);
//                    }
//                }
//            });
//        }
//
//    }
//
//    private boolean isLocationEnabled() {
//        return LocationManagerCompat.isLocationEnabled(locationManager);
//    }
//
//    public void startLocationUpdates(Context context) {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//            return;
//        }
//
//        fusedLocationClient.requestLocationUpdates(locationRequest,
//                locationCallback,
//                Looper.getMainLooper());
//    }
//
//    public void stopLocationUpdate(){
//        fusedLocationClient.removeLocationUpdates(locationCallback);
//    }
//
//    public void showSettingsAlert(final Context ctx) {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
//                ctx);
//        alertDialog.setTitle("SETTINGS");
//        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
//        alertDialog.setPositiveButton("Settings",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(
//                                Settings.ACTION_APPLICATION_SETTINGS);
//                        ctx.startActivity(intent);
//                    }
//                });
//        alertDialog.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//        alertDialog.show();
//    }

}

