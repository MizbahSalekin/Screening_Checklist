package com.example.sclreg;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import Common.Connection;
import Common.DataSyncManagement;
import Common.Global;

public class schecklist_home extends AppCompatActivity {
    static final int DATE_DIALOG = 1;
    static final int TIME_DIALOG = 2;
    private static final int UPDATEDONE = 900;
    private String S;
   // private String unit;
    private String [] unit;
    //String[] unit=Connection.split(spnFWAUnit.getSelectedItem().toString(), '-');
    Connection C;
    Global g;
    String message = "";
    int jumpTime = 0;
    android.os.Handler progressHandler = new android.os.Handler() {
        public void handleMessage(Message msg) {

            progress.incrementProgressBy(jumpTime);
            progress.setMessage(Global.getInstance().getProgressMessage());

        }

    };
    int DateID = 0;
    boolean netwoekAvailable = false;
    Location currentLocation;
    double currentLatitude, currentLongitude;
    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    SimpleAdapter mSchedule;
    LinearLayout secAll;
    TextView txtZila;
    TextView txtUpazila;
    LinearLayout ssUn;
    TextView txtUnion;
    LinearLayout ssWard;
    TextView txtWard;
    LinearLayout ssUpazilaun;
    //Spinner spnUnion;
   // Spinner spnMouza;
   // Spinner spnWardNew;
    //Spinner spnWardOld;
   // Spinner spnFWAUnit;
   // Spinner spnEPIBlock;
    //TextView VlblRegisterHA;
   // TextView VlblRegisterFWA;
   // LinearLayout secemis_home_ha;
    LinearLayout secipcsup_home;

    Integer TI = 0;
    Integer TD = 0;
    Context con;
    TextView txtVDate;
    ImageButton btnVDate;
    String VariableID;
    TextView txtHHNo;
    TextView txtPresent;
    TextView txtAbsent;
    TextView txtTotalMem;
    private ProgressDialog progress;
    private int mDay;
    private int mMonth;
    private int mYear;

