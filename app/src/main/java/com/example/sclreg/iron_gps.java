package com.example.sclreg;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.KeyEvent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import Common.*;


public class iron_gps extends Activity {
   boolean netwoekAvailable=false;
   Location currentLocation; 
   double currentLatitude,currentLongitude; 
   Location currentLocationNet; 
   double currentLatitudeNet,currentLongitudeNet; 
   
   public String id;
	public String CallinForm;
	private static String formname;
	private static String dcode;
	private static String upcode;
	private static String vcode;
	private static String bari;
	private static String hh;
	private static String wid;
	private static String pgn;
	private static String wname;
	private static String husband;
	Bundle IDbundle;
   
   //Disabled Back/Home key
   //--------------------------------------------------------------------------------------------------
   @Override 
   public boolean onKeyDown(int iKeyCode, KeyEvent event)
   {
       if(iKeyCode == KeyEvent.KEYCODE_BACK || iKeyCode == KeyEvent.KEYCODE_HOME) 
            { return false; }
       else { return true;  }
   }
   //Top menu
   //--------------------------------------------------------------------------------------------------
   public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.mnuclose, menu);
       return true;
   }
   public boolean onOptionsItemSelected(MenuItem item) {
       AlertDialog.Builder adb = new AlertDialog.Builder(iron_gps.this);
       switch (item.getItemId()) {
           case R.id.menuClose:
               adb.setTitle("Close");
                 adb.setMessage("আপনি কি এই ফর্ম থেকে বের হতে চান[Yes/No]?");
                 adb.setNegativeButton("No", null);
                 adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }});
                 adb.show();
              return true;
       }
       return false;
   }
   String VariableID;
   private int hour;
   private int minute;
   private int mDay;
   private int mMonth;
   private int mYear;
   static final int DATE_DIALOG = 1;
   static final int TIME_DIALOG = 2;

   Connection C;
   Global g;
   SimpleAdapter dataAdapter;
   ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
   private String TableName;

    private String CHReg;

     Integer nSatellites=0;
     
        EditText txtQLat;
        EditText txtQLon;
        EditText txtIdNo;

String StartTime;

public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  try
    {
        setContentView(R.layout.iron_gps);
        C = new Connection(this);
        g = Global.getInstance();
        StartTime = g.CurrentTime24();

        
        IDbundle	= getIntent().getExtras();
       // id  = IDbundle.getString("id");
      

        
    // TextView lblWomen=(TextView) findViewById(R.id.WName);
    // lblWomen.setText(wname+" ["+ husband +"]");
        
        
        
        TableName = "gps_info";

          Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
          intent.putExtra("enabled", true);
         // sendBroadcast(intent);
          FindLocation();

        txtQLat=(EditText)findViewById(R.id.txtQLat);
        txtQLon=(EditText)findViewById(R.id.txtQLon);
        txtIdNo=(EditText)findViewById(R.id.txtIdNo);
        txtIdNo.setText(g.getIdNo());
        txtIdNo.setEnabled(false);
        txtQLat.setEnabled(false);
        txtQLon.setEnabled(false);
        txtIdNo.setEnabled(false);       
        //txtIdNo.setText("123");

        DataSearch(txtIdNo.getText().toString());
       Button cmdSave = (Button) findViewById(R.id.cmdSave);
       cmdSave.setOnClickListener(new View.OnClickListener() {
       public void onClick(View v) { 
           DataSave();
       }});

        Button cmdClose = (Button) findViewById(R.id.cmdClose);
        cmdClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent f1 = new Intent(getApplicationContext(), schecklist_home.class);
                startActivity(f1);
            }});
        //cmdClose
    }
    catch(Exception  e)
    {
        Connection.MessageBox(iron_gps.this, e.getMessage());
        return;
    }
}

