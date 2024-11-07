package Common;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.sclreg.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import DataSync.DataClass;
import DataSync.DataClassProperty;
import DataSync.DownloadJSONData;
import DataSync.DownloadLMISJSONData;
import DataSync.DownloadRequestClass;
import DataSync.UploadJSONData;
import DataSync.UploadLMISJSONData;
import DataSync.downloadClass;
//com.example..pwc


///update from tanvir
//--------------------------------------------------------------------------------------------------
// Created by TanvirHossain on 17/03/2015.
//--------------------------------------------------------------------------------------------------

public class Connection extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DB_NAME = Global.DatabaseName;
    //private static final String DBLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + Global.DatabaseFolder + File.separator + DB_NAME;

    // Todo table name
    private static final String TABLE_TODO = "todo_items";

    public Context dbContext;
    public static Context ud_context;
    public Connection(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        dbContext = context;
        ud_context=context;
        try {


        } catch (Exception ex) {

        }
    }

    //Split function
    //----------------------------------------------------------------------------------------------
    public static String[] split(String s, char separator) {
        ArrayList<String> d = new ArrayList<String>();
        for (int ini = 0, end = 0; ini <= s.length(); ini = end + 1) {
            end = s.indexOf(separator, ini);
            if (end == -1) {
                end = s.length();
            }

            String st = s.substring(ini, end).trim();


            if (st.length() > 0) {
                d.add(st);
            } else {
                d.add("");
            }
        }

        String[] temp = new String[d.size()];
        temp = d.toArray(temp);
        return temp;
    }

    //Message Box
    //----------------------------------------------------------------------------------------------
    public static void MessageBox(Context ClassName, String Msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ClassName);
        builder
                .setMessage(Msg)
                .setTitle("মেসেজ")
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", null);
        builder.show();

    }

    //Check whether internet connectivity available or not
    //----------------------------------------------------------------------------------------------
    public static boolean haveNetworkConnection(Context con) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        } catch (Exception e) {

        }
        return haveConnectedWifi || haveConnectedMobile;
    }



    // Creating our initial tablesSave("delete from delivery");
    // These is where we need to write create table statements.
    // This is called when database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("Create Table abc(sid varchar(10))");
    }


    // Upgrading the database between versions
    // This method is called when database is upgraded like modifying the table structure,
    // adding constraints to database, etc
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {
            // Wipe older tables if existed
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            // Create tables again
            onCreate(db);
        }
    }

    //Check the existence of database table
    //----------------------------------------------------------------------------------------------
    public boolean TableExists(String TableName) {
        Cursor c = null;
        boolean tableExists = false;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            c = db.rawQuery("Select * from " + TableName, null);
            tableExists = true;
            c.close();
            db.close();
        } catch (Exception e) {
        }
        return tableExists;
    }

    //Create database tables
    //----------------------------------------------------------------------------------------------
    public void CreateTable(String TableName, String SQL) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (!TableExists(TableName)) {
            db.execSQL(SQL);
        }
    }

    //Read data from database and return to Cursor variable
    //----------------------------------------------------------------------------------------------
    public Cursor ReadData(String SQL) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(SQL, null);
        return cur;
    }

    //Check existence of data in database
    //----------------------------------------------------------------------------------------------
    public boolean Existence(String SQL) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(SQL, null);
        if (cur.getCount() == 0) {
            cur.close();
            db.close();
            return false;
        } else {
            cur.close();
            db.close();
            return true;
        }
    }

    //Return single result based on the SQL query
    //----------------------------------------------------------------------------------------------
    public String ReturnSingleValue(String SQL) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(SQL, null);
        String retValue = "";
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            retValue = cur.getString(0);
            cur.moveToNext();
        }
        cur.close();
        db.close();
        return retValue;
    }

    //Save/Update/Delete data in to database
    //----------------------------------------------------------------------------------------------
    public void Save(String SQL) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL);
        db.close();
    }

    public String SaveData(String SQL) {
        SQLiteDatabase db = this.getWritableDatabase();
        String resp = "";
        try {
            db.execSQL(SQL);
        }catch (Exception ex1){
            resp=ex1.getMessage();
        }
        finally {
            db.close();
        }
        return resp;
    }

    public String SaveDataTransaction(String SQL) {
        SQLiteDatabase database = this.getWritableDatabase();
        String resp = "";
        try {
            database.execSQL("PRAGMA synchronous=OFF");
            database.setLockingEnabled(false);
            database.beginTransaction();
            database.execSQL(SQL);
            database.setTransactionSuccessful();
        }catch (Exception ex1){
            resp=ex1.getMessage();
        }
        finally {
            database.endTransaction();
            database.setLockingEnabled(true);
            database.execSQL("PRAGMA synchronous=NORMAL");
            database.close();
        }
        return resp;
    }

    //Generate data list
    //----------------------------------------------------------------------------------------------
    public List<String> getDataList(String SQL) {
        List<String> data = new ArrayList<String>();
        Cursor cursor = ReadData(SQL);
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return data;
    }

    //Array adapter for spinner item
    //----------------------------------------------------------------------------------------------
    public ArrayAdapter<String> getArrayAdapter(String SQL) {
        List<String> dataList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.dbContext,
                R.layout.multiline_spinner_dropdown_item, dataList);

        return dataAdapter;
    }

    public ArrayAdapter<String> getArrayAdapterMultiline(String SQL) {
        List<String> dataList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.dbContext,
                R.layout.multiline_spinner_dropdown_item, dataList);
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter.createFromResource(this.dbContext, dataList,
        //   R.layout.multiline_spinner_dropdown_item);
        return dataAdapter;
    }


    //Find the variable positions in an array list
    //----------------------------------------------------------------------------------------------
    public int VarPosition(String VariableName, String[] ColumnList) {
        int pos = 0;
        for (int i = 0; i < ColumnList.length; i++) {
            if (VariableName.trim().equalsIgnoreCase(ColumnList[i].toString().trim())) {
                pos = i;
                i = ColumnList.length;
            }
        }
        return pos;
    }


    // Getting array list for Upload with ^ separator from Cursor
    //----------------------------------------------------------------------------------------------
    public String[] GenerateArrayList(String VariableList, String TableName) {
        Cursor cur_H;
        // cur_H = ReadData("Select "+ VariableList +" from "+ TableName);
        cur_H = ReadData("Select " + VariableList + " from " + TableName + " where upload='2'");
        cur_H.moveToFirst();
        String[] Data = new String[cur_H.getCount()];
        String DataList = "";
        String[] Count = VariableList.toString().split(",");
        int RecordCount = 0;

        while (!cur_H.isAfterLast()) {
            for (int c = 0; c < Count.length; c++) {
                if (c == 0) {
                    if (cur_H.getString(c) == null)
                        DataList = "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList = "";
                    else
                        DataList = cur_H.getString(c).toString();

                } else {
                    if (cur_H.getString(c) == null)
                        DataList += "^" + "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList += "^" + "";
                    else
                        DataList += "^" + cur_H.getString(c).toString();
                }
            }
            Data[RecordCount] = DataList;
            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return Data;
    }

    // Getting result from database server based on SQL
    //----------------------------------------------------------------------------------------------
    public String ReturnResult(String MethodName, String SQL) {
        ReturnResult r = new ReturnResult();
        String response = "";
        r.Method_Name = MethodName;
        r.SQLStr = SQL;
        try {
            response = r.execute("").get();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace();
        }
        return response;
    }

    public String ReturnSingleValueJSON(String SQL) {
        DownloadRequestClass dr = new DownloadRequestClass();
        dr.setmethodname("ReturnSingleValue");
        dr.setSQL(SQL);
        Gson gson = new Gson();
        String json = gson.toJson(dr);

        //For Web Api
        //--------------------------------------------------------------------------------------
        DownloadJSONData dload = new DownloadJSONData();
        String response = null;
        try {
            response = dload.execute(json).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String DataStringJSON(String SQL) {
        DownloadRequestClass dr = new DownloadRequestClass();
        dr.setmethodname("Existence");
        dr.setSQL(SQL);
        Gson gson = new Gson();
        String json = gson.toJson(dr);

        //For Web Api
        //--------------------------------------------------------------------------------------
        DownloadJSONData dload = new DownloadJSONData();
        String response = null;
        try {
            response = dload.execute(json).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }

    public List<String> DataListJSON(String SQL) {
        DownloadRequestClass dr = new DownloadRequestClass();
        dr.setmethodname("DownloadData");
        dr.setSQL(SQL);
        Gson gson = new Gson();
        String json = gson.toJson(dr);

        //For Web Api
        //--------------------------------------------------------------------------------------
        DownloadJSONData dload = new DownloadJSONData();
        String response = null;
        try {
            response = dload.execute(json).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<String> data = new ArrayList<String>();
        downloadClass responseData = (downloadClass) gson.fromJson(response, downloadClass.class);
        data = responseData.getdata();
        return data;
    }


    //Rebuild Local Database from Server
//----------------------------------------------------------------------------------------------

   // final String Flavel,final String WD
    public void RebuildDatabase(final String Flavel,final String Dist,final String Upz, String UN,final String WD, final String ProvType,final String ProvCode, ProgressDialog progress, String message, Handler progressHandler, int jumpTime, boolean DownloadOnlyAppropriateRecords) {
        Global.getInstance().setProvType(ProvType);
        Global.getInstance().setProvCode(ProvCode);
        jumpTime = 2;
        Global.getInstance().setProgressMessage("(1) প্রয়োজনীয় ডাটাবেস নির্মাণ হচ্ছে");
        progressHandler.sendMessage(progressHandler.obtainMessage());



        //Create Database Tables
        //------------------------------------------------------------------------------------------
       // String SQL = "Select \"tableName\", \"tableScript\" from \"databaseSetting\" where \"downloadType\"='1'";
        String SQL = "Select \"tableName\", \"tableScript\" from \"databaseSetting\" where \"downloadType\"='1'";
       // String SQL = "Select tablename, tablescript from databasesetting )";


        List<String> tableList = new ArrayList<String>();
        tableList = DataListJSON(SQL);

        for (int i = 0; i < tableList.size(); i++) {
            String VarData[] = split(tableList.get(i), '^');
            CreateTable(VarData[0], VarData[1]);
        }

        //------------------------------------------------------------------------------------------
        //Data Sync: Download data from server
        //------------------------------------------------------------------------------------------
        String Res = "";
        String SQLStr = "";

        try {
            final DataSyncManagement DS = new DataSyncManagement(ud_context,Dist,Upz,ProvType,ProvCode,300);

            //Clean Data_Sync_Management into Server
            jumpTime += 1;
            jumpTime += 1;
            message = "(2) প্রয়োজনীয় ডাটাবেস নির্মাণ হচ্ছে";
            Global.getInstance().setProgressMessage(message);
            progressHandler.sendMessage(progressHandler.obtainMessage());
            ExecuteFunctionOnServer("delete from data_sync_management where \"provCode\"='" + ProvCode + "'");

            //DatabaseSetting


            jumpTime += 1;
            jumpTime += 1;
            message = "ডাটাবেস সেটিং ডাউনলোড";
            Global.getInstance().setProgressMessage(message);
            progressHandler.sendMessage(progressHandler.obtainMessage());
            SQLStr="Select \"tableName\", \"tableScript\",\"columnList\",\"uniqueId\",\"batchSizeDown\",\"batchSizeUp\",\"syncType\",\"modifiedDate\",\"downloadType\" from \"databaseSetting\"";
            Res = DS.DownloadJSON(SQLStr, "databaseSetting", "tableName,tableScript,columnList,uniqueId,batchSizeDown,batchSizeUp,syncType,modifiedDate,downloadType", "tableName");



            //DeviceNo
            jumpTime += 1;
            jumpTime += 1;
            message = "ডাটাবেস সেটিং ডাউনলোড";
            Global.getInstance().setProgressMessage(message);
            progressHandler.sendMessage(progressHandler.obtainMessage());
            Save("Insert or Replace into DeviceNo(DeviceNo)Values('" + (ProvCode) + "')");

            jumpTime += 1;
            jumpTime += 1;
            Global.getInstance().setProgressMessage("ডিভাইস প্রস্তুত করা হচ্ছে");
            progressHandler.sendMessage(progressHandler.obtainMessage());



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

            CreateTable("login","Create table login(userid varchar(20),username varchar(50),pass varchar(20),systementrydate timestamp,modifydate timestamp,uploaddate timestamp,upload integer)");

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
            Res = DS.DownloadJSON(SQLStr, "section_2_vaccinations_info", "idno,q201,q202,q202a,q202b1,q202b2,q202b3,q203,q204a,q204b,q204c,q204d,q204e,q204f,q204g,q204h,q204i,q204j,q204k,q204l,q204x,q204x1,q205a,q205b,q205c,q205d,q205d,q205e,q205f,q205x,q206a,q206b,q206c,q207,q208,q209,q210,q211,starttime,endtime,deviceid,entryuser,lat,lon,endt,uploaddt,modifydate,upload", "idno");



            //Unions
            //--------------------------------------------------------------------------------------

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


                SQLStr = "SELECT typename,code, cname,systementrydate,modifydate,uploaddate,1 upload FROM codelist where typename in('sc','dg1','dg2','dg3','sc1','wd')";
                Res = DownloadJSON(SQLStr, "codelist", "typename,code, cname,systementrydate,modifydate,uploaddate,upload", "typename, code");


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
                SQLStr += " where u.zillaid='" + Dist + "' and u.upazilaid='" + Upz + "'";//
                Res = DS.DownloadJSON(SQLStr, "unions", "zillaid,upazilaid,municipalityid,unionid,unionnameeng,unionname,systementrydate,modifydate,uploaddate,upload", "zillaid,upazilaid,unionid");

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


                SQLStr = "SELECT typename,code, cname,systementrydate,modifydate,uploaddate,1 upload FROM codelist where typename in('sc','dg1','dg2','dg3','sc1','wd')";
                Res = DownloadJSON(SQLStr, "codelist", "typename,code, cname,systementrydate,modifydate,uploaddate,upload", "typename, code");


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
                SQLStr += " where u.zillaid='" + Dist + "' and u.upazilaid='" + Upz + "'";//+ "' and unionid='" + UN
                Res = DS.DownloadJSON(SQLStr, "unions", "zillaid,upazilaid,municipalityid,unionid,unionnameeng,unionname,systementrydate,modifydate,uploaddate,upload", "zillaid,upazilaid,unionid");



    /*            jumpTime += 1;
                jumpTime += 1;
                Global.getInstance().setProgressMessage("ক্লাস্টার তথ্য ডাউনলোড হচ্ছে");
                progressHandler.sendMessage(progressHandler.obtainMessage());
                SQLStr = "Select zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type from cluster where zillaid='" + Dist + "' and upazilaid='" + Upz + "'";
                Res = DS.DownloadJSON(SQLStr, "cluster", "zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type", "zillaid,upazilaid,unionid,clusterid");
*/

                jumpTime += 1;
                jumpTime += 1;
                Global.getInstance().setProgressMessage("ক্লাস্টার তথ্য ডাউনলোড হচ্ছে");
                progressHandler.sendMessage(progressHandler.obtainMessage());
                SQLStr = "Select zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type from cluster where zillaid='" + Dist + "' and upazilaid='" + Upz + "'";
                Res = DS.DownloadJSON(SQLStr, "cluster", "zillaid,district_name,upazilaid,upazila_name,unionid,union_name,ward_no,epi_sub_block,clusterid,epi_cluster_name,year,month_1,month_2,month_3,month_4,month_5,month_6,month_7,month_8,month_9,month_10,month_11,month_12,hh_from,hh_to,fac_type", "zillaid,upazilaid,unionid,clusterid");




                jumpTime += 1;
                jumpTime += 1;

                Global.getInstance().setProgressMessage("Code list");
                progressHandler.sendMessage(progressHandler.obtainMessage());


                SQLStr = "SELECT typename,code, cname,systementrydate,modifydate,uploaddate,1 upload FROM codelist where typename in('sc','dg1','dg2','dg3','sc1','wd')";
                Res = DS.DownloadJSON(SQLStr, "codelist", "typename,code, cname,systementrydate,modifydate,uploaddate,upload", "typename, code");


            }

            else  if(Flavel.equalsIgnoreCase("0"))
            {
                jumpTime += 1;
                jumpTime += 1;
                Global.getInstance().setProgressMessage("Code list");
                progressHandler.sendMessage(progressHandler.obtainMessage());


                SQLStr = "SELECT typename,code, cname,systementrydate,modifydate,uploaddate,1 upload FROM codelist where typename in('sc','dg1','dg2','dg3','sc1','wd')";
                Res = DS.DownloadJSON(SQLStr, "codelist", "typename,code, cname,systementrydate,modifydate,uploaddate,upload", "typename, code");


            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public String JoinQueryForAppropriateRecords(String sql, String tableName, String provCode) {

    /*    //workPlanMaster
        if (tableName.equals("workPlanMaster")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    //+ "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = (case when coalesce(cast(C.\"modifyDate\" as text),'')='' then  coalesce(cast(C.\"systemEntryDate\" as text),'') else coalesce(cast(C.\"modifyDate\" as text),'') end) and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"workPlanId\" as text)|| cast(C.\"providerId\" as text)|| cast(C.\"month\" as text))";
        }
        //workPlanDetail
        if (tableName.equals("workPlanDetail")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    //+ "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"workPlanDate\" as text),'') and  \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = (case when coalesce(cast(C.\"modifyDate\" as text),'')='' then  coalesce(cast(C.\"workPlanDate\" as text),'') else coalesce(cast(C.\"modifyDate\" as text),'') end) and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"workPlanId\" as text)|| cast(C.\"item\" as text)|| cast(C.\"workPlanDate\" as text)|| cast(C.\"providerId\" as text))";
        }*/

        //workPlanMaster
      /*  if (tableName.equals("workPlanMaster")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"workPlanId\" as text)|| cast(C.\"providerId\" as text)|| cast(C.\"month\" as text))";
        }
        //workPlanId, item, workPlanDate, providerId
        //workPlanDetail
        if (tableName.equals("workPlanDetail")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"workPlanId\" as text)|| cast(C.\"item\" as text)|| cast(C.\"workPlanDate\" as text)|| cast(C.\"providerId\" as text))";
        }
        //epiSchedulerUpdate
        // Dist,Upz,UN,wordOld,subBlockId,scheduleYear,schedulerId
        if (tableName.equals("epiSchedulerUpdate")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"Dist\" as text)|| cast(C.\"Upz\" as text)|| cast(C.\"UN\" as text)|| cast(C.\"wordOld\" as text)|| cast(C.\"subBlockId\" as text)|| cast(C.\"scheduleYear\" as text)|| cast(C.\"schedulerId\" as text))";
        }
*/

        if (tableName.equals("elcoevent")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"evcode\" as text))";
        }
       /* if (tableName.equals("classfication")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"classficationCode\" as text))";
        }
        if (tableName.equals("treatment")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"treatmentCode\" as text))";
        }
        if (tableName.equals("symtom")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"symtomCode\" as text))";
        }
        if (tableName.equals("adoSymtom")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"problemCode\" as text))";
        }

        if (tableName.equals("deathReason")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"deathReasonId\" as text))";
        }*/
        if (tableName.equals("ocplist")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"ocpcode\" as text))";
        }
        if (tableName.equals("brandmethod")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"brandcode\" as text))";
        }
        if (tableName.equals("attendantdesignation")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"attendantcode\" as text))";
        }
        if (tableName.equals("codelist")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"typename\" as text)|| cast(C.\"Code\" as text))";
        }
        if (tableName.equals("providertype")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"typename\" as text))";
        }
       /* if (tableName.equals("month")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"id\" as text))";
        }*/
       /* if (tableName.equals("fpaItem")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"itemCode\" as text)|| cast(C.\"type\" as text))";
        }*/
        if (tableName.equals("fwaunit")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"ucode\" as text))";
        }
       /* if (tableName.equals("HABlock")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"BCode\" as text))";
        }
        if (tableName.equals("item")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"itemCode\" as text))";
        }
        if (tableName.equals("currentStock")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"providerId\" as text)|| cast(C.\"itemCode\" as text))";
        }
        if (tableName.equals("immunization")) {
            sql += " " + " where not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"imuCode\" as text))";
        }*/

       /* if (tableName.equals("Household")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(h.\"EnDt\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(h.\"Dist\" as text)|| cast(h.\"Upz\" as text)|| cast(h.\"UN\" as text)|| cast(h.\"Mouza\" as text)|| cast(h.\"Vill\" as text)|| cast(h.\"HHNo\" as text))";
        }

        if (tableName.equals("Member")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(h.\"EnDt\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(h.\"Dist\" as text)|| cast(h.\"Upz\" as text)|| cast(h.\"UN\" as text)|| cast(h.\"Mouza\" as text)|| cast(h.\"Vill\" as text)|| cast(h.\"HHNo\" as text)|| cast(h.\"SNo\" as text))";
        }

        if (tableName.equals("elco")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text))";
        }

        if (tableName.equals("elcoVisit")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"visit\" as text))";
        }



        if (tableName.equals("client_segmentation")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifydate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthid\" as text)|| cast(C.\"visit\" as text))";
        }



        if (tableName.equals("fpInfo")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text))";
        }*/

    /*    if (tableName.equals("household")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(h.\"modifydate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(h.\"zillaid\" as text)|| cast(h.\"upazilaid\" as text)|| cast(h.\"unionid\" as text)|| cast(h.\"mouzaid\" as text)|| cast(h.\"villageid\" as text)|| cast(h.\"hhno\" as text))";
        }
*/

        if (tableName.equals("household")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(h.\"endt\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(h.\"zillaid\" as text)|| cast(h.\"upazilaid\" as text)|| cast(h.\"unionid\" as text)|| cast(h.\"mouzaid\" as text)|| cast(h.\"villageid\" as text)|| cast(h.\"hhno\" as text))";
        }


        if (tableName.equals("member")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(h.\"endt\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(h.\"zillaid\" as text)|| cast(h.\"upazilaid\" as text)|| cast(h.\"unionid\" as text)|| cast(h.\"mouzaid\" as text)|| cast(h.\"villageid\" as text)|| cast(h.\"hhno\" as text)|| cast(h.\"sno\" as text))";
        }

        if (tableName.equals("elco")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifydate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthid\" as text))";
        }

        if (tableName.equals("elcovisit")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifydate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthid\" as text)|| cast(C.\"visit\" as text))";
        }
        if (tableName.equals("client_segmentation")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifydate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthid\" as text)|| cast(C.\"visit\" as text))";
        }

        if (tableName.equals("service_record")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifydate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthid\" as text)|| cast(C.\"visit\" as text))";
        }



        if (tableName.equals("fpinfo")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifydate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthid\" as text))";
        }

        if (tableName.equals("clientmap")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifydate\" as text),'') and \n"
                    //"\"modifyDate\" = coalesce(cast(C.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"generatedid\" as text))";
        }


        if (tableName.equals("pregWomen")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"pregNo\" as text))";
        }
        if (tableName.equals("pregDangerSign")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"pregNo\" as text))";
        }

        if (tableName.equals("PregRefer")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"pregNo\" as text)|| cast(C.\"serviceId\" as text))";
        }

        if (tableName.equals("ancService")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"pregNo\" as text)|| cast(C.\"serviceId\" as text))";
        }
        if (tableName.equals("delivery")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"pregNo\" as text))";
        }

        if (tableName.equals("newBorn")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"pregNo\" as text)|| cast(C.\"childNo\" as text))";
        }
        if (tableName.equals("pncServiceChild")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"pregNo\" as text)|| cast(C.\"childNo\" as text)|| cast(C.\"serviceId\" as text))";
        }

        if (tableName.equals("pncServiceMother")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"pregNo\" as text)|| cast(C.\"serviceId\" as text))";
        }

       /* if (tableName.equals("stockTransaction")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"providerId\" as text)|| cast(C.\"transactionId\" as text)|| cast(C.\"transactionType\" as text)|| cast(C.\"itemCode\" as text))";
        }
        if (tableName.equals("immunizationHistory")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"imuCode\" as text)|| cast(C.\"imuDose\" as text))";
        }

        if (tableName.equals("imuAdversEvent")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"imuCode\" as text)|| cast(C.\"imuDose\" as text))";
        }

        if (tableName.equals("death")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text))";
        }
        if (tableName.equals("adolescent")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"providerId\" as text)|| cast(C.\"visitDate\" as text))";
        }

        if (tableName.equals("adolescentProblem")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    //+ "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"providerId\" as text)|| cast(C.\"visitDate\" as text)|| cast(C.\"problemCode\" as text))";
        }
        if (tableName.equals("under5Child")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"providerId\" as text)|| cast(C.\"visitDate\" as text))";
        }
        if (tableName.equals("under5ChildProblem")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"providerId\" as text)|| cast(C.\"visitDate\" as text)|| cast(C.\"problemCode\" as text)|| cast(C.\"classficationCode\" as text))";
        }




        if (tableName.equals("under5ChildAdvice")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"providerId\" as text)|| cast(C.\"visitDate\" as text)|| cast(C.\"adviceCode\" as text)|| cast(C.\"classficationCode\" as text))";
        }

        if (tableName.equals("ses")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"enDt\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"dist\" as text)|| cast(C.\"upz\" as text)|| cast(C.\"un\" as text)|| cast(C.\"mouza\" as text)|| cast(C.\"vill\" as text)|| cast(C.\"hhNo\" as text))";
        }


        if (tableName.equals("hhno_repository")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(h.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(h.\"Dist\" as text)|| cast(h.\"Upz\" as text)|| cast(h.\"UN\" as text)|| cast(h.\"Mouza\" as text)|| cast(h.\"Vill\" as text)|| cast(h.\"HHNo\" as text))";
        }
*/

       /* if (tableName.equals("Visits")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(h.\"enDt\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(h.dist as text)|| cast(h.Upz as text)|| cast(h.UN as text)|| cast(h.Mouza as text)|| cast(h.Vill as text)|| cast(h.\"hhNo\" as text)|| cast(h.\"vDate\" as text))";
        }

        if (tableName.equals("childNutrition")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"visit\" as text))";
        }

        if (tableName.equals("motherNutrition")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"visit\" as text))";
        }

        if (tableName.equals("womanInjectable")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)||cast(C.\"doseId\" as text))";
            //Primary key of womanInjectable table C."healthId",C."providerId",C."doseId".Need to discussion with SCI
        }


        //DownloadJSON(sql, "vaccineCause", VariableList, "healthId,causeOfVaccineDT");
        if (tableName.equals("vaccineCause")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text)|| cast(C.\"causeOfVaccineDT\" as text))";

        }




        if (tableName.equals("itemRequest")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"requestId\" as text)|| cast(C.\"requestBy\" as text)|| cast(C.\"requestTo\" as text)|| cast(C.\"itemCode\" as text))";

        }

        if (tableName.equals("epiMasterWoman")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text))";
        }

        if (tableName.equals("epiMaster")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text))";
        }

        if (tableName.equals("sessionMasterWomanUpdate")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(A.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(A.\"Dist\" as text)|| cast(A.\"Upz\" as text)|| cast(A.\"UN\" as text)|| cast(A.\"wardOld\" as text)|| cast(A.\"subBlockId\" as text)|| cast(A.\"scheduleYear\" as text)|| cast(A.\"schedulerId\" as text)|| cast(A.\"providerId\" as text))";


        }

        if (tableName.equals("sessionMasterUpdate")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(A.\"modifyDate\" as text),'') and \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(A.\"Dist\" as text)|| cast(A.\"Upz\" as text)|| cast(A.\"UN\" as text)|| cast(A.\"wardOld\" as text)|| cast(A.\"subBlockId\" as text)|| cast(A.\"scheduleYear\" as text)|| cast(A.\"schedulerId\" as text)|| cast(A.\"providerId\" as text))";

        }

        if (tableName.equals("migrationOut")) {
            sql += " " + " and not exists(select \"recordId\" from  data_sync_management where "
                    + "\"tableName\"='" + tableName + "' and \n"
                    + "coalesce(cast(\"modifyDate\" as text),'') = coalesce(cast(C.\"modifyDate\" as text),'') and  \n"
                    + "\"provCode\"=" + provCode + " and \n"
                    + "\"recordId\"=cast(C.\"healthId\" as text))";
        }

*/
        return sql;
    }





    //To get the list of columns(string) in table
    //----------------------------------------------------------------------------------------------
    @SuppressLint("Range")
    public String GetColumnList(String TableName) {
        String CList = "";
        Cursor cur_H;
        cur_H = ReadData("pragma table_info('" + TableName + "')");

        cur_H.moveToFirst();
        int RecordCount = 0;

        while (!cur_H.isAfterLast()) {
            if (RecordCount == 0)
                CList += cur_H.getString(cur_H.getColumnIndex("name"));
            else
                CList += "," + cur_H.getString(cur_H.getColumnIndex("name"));

            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return CList;
    }

    //To get the list of columns(string array) in table
    //----------------------------------------------------------------------------------------------
    public String[] GetColumnListArray(String TableName) {
        Cursor cur = ReadData("SELECT * FROM " + TableName + " WHERE 0");
        String[] columnNames;
        try {
            columnNames = cur.getColumnNames();
        } finally {
            cur.close();
        }
        return columnNames;
    }

    public void ExecuteFunctionOnServer(String SQL) {
        ReturnSingleValueJSON(SQL);
    }

    public int TotalRecordCount(String TableName) {
        return Integer.parseInt(this.ReturnSingleValue("Select COUNT(*) AS TotalRecords from " + TableName + " WHERE Upload='2'"));
    }

    public List<DataClassProperty> GetDataListJSON(String VariableList, String TableName, String UniqueField, int limit_of_records, int offset_settings) {

      /*//  Save("PRAGMA journal_mode = TRUNCATE");
        Save("PRAGMA auto_vacuum = 1");
        Save("PRAGMA locking_mod = EXCLUSIVE");*/
        Cursor cur_H;
        if(TableName.equalsIgnoreCase("service_record"))
      {
          cur_H = ReadData("Select " + VariableList + " from " + TableName + " WHERE Upload='2' and healthid is not null LIMIT " + limit_of_records);

      }

      else {
          cur_H = ReadData("Select " + VariableList + " from " + TableName + " WHERE Upload='2' LIMIT " + limit_of_records);

      }
        cur_H.moveToFirst();
        List<DataClassProperty> data = new ArrayList<DataClassProperty>();
        DataClassProperty d;

        String DataList = "";

        String[] Count = VariableList.toString().replace(" ", "").split(",");
        String[] UField = UniqueField.toString().replace(" ", "").split(",");
        int RecordCount = 0;

        String WhereClause = "";
        String VarData[];
        int varPos = 0;
        while (!cur_H.isAfterLast()) {
            //Prepare Data List
            for (int c = 0; c < Count.length; c++) {
                if (c == 0) {
                    if (cur_H.getString(c) == null)
                        DataList = "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList = "";
                    else
                        DataList = cur_H.getString(c).toString();

                } else {
                    if (cur_H.getString(c) == null)
                        DataList += "^" + "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList += "^" + "";
                    else
                        DataList += "^" + cur_H.getString(c).toString();
                }
            }

            //Prepare Where Clause
            //VarData = DataList.split("\\^");
            VarData = Connection.split(DataList,'^');//.split("\\^");
            varPos = 0;


            for (int j = 0; j < UField.length; j++) {
                varPos = VarPosition(UField[j].toString(), Count);
                if (j == 0) {
                    WhereClause = "\"" + UField[j].toString() + "\"=" + "'" + VarData[varPos].toString() + "'";
                } else {
                    WhereClause += " and \"" + UField[j].toString() + "\"=" + "'" + VarData[varPos].toString() + "'";
                }
            }

            d = new DataClassProperty();
            d.setdatalist(DataList);
            d.setuniquefieldwithdata(WhereClause);
            data.add(d);

            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return data;
    }




    ///New DownloadJSON
    //----------------------------------------------------------------------------------------------
    public String DownloadJSON(String SQL, String TableName, String ColumnList, String UniqueField) {
        DownloadRequestClass dr = new DownloadRequestClass();
        dr.setmethodname("DownloadData");
        dr.setSQL(SQL);
        Gson gson = new Gson();
        String json = gson.toJson(dr);

        int varPos = 0;
        int varPos_modifyDate = 0;

        String response = "";
        String resp = "";
        String IDNO = "";
        String SaveResp = "";
        try {
            //Download from server
            //--------------------------------------------------------------------------------------
            DownloadJSONData dload = new DownloadJSONData();
            //response = dload.execute(json).get();
            response = new DownloadJSONData().execute(json).get();

            downloadClass responseData = (downloadClass) gson.fromJson(response, downloadClass.class);

            //--------------------------------------------------------------------------------------
            String UField[]  = UniqueField.toString().replace(" ", "").split(",");
            String VarList[] = ColumnList.toString().replace(" ", "").split(",");

            List<DataClassProperty> dataTemp = new ArrayList<DataClassProperty>();
            List<DataClassProperty> data = new ArrayList<DataClassProperty>();
            DataClassProperty d;
            String DataList = "";

            String modify_Date = "";
            //Integer insertBatchSize = responseData.getdata().size()/100 + 1;
            if (responseData != null) {
                SQL = "Insert or replace into "+ TableName +"("+ ColumnList +")Values";
                for (int i = 0; i < responseData.getdata().size(); i++) {
                    String VarData[] = Connection.split(responseData.getdata().get(i).toString(), '^');

                    //Generate Unique ID
                    //------------------------------------------------------------------------------
                    for (int j = 0; j < UField.length; j++) {
                        varPos = VarPosition(UField[j].toString(), VarList);
                        if (j == 0) {
                            IDNO += VarData[varPos].toString();
                        } else {
                            IDNO += VarData[varPos].toString();
                        }
                    }

                    //Variable Position: modifyDate
                    if(TableName.equalsIgnoreCase("Member")||TableName.equalsIgnoreCase("Household")||TableName.equalsIgnoreCase("ses")||TableName.equalsIgnoreCase("visits")) {
                        varPos_modifyDate = VarPosition("EnDt", VarList);
                        modify_Date = VarData[varPos_modifyDate].toString().replace("null", "");
                    }else{
                        varPos_modifyDate = VarPosition("modifyDate", VarList);
                        modify_Date = VarData[varPos_modifyDate].toString().replace("null", "");
                    }

                    //------------------------------------------------------------------------------
                    if (i == 0) {
                        SQL += "('" + responseData.getdata().get(i).toString().replace("^","','").replace("null","") +"')";
                    } else {
                        SQL += ",('" + responseData.getdata().get(i).toString().replace("^","','").replace("null","") +"')";
                    }

                    String PT = Global.getInstance().getProvType();
                    String PC = Global.getInstance().getProvCode();

                    //Populate class with data for sync_management
                    //------------------------------------------------------------------------------
                    DataList = TableName + "^" + IDNO + "^" + modify_Date + "^" + PT + "^" + PC;
                    d = new DataClassProperty();
                    d.setdatalist(DataList);
                    d.setuniquefieldwithdata("" +
                            "\"tableName\"  ='" + TableName + "' and " +
                            "\"recordId\"   ='" + IDNO + "' and " +
                            "\"modifyDate\" ='" + modify_Date + "' and " +
                            "\"provType\"   ='" + PT + "' and " +
                            "\"provCode\"   ='" + PC + "'");
                    dataTemp.add(d);

                    IDNO = "";
                }
                SaveResp = SaveData(SQL);
                if(SaveResp.length()==0){
                    data = dataTemp;
                }

                //Update data on sync management
                //------------------------------------------------------------------------------
                DataClass dt = new DataClass();
                dt.setmethodname("UploadData_Sync");
                dt.settablename("data_sync_management");
                dt.setcolumnlist("tableName,recordId,modifyDate,provType,provCode");
                dt.setdata(data);

                Gson gson1   = new Gson();
                String json1 = gson1.toJson(dt);
                String response1  = new UploadJSONData().execute(json1).get();
            }

        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }
        return resp;
    }

    //Download LMIS Json
    //----------------------------------------------------------------------------------------------
    public String DownloadLMISJSON(String SQL, String TableName, String ColumnList, String UniqueField) {
        DownloadRequestClass dr = new DownloadRequestClass();
        dr.setmethodname("DownloadData");
        dr.setSQL(SQL);
        Gson gson = new Gson();
        String json = gson.toJson(dr);

        int varPos = 0;
        int varPos_modifyDate = 0;

        String response = "";
        String resp = "";
        String IDNO = "";
        String SaveResp = "";
        try {
            //Download from server
            //--------------------------------------------------------------------------------------
            DownloadLMISJSONData dload = new DownloadLMISJSONData();
            //response = dload.execute(json).get();
            response = new DownloadLMISJSONData().execute(json).get();

            downloadClass responseData = (downloadClass) gson.fromJson(response, downloadClass.class);

            //--------------------------------------------------------------------------------------
            String UField[]  = UniqueField.toString().replace(" ", "").split(",");
            String VarList[] = ColumnList.toString().replace(" ", "").split(",");

            List<DataClassProperty> dataTemp = new ArrayList<DataClassProperty>();
            List<DataClassProperty> data = new ArrayList<DataClassProperty>();
            DataClassProperty d;
            String DataList = "";

            String modify_Date = "";
            //Integer insertBatchSize = responseData.getdata().size()/100 + 1;
            if (responseData != null) {
                SQL = "Insert or replace into "+ TableName +"("+ ColumnList +")Values";
                for (int i = 0; i < responseData.getdata().size(); i++) {
                    String VarData[] = Connection.split(responseData.getdata().get(i).toString(), '^');

                    //Generate Unique ID
                    //------------------------------------------------------------------------------
                    for (int j = 0; j < UField.length; j++) {
                        varPos = VarPosition(UField[j].toString(), VarList);
                        if (j == 0) {
                            IDNO += VarData[varPos].toString();
                        } else {
                            IDNO += VarData[varPos].toString();
                        }
                    }

                    //Variable Position: modifyDate
                    if(TableName.equalsIgnoreCase("Member")||TableName.equalsIgnoreCase("Household")||TableName.equalsIgnoreCase("ses")||TableName.equalsIgnoreCase("visits")) {
                        varPos_modifyDate = VarPosition("EnDt", VarList);
                        modify_Date = VarData[varPos_modifyDate].toString().replace("null", "");
                    }else{
                        varPos_modifyDate = VarPosition("modifyDate", VarList);
                        modify_Date = VarData[varPos_modifyDate].toString().replace("null", "");
                    }

                    //------------------------------------------------------------------------------
                    if (i == 0) {
                        SQL += "('" + responseData.getdata().get(i).toString().replace("^","','").replace("null","") +"')";
                    } else {
                        SQL += ",('" + responseData.getdata().get(i).toString().replace("^","','").replace("null","") +"')";
                    }

                    String PT = Global.getInstance().getProvType();
                    String PC = Global.getInstance().getProvCode();

                    //Populate class with data for sync_management
                    //------------------------------------------------------------------------------
                    DataList = TableName + "^" + IDNO + "^" + modify_Date + "^" + PT + "^" + PC;
                    d = new DataClassProperty();
                    d.setdatalist(DataList);
                    d.setuniquefieldwithdata("" +
                            "\"tableName\"  ='" + TableName + "' and " +
                            "\"recordId\"   ='" + IDNO + "' and " +
                            "\"modifyDate\" ='" + modify_Date + "' and " +
                            "\"provType\"   ='" + PT + "' and " +
                            "\"provCode\"   ='" + PC + "'");
                    dataTemp.add(d);

                    IDNO = "";
                }
                SaveResp = SaveData(SQL);
                if(SaveResp.length()==0){
                    data = dataTemp;
                }

                //Update data on sync management
                //------------------------------------------------------------------------------
                DataClass dt = new DataClass();
                dt.setmethodname("UploadData_Sync");
                dt.settablename("data_sync_management");
                dt.setcolumnlist("tableName,recordId,modifyDate,provType,provCode");
                dt.setdata(data);

                Gson gson1   = new Gson();
                String json1 = gson1.toJson(dt);
                String response1  = new UploadLMISJSONData().execute(json1).get();
            }

        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }
        return resp;
    }


    public String ExecuteCommandOnServerJSON(List<String> SQLList) {
        ExecuteCommandClass dr = new ExecuteCommandClass();
        dr.setmethodname("ExecuteSQLList");
        dr.setSQL(SQLList);
        dr.setSecutiryCodeL("org.postgresql");
        Gson gson = new Gson();
        String json = gson.toJson(dr);

        DownloadJSONData dload = new DownloadJSONData();
        String response = null;
        try {
            response = dload.execute(json).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }
    //Upload Data to Server (Soap Service/Web API)

    public static String SelectedSpinnerValue(String SelectedTest, String SplitValue) {
        String[] D = SelectedTest.split(SplitValue);
        return D[0];
    }
    public static int SpinnerItemPositionAnyLength(Spinner spn, String Value)
    {
        //String SelectedSpinnerValue;
        int pos = 0;
        if(Value.length()!=0)
        {
            for(int i=0;i<spn.getCount();i++)
            {
                if(spn.getItemAtPosition(i).toString().length()!=0)
                {
                    if(SelectedSpinnerValue(spn.getItemAtPosition(i).toString(),"-").equalsIgnoreCase(Value))
                    {
                        pos = i;
                        i   = spn.getCount();
                    }
                }
            }
        }
        return pos;
    }

    public List<String> WorkPlanMonthList() {
       List<String> Monthlist = new ArrayList<String>();
       Cursor cur = ReadData("Select ifnull(month,'')||':'||ifnull(providerId,'') from workPlanMaster");
       String retValue = "";
       cur.moveToFirst();
       while (!cur.isAfterLast()) {
           Monthlist.add(cur.getString(0));

           cur.moveToNext();
       }
       cur.close();
       return Monthlist;
   }

    //for data sync by village: 22 nov 2016: tanvir
    public List<String> VillageList() {
        List<String> vlist = new ArrayList<String>();
        Cursor cur = ReadData("Select zillaid||':'||upazilaid||':'||unionid||':'||mouzaid||':'||villageid from Village");
        String retValue = "";
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            vlist.add(cur.getString(0));

            cur.moveToNext();
        }
        cur.close();
        return vlist;
    }

    public List<String> VillageWiseRestofHH() {
        List<String> vlist = new ArrayList<String>();
        String SQL="select village.zillaid||':'||village.upazilaid||':'||village.unionid||':'||village.mouzaid||':'||village.villageid\n" +
                "||':'||count(hhno_repository.HHNo) as HHNo \n" +
                "from village \n" +
                "left  join hhno_repository on village.zillaid=hhno_repository.dist and\n" +
                "village.upazilaid=hhno_repository.upz and\n" +
                "village.unionid=hhno_repository.un and\n" +
                "village.mouzaid=hhno_repository.mouza and\n" +
                "village.villageid=hhno_repository.vill\n" +
                "and hhno_repository.status=1\n" +
                "group by village.zillaid,village.upazilaid,village.unionid,village.mouzaid,village.villageid\n" +
                "having count(hhno_repository.HHNo)<70";
        Cursor cur = ReadData(SQL);
        String retValue = "";
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            vlist.add(cur.getString(0));

            cur.moveToNext();
        }
        cur.close();
        return vlist;
    }

    public List<String> RemoveSpecialCharForNameList() {
        List<String> CharForNamelist = new ArrayList<String>();
        Cursor cur = ReadData("Select nameeng||':'||Dist||':'||Upz||':'||UN||':'||Mouza||':'||Vill||':'||HHNo||':'||SNo from Member where nameeng glob '*[^ .a-zA-Z]*'");
        String retValue = "";
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            CharForNamelist.add(cur.getString(0));

            cur.moveToNext();
        }
        cur.close();
        return CharForNamelist;
    }

    public List<String> RemoveSpecialCharForFNameList() {
        List<String> CharForFNamelist = new ArrayList<String>();
        Cursor cur = ReadData("Select Father||':'||Dist||':'||Upz||':'||UN||':'||Mouza||':'||Vill||':'||HHNo||':'||SNo  from Member where Father glob '*[^ .a-zA-Z]*'");
        String retValue = "";
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            CharForFNamelist.add(cur.getString(0));

            cur.moveToNext();
        }
        cur.close();
        return CharForFNamelist;
    }

    public List<String> RemoveSpecialCharForMNameList() {
        List<String> CharForMNamelist = new ArrayList<String>();
        Cursor cur = ReadData("Select Mother||':'||Dist||':'||Upz||':'||UN||':'||Mouza||':'||Vill||':'||HHNo||':'||SNo  from Member where Mother glob '*[^ .a-zA-Z]*'");
        String retValue = "";
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            CharForMNamelist.add(cur.getString(0));

            cur.moveToNext();
        }
        cur.close();
        return CharForMNamelist;
    }

    public int batchSizeDown(String TableName)
    {
        return Integer.parseInt(ReturnSingleValue("select batchSizeDown from databasesetting where tableName='" + TableName + "'"));
    }

    public int batchSizeUp(String TableName)
    {
        return Integer.parseInt(ReturnSingleValue("select batchSizeDown from databasesetting where tableName='" + TableName + "'"));
    }
    //Download Data from Server (Soap Service/Web API)
    public String DownloadJSON_InsertOnly(String SQL, String TableName, String ColumnList, String UniqueField) {
        DownloadRequestClass dr = new DownloadRequestClass();
        dr.setmethodname("DownloadData");
        dr.setSQL(SQL);
        Gson gson = new Gson();
        String json = gson.toJson(dr);

        String WhereClause = "";
        int varPos = 0;

        String response = "";
        String resp = "";
        String IDNO = "";
        try {
            //For Web Api
            //--------------------------------------------------------------------------------------
            DownloadJSONData dload = new DownloadJSONData();
            response = dload.execute(json).get();
            downloadClass responseData = (downloadClass) gson.fromJson(response, downloadClass.class);
            //--------------------------------------------------------------------------------------


            String UField[] = UniqueField.toString().replace(" ", "").split(",");
            String VarList[] = ColumnList.toString().replace(" ", "").split(",");
            String InsertSQL = "";

            List<String> dataStatus = new ArrayList<String>();
            List<DataClassProperty> data = new ArrayList<DataClassProperty>();
            DataClassProperty d;
            String DataList = "";

            String modify_Date = "";
            if (responseData != null) {
                for (int i = 0; i < responseData.getdata().size(); i++) {
                    String VarData[] = split(responseData.getdata().get(i).toString(), '^');

                    //Generate where clause
                    SQL = "";
                    WhereClause = "";
                    varPos = 0;
                    for (int j = 0; j < UField.length; j++) {
                        varPos = VarPosition(UField[j].toString(), VarList);
                        if (j == 0) {
                            WhereClause = UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                            IDNO += VarData[varPos].toString();
                        } else {
                            WhereClause += " and " + UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                            IDNO += VarData[varPos].toString();
                        }
                    }

                    for (int r = 0; r < VarList.length; r++) {
                        if (r == 0) {
                            SQL = "Insert into " + TableName + "(" + ColumnList + ")Values(";
                            SQL += "'" + VarData[r].toString() + "'";
                        } else {
                            SQL += ",'" + VarData[r].toString() + "'";
                        }
                        if(TableName.equalsIgnoreCase("Member")||TableName.equalsIgnoreCase("Household")||TableName.equalsIgnoreCase("ses")||TableName.equalsIgnoreCase("visits")) {
                            if ("EnDt".equalsIgnoreCase(VarList[r])) {
                                modify_Date = VarData[r].toString().replace("null", "");
                            }
                        }
                        else
                        {
                            if ("modifyDate".equalsIgnoreCase(VarList[r])) {
                                modify_Date = VarData[r].toString().replace("null", "");
                            }
                        }
                    }
                    SQL += ")";

                    try{
                        Save(SQL);
                    }
                    catch (Exception ex){

                        Log.e("Error from Connection", ex.getMessage());
                        //Connection.MessageBox(Connection.this, ex.getMessage());
                        //return;

                    }
                    //Populate class with data for sync_management
                    //------------------------------------------------------------------------------
                    String PT = Global.getInstance().getProvType();
                    String PC = Global.getInstance().getProvCode();

                    DataList = TableName + "^" + IDNO + "^" + modify_Date + "^" + PT + "^" + PC;
                    d = new DataClassProperty();
                    d.setdatalist(DataList);
                    d.setuniquefieldwithdata("" +
                            "\"tableName\"  ='" + TableName + "' and " +
                            "\"recordId\"   ='" + IDNO + "' and " +
                            "\"modifyDate\" ='" + modify_Date + "' and " +
                            "\"provType\"   ='" + PT + "' and " +
                            "\"provCode\"   ='" + PC + "'");
                    data.add(d);

                    IDNO = "";

                }

                //Update data on sync management
                //------------------------------------------------------------------------------
                DataClass dt = new DataClass();
                //Insert or Update
                dt.setmethodname("UploadData_For_Sync");
                //Insert only
                //dt.setmethodname("UploadData_Sync_Management");
                dt.settablename("data_sync_management");
                dt.setcolumnlist("tableName,recordId,modifyDate,provType,provCode");
                dt.setdata(data);

                Gson gson1   = new Gson();
                String json1 = gson1.toJson(dt);
                String response1 = "";

                try{
                    response1 = new UploadJSONData().execute(json1).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }

        return resp;
    }

    //Date: 31 Jul 2017 : Tanvir
    //----------------------------------------------------------------------------------------------
    public void UploadJSON(String TableName, String ColumnList, String UniqueFields) {
        int totalRecord = this.TotalRecordCount(TableName);
        int BatchSize = 0;


        BatchSize = Integer.parseInt(this.ReturnSingleValue("select batchsizeup AS Total from databasesetting   WHERE tableName = '" + TableName + "'"));

        int totalBatch = (totalRecord / BatchSize) + 1;
        DataClass dt;

        List<DataClassProperty> data;
        String SQLUpdateStatement = "";

        for (int i = 0; i < totalBatch; i++) {
            data = GetDataListJSON(ColumnList, TableName, UniqueFields, BatchSize, 0);
            if(data.size()>0) {
                dt = new DataClass();
                dt.setmethodname("UploadData_Sync");
                dt.settablename(TableName);
                dt.setcolumnlist(ColumnList);
                dt.setdata(data);

                Gson gson = new Gson();
                String json = gson.toJson(dt);
                try {
                    String response = new UploadJSONData().execute(json).get();

                    //Process Response
                    if(response.length()>0) {
                        downloadClass d = new downloadClass();
                        Type collType = new TypeToken<downloadClass>() {
                        }.getType();
                        downloadClass responseData = (downloadClass) gson.fromJson(response, collType);

                        for (int j = 0; j < responseData.getdata().size(); j++) {
                            SQLUpdateStatement += "Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(j).toString() + ";";
                        }
                        Save(SQLUpdateStatement);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Date: 9 August 2017 : Fazlu
    //----------------------------------------------------------------------------------------------
    public void UploadLMISJSON(String TableName, String ColumnList, String UniqueFields) {
        int totalRecord = this.TotalRecordCount(TableName);
        int BatchSize = 0;
        BatchSize = Integer.parseInt(this.ReturnSingleValue("select batchsizeup AS Total from databasesetting   WHERE tableName = '" + TableName + "'"));

        int totalBatch = (totalRecord / BatchSize) + 1;
        DataClass dt;

        List<DataClassProperty> data;
        String SQLUpdateStatement = "";

        for (int i = 0; i < totalBatch; i++) {
            data = GetDataListJSON(ColumnList, TableName, UniqueFields, BatchSize, 0);
            if(data.size()>0) {
                dt = new DataClass();
                dt.setmethodname("UploadData_Sync");
                dt.settablename(TableName);
                dt.setcolumnlist(ColumnList);
                dt.setdata(data);

                Gson gson = new Gson();
                String json = gson.toJson(dt);
                try {
                    String response = new UploadLMISJSONData().execute(json).get();

                    //Process Response
                    if(response.length()>0) {
                        downloadClass d = new downloadClass();
                        Type collType = new TypeToken<downloadClass>() {
                        }.getType();
                        downloadClass responseData = (downloadClass) gson.fromJson(response, collType);

                        for (int j = 0; j < responseData.getdata().size(); j++) {
                            SQLUpdateStatement += "Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(j).toString() + ";";
                        }
                        Save(SQLUpdateStatement);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void UploadJSONworkPlanMaster(String TableName, String ColumnList, String UniqueFields, String Month, String providerId, int total_records_in_table) {
        // int total_records_in_table = this.TotalRecordCount(TableName);
        int limit_of_records = total_records_in_table;
        int offset_settings = 0;


        DataClass dt = new DataClass();
         dt.setmethodname("UploadData_Sync");
        dt.settablename(TableName);
        dt.setcolumnlist(ColumnList);
        List<DataClassProperty> data = GetDataListJSONworkPlanMaster(ColumnList, TableName, UniqueFields, Month, providerId, limit_of_records, offset_settings);
        dt.setdata(data);

        Gson gson = new Gson();
        String json = gson.toJson(dt);
        String response = "";

        //Web Service(asmx)
        //------------------------------------------------------------------------------------------
        //UploadDataJSON u = new UploadDataJSON();

        //Web APIs(java)
        //------------------------------------------------------------------------------------------
        UploadJSONData u = new UploadJSONData();

        try {
            response = u.execute(json).get();

            //Process Response
            downloadClass d = new downloadClass();
            Type collType = new TypeToken<downloadClass>() {
            }.getType();
            downloadClass responseData = (downloadClass) gson.fromJson(response, collType);


            for (int i = 0; i < responseData.getdata().size(); i++) {
                // String s ="Update " + TableName + " Set Upload='2' where " + responseData.getdata().get(i).toString();
                Save("Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString());


            }

        } catch (Exception e) {
            //  Toast.makeText(dbContext, "All data did not upload. Try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void UploadJSONworkPlanMaster(String TableName, String ColumnList, String UniqueFields, String Month, String providerId) {
        int total_records_in_table = this.TotalRecordCount(TableName);
        int records_to_sync_at_a_time = Integer.parseInt(this.ReturnSingleValue("select batchsizeup AS Total from databasesetting   WHERE tableName = '" + TableName + "'"));
        //int records_to_sync_at_a_time = Integer.parseInt(this.ReturnSingleValue("SELECT  dataLength AS Total FROM DataLengthTable  WHERE tableName = '" + TableName + "'"));
        if (records_to_sync_at_a_time <= 0) {
            records_to_sync_at_a_time = 20;
        }
        int limit_of_records = records_to_sync_at_a_time;
        int offset_settings = 0;
        int total_sent = 0;

        if (total_records_in_table == 0)
            return;

        if (total_records_in_table <= records_to_sync_at_a_time) {
            UploadJSONworkPlanMaster(TableName, ColumnList, UniqueFields, Month, providerId, total_records_in_table);
            return;
        }


        int total_count = total_records_in_table / records_to_sync_at_a_time;

        for (int start = 0; start <= total_count; start = start + 1) {
            if ((total_records_in_table - total_sent) <= records_to_sync_at_a_time) {
                UploadJSONworkPlanMaster(TableName, ColumnList, UniqueFields, Month, providerId, total_records_in_table);
                return;
            }

            DataClass dt = new DataClass();
             dt.setmethodname("UploadData_Sync");
            dt.settablename(TableName);
            dt.setcolumnlist(ColumnList);
            List<DataClassProperty> data = GetDataListJSONworkPlanMaster(ColumnList, TableName, UniqueFields, Month, providerId, limit_of_records, offset_settings);
            dt.setdata(data);

            Gson gson = new Gson();
            String json = gson.toJson(dt);
            String response = "";

            //Web Service(asmx)
            //------------------------------------------------------------------------------------------
            //UploadDataJSON u = new UploadDataJSON();

            //Web APIs(java)
            //------------------------------------------------------------------------------------------
            UploadJSONData u = new UploadJSONData();

            try {
                response = u.execute(json).get();

                //Process Response
                downloadClass d = new downloadClass();
                Type collType = new TypeToken<downloadClass>() {
                }.getType();
                downloadClass responseData = (downloadClass) gson.fromJson(response, collType);


                for (int i = 0; i < responseData.getdata().size(); i++) {
                    // String s ="Update " + TableName + " Set Upload='2' where " + responseData.getdata().get(i).toString();
                    Save("Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString());
                    total_sent = total_sent + 1;

                }

            } catch (Exception e) {
                //  Toast.makeText(dbContext, "All data did not upload. Try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            offset_settings = limit_of_records + offset_settings;
            // return response;
        }


    }


    //workPlanDetail
    // Month,providerId

    public void UploadJSONworkPlanDetail(String TableName, String ColumnList, String UniqueFields, String Month, String providerId, int total_records_in_table) {
        // int total_records_in_table = this.TotalRecordCount(TableName);
        int limit_of_records = total_records_in_table;
        int offset_settings = 0;


        DataClass dt = new DataClass();
         dt.setmethodname("UploadData_Sync");
        dt.settablename(TableName);
        dt.setcolumnlist(ColumnList);
        List<DataClassProperty> data = GetDataListJSONworkPlanDetail(ColumnList, TableName, UniqueFields, Month, providerId, limit_of_records, offset_settings);
        dt.setdata(data);

        Gson gson = new Gson();
        String json = gson.toJson(dt);
        String response = "";

        //Web Service(asmx)
        //------------------------------------------------------------------------------------------
        //UploadDataJSON u = new UploadDataJSON();

        //Web APIs(java)
        //------------------------------------------------------------------------------------------
        UploadJSONData u = new UploadJSONData();

        try {
            response = u.execute(json).get();

            //Process Response
            downloadClass d = new downloadClass();
            Type collType = new TypeToken<downloadClass>() {
            }.getType();
            downloadClass responseData = (downloadClass) gson.fromJson(response, collType);


            for (int i = 0; i < responseData.getdata().size(); i++) {
                // String s ="Update " + TableName + " Set Upload='2' where " + responseData.getdata().get(i).toString();
                Save("Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString());


            }

        } catch (Exception e) {
            //  Toast.makeText(dbContext, "All data did not upload. Try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void UploadJSONworkPlanDetail(String TableName, String ColumnList, String UniqueFields, String Month, String providerId) {
        int total_records_in_table = this.TotalRecordCount(TableName);
        //int records_to_sync_at_a_time = Integer.parseInt(this.ReturnSingleValue("SELECT  dataLength AS Total FROM DataLengthTable  WHERE tableName = '" + TableName + "'"));
        int records_to_sync_at_a_time = Integer.parseInt(this.ReturnSingleValue("select batchsizeup AS Total from databasesetting   WHERE tableName = '" + TableName + "'"));
        if (records_to_sync_at_a_time <= 0) {
            records_to_sync_at_a_time = 20;
        }
        int limit_of_records = records_to_sync_at_a_time;
        int offset_settings = 0;
        int total_sent = 0;

        if (total_records_in_table == 0)
            return;

        if (total_records_in_table <= records_to_sync_at_a_time) {
            UploadJSONworkPlanDetail(TableName, ColumnList, UniqueFields, Month, providerId, total_records_in_table);
            return;
        }


        int total_count = total_records_in_table / records_to_sync_at_a_time;

        for (int start = 0; start <= total_count; start = start + 1) {
            if ((total_records_in_table - total_sent) <= records_to_sync_at_a_time) {
                UploadJSONworkPlanDetail(TableName, ColumnList, UniqueFields, Month, providerId, total_records_in_table);
                return;
            }

            DataClass dt = new DataClass();
             dt.setmethodname("UploadData_Sync");
            dt.settablename(TableName);
            dt.setcolumnlist(ColumnList);
            List<DataClassProperty> data = GetDataListJSONworkPlanDetail(ColumnList, TableName, UniqueFields, Month, providerId, limit_of_records, offset_settings);
            dt.setdata(data);

            Gson gson = new Gson();
            String json = gson.toJson(dt);
            String response = "";

            //Web Service(asmx)
            //------------------------------------------------------------------------------------------
            //UploadDataJSON u = new UploadDataJSON();

            //Web APIs(java)
            //------------------------------------------------------------------------------------------
            UploadJSONData u = new UploadJSONData();

            try {
                response = u.execute(json).get();

                //Process Response
                downloadClass d = new downloadClass();
                Type collType = new TypeToken<downloadClass>() {
                }.getType();
                downloadClass responseData = (downloadClass) gson.fromJson(response, collType);


                for (int i = 0; i < responseData.getdata().size(); i++) {
                    // String s ="Update " + TableName + " Set Upload='2' where " + responseData.getdata().get(i).toString();
                    Save("Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString());
                    total_sent = total_sent + 1;

                }

            } catch (Exception e) {
                //  Toast.makeText(dbContext, "All data did not upload. Try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            offset_settings = limit_of_records + offset_settings;
            // return response;
        }


    }

    //New code 10/31/2016
    //NotApproved

    public void UploadJSONworkPlanDetailNotApproved(String TableName, String ColumnList, String UniqueFields, String Month, String providerId, String status, int total_records_in_table) {
        // int total_records_in_table = this.TotalRecordCount(TableName);
        int limit_of_records = total_records_in_table;
        int offset_settings = 0;


        DataClass dt = new DataClass();
         dt.setmethodname("UploadData_Sync");
        dt.settablename(TableName);
        dt.setcolumnlist(ColumnList);
        List<DataClassProperty> data = GetDataListJSONworkPlanDetailNotApproved(ColumnList, TableName, UniqueFields, Month, providerId, status, limit_of_records, offset_settings);
        dt.setdata(data);

        Gson gson = new Gson();
        String json = gson.toJson(dt);
        String response = "";

        //Web Service(asmx)
        //------------------------------------------------------------------------------------------
        //UploadDataJSON u = new UploadDataJSON();

        //Web APIs(java)
        //------------------------------------------------------------------------------------------
        UploadJSONData u = new UploadJSONData();

        try {
            response = u.execute(json).get();

            //Process Response
            downloadClass d = new downloadClass();
            Type collType = new TypeToken<downloadClass>() {
            }.getType();
            downloadClass responseData = (downloadClass) gson.fromJson(response, collType);


            for (int i = 0; i < responseData.getdata().size(); i++) {
                // String s ="Update " + TableName + " Set Upload='2' where " + responseData.getdata().get(i).toString();
                Save("Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString());


            }

        } catch (Exception e) {
            //  Toast.makeText(dbContext, "All data did not upload. Try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void UploadJSONworkPlanDetailNotApproved(String TableName, String ColumnList, String UniqueFields, String Month, String providerId, String status) {
        int total_records_in_table = this.TotalRecordCount(TableName);
        int records_to_sync_at_a_time = Integer.parseInt(this.ReturnSingleValue("select batchsizeup AS Total from databasesetting   WHERE tableName = '" + TableName + "'"));
        //int records_to_sync_at_a_time = Integer.parseInt(this.ReturnSingleValue("SELECT  dataLength AS Total FROM DataLengthTable  WHERE tableName = '" + TableName + "'"));
        if (records_to_sync_at_a_time <= 0) {
            records_to_sync_at_a_time = 20;
        }
        int limit_of_records = records_to_sync_at_a_time;
        int offset_settings = 0;
        int total_sent = 0;

        if (total_records_in_table == 0)
            return;

        if (total_records_in_table <= records_to_sync_at_a_time) {
            UploadJSONworkPlanDetailNotApproved(TableName, ColumnList, UniqueFields, Month, providerId, status, total_records_in_table);
            return;
        }


        int total_count = total_records_in_table / records_to_sync_at_a_time;

        for (int start = 0; start <= total_count; start = start + 1) {
            if ((total_records_in_table - total_sent) <= records_to_sync_at_a_time) {
                UploadJSONworkPlanDetailNotApproved(TableName, ColumnList, UniqueFields, Month, providerId, status, total_records_in_table);
                return;
            }

            DataClass dt = new DataClass();
             dt.setmethodname("UploadData_Sync");
            dt.settablename(TableName);
            dt.setcolumnlist(ColumnList);
            List<DataClassProperty> data = GetDataListJSONworkPlanDetailNotApproved(ColumnList, TableName, UniqueFields, Month, providerId, status, limit_of_records, offset_settings);
            dt.setdata(data);

            Gson gson = new Gson();
            String json = gson.toJson(dt);
            String response = "";

            //Web Service(asmx)
            //------------------------------------------------------------------------------------------
            //UploadDataJSON u = new UploadDataJSON();

            //Web APIs(java)
            //------------------------------------------------------------------------------------------
            UploadJSONData u = new UploadJSONData();

            try {
                response = u.execute(json).get();

                //Process Response
                downloadClass d = new downloadClass();
                Type collType = new TypeToken<downloadClass>() {
                }.getType();
                downloadClass responseData = (downloadClass) gson.fromJson(response, collType);


                for (int i = 0; i < responseData.getdata().size(); i++) {
                    // String s ="Update " + TableName + " Set Upload='2' where " + responseData.getdata().get(i).toString();
                    Save("Update " + TableName + " Set Upload='1' where " + responseData.getdata().get(i).toString());
                    total_sent = total_sent + 1;

                }

            } catch (Exception e) {
                //  Toast.makeText(dbContext, "All data did not upload. Try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            offset_settings = limit_of_records + offset_settings;
            // return response;
        }


    }

    public List<DataClassProperty> GetDataListJSONworkPlanDetailNotApproved(String VariableList, String TableName, String UniqueField, String Month, String providerId, String status, int limit_of_records, int offset_settings) {

  /*//  Save("PRAGMA journal_mode = TRUNCATE");
    Save("PRAGMA auto_vacuum = 1");
    Save("PRAGMA locking_mod = EXCLUSIVE");*/

 /*   select * from workPlanMaster A
    INNER JOIN workPlanDetail B ON A.workPlanId = B.workPlanId
    WHERE  A.month='2016-01' and A.providerId='93002'*/

        // cur_H = ReadData("Select "+ VariableList +" from "+ TableName +" s1 inner join Section2 s2 inner join Section3 s3 inner join Section4 s4 inner join Section5 s5 inner join Section6 s6 inner join Section7 s7 inner join Section8 s8 inner join Section9 s9 inner join Section10 s10  inner join Section77 s77 where s1.Upload='2' and s2.idno=s1.idno and s3.idno=s1.idno and s4.idno=s1.idno and s5.idno=s1.idno and s6.idno=s1.idno and s7.idno=s1.idno and s8.idno=s1.idno and s9.idno=s1.idno and (s10.idno=s1.idno and s10.Slno='01') and (s77.idno=s1.idno and s77.Slno='01')");
        // Cursor cur_H = ReadData("Select " + VariableList + " from " + TableName + " WHERE Upload='2' LIMIT "+ limit_of_records);
        Cursor cur_H = ReadData("Select " + VariableList + " from " + TableName + "  WHERE Upload='1' and status='" + status + "' AND substr( workPlanDate, 1, 7 )='" + Month + "' AND providerId='" + providerId + "' LIMIT " + limit_of_records); // +" OFFSET "+ offset_settings
        cur_H.moveToFirst();
        List<DataClassProperty> data = new ArrayList<DataClassProperty>();
        DataClassProperty d;

        String DataList = "";
        //String[] Count  = VariableList.toString().split(",");
        String[] Count = VariableList.toString().replace(" ", "").split(",");
        //String[] UField = UniqueField.toString().split(",");
        String[] UField = UniqueField.toString().replace(" ", "").split(",");
        int RecordCount = 0;

        String WhereClause = "";
        String VarData[];
        int varPos = 0;
        while (!cur_H.isAfterLast()) {
            //Prepare Data List
            for (int c = 0; c < Count.length; c++) {
                if (c == 0) {
                    if (cur_H.getString(c) == null)
                        DataList = "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList = "";
                    else
                        DataList = cur_H.getString(c).toString();

                } else {
                    if (cur_H.getString(c) == null)
                        DataList += "^" + "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList += "^" + "";
                    else
                        DataList += "^" + cur_H.getString(c).toString();
                }
            }

            //Prepare Where Clause
            VarData = DataList.split("\\^");
            varPos = 0;


            for (int j = 0; j < UField.length; j++) {
                varPos = VarPosition(UField[j].toString(), Count);
                if (j == 0) {
                    WhereClause = "\"" + UField[j].toString() + "\"=" + "'" + VarData[varPos].toString() + "'";
                } else {
                    WhereClause += " and \"" + UField[j].toString() + "\"=" + "'" + VarData[varPos].toString() + "'";
                }
            }

            d = new DataClassProperty();
            d.setdatalist(DataList);
            d.setuniquefieldwithdata(WhereClause);
            data.add(d);

            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return data;
    }


    //New add code by nisan
    public List<DataClassProperty> GetDataListJSONworkPlanMaster(String VariableList, String TableName, String UniqueField, String Month, String providerId, int limit_of_records, int offset_settings) {

      /*//  Save("PRAGMA journal_mode = TRUNCATE");
        Save("PRAGMA auto_vacuum = 1");
        Save("PRAGMA locking_mod = EXCLUSIVE");*/

     /*   select * from workPlanMaster A
        INNER JOIN workPlanDetail B ON A.workPlanId = B.workPlanId
        WHERE  A.month='2016-01' and A.providerId='93002'*/

        // cur_H = ReadData("Select "+ VariableList +" from "+ TableName +" s1 inner join Section2 s2 inner join Section3 s3 inner join Section4 s4 inner join Section5 s5 inner join Section6 s6 inner join Section7 s7 inner join Section8 s8 inner join Section9 s9 inner join Section10 s10  inner join Section77 s77 where s1.Upload='2' and s2.idno=s1.idno and s3.idno=s1.idno and s4.idno=s1.idno and s5.idno=s1.idno and s6.idno=s1.idno and s7.idno=s1.idno and s8.idno=s1.idno and s9.idno=s1.idno and (s10.idno=s1.idno and s10.Slno='01') and (s77.idno=s1.idno and s77.Slno='01')");
        // Cursor cur_H = ReadData("Select " + VariableList + " from " + TableName + " WHERE Upload='2' LIMIT "+ limit_of_records);
        Cursor cur_H = ReadData("Select " + VariableList + " from " + TableName + "  WHERE Upload='2' AND month='" + Month + "' AND providerId='" + providerId + "' LIMIT " + limit_of_records); // +" OFFSET "+ offset_settings
        cur_H.moveToFirst();
        List<DataClassProperty> data = new ArrayList<DataClassProperty>();
        DataClassProperty d;

        String DataList = "";
        //String[] Count  = VariableList.toString().split(",");
        String[] Count = VariableList.toString().replace(" ", "").split(",");
        //String[] UField = UniqueField.toString().split(",");
        String[] UField = UniqueField.toString().replace(" ", "").split(",");
        int RecordCount = 0;

        String WhereClause = "";
        String VarData[];
        int varPos = 0;
        while (!cur_H.isAfterLast()) {
            //Prepare Data List
            for (int c = 0; c < Count.length; c++) {
                if (c == 0) {
                    if (cur_H.getString(c) == null)
                        DataList = "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList = "";
                    else
                        DataList = cur_H.getString(c).toString();

                } else {
                    if (cur_H.getString(c) == null)
                        DataList += "^" + "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList += "^" + "";
                    else
                        DataList += "^" + cur_H.getString(c).toString();
                }
            }

            //Prepare Where Clause
            VarData = DataList.split("\\^");
            varPos = 0;


            for (int j = 0; j < UField.length; j++) {
                varPos = VarPosition(UField[j].toString(), Count);
                if (j == 0) {
                    WhereClause = "\"" + UField[j].toString() + "\"=" + "'" + VarData[varPos].toString() + "'";
                } else {
                    WhereClause += " and \"" + UField[j].toString() + "\"=" + "'" + VarData[varPos].toString() + "'";
                }
            }

            d = new DataClassProperty();
            d.setdatalist(DataList);
            d.setuniquefieldwithdata(WhereClause);
            data.add(d);

            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return data;
    }

    public List<DataClassProperty> GetDataListJSONworkPlanDetail(String VariableList, String TableName, String UniqueField, String Month, String providerId, int limit_of_records, int offset_settings) {

      /*//  Save("PRAGMA journal_mode = TRUNCATE");
        Save("PRAGMA auto_vacuum = 1");
        Save("PRAGMA locking_mod = EXCLUSIVE");*/

     /*   select * from workPlanMaster A
        INNER JOIN workPlanDetail B ON A.workPlanId = B.workPlanId
        WHERE  A.month='2016-01' and A.providerId='93002'*/

        // cur_H = ReadData("Select "+ VariableList +" from "+ TableName +" s1 inner join Section2 s2 inner join Section3 s3 inner join Section4 s4 inner join Section5 s5 inner join Section6 s6 inner join Section7 s7 inner join Section8 s8 inner join Section9 s9 inner join Section10 s10  inner join Section77 s77 where s1.Upload='2' and s2.idno=s1.idno and s3.idno=s1.idno and s4.idno=s1.idno and s5.idno=s1.idno and s6.idno=s1.idno and s7.idno=s1.idno and s8.idno=s1.idno and s9.idno=s1.idno and (s10.idno=s1.idno and s10.Slno='01') and (s77.idno=s1.idno and s77.Slno='01')");
        // Cursor cur_H = ReadData("Select " + VariableList + " from " + TableName + " WHERE Upload='2' LIMIT "+ limit_of_records);
        Cursor cur_H = ReadData("Select " + VariableList + " from " + TableName + "  WHERE Upload='2' AND substr( workPlanDate, 1, 7 )='" + Month + "' AND providerId='" + providerId + "' LIMIT " + limit_of_records); // +" OFFSET "+ offset_settings
        cur_H.moveToFirst();
        List<DataClassProperty> data = new ArrayList<DataClassProperty>();
        DataClassProperty d;

        String DataList = "";
        //String[] Count  = VariableList.toString().split(",");
        String[] Count = VariableList.toString().replace(" ", "").split(",");
        //String[] UField = UniqueField.toString().split(",");
        String[] UField = UniqueField.toString().replace(" ", "").split(",");
        int RecordCount = 0;

        String WhereClause = "";
        String VarData[];
        int varPos = 0;
        while (!cur_H.isAfterLast()) {
            //Prepare Data List
            for (int c = 0; c < Count.length; c++) {
                if (c == 0) {
                    if (cur_H.getString(c) == null)
                        DataList = "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList = "";
                    else
                        DataList = cur_H.getString(c).toString();

                } else {
                    if (cur_H.getString(c) == null)
                        DataList += "^" + "";
                    else if (cur_H.getString(c).equals("null"))
                        DataList += "^" + "";
                    else
                        DataList += "^" + cur_H.getString(c).toString();
                }
            }

            //Prepare Where Clause
            VarData = DataList.split("\\^");
            varPos = 0;


            for (int j = 0; j < UField.length; j++) {
                varPos = VarPosition(UField[j].toString(), Count);
                if (j == 0) {
                    WhereClause = "\"" + UField[j].toString() + "\"=" + "'" + VarData[varPos].toString() + "'";
                } else {
                    WhereClause += " and \"" + UField[j].toString() + "\"=" + "'" + VarData[varPos].toString() + "'";
                }
            }

            d = new DataClassProperty();
            d.setdatalist(DataList);
            d.setuniquefieldwithdata(WhereClause);
            data.add(d);

            RecordCount += 1;
            cur_H.moveToNext();
        }
        cur_H.close();

        return data;
    }


//    public void ExecuteSQLFromFile(String fileName) {
//        List<String> dataList = Global.ReadTextFile(fileName);
//        for (int i = 0; i < dataList.size(); i++) {
//            Save(dataList.get(i));
//        }
//    }

    //**********************************************************************************************
    //MasterTables Sync: 15 June 2016
    //**********************************************************************************************
    public static void Sync_MasterTables(String ProvCode)
    {
        Connection C = new Connection(ud_context);
        String SQLStr = "";
        String Res = "";


        //Download OCP Code List
        SQLStr = "select \"ocpCode\", \"ocpName\", \"dCode\", \"upz\", \"ocpSequence\" from \"ocpList\" order by \"ocpSequence\"";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "ocplist", "ocpCode, ocpName, DCode, Upz, ocpSequence", "ocpCode");


        SQLStr = "SELECT * FROM \"BrandMethod\"";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "BrandMethod", "brandCode, brandName", "brandCode");


        SQLStr = "SELECT * FROM \"AttendantDesignation\"";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "AttendantDesignation", "attendantCode, attendantDesig", "attendantCode");


        //CodeList
            /*SQLStr = "SELECT * FROM \"CodeList\"";
            Res = DownloadJSON(SQLStr, "CodeList", "TypeName, Code, CName", "TypeName, Code");*/


        //ProviderType
        SQLStr = "SELECT * FROM \"ProviderType\"";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "ProviderType", "ProvType, TypeName", "TypeName");

        //FWA Unit

        SQLStr = "select \"UCode\",\"UName\",\"UNameBan\" from \"FWAUnit\"";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "FWAUnit", "UCode,UName,UNameBan", "UCode");



        //Block
        SQLStr = "select \"BCode\",\"BName\",\"BNameBan\" from \"HABlock\"";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "HABlock", "BCode,BName,BNameBan", "BCode");


        //item
        SQLStr = "select \"itemCode\",\"itemName\", \"brand\", \"unit\", \"status\" from item";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "item", "itemCode, itemName, brand, unit, status", "itemCode");


        //currentStock
        SQLStr = "select \"providerId\",\"itemCode\",\"stockQty\", \"systemEntryDate\", \"modifyDate\", \"upload\" from \"currentStock\" where \"providerId\"='" + ProvCode + "'";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "currentStock", "providerId, itemCode, stockQty, systemEntryDate, modifyDate, upload", "providerId,itemCode");


        //classfication
        SQLStr = "select \"classficationCode\", \"classficationName\" from classfication";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "classfication", "classficationCode, classficationName", "classficationCode");


        //symtom
        SQLStr = "select \"symtomCode\", \"symtomDes\" from symtom";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "symtom", "symtomCode, symtomDes", "symtomCode");


        //treatment
        SQLStr = "select \"treatmentCode\", \"tretmentDes\" from treatment";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "treatment", "treatmentCode, tretmentDes", "treatmentCode");


        //adoSymtom
        SQLStr = "select \"problemCode\", \"problemDes\" from \"adoSymtom\"";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "adoSymtom", "problemCode, problemDes", "problemCode");


        SQLStr = "select * from \"immunization\"";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "immunization", "imuCode, imuName, numOfDose,forChild,forWoman", "imuCode");


        //ElcoEvent
        SQLStr = "select \"EVCode\", \"EVName\" from \"ElcoEvent\"";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "ElcoEvent", "EVCode, EVName", "EVCode");


        //deathReason
        SQLStr = "select \"deathReasonId\", \"code\", \"detail\" from \"deathReason\"";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "deathReason", "deathReasonId, code, detail", "deathReasonId");


        SQLStr = "SELECT * FROM \"fpaItem\"";
        Res = C.DownloadJSON_InsertOnly(SQLStr, "fpaItem", "itemCode, itemDes,type", "itemCode,type");


        SQLStr = "SELECT * FROM \"month\"";
        Res = C.DownloadJSON(SQLStr, "month", "id,mname", "id");
        //ccInfo

        //SQLStr = "SELECT * FROM \"ccInfo\"";
        //  Res = C.DownloadJSON_InsertOnly(SQLStr, "ccInfo", "zilaid, upazilaid, unionid, unionname, wardid, ward, ccid, ccname, hprovdername, mobailno", "ccid");

    }


    //**********************************************************************************************
    //HealthID Sync: 18 Dec 2016
    //**********************************************************************************************
    public static void Sync_HealthID(String PType, String PCode)
    {
        Connection C = new Connection(ud_context);
        String SQL = "";

        //Trigger for updating client map
        /*SQL = "create trigger if not exists tri_hid_update after";
        SQL += " update on Member";
        SQL += " for each row";
        SQL += " when (New.HealthId<>Old.HealthId)";
        SQL += " begin";
        SQL += "    Update ClientMap set HealthId=New.HealthId where HealthId=Old.HealthId;";
        SQL += " end";*/

        C.Save("drop trigger if exists tri_hid_update");
        SQL = "Create trigger if not exists tri_hid_update";
        SQL += " after update on Member";
        SQL += " for each row";
        SQL += " when (New.HealthId<>Old.HealthId)";
        SQL += " begin";
        SQL += " Update ClientMap set HealthId=New.HealthId where";
        SQL += "     HealthId=Old.HealthId and";
        SQL += "     zillaid = Old.Dist and";
        SQL += "     upazilaid = Old.Upz and";
        SQL += "     unionid = Old.Un and";
        SQL += "     mouzaid = Old.Mouza and";
        SQL += "     villageid = Old.Vill and";
        SQL += "     houseGRHoldingNo = Old.HHNo and";
        SQL += "     SNo = Old.SNo;";
        SQL += " end";
        C.Save(SQL);

        //New Trigger will execute  when name and DOB are same in both member and clientMap table
       /* C.Save("drop trigger if exists tri_hid_update_basedOn_name_and_dob");
        SQL = "Create trigger if not exists tri_hid_update_basedOn_name_and_dob";
        SQL += " after update on Member";
        SQL += " for each row";
        SQL += " when (name = Old . NameEng AND date(dob)=date(Old.DOB))";
        SQL += " begin";
        SQL += " Update ClientMap set HealthId=New.HealthId,upload=2 where";
        SQL += " name = Old . NameEng AND";
        SQL += " zillaid = Old.Dist and";
        SQL += " upazilaid = Old.Upz and";
        SQL += " unionid = Old.Un and";
        SQL += " mouzaid = Old.Mouza and";
        SQL += " villageid = Old.Vill and";
        SQL += " houseGRHoldingNo = Old.HHNo";
        SQL += " date(dob)=date(Old.DOB);";
        SQL += " end";
        C.Save(SQL);*/

        //Total number of records need to download
        SQL = "Select count(*)totalRecord from \"Member\" as t";
        SQL += " inner join \"ProviderArea\" a on t.\"Dist\"=a.\"zillaid\" and t.\"Upz\"=a.\"upazilaid\" and t.\"UN\"=a.\"unionid\" and t.\"Mouza\"=a.\"mouzaid\" and t.\"Vill\"=a.\"villageid\" and a.\"provType\"='" + PType + "' and a.\"provCode\"='" + PCode + "'";
        SQL += " where not exists(select * from data_sync_hid where";
        SQL += " \"tableName\"  = 'Member' and";
        SQL += " to_date(\"modifyDate\",'yyyy-mm-dd') = to_date(t.\"EnDt\",'yyyy-mm-dd') and";
        SQL += " \"recordId\" = cast(\"Dist\" as text)||cast(\"Upz\" as text)||cast(\"UN\" as text)||cast(\"Mouza\" as text)||cast(\"Vill\" as text)||cast(\"HHNo\" as text)||cast(\"SNo\" as text)";
        SQL += " and \"provType\"='" + PType + "' and \"provCode\"='" + PCode + "')";

        String TR =C.ReturnSingleValueJSON(SQL);
        int totalRecord = Integer.valueOf(TR.length()==0?"0":TR);

        int batchSize = 500;
        int totalBatch = (totalRecord/batchSize)+1;
        for(int i = 0; i < totalBatch; i++) {
            SQL = "Select \"Dist\", \"Upz\", \"UN\", \"Mouza\", \"Vill\", \"HHNo\", \"SNo\",\"HealthID\",to_date(\"EnDt\",'yyyy-mm-dd')modifyDate from \"Member\" as t";
            SQL += " inner join \"ProviderArea\" a on t.\"Dist\"=a.\"zillaid\" and t.\"Upz\"=a.\"upazilaid\" and t.\"UN\"=a.\"unionid\" and t.\"Mouza\"=a.\"mouzaid\" and t.\"Vill\"=a.\"villageid\" and a.\"provType\"='" + PType + "' and a.\"provCode\"='" + PCode + "'";
            SQL += " where not exists(select * from data_sync_hid where";
            SQL += " \"tableName\"  = 'Member' and";
            SQL += " to_date(\"modifyDate\",'yyyy-mm-dd') = to_date(t.\"EnDt\",'yyyy-mm-dd') and";
            SQL += " \"recordId\" = cast(\"Dist\" as text)||cast(\"Upz\" as text)||cast(\"UN\" as text)||cast(\"Mouza\" as text)||cast(\"Vill\" as text)||cast(\"HHNo\" as text)||cast(\"SNo\" as text)";
            SQL += " and \"provType\"='" + PType + "' and \"provCode\"='" + PCode + "') limit " + batchSize;

            C.DownloadJSON_HealthID(SQL, "Member", "Dist, Upz, UN, Mouza, Vill, HHNo, SNo, HealthID, modifyDate", "Dist, Upz, UN, Mouza, Vill, HHNo, SNo");
        }
        //finally drop trigger from database
        //C.Save("drop trigger if exists tri_hid_update");
    }


    //Download Data from Server
    public String DownloadJSON_HealthID(String SQL, String TableName, String ColumnList, String UniqueField) {
        DownloadRequestClass dr = new DownloadRequestClass();
        dr.setmethodname("DownloadData");
        dr.setSQL(SQL);
        Gson gson = new Gson();
        String json = gson.toJson(dr);

        String WhereClause = "";
        int varPos = 0;

        String response = "";
        String resp = "";
        String IDNO = "";
        String OldHID="";//select HealthId from  Member Where " + WhereClause;
        String OLD_HID="";//ReturnSingleValue(OldHID);
        String ClientSQL = "";
        //SQLiteDatabase database = this.getWritableDatabase();
        try {
            //For Web Api
            //--------------------------------------------------------------------------------------
            response = new DownloadJSONData().execute(json).get();
            downloadClass responseData = (downloadClass) gson.fromJson(response, downloadClass.class);
            //--------------------------------------------------------------------------------------
            String UField[] = UniqueField.toString().replace(" ", "").split(",");
            String VarList[] = ColumnList.toString().replace(" ", "").split(",");
            String InsertSQL = "";

            List<String> dataStatus = new ArrayList<String>();
            List<DataClassProperty> data = new ArrayList<DataClassProperty>();
            DataClassProperty d;
            String DataList = "";

            String modify_Date = "";
            if (responseData != null) {
                //database.beginTransaction();
                for (int i = 0; i < responseData.getdata().size(); i++) {
                    String VarData[] = split(responseData.getdata().get(i).toString(), '^');

                    //Generate where clause
                    SQL = "";
                    WhereClause = "";
                    varPos = 0;
                    for (int j = 0; j < UField.length; j++) {
                        varPos = VarPosition(UField[j].toString(), VarList);
                        if (j == 0) {
                            WhereClause = UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                            IDNO += VarData[varPos].toString();
                        } else {
                            WhereClause += " and " + UField[j].toString() + "=" + "'" + VarData[varPos].toString() + "'";
                            IDNO += VarData[varPos].toString();
                        }
                    }

                    //Update command
                    int hid_var_pos = VarPosition("HealthId", VarList);
                    int modifydt_var_pos = VarPosition("modifyDate", VarList);
                    modify_Date = VarData[modifydt_var_pos].toString().replace("null", "");


                    try{
                        String SQL1="Update Member set HealthId='"+ VarData[hid_var_pos].toString() +"' Where " + WhereClause;
                        Save(SQL1);
                    }catch (Exception ex){

                    }

                    DataList = TableName + "^" + IDNO + "^" + modify_Date + "^" + Global.getInstance().getProvType() + "^" + Global.getInstance().getProvCode();
                    d = new DataClassProperty();
                    d.setdatalist(DataList);
                    d.setuniquefieldwithdata("\"tableName\"='" + TableName + "' and \"recordId\"='" + IDNO + "' and \"modifyDate\"='" + modify_Date + "' and \"provCode\"='" + Global.getInstance().getProvCode() + "'");
                    data.add(d);

                    IDNO = "";
                }
                //database.setTransactionSuccessful();

                //Upload status to data_sync_management
                DataClass dt = new DataClass();
                //Insert only
                //dt.setmethodname("UploadData_Sync_Management");

                //Insert or Update
                //dt.setmethodname("UploadData_For_Sync");
                dt.setmethodname("UploadData_Sync");
                dt.settablename("data_sync_hid");
                dt.setcolumnlist("tableName,recordId,modifyDate,provType,provCode");
                dt.setdata(data);

                Gson gson1   = new Gson();
                String json1 = gson1.toJson(dt);
                String response1 = "";

                //UploadJSONData u = new UploadJSONData();

                try{
                    //response1=u.execute(json1).get();
                    response1 = new UploadJSONData().execute(json1).get();
                } catch (Exception e) {
                    //  Toast.makeText(dbContext, "All data did not upload. Try again.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            resp = e.getMessage();
            e.printStackTrace();
        }finally {
            //database.endTransaction();
            //database.close();
        }

        return resp;
    }

    public void BatchProcess(downloadClass responseData, String TABLE_NAME, String ColumnList)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        //SQLiteStatement statement = db.compileStatement(sql);
        DatabaseUtils.InsertHelper ih = new DatabaseUtils.InsertHelper(database, TABLE_NAME);
        try {
            database.execSQL("PRAGMA synchronous=OFF");
            database.setLockingEnabled(false);
            database.beginTransaction();
            for (int i = 0; i < responseData.getdata().size(); i++) {
                ih.prepareForInsert();

                //ih.bind(nameColumn, Members.get(i));

                ih.execute();
            }

                /*for (int i = 0; i < Members.size(); i++) {
                    ih.prepareForInsert();

                    ih.bind(nameColumn, Members.get(i));

                    ih.execute();
                }*/
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            database.setLockingEnabled(true);
            database.execSQL("PRAGMA synchronous=NORMAL");
            ih.close();
                /*if (Globals.ENABLE_LOGGING) {
                    final long endtime = System.currentTimeMillis();
                    Log.i("Time to insert Members: ", String.valueOf(endtime - startTime));
                }*/
        }
    }

}