package com.example.sclreg;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;

public class Iron_GPS_DataModel {

       private String _idno = "";
       public String getidno(){
             return _idno;
        }
       public void setidno(String newValue){
             _idno = newValue;
        }



       private String _Lat = "";
       public String getLat(){
        return _Lat;
       }
       public void setLat(String newValue){
             _Lat = newValue;
        }

       private String _Lon = "";
       public String getLot(){
        return _Lon;
       }
       public void setLon(String newValue){
             _Lon = newValue;
        }

    private String _EnDt = "";
    public void setEnDt(String newValue){
        _EnDt = newValue;
    }
    private String _Upload = "2";

    private String _modifyDate = "";
    public void setmodifyDate(String newValue){
        _modifyDate = newValue;
    }

       String TableName = "gps_info";

       public String SaveUpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
           {
                if(C.Existence("Select * from "+ TableName +"  Where idno='"+ _idno +"' "))
                   response = UpdateData(context);
                else
                   response = SaveData(context);
           }
           catch(Exception  e)
           {
                response = e.getMessage();
           }
          return response;
       }
       Connection C;

       private String SaveData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
             {
                SQL = "Insert into "+ TableName +" (idno,Lat,Lon,EnDt,Upload,modifyDate)Values('"+ _idno +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"', '"+ _modifyDate +"')";
                C.Save(SQL);
                C.close();
             }
             catch(Exception  e)
             {
                response = e.getMessage();
             }
          return response;
       }

       private String UpdateData(Context context)
       {
           String response = "";
           C = new Connection(context);
           String SQL = "";
           try
             {
                SQL = "Update "+ TableName +" Set Upload='2',modifyDate='" + _modifyDate + "' ,idno = '"+ _idno +"',lat = '"+_Lat+"',lon = '"+ _Lon  +"'  Where idno='"+ _idno +"'";
                C.Save(SQL);
                C.close();
             }
             catch(Exception  e)
             {
                response = e.getMessage();
             }
          return response;
       }


       public List<Iron_GPS_DataModel> SelectAll(Context context, String SQL)
       {
           Connection C = new Connection(context);
           List<Iron_GPS_DataModel> data = new ArrayList<Iron_GPS_DataModel>();
           Iron_GPS_DataModel d = new Iron_GPS_DataModel();
           Cursor cur = C.ReadData(SQL);

           cur.moveToFirst();
           while(!cur.isAfterLast())
           {
               d = new Iron_GPS_DataModel();
               d._idno = cur.getString(cur.getColumnIndex("idno"));
               d._Lat = cur.getString(cur.getColumnIndex("lat"));
               d._Lon = cur.getString(cur.getColumnIndex("lon"));
               data.add(d);

               cur.moveToNext();
           }
           cur.close();
         return data;
       }
}