private void DataSave()
{
  try
    {
	  
	  if(txtQLat.getText().toString().trim().length()==0)
	  {
		  Connection.MessageBox(iron_gps.this, "Latitude value missing");         
          return;    
	  }
	  else if(txtQLat.getText().toString().trim().length()>0)
	  {
		  if(Double.valueOf(txtQLat.getText().toString().trim().length())==0)
		  {
			  Connection.MessageBox(iron_gps.this, "Latitude value missing");         
	          return;    
		  }   
	  }
	  
	  if(txtQLon.getText().toString().trim().length()==0)
	  {
		  Connection.MessageBox(iron_gps.this, "Longitude value missing");         
          return;    
	  }
	  else if(txtQLon.getText().toString().trim().length()>0)
	  {
		  if(Double.valueOf(txtQLon.getText().toString().trim().length())==0)
		  {
			  Connection.MessageBox(iron_gps.this, "Longitude value missing");         
	          return;    
		  }   
	  }
	  
	 
	   // String SQL="insert into gps_info(IdNo,Lat,Lon) values('" + txtIdNo.getText() + "','"+ txtQLat.getText() +"','"+ txtQLon.getText() +"')";
      //  C.Save(SQL);

        Iron_GPS_DataModel objSave = new Iron_GPS_DataModel();
        objSave.setidno(txtIdNo.getText().toString());
        objSave.setLat(txtQLat.getText().toString());
        objSave.setLon(txtQLon.getText().toString());
        objSave.setEnDt(Global.DateTimeNowYMDHMS());
        objSave.setmodifyDate(Global.DateTimeNowYMDHMS());


        String status = objSave.SaveUpdateData(this);
        if(status.length()==0) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("res", "");
            setResult(Activity.RESULT_OK, returnIntent);

            Connection.MessageBox(iron_gps.this, "Saved Successfully");
        }
        else{
            Connection.MessageBox(iron_gps.this, status);
            return;
        }

        g.setIdNo("");
        finish();
        Intent f1 = new Intent(getApplicationContext(), schecklist_home.class);
        startActivity(f1);
       /* CHReg =  C.ReturnSingleValue("Select ifnull(q109a,'0') as q109a  from section_1_ha_service where idno='"+g.getIdNo()+"'");

        if(CHReg.equalsIgnoreCase("1"))
        {
            g.setIdNo("");
            finish();
            Intent f1 = new Intent(getApplicationContext(), Section_1_child_and_child_mother_reg_idf.class);
            startActivity(f1);
        }

        else {
            g.setIdNo("");
            finish();
            Intent f1 = new Intent(getApplicationContext(), schecklist_home.class);
            startActivity(f1);

        }*/

       
    }
    catch(Exception  e)
    {
        Connection.MessageBox(iron_gps.this, e.getMessage());
        return;
    }
}
    private void DataSearch(String idno)
    {
        try
        {

            RadioButton rb;
            Iron_GPS_DataModel d = new Iron_GPS_DataModel();
            String SQL = "Select * from "+ TableName +"  Where idno='"+ idno +"'";
            List<Iron_GPS_DataModel> data = d.SelectAll(this, SQL);
            for(Iron_GPS_DataModel item : data){
                txtIdNo.setText(item.getidno());
                txtQLat.setText(item.getLat());
                txtQLon.setText(item.getLot());
            }
        }
        catch(Exception  e)
        {
            Connection.MessageBox(iron_gps.this, e.getMessage());
            return;
        }
    }
    //GPS Reading
    //.....................................................................................................
    public void FindLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                updateLocation(location);
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            public void onProviderEnabled(String provider) {
            }
            public void onProviderDisabled(String provider) {
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    void updateLocation(Location location) {


        // btnClose.setText(null);
        currentLocation = location;
        currentLatitude = currentLocation.getLatitude();
        currentLongitude = currentLocation.getLongitude();
        //  nSatellites = location.getExtras().getInt("satellites", 1);
        //  nSatellites+=1;
        //  if(nSatellites>=3)
        //  {
        txtQLat.setText(String.valueOf(currentLatitude));
        txtQLon.setText(String.valueOf(currentLongitude));
        //  }
    }


    // Method to turn on GPS
    public void turnGPSOn(){
        try
        {
            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if(!provider.contains("gps")){ //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        }
        catch (Exception e) {
        }
    }

    // Method to turn off the GPS
    public void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    // turning off the GPS if its in on state. to avoid the battery drain.
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        turnGPSOff();
    }

/*public void FindLocation() {
    LocationManager locationManager = (LocationManager) this 
            .getSystemService(Context.LOCATION_SERVICE); 
    //psatelitte=0;
    //btnClose.setText(String.valueOf(psatelitte));
    LocationListener locationListener = new LocationListener() { 
    	
        public void onLocationChanged(Location location) { 
        //	psatelitte=0;
            updateLocation(location);   
            
            } 

        public void onStatusChanged(String provider, int status, Bundle extras) 
        { 
        
        	 
        } 

        public void onProviderEnabled(String provider) { 
        } 

        public void onProviderDisabled(String provider) { 
        } 
    }; 
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
   
}

void updateLocation(Location location) 
{ 
	// btnClose.setText(null);
    currentLocation = location; 
    currentLatitude = currentLocation.getLatitude(); 
    currentLongitude = currentLocation.getLongitude();
  //  nSatellites = location.getExtras().getInt("satellites", 1);
  //  nSatellites+=1;
  //  if(nSatellites>=3)
  //  {
    	 txtQLat.setText(String.valueOf(currentLatitude));
    	 txtQLon.setText(String.valueOf(currentLongitude));
  //  }
   
   // nSatellites = location.getExtras().getInt("satellites", 1);
    //Connection.MessageBox(LoginActivity.this, String.valueOf(nSatellites));	
  //  psatelitte+=1;

   // btnClose.setText(String.valueOf(psatelitte));
} */


/*private void DataSearch(String WID, String VisitNo)
    {
      try
       {
    
          RadioButton rb;
          Cursor cur = C.ReadData("Select * from "+ TableName +"  Where WID='"+ WID +"' and VisitNo='"+ VisitNo +"'");
          cur.moveToFirst();
          while(!cur.isAfterLast())
          {
            txtDCode.setText(cur.getString(cur.getColumnIndex("DCode")));
            txtUpCode.setText(cur.getString(cur.getColumnIndex("UpCode")));
            txtVCode.setText(cur.getString(cur.getColumnIndex("VCode")));
            txtBari.setText(cur.getString(cur.getColumnIndex("Bari")));
            txtHH.setText(cur.getString(cur.getColumnIndex("HH")));
            txtWID.setText(cur.getString(cur.getColumnIndex("WID")));
            txtPGN.setText(cur.getString(cur.getColumnIndex("PGN")));
            txtPhNo.setText(cur.getString(cur.getColumnIndex("PhNo")));
            dtpVDate.setText(Global.DateConvertDMY(cur.getString(cur.getColumnIndex("VDate"))));
            spnResult.setSelection(Global.SpinnerItemPosition(spnResult, 2 ,cur.getString(cur.getColumnIndex("Result"))));
            txtWPoit.setText(cur.getString(cur.getColumnIndex("WPoit")));
            txtLatDeg.setText(cur.getString(cur.getColumnIndex("LatDeg")));
            txtLatMin.setText(cur.getString(cur.getColumnIndex("LatMin")));
            txtLatSec.setText(cur.getString(cur.getColumnIndex("LatSec")));
            txtLonDeg.setText(cur.getString(cur.getColumnIndex("LonDeg")));
            txtLonMin.setText(cur.getString(cur.getColumnIndex("LonMin")));
            txtLonSec.setText(cur.getString(cur.getColumnIndex("LonSec")));
            txtVisitNo.setText(cur.getString(cur.getColumnIndex("VisitNo")));
            for (int i = 0; i < rdogrpQ201.getChildCount(); i++)
            {
               rb = (RadioButton)rdogrpQ201.getChildAt(i);
               if (Global.Left(rb.getText().toString(), 1).equalsIgnoreCase(cur.getString(cur.getColumnIndex("Q201"))))
                  rb.setChecked(true);
               else
                  rb.setChecked(false);
            }
            spnQ202.setSelection(Global.SpinnerItemPosition(spnQ202, 2 ,cur.getString(cur.getColumnIndex("Q202"))));
            txtQ203.setText(cur.getString(cur.getColumnIndex("Q203")));

            cur.moveToNext();
          }
          cur.close();
       }
       catch(Exception  e)
       {
           Connection.MessageBox(iron_visits.this, e.getMessage());
           return;
       }
    }

*/




}