    //Disabled Back/Home key
    //--------------------------------------------------------------------------------------------------
    @Override
    public boolean onKeyDown(int iKeyCode, KeyEvent event) {
        if (iKeyCode == KeyEvent.KEYCODE_BACK || iKeyCode == KeyEvent.KEYCODE_HOME) {
            return false;
        } else {
            return true;
        }
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;
            txtVDate.setText(new StringBuilder()
                    .append(Global.Right("00" + mDay, 2)).append("/")
                    .append(Global.Right("00" + mMonth, 2)).append("/")
                    .append(mYear));

            VisitedStatus(Global.DateConvertYMD(txtVDate.getText().toString()));
        }
    };

    private boolean IsUserMan_1() {
        if (g.getlevel().equalsIgnoreCase("1"))
            return true;
        else
            return false;
    }
    private boolean IsUserMan_2() {
        if (g.getlevel().equalsIgnoreCase("2"))
            return true;
        else
            return false;
    }
    private boolean IsUserMan_3() {
        if (g.getlevel().equalsIgnoreCase("3"))
            return true;
        else
            return false;
    }

    private boolean IsUserMan_4() {
        if (g.getlevel().equalsIgnoreCase("4"))
            return true;
        else
            return false;
    }
 /*   private boolean IsUserMan_4() {
        if (g.getProvType().equalsIgnoreCase("14"))
            return true;
        else
            return false;
    }
    private boolean IsUserMan_5() {
        if (g.getProvType().equalsIgnoreCase("15"))
            return true;
        else
            return false;
    }*/

    //Top menu
    //--------------------------------------------------------------------------------------------------
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnuexit, menu);

        return true;

    }

    private void Download(ProgressDialog progress, String message, android.os.Handler progressHandler, int jumpTime, boolean DownloadOnlyAppropriateRecords) {
        Global.getInstance().setProvType(g.getProvType());
        Global.getInstance().setProvCode(g.getProvCode());
        DataSyncManagement DS = new DataSyncManagement(schecklist_home.this,g.getDistrict(),g.getUpazila(),g.getProvType(),g.getProvCode(),300);
        //35
        message = "Downloading...";
        jumpTime = 1;
        Global.getInstance().setProgressMessage(message);
        progressHandler.sendMessage(progressHandler.obtainMessage());
        final String ProvCode = g.getProvCode();

        final String Dist = g.getDistrict();
        final String Upz = g.getUpazila();
        final String UN = g.getUnion();
        final String WD = g.getward();
        final String ProvType = g.getProvType();
        final String Flavel=g.getlevel();
        //List<String> VList = C.VillageList();


        String SQLStr = "";
        String Res ="";

        message = "Downloading Service Tables";
        jumpTime += 1;
        progressHandler.sendMessage(progressHandler.obtainMessage());
        //DS.Sync_MasterTables_All(Dist,Upz,ProvCode);


        jumpTime += 1;
        jumpTime += 1;
        Global.getInstance().setProgressMessage("section_1_screening_checklist_idf তথ্য ডাউনলোড হচ্ছে");
        progressHandler.sendMessage(progressHandler.obtainMessage());
        SQLStr = "Select distinct idno,slno,zillaid,upazilaid,unionid,q105,q106,q107,q108,q109,starttime,endtime,deviceid,entryuser,lat,lon,endt,modifydate,1 upload from section_1_screening_checklist_idf where";
        SQLStr += " entryuser='" + ProvCode + "'";
        Res = DS.DownloadJSON(SQLStr, "section_1_screening_checklist_idf", "idno,slno,zillaid,upazilaid,unionid,q105,q106,q107,q108,q109,starttime,endtime,deviceid,entryuser,lat,lon,endt,modifydate,upload", "idno");


        jumpTime += 1;
        jumpTime += 1;
        message = "section_2_vaccinations_Info table তথ্য ডাউনলোড হচ্ছে";
        Global.getInstance().setProgressMessage(message);
        progressHandler.sendMessage(progressHandler.obtainMessage());

        SQLStr = "Select distinct idno,q201,q202,q202a,q202b1,q202b2,q202b3,q203,q204a,q204b,q204c,q204d,q204e,q204f,q204g,q204h,q204i,q204j,q204k,q204l,q204x,q204x1,q205a,q205b,q205c,q205d,q205e,q205f,q205x,q206a,q206b,q206c,q206d,q207,q208,q209,q210,q211,starttime,endtime,deviceid,entryuser,lat,lon,endt,uploaddt,modifydate, 1 upload from section_2_vaccinations_info where ";//
        SQLStr += " entryuser='" + ProvCode + "'";
        Res = DS.DownloadJSON(SQLStr, "section_2_vaccinations_info", "idno,q201,q202,q202a,q202b1,q202b2,q202b3,q203,q204a,q204b,q204c,q204d,q204e,q204f,q204g,q204h,q204i,q204j,q204k,q204l,q204x,q204x1,q205a,q205b,q205c,q205d,q205e,q205f,q205x,q206a,q206b,q206c,q206d,q207,q208,q209,q210,q211,starttime,endtime,deviceid,entryuser,lat,lon,endt,uploaddt,modifydate,upload", "idno");

/*

        jumpTime += 1;
        jumpTime += 1;
        Global.getInstance().setProgressMessage("ক্লাস্টার তথ্য ডাউনলোড হচ্ছে");
        progressHandler.sendMessage(progressHandler.obtainMessage());
        SQLStr = "Select zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type from cluster where zillaid='" + Dist + "' and upazilaid='" + Upz+ "' and unionid='" + UN + "' and ward_no='" + WD + "'";
        Res = DS.DownloadJSON(SQLStr, "cluster", "zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type", "zillaid,upazilaid,unionid,clusterid");

        jumpTime += 1;
        jumpTime += 1;
        Global.getInstance().setProgressMessage("Code list");
        progressHandler.sendMessage(progressHandler.obtainMessage());


        SQLStr = "SELECT typename,code, cname,systementrydate,modifydate,uploaddate,1 upload FROM codelist where typename in('sc','dg1','dg2','dg3','sc1','wd')";
        Res = DS.DownloadJSON(SQLStr, "codelist", "typename,code, cname,systementrydate,modifydate,uploaddate,upload", "typename, code");

*/


        jumpTime += 1;
        jumpTime += 1;
        message = "ডাটাবেস সেটিং ডাউনলোড";
        Global.getInstance().setProgressMessage(message);
        progressHandler.sendMessage(progressHandler.obtainMessage());
        SQLStr="Select \"tableName\", \"tableScript\",\"columnList\",\"uniqueId\",\"batchSizeDown\",\"batchSizeUp\",\"syncType\",\"modifiedDate\",\"downloadType\" from \"databaseSetting\"";//downloadType
        Res = DS.DownloadJSON(SQLStr, "databaseSetting", "tableName,tableScript,columnList,uniqueId,batchSizeDown,batchSizeUp,syncType,modifiedDate,downloadType", "tableName");



        //ProviderDB
        //--------------------------------------------------------------------------------------
        jumpTime += 1;
        jumpTime += 1;
        message = "সেবা প্রদানকারীর তথ্য ডাউনলোড হচ্ছে";
        Global.getInstance().setProgressMessage(message);
        progressHandler.sendMessage(progressHandler.obtainMessage());

        SQLStr = "Select distinct divid,zillaid,upazilaid,unionid,ward,provtype,providerid,provname,mobileno,endate,exdate,active,devicesetting,systemupdatedt,healthidrequest,tablestructurerequest,areaupdate,level_id,supervisorcode,provpass,facilityname,csba,systementrydate,modifydate,uploaddate,1 upload from providerdb where ";//
        SQLStr += " providerid='" + ProvCode + "' and";
        SQLStr += " active='1'";
        Res = DS.DownloadJSON(SQLStr, "providerdb", "divid,zillaid,upazilaid,unionid,ward,provtype,providerid,provname,mobileno,endate,exdate,active,devicesetting,systemupdatedt,healthidrequest,tablestructurerequest,areaupdate,level_id,supervisorcode,provpass,facilityname,csba,systementrydate,modifydate,uploaddate,upload", "providerid");


//Login
        //--------------------------------------------------------------------------------------
        jumpTime += 1;
        jumpTime += 1;
        Global.getInstance().setProgressMessage("সেবা প্রদানকারীর কর্ম এলাকার তথ্য ডাউনলোড হচ্ছে");
        progressHandler.sendMessage(progressHandler.obtainMessage());

        //CreateTable("login","Create table login(userid varchar(20),username varchar(50),pass varchar(20),systementrydate timestamp,modifydate timestamp,uploaddate timestamp,upload integer)");

        SQLStr = "Select distinct providerid,provname,provpass,systementrydate,modifydate,uploaddate,1 upload from providerdb Where ";
        SQLStr += " providerid='" + ProvCode + "' and";
        SQLStr += " Active='1'";
        Res = DS.DownloadJSON(SQLStr, "login", "userid,username,pass,systementrydate,modifydate,uploaddate,upload", "userid");


        //Division
        //--------------------------------------------------------------------------------------
        jumpTime += 1;
        jumpTime += 1;
        Global.getInstance().setProgressMessage("বিভাগ ডাউনলোড হচ্ছে");
        progressHandler.sendMessage(progressHandler.obtainMessage());
        SQLStr = "Select id,division,divisioneng,systementrydate,modifydate,uploaddate,1 upload from division";
        Res = DS.DownloadJSON(SQLStr, "division", "id,division,divisioneng,systementrydate,modifydate,uploaddate,upload", "id");




        //District
        //--------------------------------------------------------------------------------------
        jumpTime += 1;
        jumpTime += 1;
        Global.getInstance().setProgressMessage("জেলা তথ্য ডাউনলোড হচ্ছে");
        progressHandler.sendMessage(progressHandler.obtainMessage());
        // SQLStr = "Select divid,zillaid,zillanameeng,zillaname,systementrydate,modifydate,uploaddate,1 upload from zilla where zillaid='" + Dist + "'";
        SQLStr = "Select divid,zillaid,zillanameeng,zillaname,systementrydate,modifydate,uploaddate,1 upload from zilla where zillaid='" + Dist + "'";
        Res = DS.DownloadJSON(SQLStr, "zilla", "divid,zillaid,zillanameeng,zillaname,systementrydate,modifydate,uploaddate,upload", "divid,zillaid");


        if(Flavel.equalsIgnoreCase("1"))
        {

            //Upazila
            //--------------------------------------------------------------------------------------
            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("উপজেলা তথ্য ডাউনলোড হচ্ছে");
            progressHandler.sendMessage(progressHandler.obtainMessage());
            SQLStr = "Select zillaid,upazilaid,upazilanameeng,upazilaname,systementrydate,modifydate,uploaddate,1 upload from upazila where zillaid='" + Dist + "' and upazilaid='" + Upz + "'";
            Res = DS.DownloadJSON(SQLStr, "upazila", "zillaid,upazilaid,upazilanameeng,upazilaname,systementrydate,modifydate,uploaddate,upload", "zillaid,upazilaid");


            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("ইউনিয়ন তথ্য ডাউনলোড হচ্ছে");
            progressHandler.sendMessage(progressHandler.obtainMessage());
            SQLStr = "Select distinct u.zillaid,u.upazilaid,u.municipalityid,u.unionid,u.unionnameeng,u.unionname,u.systementrydate,u.modifydate,u.uploaddate,1 upload from unions u";
            SQLStr += " where u.zillaid='" + Dist + "' and u.upazilaid='" + Upz + "'";
            Res = DS.DownloadJSON(SQLStr, "unions", "zillaid,upazilaid,municipalityid,unionid,unionnameeng,unionname,systementrydate,modifydate,uploaddate,upload", "zillaid,upazilaid,unionid");

            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("ক্লাস্টার তথ্য ডাউনলোড হচ্ছে");
            progressHandler.sendMessage(progressHandler.obtainMessage());
            SQLStr = "Select zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type from cluster where zillaid='" + Dist + "' and upazilaid='" + Upz+ "'";
            Res = DS.DownloadJSON(SQLStr, "cluster", "zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type", "zillaid,upazilaid,unionid,clusterid");
            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("Code list");
            progressHandler.sendMessage(progressHandler.obtainMessage());


            SQLStr = "SELECT typename,code, cname,systementrydate,modifydate,uploaddate,1 upload FROM codelist where typename in('sc','dg1','dg2','dg3','sc1')";
            Res = DS.DownloadJSON(SQLStr, "codelist", "typename,code, cname,systementrydate,modifydate,uploaddate,upload", "typename, code");


        }
        else if(Flavel.equalsIgnoreCase("2"))
        {

            //Upazila
            //--------------------------------------------------------------------------------------
            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("উপজেলা তথ্য ডাউনলোড হচ্ছে");
            progressHandler.sendMessage(progressHandler.obtainMessage());
            SQLStr = "Select zillaid,upazilaid,upazilanameeng,upazilaname,systementrydate,modifydate,uploaddate,1 upload from upazila where zillaid='" + Dist + "' and upazilaid='" + Upz + "'";
            Res = DS.DownloadJSON(SQLStr, "upazila", "zillaid,upazilaid,upazilanameeng,upazilaname,systementrydate,modifydate,uploaddate,upload", "zillaid,upazilaid");

            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("ইউনিয়ন তথ্য ডাউনলোড হচ্ছে");
            progressHandler.sendMessage(progressHandler.obtainMessage());
            SQLStr = "Select distinct u.zillaid,u.upazilaid,u.municipalityid,u.unionid,u.unionnameeng,u.unionname,u.systementrydate,u.modifydate,u.uploaddate,1 upload from unions u";
            SQLStr += " where u.zillaid='" + Dist + "' and u.upazilaid='" + Upz + "'"; //+ "' and unionid='" + UN
            Res = DS.DownloadJSON(SQLStr, "unions", "zillaid,upazilaid,municipalityid,unionid,unionnameeng,unionname,systementrydate,modifydate,uploaddate,upload", "zillaid,upazilaid,unionid");

            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("ক্লাস্টার তথ্য ডাউনলোড হচ্ছে");
            progressHandler.sendMessage(progressHandler.obtainMessage());
            SQLStr = "Select zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type from cluster where zillaid='" + Dist + "' and upazilaid='" + Upz+ "'"; //+ "' and unionid='" + UN
            Res = DS.DownloadJSON(SQLStr, "cluster", "zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type", "zillaid,upazilaid,unionid,clusterid");

            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("Code list");
            progressHandler.sendMessage(progressHandler.obtainMessage());


            SQLStr = "SELECT typename,code, cname,systementrydate,modifydate,uploaddate,1 upload FROM codelist where typename in('sc','dg1','dg2','dg3','sc1')";
            Res = DS.DownloadJSON(SQLStr, "codelist", "typename,code, cname,systementrydate,modifydate,uploaddate,upload", "typename, code");


        }
        else  if(Flavel.equalsIgnoreCase("3"))
        {

            //Upazila
            //--------------------------------------------------------------------------------------
            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("উপজেলা তথ্য ডাউনলোড হচ্ছে");
            progressHandler.sendMessage(progressHandler.obtainMessage());
            SQLStr = "Select zillaid,upazilaid,upazilanameeng,upazilaname,systementrydate,modifydate,uploaddate,1 upload from upazila where zillaid='" + Dist + "' and upazilaid='" + Upz + "'";
            Res = DS.DownloadJSON(SQLStr, "upazila", "zillaid,upazilaid,upazilanameeng,upazilaname,systementrydate,modifydate,uploaddate,upload", "zillaid,upazilaid");


            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("ইউনিয়ন তথ্য ডাউনলোড হচ্ছে");
            progressHandler.sendMessage(progressHandler.obtainMessage());
            SQLStr = "Select distinct u.zillaid,u.upazilaid,u.municipalityid,u.unionid,u.unionnameeng,u.unionname,u.systementrydate,u.modifydate,u.uploaddate,1 upload from unions u";
            SQLStr += " where u.zillaid='" + Dist + "' and u.upazilaid='" + Upz  + "'"; //+ "' and unionid='" + UN
            Res = DS.DownloadJSON(SQLStr, "unions", "zillaid,upazilaid,municipalityid,unionid,unionnameeng,unionname,systementrydate,modifydate,uploaddate,upload", "zillaid,upazilaid,unionid");

/*
            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("ক্লাস্টার তথ্য ডাউনলোড হচ্ছে");
            progressHandler.sendMessage(progressHandler.obtainMessage());
            SQLStr = "Select zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type from cluster where zillaid='" + Dist + "' and upazilaid='" + Upz+ "' and unionid='" + UN + "' and ward_no='" + WD + "'";
            Res = DS.DownloadJSON(SQLStr, "cluster", "zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type", "zillaid,upazilaid,unionid,clusterid");

*/

            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("ক্লাস্টার তথ্য ডাউনলোড হচ্ছে");
            progressHandler.sendMessage(progressHandler.obtainMessage());
            SQLStr = "Select zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type from cluster where zillaid='" + Dist + "' and upazilaid='" + Upz + "'"; //+ "' and unionid='" + UN
            Res = DS.DownloadJSON(SQLStr, "cluster", "zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type", "zillaid,upazilaid,unionid,clusterid");


            jumpTime += 1;
            jumpTime += 1;

            Global.getInstance().setProgressMessage("Code list");
            progressHandler.sendMessage(progressHandler.obtainMessage());


            SQLStr = "SELECT typename,code, cname,systementrydate,modifydate,uploaddate,1 upload FROM codelist where typename in('sc','dg1','dg2','dg3','sc1')";
            Res = DS.DownloadJSON(SQLStr, "codelist", "typename,code, cname,systementrydate,modifydate,uploaddate,upload", "typename, code");


        }



        message = "Finishing data sync. Please wait.";
        jumpTime += 1;
        Global.getInstance().setProgressMessage(message);
        progressHandler.sendMessage(progressHandler.obtainMessage());
        // C.GeneralProcess(progress, message,  progressHandler,  jumpTime,true);




    }

 /*   private void Download(ProgressDialog progress, String message, android.os.Handler progressHandler, int jumpTime, boolean DownloadOnlyAppropriateRecords) {
        Global.getInstance().setProvType(g.getProvType());
        Global.getInstance().setProvCode(g.getProvCode());
        DataSyncManagement DS = new DataSyncManagement(schecklist_home.this,g.getDistrict(),g.getUpazila(),g.getProvType(),g.getProvCode(),300);
        //35
        message = "Downloading...";
        jumpTime = 1;
        Global.getInstance().setProgressMessage(message);
        progressHandler.sendMessage(progressHandler.obtainMessage());
        final String ProvCode = g.getProvCode();

        final String Dist = g.getDistrict();
        final String Upz = g.getUpazila();
        final String UN = g.getUnion();
        final String ProvType = g.getProvType();
        //List<String> VList = C.VillageList();


        String SQLStr = "";
        String Res ="";

        message = "Downloading Service Tables";
        jumpTime += 1;
        progressHandler.sendMessage(progressHandler.obtainMessage());
        DS.Sync_MasterTables_All(Dist,Upz,ProvCode);



    //-----------------------------------------------------

       *//* message = "Downloading Login Credential";
        jumpTime += 1;
        Global.getInstance().setProgressMessage(message);
        progressHandler.sendMessage(progressHandler.obtainMessage());
        SQLStr = "Select distinct \"providerid\",\"provname\",\"provpass\" from \"providerdb\" Where ";
        SQLStr += " zillaid='" + Dist + "' and";
        SQLStr += " upazilaid='" + Upz + "' and";
        SQLStr += " unionid='" + UN + "' and";
        SQLStr += " \"provtype\"='" + ProvType + "' and";
        SQLStr += " \"providerid\"='" + ProvCode + "' and";
        SQLStr += " \"active\"='1'";
        Res = DS.DownloadJSON(SQLStr, "login", "userid,username,pass", "userid");
*//*

        message = "Finishing data sync. Please wait.";
        jumpTime += 1;
        Global.getInstance().setProgressMessage(message);
        progressHandler.sendMessage(progressHandler.obtainMessage());
       // C.GeneralProcess(progress, message,  progressHandler,  jumpTime,true);




    }
*/

    private void Upload(ProgressDialog progress, String message, android.os.Handler progressHandler, int jumpTime) {

        String TableName = "";
        String VariableList = "";


        message = "Uploading section_1_screening_checklist_idf";
        jumpTime += 1;
        Global.getInstance().setProgressMessage(message);
        progressHandler.sendMessage(progressHandler.obtainMessage());
        TableName = "section_1_screening_checklist_idf";
        VariableList = "idno,slno,zillaid,upazilaid,unionid,q105,q106,q107,q108,q109,starttime,endtime,deviceid,entryuser,lat,lon,endt,modifydate,upload";//
        C.UploadJSON(TableName, VariableList, "idno");

        message = "Uploading Section_2_Vaccinations_Info";
        jumpTime += 1;
        Global.getInstance().setProgressMessage(message);
        progressHandler.sendMessage(progressHandler.obtainMessage());
        TableName = "section_2_vaccinations_info";
        VariableList = "idno,q201,q202,q202a,q202b1,q202b2,q202b3,q203,q204a,q204b,q204c,q204d,q204e,q204f,q204g,q204h,q204i,q204j,q204k,q204l,q204x,q204x1,q205a,q205b,q205c,q205d,q205e,q205f,q205x,q206a,q206b,q206c,q206d,q207,q208,q209,q210,q211,starttime,endtime,deviceid,entryuser,lat,lon,endt,uploaddt,modifydate,upload";
        C.UploadJSON(TableName, VariableList, "idno");




    }

    //Toolbar toolbar;
    //Spinner spnAction;
    TextView lblAppName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            C = new Connection(this);
            g = Global.getInstance();
            setContentView(R.layout.schecklist_home);
          //  secemis_home_ha=(LinearLayout)findViewById(R.id.secemis_home_ha) ;
            secipcsup_home=(LinearLayout)findViewById(R.id.secipcsup_home) ;
            lblAppName = (TextView) findViewById(R.id.lblAppName);

            //lblAppName.setText("পরিবার কল্যাণ সহকারী ইলেক্ট্রনিক রেজিস্টার");

            //lblAppName.setText("ইলেক্ট্রনিক ফ্যামিলি প্ল্যানিং ক্লায়েন্ট সেগমেন্টেশন");
           // lblAppName.setText("Enrollment and service record Survey System");



            if (IsUserMan_1()||IsUserMan_2()||IsUserMan_3()) {
                lblAppName.setText("ই-স্ক্রীনিং চেকলিস্ট");
                secipcsup_home.setVisibility(View.VISIBLE);
            }

            /*if (IsUserHA()) {
                lblAppName.setText("স্বাস্থ্য সহকারী ইলেক্ট্রনিক রেজিস্টার");
                secemis_home_ha.setVisibility(View.VISIBLE);
            } else {
                lblAppName.setText("পরিবার কল্যাণ সহকারী ইলেক্ট্রনিক রেজিস্টার");
                secemis_home_fwa.setVisibility(View.VISIBLE);
            }
           */
            //Sync Scheduler Service Automatic: 06 Mar 2016
            //----------------------------------------------------------------------------------

          //  SyncScheduler.startOnlyIfConnectedToNetwork(getApplicationContext());

            //----------------------------------------------------------------------------------


            ((ImageButton) findViewById(R.id.cmdExit)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(schecklist_home.this);
                    adb.setTitle("বাহির");
                    adb.setMessage("আপনি কি সিস্টেম থেকে বের হতে চান?");
                    adb.setNegativeButton("না", null);
                    adb.setPositiveButton("হ্যাঁ", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent f1 = new Intent(schecklist_home.this, LoginActivity.class);
                            startActivity(f1);

                            finish();
                            //System.exit(0);
                        }
                    });
                    adb.show();
                }
            });


            ((Button) findViewById(R.id.cmdSync)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    try {
                        //Check for Internet connectivity
                        boolean netwoekAvailable = false;
                        if (Connection.haveNetworkConnection(schecklist_home.this)) {
                            netwoekAvailable = true;

                        } else {
                            netwoekAvailable = false;
                            Connection.MessageBox(schecklist_home.this, "ইন্টারনেট সংযোগ পাওয়া যাচ্ছে না।");
                            return;
                        }


                        String ResponseString = "Status:";

                        // final ProgressDialog progDailog = ProgressDialog.show(VillageList.this, "", "অপেক্ষা করুন ...", true);

                        //  new Thread() {
                        //  public void run() {
                        progress = new ProgressDialog(schecklist_home.this);

                        progress.setMessage("");
                        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progress.setIcon(R.drawable.rhislogo2);
                        progress.setIndeterminate(false);
                        progress.setCancelable(false);
                        progress.setProgress(0);

                        progress.show();

                        final Thread t = new Thread() {
                            @Override
                            public void run() {

                                String TableName = "";
                                String VariableList = "";
                                String ColumnList = "";
                                String UniqueField = "";

                                String ResponseString = "Status:";
                                String response;

                                try {

                                    message = "Data Syncing. Please Wait...";
                                    jumpTime = 1;
                                    progressHandler.sendMessage(progressHandler.obtainMessage());
                                    sleep(2);

                                     //Execute data from server
                                  //  ExecuteFunctionOnServerDBlink(progress, message, progressHandler, jumpTime);
                                    //Uploading data from server
                                    Upload(progress, message, progressHandler, jumpTime);
                                    //Downloading data from server
                                   Download(progress, message, progressHandler, jumpTime, true);


                                    progress.dismiss();


                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                //}
                                // Looper.loop();
                            }

                        };
                        t.start();
                        //Connection.MessageBox(VillageList.this,"তথ্য সফলভাবে ডাটাবেজ সার্ভারে আপলোড হয়েছে।");

                    } catch (Exception ex) {
                        Connection.MessageBox(schecklist_home.this, ex.getMessage());
                    }
                }
            });




            //C.InsertUnitWardToVillage();
            //C.InsertWardToVillage();

            turnGPSOn();

            //GPS Location
            FindLocation();




            //Start Data Sync Scheduler
            try {


            } catch (Exception ex) {
                Connection.MessageBox(schecklist_home.this, ex.getMessage());
                return;
            }

            txtZila= (TextView) findViewById(R.id.txtZila);
            txtZila.setText(" : " + C.ReturnSingleValue("select zillaid||' - '||zillaname from zilla where zillaid='" + g.getDistrict() + "'"));

            txtUpazila = (TextView) findViewById(R.id.txtUpazila);
            txtUpazila.setText(": " + C.ReturnSingleValue("select cast(upazilaid as varchar(2))||' - '||upazilaname from upazila where zillaid='" + g.getDistrict() + "' and upazilaid='" + g.getUpazila() + "'"));
            //txtUpazila.setText(" : " + C.ReturnSingleValue("select upazilaname from upazila where zillaid='" + g.getDistrict() + "' and upazilaid='" + g.getUpazila() + "'"));
            ssWard = (LinearLayout) findViewById(R.id.ssWard);
            txtWard = (TextView) findViewById(R.id.txtWard);
            if(IsUserMan_1()) {
                ssUn= (LinearLayout) findViewById(R.id.ssUn);
                ssWard = (LinearLayout) findViewById(R.id.ssWard);
                ssUn.setVisibility(View.GONE);
                ssWard.setVisibility(View.GONE);

            }

         if(IsUserMan_2()) {

                ssUn= (LinearLayout) findViewById(R.id.ssUn);
                txtUnion = (TextView) findViewById(R.id.txtUnion);
                txtUnion.setText(C.ReturnSingleValue("select cast(unionid as varchar(2))||'- ' ||unionname from unions where zillaid='" + g.getDistrict() + "' and upazilaid='" + g.getUpazila() + "' and unionid='" + g.getUnion() + "'"));
                ssUn.setVisibility(View.VISIBLE);
                ssWard = (LinearLayout) findViewById(R.id.ssWard);
                ssWard.setVisibility(View.GONE);

              }

           if(IsUserMan_3()) {


                ssUn= (LinearLayout) findViewById(R.id.ssUn);
                txtUnion = (TextView) findViewById(R.id.txtUnion);
                txtUnion.setText(C.ReturnSingleValue("select cast(unionid as varchar(2))||'- ' ||unionname from unions where zillaid='" + g.getDistrict() + "' and upazilaid='" + g.getUpazila() + "' and unionid='" + g.getUnion() + "'"));
                ssUn.setVisibility(View.VISIBLE);

                ssWard = (LinearLayout) findViewById(R.id.ssWard);
                txtWard = (TextView) findViewById(R.id.txtWard);
                txtWard.setText(C.ReturnSingleValue("select ward from providerdb where zillaid='" + g.getDistrict() + "' and upazilaid='" + g.getUpazila() + "' and unionid='" + g.getUnion() + "'"));
                ssWard.setVisibility(View.VISIBLE);
            }



            //  spnUnion = (Spinner) findViewById(R.id.spnUnion);

            //MultiUnit and Ward Wise Area

