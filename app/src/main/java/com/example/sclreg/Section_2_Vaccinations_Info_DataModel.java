package com.example.sclreg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;

public class Section_2_Vaccinations_Info_DataModel {

       private String _idno = "";
       public String getidno(){
             return _idno;
        }
       public void setidno(String newValue){
             _idno = newValue;
        }

/*    private String _q201 = "";
    public String getq201(){
        return _q201;
    }
    public void setq201(String newValue){
        _q201 = newValue;
    }
    private String _q202 = "";
    public String getq202(){
        return _q202;
    }
    public void setq202(String newValue){
        _q202 = newValue;
    }
    private String _q203a = "";
    public String getq203a(){
        return _q203a;
    }
    public void setq203a(String newValue){
        _q203a = newValue;
    }
    private String _q203b = "";
    public String getq203b(){
        return _q203b;
    }
    public void setq203b(String newValue){
        _q203b = newValue;
    }
    private String _q203c = "";
    public String getq203c(){
        return _q203c;
    }
    public void setq203c(String newValue){
        _q203c = newValue;
    }
    private String _q203d = "";
    public String getq203d(){
        return _q203d;
    }
    public void setq203d(String newValue){
        _q203d = newValue;
    }
    private String _q203e = "";
    public String getq203e(){
        return _q203e;
    }
    public void setq203e(String newValue){
        _q203e = newValue;
    }
    private String _q203f = "";
    public String getq203f(){
        return _q203f;
    }
    public void setq203f(String newValue){
        _q203f = newValue;
    }
    private String _q203g = "";
    public String getq203g(){
        return _q203g;
    }
    public void setq203g(String newValue){
        _q203g = newValue;
    }
    private String _q203h = "";
    public String getq203h(){
        return _q203h;
    }
    public void setq203h(String newValue){
        _q203h = newValue;
    }
    private String _q203i = "";
    public String getq203i(){
        return _q203i;
    }
    public void setq203i(String newValue){
        _q203i = newValue;
    }
    private String _q203j = "";
    public String getq203j(){
        return _q203j;
    }
    public void setq203j(String newValue){
        _q203j = newValue;
    }
    private String _q203k = "";
    public String getq203k(){
        return _q203k;
    }
    public void setq203k(String newValue){
        _q203k = newValue;
    }
    private String _q203l = "";
    public String getq203l(){
        return _q203l;
    }
    public void setq203l(String newValue){
        _q203l = newValue;
    }
    private String _q203m = "";
    public String getq203m(){
        return _q203m;
    }
    public void setq203m(String newValue){
        _q203m = newValue;
    }

    private String _q204 = "";
    public String getq204(){
        return _q204;
    }
    public void setq204(String newValue){
        _q204 = newValue;
    }

    private String _q205 = "";
    public String getq205(){
        return _q205;
    }
    public void setq205(String newValue){
        _q205 = newValue;
    }*/

