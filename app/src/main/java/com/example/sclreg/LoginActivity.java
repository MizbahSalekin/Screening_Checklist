package com.example.sclreg;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.List;

import Common.Connection;
import Common.Global;
import Common.PermissionCheck;

public class LoginActivity extends AppCompatActivity {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    Connection C;
    Global g;
    boolean networkAvailable = false;
    int count = 0;
    TextView lblStaffType;
    String SystemUpdateDT = "";
    private String UpdateserverReleaseDate="";
    //public String  UpdateDT="";
    TextView AppName;
    List<String> VList;
    private ProgressDialog dialog;
    private String Password = "";
    //LocationService locationService;

    @Override
    public boolean onKeyDown(int iKeyCode, KeyEvent event) {
        if (iKeyCode == KeyEvent.KEYCODE_BACK || iKeyCode == KeyEvent.KEYCODE_HOME) {
            AlertDialog.Builder adb = new AlertDialog.Builder(LoginActivity.this);
            adb.setTitle("Close");
            adb.setMessage("আপনি কি সিস্টেম থেকে বের হতে চান?");
            adb.setNegativeButton("না", null);
            adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            adb.show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        locationService = new LocationService(this, new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//
//                for (Location location : locationResult.getLocations()) {
//                    if (location != null) {
////                        latitude = String.valueOf(location.getLatitude());
////                        longitude = String.valueOf(location.getLongitude());
////                        Log.e("LOCATION", location.getLatitude() + ", " + location.getLongitude());
//                    }
//                }
//            }
//
//            @Override
//            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
//                super.onLocationAvailability(locationAvailability);
//            }
//        });

        new PermissionCheck(this, new PermissionCheck.PermissionCallBack() {
            @Override
            public void onPermissionGranted(int permission_code, boolean isGranted) {
                if (permission_code==101){
                    Log.e("Permission", String.valueOf(isGranted));
                }
            }
        });

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // Removes notification bar

            setContentView(R.layout.login);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            C = new Connection(this);
            g = Global.getInstance();


            final EditText uid = (EditText) findViewById(R.id.userId);
            final EditText pass = (EditText) findViewById(R.id.pass);
            TextView lblSystemDate = (TextView) findViewById(R.id.lblSystemDate);
            TextView lblSystemDate1 = (TextView) findViewById(R.id.lblSystemDate1);

            //Need to update date every time whenever shared updated system
            //Format: DDMMYYYYom
            //**************************SystemUpdateDT Based on Upazila*******************************************


            SystemUpdateDT = "20/09/2024";

            lblSystemDate.setText("Version: 1.10, Release Date: " + SystemUpdateDT);
           // lblSystemDate1.setText("Version: 1.0, Release Date: " + SystemUpdateDT);
            //lblSystemDate.setTypeface(typeace_english);

            //  FontsOverride.setDefaultFont(this,"BANGLA","bangla.ttf");

            //Check for Internet connectivity
            if (Connection.haveNetworkConnection(LoginActivity.this)) {
                networkAvailable = true;
            } else {
                networkAvailable = false;
            }

            //Rebuild Database
            String TotalTab = C.ReturnSingleValue("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence'");

            if (Integer.valueOf(TotalTab) == 0) {
                if (networkAvailable) {
                    finish();
                   Intent f1 = new Intent(getApplicationContext(), SettingForm.class);
                    startActivity(f1);
                    return;
                } else {
                    Connection.MessageBox(LoginActivity.this, "Internet connection is not available for building initial database.");
                    return;
                }
            }
            /*else if (Integer.valueOf(TotalTab) > 0 & Integer.valueOf(TotalTab) < 79) {
                if (networkAvailable) {
                    String SQL = "Select \"TableName\", \"TableScript\" from \"DatabaseTab\"";
                    List<String> tableList = new ArrayList<String>();
                    tableList = C.DataListJSON(SQL);

                    for (int i = 0; i < tableList.size(); i++) {
                        String VarData[] = C.split(tableList.get(i), '^');
                        C.CreateTable(VarData[0], VarData[1]);
                    }

                } else {
                    Connection.MessageBox(LoginActivity.this, "Internet connection is not available for synchronizing database changes.");
                    return;
                }
            }*/


            //Assign Global Variable
            //String Area = C.ReturnSingleValue("Select zillaid||'^'||upazilaid||'^'||unionid||'^'||ProvType||'^'||ProvCode||'^'||ProvName from ProviderDB where Active='1'");
            String Device_no=C.ReturnSingleValue("select ifnull(deviceno,'') as deviceno from deviceno");
            String ward_no=C.ReturnSingleValue("select ifnull(ward,'0') as ward from providerdb");
            String level_id=C.ReturnSingleValue("select ifnull(level_id,'0') as ward from providerdb");
            /*String Area = C.ReturnSingleValue("Select p.zillaid||'^'||p.upazilaid||'^'||p.unionid||'^'||p.ProvType||'^'||\n" +
                    "p.ProvCode||'^'||p.ProvName||'^'||ifnull(d.id,'') as divisionId\n" +
                    "from ProviderDB p left outer join zilla j on p.zillaid=j.ZILLAID  \n" +
                    "left outer join division d on j.DIVID=d.id where p.Active=1");*/

            String Area = C.ReturnSingleValue("select p.zillaid||'^'||p.upazilaid||'^'||p.unionid||'^'||p.provtype||'^'||\n" +
                    " p.providerid||'^'||p.provname||'^'||ifnull(d.id,'') as divisionid\n" +
                    " from providerdb p \n" +
                    " left outer join zilla j on p.zillaid=j.zillaid\n" +
                    " left outer join division d on j.divid=cast(d.id as int) where p.active=1");


            String[] A = Connection.split(Area, '^');
            //g.setDivision(A[0]);
            g.setDistrict(A[0]);
            g.setUpazila(A[1]);
            g.setUnion(A[2]);
            g.setLoginProvType(A[3]);
            g.setLoginProvCode(A[4]);
            g.setProvType(A[3]);
            g.setProvCode(A[4]);
            g.setDivision(A[6]);
            g.setDeviceNo(Device_no);
            g.setward(ward_no);
            g.setlevel(level_id);


             if (g.getlevel().equalsIgnoreCase("1")||g.getlevel().equalsIgnoreCase("2")||
              g.getlevel().equalsIgnoreCase("3")) {
                AppName = (TextView) findViewById(R.id.AppName);

                AppName.setText("ই-স্ক্রীনিং চেকলিস্ট");

            }

            String DeviceNo = C.ReturnSingleValue("Select deviceno from deviceno");
            g.setDeviceNo(DeviceNo);

            uid.setText(C.ReturnSingleValue("Select userid||' - '||username from login"));
            //uid.setAdapter(C.getArrayAdapter("Select UserId||'-'||UserName from Login"));
            //uid.setSelection(Global.SpinnerItemPosition(uid, 3, C.ReturnSingleValue("Select LoginID from LastLogin")));





            //***********************************************
            //netwoekAvailable=false;
            //***********************************************
            Button btnClose = (Button) findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    finish();
                    System.exit(0);
                }
            });