/*
            ((Button) findViewById(R.id.cmdNewReg)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    g.setIdNo("");
                    g.setCallFrom("");
                    //Intent f1 = new Intent(getApplicationContext(), iron_gps.class);
                    Intent f1 = new Intent(getApplicationContext(), Section_1_child_and_child_mother_reg_idf.class);
                    startActivity(f1);
                }
            });

            ((Button) findViewById(R.id.cmdUpdateReg)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent f1 = new Intent(getApplicationContext(), childandmother_reg_view_list.class);
                    startActivity(f1);
                }
            });*/
           // ssUn.setVisibility(View.GONE);
           // ssWard.setVisibility(View.GONE);
            ((Button) findViewById(R.id.cmdNewsSChecklist)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    g.setIdNo("");
                    g.setCallFrom("");
                    //Intent f1 = new Intent(getApplicationContext(), iron_gps.class);
                    Intent f1 = new Intent(schecklist_home.this, Section_1_Screening_Checklist_Idf.class);
                    startActivity(f1);
                }
            });

            ((Button) findViewById(R.id.cmdUpdateSChecklist)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   Intent f1 = new Intent(schecklist_home.this, scl_view_list.class);
                    startActivity(f1);
                }
            });

            //cmdNewsSChecklist
            //cmdUpdateSChecklist
            /*((Button) findViewById(R.id.cmdIPCChecklist)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    g.setIdNo("");
                    g.setCallFrom("");
                   *//* g.setDistrict(Global.Left(txtZila.getText().toString(),2));
                    g.setUpazila(Global.Left(txtUpazila.getText().toString(),2));
                    g.setUnion(Global.Left(txtUnion.getText().toString(),2));*//*
                    Intent f1 = new Intent(getApplicationContext(), Section_1_ipc_reg.class);
                    startActivity(f1);
                }
            });
            ((Button) findViewById(R.id.cmdUpdateIPC)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent f1 = new Intent(getApplicationContext(), ipc_view_list.class);
                    startActivity(f1);
                }
            });*/


        } catch (Exception ex) {
            Connection.MessageBox(schecklist_home.this, ex.getMessage());
        }
    }


    public void onResume() {
        super.onResume();

        /*
        //Start Data Sync Scheduler
        try {
            SyncScheduler.startOnlyIfConnectedToNetwork(getApplicationContext());
        }
        catch(Exception ex)
        {
            Connection.MessageBox(VillageList.this,ex.getMessage());
            return;
        }*/

    }


    private void VisitedStatus(String VisitDate) {
        txtHHNo.setText(C.ReturnSingleValue("Select Count(*)Total from Household  where strftime('%Y-%m-%d',EnDt)='" + VisitDate + "'"));
        txtTotalMem.setText(C.ReturnSingleValue("Select Count(*)Total from Member  where strftime('%Y-%m-%d',EnDt)='" + VisitDate + "'"));
    }



    //GPS Reading
    //.....................................................................................................
    @SuppressLint("MissingPermission")
    public void FindLocation() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                updateLocation(location);
            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    void updateLocation(Location location) {
        currentLocation = location;
        currentLatitude = currentLocation.getLatitude();
        currentLongitude = currentLocation.getLongitude();
    }

    //Method to turn on GPS
    public void turnGPSOn() {
        try {
            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        } catch (Exception e) {

        }
    }

    //Method to turn off the GPS
    public void turnGPSOff() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider!=null && provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    //turning off the GPS if its in on state. to avoid the battery drain.
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        turnGPSOff();
    }

    protected Dialog onCreateDialog(int id) {
        final Calendar c = Calendar.getInstance();
        Integer Y = g.mYear;
        Integer M = g.mMonth;
        Integer D = g.mDay;

        if (txtVDate.getText().length() > 0) {
            Y = Integer.valueOf(Global.Right(txtVDate.getText().toString(), 4));
            M = Integer.valueOf(txtVDate.getText().toString().substring(4, 5));
            D = Integer.valueOf(Global.Left(txtVDate.getText().toString(), 2));
        }
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mDateSetListener, Y, M - 1, D);
        }
        return null;
    }



}