    private String _q201 = "";
    public String getq201(){
        return _q201;
    }
    public void setq201(String newValue){
        _q201 = newValue;
    }
    private String _q202 = "";
    public String getq202(){
        return _q202;
    }
    public void setq202(String newValue){
        _q202 = newValue;
    }
    private String _q202a = "";
    public String getq202a(){
        return _q202a;
    }
    public void setq202a(String newValue){
        _q202a = newValue;
    }
    private String _q202b1 = "";
    public String getq202b1(){
        return _q202b1;
    }
    public void setq202b1(String newValue){
        _q202b1 = newValue;
    }
    private String _q202b2 = "";
    public String getq202b2(){
        return _q202b2;
    }
    public void setq202b2(String newValue){
        _q202b2 = newValue;
    }
    private String _q202b3 = "";
    public String getq202b3(){
        return _q202b3;
    }
    public void setq202b3(String newValue){
        _q202b3 = newValue;
    }
    private String _q203 = "";
    public String getq203(){
        return _q203;
    }
    public void setq203(String newValue){
        _q203 = newValue;
    }
    private String _q204a = "";
    public String getq204a(){
        return _q204a;
    }
    public void setq204a(String newValue){
        _q204a = newValue;
    }
    private String _q204b = "";
    public String getq204b(){
        return _q204b;
    }
    public void setq204b(String newValue){
        _q204b = newValue;
    }
    private String _q204c = "";
    public String getq204c(){
        return _q204c;
    }
    public void setq204c(String newValue){
        _q204c = newValue;
    }
    private String _q204d = "";
    public String getq204d(){
        return _q204d;
    }
    public void setq204d(String newValue){
        _q204d = newValue;
    }
    private String _q204e = "";
    public String getq204e(){
        return _q204e;
    }
    public void setq204e(String newValue){
        _q204e = newValue;
    }
    private String _q204f = "";
    public String getq204f(){
        return _q204f;
    }
    public void setq204f(String newValue){
        _q204f = newValue;
    }
    private String _q204g = "";
    public String getq204g(){
        return _q204g;
    }
    public void setq204g(String newValue){
        _q204g = newValue;
    }
    private String _q204h = "";
    public String getq204h(){
        return _q204h;
    }
    public void setq204h(String newValue){
        _q204h = newValue;
    }
    private String _q204i = "";
    public String getq204i(){
        return _q204i;
    }
    public void setq204i(String newValue){
        _q204i = newValue;
    }
    private String _q204j = "";
    public String getq204j(){
        return _q204j;
    }
    public void setq204j(String newValue){
        _q204j = newValue;
    }
    private String _q204k = "";
    public String getq204k(){
        return _q204k;
    }
    public void setq204k(String newValue){
        _q204k = newValue;
    }
    private String _q204l = "";
    public String getq204l(){
        return _q204l;
    }
    public void setq204l(String newValue){
        _q204l = newValue;
    }
    private String _q204x = "";
    public String getq204x(){
        return _q204x;
    }
    public void setq204x(String newValue){
        _q204x = newValue;
    }
    private String _q204x1 = "";
    public String getq204x1(){
        return _q204x1;
    }
    public void setq204x1(String newValue){
        _q204x1 = newValue;
    }
    private String _q205a = "";
    public String getq205a(){
        return _q205a;
    }
    public void setq205a(String newValue){
        _q205a = newValue;
    }
    private String _q205b = "";
    public String getq205b(){
        return _q205b;
    }
    public void setq205b(String newValue){
        _q205b = newValue;
    }
    private String _q205c = "";
    public String getq205c(){
        return _q205c;
    }
    public void setq205c(String newValue){
        _q205c = newValue;
    }
    private String _q205d = "";
    public String getq205d(){
        return _q205d;
    }
    public void setq205d(String newValue){
        _q205d = newValue;
    }
    private String _q205e = "";
    public String getq205e(){
        return _q205e;
    }
    public void setq205e(String newValue){
        _q205e = newValue;
    }
    private String _q205f = "";
    public String getq205f(){
        return _q205f;
    }
    public void setq205f(String newValue){
        _q205f = newValue;
    }
    private String _q205x = "";
    public String getq205x(){
        return _q205x;
    }
    public void setq205x(String newValue){
        _q205x = newValue;
    }
    private String _q206a = "";
    public String getq206a(){
        return _q206a;
    }
    public void setq206a(String newValue){
        _q206a = newValue;
    }
    private String _q206b = "";
    public String getq206b(){
        return _q206b;
    }
    public void setq206b(String newValue){
        _q206b = newValue;
    }

    private String _q206c = "";
    public String getq206c(){
        return _q206c;
    }
    public void setq206c(String newValue){
        _q206c = newValue;
    }

    private String _q206d = "";
    public String getq206d(){
        return _q206d;
    }
    public void setq206d(String newValue){
        _q206d = newValue;
    }


    private String _q206 = "";
    public String getq206(){
        return _q206;
    }
    public void setq206(String newValue){
        _q206 = newValue;
    }

    private String _q207 = "";
    public String getq207(){
        return _q207;
    }
    public void setq207(String newValue){
        _q207 = newValue;
    }

    private String _q208 = "";
    public String getq208(){
        return _q208;
    }
    public void setq208(String newValue){
        _q208 = newValue;
    }

    private String _q209 = "";
    public String getq209(){
        return _q209;
    }
    public void setq209(String newValue){
        _q209 = newValue;
    }

    private String _q210 = "";
    public String getq210(){
        return _q210;
    }
    public void setq210(String newValue){
        _q210 = newValue;
    }

    private String _q211 = "";
    public String getq211(){
        return _q211;
    }
    public void setq211(String newValue){
        _q211 = newValue;
    }