            //Login -----------------------------------------------------------------------
            Button loginButton = (Button) findViewById(R.id.btnLogin);
            loginButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        if (pass.getText().toString().equals("geodata")) {
                            /*C.Save("delete from Upazila");
                            C.ExecuteSQLFromFile("upazila.txt");
                            C.Save("delete from Unions");
                            C.ExecuteSQLFromFile("unions.txt");
                            C.Save("delete from Mouza");
                            C.ExecuteSQLFromFile("mouza.txt");
                            C.Save("delete from Village");
                            C.ExecuteSQLFromFile("village.txt");
                            C.InsertUnitWardToVillage();*/
                        }



                        String[] U = Connection.split(uid.getText().toString().trim(), '-');
                        g.setUserID(U[0]);



                        //Download pass if internete connection is available


                        //stop for development
                        String str="select pass from login where userid=" + g.getProvCode() + "";
                        String passstr=C.ReturnSingleValue(str);
                        if (pass.getText().toString().equals(passstr)) {




                        }
                        else if (pass.getText().toString().equals("dev321")) {
                            //Developer can access table



                        }
                        else if(pass.getText().toString().equals(""))
                        {
                            Connection.MessageBox(LoginActivity.this,"পাসওয়ার্ড লিখুন");
                            pass.requestFocus();
                            return;
                        }
                        else
                        {
                            Connection.MessageBox(LoginActivity.this,"আপনার পাসওয়ার্ডটি সঠিক নয়, পুনরায় চেষ্টা করুন।");
                            pass.requestFocus();
                            pass.setText("");
                            return;
                        }

