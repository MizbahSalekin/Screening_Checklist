package Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import DataSync.DataClass;
import DataSync.DataClassProperty;
import DataSync.DownloadJSONData;
import DataSync.DownloadRequestClass;
import DataSync.UploadJSONData;
import DataSync.downloadClass;

/**
 * Created by thossain on 16/06/2017.
 */

public class DataSyncManagement {
    Connection C;
    Context ud_context;

    String Dist;
    String Upz;
    String ProvType;
    String ProvCode;
    int BatchSize;
    List<String> VList;
    String FWAUnit = "";
    String HAWard="";
    int totalBatch=0;
    int i = 0;

    private String[] TL;


    public DataSyncManagement(Context context,String dist, String upz, String provtype, String provcode, int batchsize)
    {
        ud_context = context;
        C = new Connection(ud_context);

        Dist = dist;
        Upz  = upz;
        ProvType = provtype;
        ProvCode = provcode;
        BatchSize = batchsize;
        //VList = C.VillageList();
    }

   // String Res="";

    public void Sync_MasterTables_All(String Dist,String Upz,String Providerid)
    {
        String SQLStr = "";
        String Res = "";

        SQLStr="Select \"tableName\", \"tableScript\",\"columnList\",\"uniqueId\",\"batchSizeDown\",\"batchSizeUp\",\"syncType\",\"modifiedDate\",\"downloadType\" from \"databaseSetting\"";
        Res = DownloadJSON(SQLStr, "databaseSetting", "tableName,tableScript,columnList,uniqueId,batchSizeDown,batchSizeUp,syncType,modifiedDate,downloadType", "tableName");


        SQLStr = "Select distinct divid,zillaid,upazilaid,unionid,ward,provtype,providerid,provname,mobileno,endate,exdate,active,devicesetting,systemupdatedt,healthidrequest,tablestructurerequest,areaupdate,level_id,supervisorcode,provpass,facilityname,csba,systementrydate,modifydate,uploaddate,1 upload from providerdb where ";//
/*          SQLStr += " zillaid='" + Dist + "' and";
            SQLStr += " upazilaid='" + Upz + "' and";
            SQLStr += " unionid='" + UN + "' and";
            SQLStr += " provtype='" + ProvType + "' and";*/
        SQLStr += " providerid='" + ProvCode + "' and";
        SQLStr += " active='1'";
        Res =DownloadJSON(SQLStr, "providerdb", "divid,zillaid,upazilaid,unionid,ward,provtype,providerid,provname,mobileno,endate,exdate,active,devicesetting,systemupdatedt,healthidrequest,tablestructurerequest,areaupdate,level_id,supervisorcode,provpass,facilityname,csba,systementrydate,modifydate,uploaddate,upload", "providerid");


//Login
        //--------------------------------------------------------------------------------------

        SQLStr = "Select distinct providerid,provname,provpass,systementrydate,modifydate,uploaddate,1 upload from providerdb Where ";
            /*SQLStr += " zillaid='" + Dist + "' and";
            SQLStr += " upazilaid='" + Upz + "' and";
            SQLStr += " unionid='" + UN + "' and";
            SQLStr += " provtype='" + ProvType + "' and";*/
        SQLStr += " providerid='" + ProvCode + "' and";
        SQLStr += " Active='1'";
        Res = DownloadJSON(SQLStr, "login", "userid,username,pass,systementrydate,modifydate,uploaddate,upload", "userid");



        //Division
        //--------------------------------------------------------------------------------------

        SQLStr = "Select id,division,divisioneng,systementrydate,modifydate,uploaddate,1 upload from division";
        Res = DownloadJSON(SQLStr, "division", "id,division,divisioneng,systementrydate,modifydate,uploaddate,upload", "id");

        //District
        //--------------------------------------------------------------------------------------

        // SQLStr = "Select divid,zillaid,zillanameeng,zillaname,systementrydate,modifydate,uploaddate,1 upload from zilla where zillaid='" + Dist + "'";
        SQLStr = "Select divid,zillaid,zillanameeng,zillaname,systementrydate,modifydate,uploaddate,1 upload from zilla where zillaid='" + Dist + "'";
        Res = DownloadJSON(SQLStr, "zilla", "divid,zillaid,zillanameeng,zillaname,systementrydate,modifydate,uploaddate,upload", "divid,zillaid");

        SQLStr = "Select zillaid,upazilaid,upazilanameeng,upazilaname,systementrydate,modifydate,uploaddate,1 upload from upazila where zillaid='" + Dist + "' and upazilaid='" + Upz + "'";
        Res = DownloadJSON(SQLStr, "upazila", "zillaid,upazilaid,upazilanameeng,upazilaname,systementrydate,modifydate,uploaddate,upload", "zillaid,upazilaid");

        SQLStr = "SELECT typename,code, cname,systementrydate,modifydate,uploaddate,1 upload FROM codelist where typename in('sc','dg1','dg2','dg3','sc1')";
        Res = DownloadJSON(SQLStr, "codelist", "typename,code, cname,systementrydate,modifydate,uploaddate,upload", "typename, code");

    }



//**************************************************************************************************



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
            if (responseData != null & responseData.getdata().size()>0) {
                SQL = "Insert or replace into "+ TableName +"("+ ColumnList +")Values";
                //SQL = "Insert into "+ TableName +"("+ ColumnList +")Values";
                for (int i = 0; i < responseData.getdata().size(); i++) {
                    String VarData[] = Connection.split(responseData.getdata().get(i).toString(), '^');

                    //Generate Unique ID
                    //------------------------------------------------------------------------------
                    for (int j = 0; j < UField.length; j++) {
                        varPos = C.VarPosition(UField[j].toString(), VarList);
                        if (j == 0) {
                            IDNO += VarData[varPos].toString();
                        } else {
                            IDNO += VarData[varPos].toString();
                        }
                    }


                     if(TableName.equalsIgnoreCase("elcovisit")) {
                        varPos_modifyDate = C.VarPosition("modifydate", VarList);
                        if(VarData[varPos_modifyDate].toString().equalsIgnoreCase("")||VarData[varPos_modifyDate].toString().equalsIgnoreCase("null"))
                        {
                            varPos_modifyDate = C.VarPosition("vdate", VarList);
                            modify_Date = VarData[varPos_modifyDate].toString().replace("null", "");
                        }
                        else {
                            modify_Date = VarData[varPos_modifyDate].toString().replace("null", "");
                        }
                    }
                    else{
                        varPos_modifyDate = C.VarPosition("modifydate", VarList);
                        modify_Date = VarData[varPos_modifyDate].toString().replace("null", "");
                    }

                    //------------------------------------------------------------------------------
                    if (i == 0) {
                        SQL += "('" + responseData.getdata().get(i).toString().replace("^","','").replace("null","") +"')";
                    } else {
                        SQL += ",('" + responseData.getdata().get(i).toString().replace("^","','").replace("null","") +"')";
                    }

                    //Populate class with data for sync_management
                    //------------------------------------------------------------------------------
                    DataList = TableName + "^" + IDNO + "^" + modify_Date + "^" + ProvType + "^" + ProvCode;
                    d = new DataClassProperty();
                    d.setdatalist(DataList);
                    d.setuniquefieldwithdata("" +
                            "\"tableName\"  ='" + TableName + "' and " +
                            "\"recordId\"   ='" + IDNO + "' and " +
                            "\"modifyDate\" ='" + modify_Date + "' and " +
                            "\"provType\"   ='" + ProvType + "' and " +
                            "\"provCode\"   ='" + ProvCode + "'");
                    dataTemp.add(d);

                    IDNO = "";
                }
                SaveResp = C.SaveData(SQL);
                if(SaveResp.length()==0){
                    data = dataTemp;
                }else{
                    C.Save("Insert into templog values('"+ TableName +": "+ SaveResp +"')");
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
            C.Save("Insert into templog values('"+ TableName +": "+ SaveResp +"')");
            e.printStackTrace();
        }
        return resp;
    }




}