       private String _StartTime = "";
       public void setStartTime(String newValue){
             _StartTime = newValue;
        }
       private String _EndTime = "";
       public void setEndTime(String newValue){
             _EndTime = newValue;
        }
       private String _DeviceID = "";
       public void setDeviceID(String newValue){
             _DeviceID = newValue;
        }
       private String _EntryUser = "";
       public void setEntryUser(String newValue){
             _EntryUser = newValue;
        }
       private String _Lat = "";
       public void setLat(String newValue){
             _Lat = newValue;
        }
       private String _Lon = "";
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

       String TableName = "Section_2_Vaccinations_Info";

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
            SQL = "Insert into "+ TableName +" (idno,q201,q202,q202a,q202b1,q202b2,q202b3,q203,q204a,q204b,q204c,q204d,q204e,q204f,q204g,q204h,q204i,q204j,q204k,q204l,q204x,q204x1,q205a,q205b,q205c,q205d,q205e,q205f,q205x,q206a,q206b,q206c,q206d,q207,q208,q209,q210,q211,starttime,endtime,deviceid,entryuser,lat,lon,endt,upload,modifydate)Values('"+ _idno +"', '"+ _q201 +"', '"+ _q202 +"', '"+ _q202a +"', '"+ _q202b1 +"', '"+ _q202b2 +"', '"+ _q202b3 +"', '"+ _q203 +"', '"+ _q204a +"', '"+ _q204b +"', '"+ _q204c +"', '"+ _q204d +"', '"+ _q204e +"', '"+ _q204f +"', '"+ _q204g +"', '"+ _q204h +"', '"+ _q204i +"', '"+ _q204j +"', '"+ _q204k +"', '"+ _q204l +"', '"+ _q204x +"', '"+ _q204x1 +"', '"+ _q205a +"', '"+ _q205b +"', '"+ _q205c +"', '"+ _q205d +"', '"+ _q205e +"', '"+ _q205f +"', '"+ _q205x +"', '"+ _q206a +"', '"+ _q206b +"', '"+ _q206c+"', '"+ _q206d+"', '"+ _q207 +"', '"+ _q208+"', '"+ _q209 +"', '"+ _q210 +"', '"+ _q211 +"', '"+ _StartTime +"', '"+ _EndTime +"', '"+ _DeviceID +"', '"+ _EntryUser +"', '"+ _Lat +"', '"+ _Lon +"', '"+ _EnDt +"', '"+ _Upload +"', '"+ _modifyDate +"')";
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
            SQL = "Update "+ TableName +" Set Upload='2',modifyDate='" + _modifyDate + "' ,idno = '"+ _idno +"',q201 = '"+ _q201 +"',q202 = '"+ _q202 +"',q202a = '"+ _q202a +"',q202b1 = '"+ _q202b1 +"',q202b2 = '"+ _q202b2 +"',q202b3 = '"+ _q202b3 +"',q203 = '"+ _q203 +"',q204a = '"+ _q204a +"',q204b = '"+ _q204b +"',q204c = '"+ _q204c +"',q204d = '"+ _q204d +"',q204e = '"+ _q204e +"',q204f = '"+ _q204f +"',q204g = '"+ _q204g +"',q204h = '"+ _q204h +"',q204i = '"+ _q204i +"',q204j = '"+ _q204j +"',q204k = '"+ _q204k +"',q204l = '"+ _q204l +"',q204x = '"+ _q204x +"',q204x1 = '"+ _q204x1 +"',q205a = '"+ _q205a +"',q205b = '"+ _q205b +"',q205c = '"+ _q205c +"',q205d = '"+ _q205d +"',q205e = '"+ _q205e +"',q205f = '"+ _q205f +"',q205x = '"+ _q205x +"',q206a = '"+ _q206a +"',q206b = '"+ _q206b +"',q206c = '"+ _q206c +"',q206d = '"+ _q206d+"',q207 = '"+ _q207+"',q208= '"+ _q208 +"',q209 = '"+ _q209+"',q210 = '"+ _q210+"',q211 = '"+ _q211+"'  Where idno='"+ _idno +"'";
            C.Save(SQL);
            C.close();
        }
        catch(Exception  e)
        {
            response = e.getMessage();
        }
        return response;
    }


    @SuppressLint("Range")
    public List<Section_2_Vaccinations_Info_DataModel> SelectAll(Context context, String SQL)
    {
        Connection C = new Connection(context);
        List<Section_2_Vaccinations_Info_DataModel> data = new ArrayList<Section_2_Vaccinations_Info_DataModel>();
        Section_2_Vaccinations_Info_DataModel d = new Section_2_Vaccinations_Info_DataModel();
        Cursor cur = C.ReadData(SQL);

        cur.moveToFirst();
        while(!cur.isAfterLast())
        {
            d = new Section_2_Vaccinations_Info_DataModel();
            d._idno = cur.getString(cur.getColumnIndex("idno"));
           /* d._q201 = cur.getString(cur.getColumnIndex("q201"));
            d._q202 = cur.getString(cur.getColumnIndex("q202"));
            d._q203a = cur.getString(cur.getColumnIndex("q203a"));
            d._q203b = cur.getString(cur.getColumnIndex("q203b"));
            d._q203c = cur.getString(cur.getColumnIndex("q203c"));
            d._q203d = cur.getString(cur.getColumnIndex("q203d"));
            d._q203e = cur.getString(cur.getColumnIndex("q203e"));
            d._q203f = cur.getString(cur.getColumnIndex("q203f"));
            d._q203g = cur.getString(cur.getColumnIndex("q203g"));
            d._q203h = cur.getString(cur.getColumnIndex("q203h"));
            d._q203i = cur.getString(cur.getColumnIndex("q203i"));
            d._q203j = cur.getString(cur.getColumnIndex("q203j"));
            d._q203k = cur.getString(cur.getColumnIndex("q203k"));
            d._q203l = cur.getString(cur.getColumnIndex("q203l"));
            d._q203m = cur.getString(cur.getColumnIndex("q203m"));
            d._q204 = cur.getString(cur.getColumnIndex("q204"));
            d._q205 = cur.getString(cur.getColumnIndex("q205"));*/
            d._q201 = cur.getString(cur.getColumnIndex("q201"));
            d._q202 = cur.getString(cur.getColumnIndex("q202"));
            d._q202a = cur.getString(cur.getColumnIndex("q202a"));
            d._q202b1 = cur.getString(cur.getColumnIndex("q202b1"));
            d._q202b2 = cur.getString(cur.getColumnIndex("q202b2"));
            d._q202b3 = cur.getString(cur.getColumnIndex("q202b3"));
            d._q203 = cur.getString(cur.getColumnIndex("q203"));
            d._q204a = cur.getString(cur.getColumnIndex("q204a"));
            d._q204b = cur.getString(cur.getColumnIndex("q204b"));
            d._q204c = cur.getString(cur.getColumnIndex("q204c"));
            d._q204d = cur.getString(cur.getColumnIndex("q204d"));
            d._q204e = cur.getString(cur.getColumnIndex("q204e"));
            d._q204f = cur.getString(cur.getColumnIndex("q204f"));
            d._q204g = cur.getString(cur.getColumnIndex("q204g"));
            d._q204h = cur.getString(cur.getColumnIndex("q204h"));
            d._q204i = cur.getString(cur.getColumnIndex("q204i"));
            d._q204j = cur.getString(cur.getColumnIndex("q204j"));
            d._q204k = cur.getString(cur.getColumnIndex("q204k"));
            d._q204l = cur.getString(cur.getColumnIndex("q204l"));
            d._q204x = cur.getString(cur.getColumnIndex("q204x"));
            d._q204x1 = cur.getString(cur.getColumnIndex("q204x1"));
            d._q205a = cur.getString(cur.getColumnIndex("q205a"));
            d._q205b = cur.getString(cur.getColumnIndex("q205b"));
            d._q205c = cur.getString(cur.getColumnIndex("q205c"));
            d._q205d = cur.getString(cur.getColumnIndex("q205d"));
            d._q205e = cur.getString(cur.getColumnIndex("q205e"));
            d._q205f = cur.getString(cur.getColumnIndex("q205f"));
            d._q205x = cur.getString(cur.getColumnIndex("q205x"));
            d._q206a = cur.getString(cur.getColumnIndex("q206a"));
            d._q206b = cur.getString(cur.getColumnIndex("q206b"));
            d._q206c = cur.getString(cur.getColumnIndex("q206c"));
            d._q207 = cur.getString(cur.getColumnIndex("q207"));
            d._q208 = cur.getString(cur.getColumnIndex("q208"));
            d._q209 = cur.getString(cur.getColumnIndex("q209"));
            d._q210 = cur.getString(cur.getColumnIndex("q210"));
            d._q211 = cur.getString(cur.getColumnIndex("q211"));
            d._q206d = cur.getString(cur.getColumnIndex("q206d"));
            data.add(d);

            cur.moveToNext();
        }
        cur.close();
        return data;
    }
}