                        Cursor section_2_vaccinations_Info= C.ReadData("Select * from section_2_vaccinations_info limit 1");
                        if (section_2_vaccinations_Info.getColumnCount() == 59) {

                            C.Save("alter table section_2_vaccinations_info add q206d VARCHAR (254)");
                            // C.Save("alter table section_6_during_childbirth add q608 VARCHAR (1)");
                        }



                        if (Connection.haveNetworkConnection(LoginActivity.this)) {
                            networkAvailable = true;



                        } else {
                            networkAvailable = false;
                        }


                        //netwoekAvailable=false;

                        //Download Updated System
                        //...................................................................................
                        if (networkAvailable == true) {

                            //...................................................................................

                            final ProgressDialog progDailog = ProgressDialog.show(
                                    LoginActivity.this, "", "অপেক্ষা করুন ...", true);
                            new Thread() {
                                public void run() {

                                    try {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                                //C.GenerateElco();
                                                Intent f1 = new Intent(getApplicationContext(), schecklist_home.class);
                                                startActivity(f1);
                                                progDailog.dismiss();
                                            }
                                        });
                                    } catch (Exception e) {

                                    }
                                }
                            }.start();
                        }
                        // }

                        else {


                            // else {
                            //Village List Form
                            //...................................................................................
                            final ProgressDialog progDailog = ProgressDialog.show(
                                    LoginActivity.this, "", "অপেক্ষা করুন ...", true);
                            new Thread() {
                                public void run() {

                                    try {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                                //C.GenerateElco();
                                                Intent f1 = new Intent(getApplicationContext(), schecklist_home.class);
                                                startActivity(f1);
                                                progDailog.dismiss();
                                            }
                                        });
                                    } catch (Exception e) {

                                    }
                                }
                            }.start();
                            // }
                        }
                    } catch (Exception ex) {
                        //Connection.MessageBox(LoginActivity.this, ex.getMessage());
                        Connection.MessageBox(LoginActivity.this, "সার্ভার সাময়িক ভাবে বন্ধ আছে একটু পরে আবার চেষ্টা করুণ ");

                        return;
                    }
                }
            });
        } catch (Exception ex) {
            Connection.MessageBox(LoginActivity.this, ex.getMessage());
        }
    }





    //Install application
    private void InstallApplication() {
        File apkfile = new File(Environment.getExternalStorageDirectory() + File.separator + Global.NewVersionName + ".apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");

        startActivity(intent);
    }

    //Install application based on Update apk
    private void InstallApplicationUpdateApk(String UpdateDT) {
        File DSTapkfile = new File(Environment.getExternalStorageDirectory() + File.separator + UpdateDT + ".apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
        intent.setDataAndType(Uri.parse("file://" + DSTapkfile.toString()), "application/vnd.android.package-archive");

        startActivity(intent);
    }

    public void copy(File src, File dst) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }

    /*private void HealthIDDB(String D, String UP, String UN, String PT, String PC) {
        C.DownloadHealthID(D, UP, UN, PT, PC, 500);
    }*/

    /*private boolean IsUserHA() {
        String providerType = C.ReturnSingleValue("select ProvType from ProviderDB");
        if (providerType.equalsIgnoreCase("2"))
            return true;
        else
            return false;


    }*/

    //Downloading updated system from the central server
    class systemDownload extends AsyncTask<String, String, Void> {
        private Context context;

        public void setContext(Context contextf) {
            context = contextf;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Downloading Updated System...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }


        protected void onProgressUpdate(String... progress) {
            dialog.setProgress(Integer.parseInt(progress[0]));
            //publishProgress(progress);

        }

        //@Override
        protected void onPostExecute(String unused) {
            dialog.dismiss();
        }


        @Override
        protected Void doInBackground(String... arg0) {
            try {
                URL url = new URL(arg0[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.connect();
                int lenghtOfFile = c.getContentLength();

                File file = Environment.getExternalStorageDirectory();

                file.mkdirs();
                File outputFile = new File(file.getAbsolutePath() + File.separator + Global.NewVersionName + ".apk");

                if (outputFile.exists()) {
                    outputFile.delete();
                } else {
                    outputFile.createNewFile();
                }

                //UpdateDT
                /*File outputFileUpdateDT = new File(file.getAbsolutePath() + File.separator + UpdateDT + ".apk");
                if (outputFileUpdateDT.exists()) {
                    outputFileUpdateDT.delete();
                } else {
                    outputFileUpdateDT.createNewFile();
                }*/
                //FileOutputStream fos1 = new FileOutputStream(outputFileUpdateDT);

                FileOutputStream fos = new FileOutputStream(outputFile);

                InputStream is = c.getInputStream();


                byte[] buffer = new byte[1024];
                int len1 = 0;
                long total = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    //fos1.write(buffer, 0, len1);
                    count++;
                }
                fos.close();
                //fos1.close();
                is.close();


                /*File SRCapkfile = new File(file.getAbsolutePath() + File.separator + Global.NewVersionName + ".apk");
                if (SRCapkfile.isFile()) {
                    File DSTapkfile = new File(Environment.getExternalStorageDirectory() + File.separator + UpdateDT + ".apk");
                    try {
                        copy(SRCapkfile, DSTapkfile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/


                InstallApplication();

                dialog.dismiss();

            } catch (IOException e) {
                //Log.e("UpdateAPP", "Update error! " + e.getMessage());
            }
            return null;
        }
    }

    //Download Health ID
    class HealthIDDownload extends AsyncTask<String, String, Void> {
        private Context context;

        public void setContext(Context contextf) {
            context = contextf;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Downloading Health ID...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }


        protected void onProgressUpdate(String... progress) {
            dialog.setProgress(Integer.parseInt(progress[0]));
            //publishProgress(progress);

        }

        //@Override
        protected void onPostExecute(String unused) {


            dialog.dismiss();
        }


        @Override
        protected Void doInBackground(String... arg0) {
            try {
                String[] P = Connection.split(arg0[0], '^');

                //Rebuild database
                //C.RebuildDatabase(P[0], P[1], P[2], P[3], P[4]);
                //HealthIDDB(P[0], P[1], P[2], P[3], P[4]);
                dialog.dismiss();

            } catch (Exception e) {
                //Log.e("UpdateAPP", "Update error! " + e.getMessage());
            }
            return null;
        }
    }

    //Downloading updated system from the central server
//    class DownloadTextFile extends AsyncTask<String, String, Void> {
//        private Context context;
//
//        public void setContext(Context contextf) {
//            context = contextf;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new ProgressDialog(LoginActivity.this);
//            dialog.setMessage("Downloading master data file...");
//            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            dialog.setCancelable(false);
//            dialog.show();
//        }
//
//
//        protected void onProgressUpdate(String... progress) {
//            dialog.setProgress(Integer.parseInt(progress[0]));
//            //publishProgress(progress);
//
//        }
//
//        //@Override
//        protected void onPostExecute(String unused) {
//            dialog.dismiss();
//        }
//
//
//        @Override
//        protected Void doInBackground(String... arg0) {
//            try {
//                URL url = new URL(arg0[0]);
//                HttpURLConnection c = (HttpURLConnection) url.openConnection();
//                c.setRequestMethod("GET");
//                c.connect();
//                int lenghtOfFile = c.getContentLength();
//
//                File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//
//                file.mkdirs();
//                File outputFile = new File(file.getAbsolutePath() + File.separator + Global.DatabaseFolder + File.separator + arg0[1]);
//                if (outputFile.exists()) {
//                    outputFile.delete();
//                } else {
//                    outputFile.createNewFile();
//                }
//
//                FileOutputStream fos = new FileOutputStream(outputFile);
//
//                InputStream is = c.getInputStream();
//
//
//                byte[] buffer = new byte[1024];
//                int len1 = 0;
//                long total = 0;
//                while ((len1 = is.read(buffer)) != -1) {
//                    fos.write(buffer, 0, len1);
//                    count++;
//                }
//                fos.close();
//                is.close();
//
//                //String filaName = "unions.txt";
//                C.ExecuteSQLFromFile(arg0[1]);
//
//                dialog.dismiss();
//
//            } catch (IOException e) {
//                //Log.e("UpdateAPP", "Update error! " + e.getMessage());
//            }
//            return null;
//        }
//    }
}


