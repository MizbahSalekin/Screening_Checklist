package com.example.sclreg;


import static Common.Connection.MessageBox;
import static Common.Connection.SelectedSpinnerValue;
import static Common.Connection.SpinnerItemPositionAnyLength;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import Common.Connection;
import Common.Global;

public class Section_1_Screening_Checklist_Idf extends Activity {
    boolean networkAvailable = false;
    Location currentLocation;
    double currentLatitude, currentLongitude;

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

    String VariableID;
    private int hour;
    private int minute;
    /*private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;
            EditText tpTime;

        }
    };*/
    private int mDay;
    private int mMonth;
    private int mYear;
    static final int DATE_DIALOG = 1;
    static final int TIME_DIALOG = 2;

    Connection C;

    Global g;
    SimpleAdapter dataAdapter;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();


    LinearLayout secidno;
    View lineidno;
    TextView Vlblidno;
    TextView txtidno;
    TextView txtslno;

 /*   LinearLayout secq1;
    View lineq1;
    TextView Vlblq1;
    RadioGroup rdogrpq1;

    RadioButton rdoq11;
    RadioButton rdoq12;
    RadioButton rdoq13;*/

/*    LinearLayout secq2;
    View lineq2;
    TextView Vlblq2;
    Spinner spnq2;*/

 /*   LinearLayout secq3;
    View lineq3;
    TextView Vlblq3;
    Spinner spnq3;*/

    LinearLayout seczillaid;
    View linezillaid;
    TextView Vlblzillaid;
    Spinner spnzillaid;

    LinearLayout secupazilaid;
    View lineupazilaid;
    TextView Vlblupazilaid;
    Spinner spnupazilaid;
    LinearLayout secunionid;
    View lineunionid;
    TextView Vlblunionid;
    Spinner spnunionid;

    LinearLayout secunionother;
    View lineunionother;
    TextView Vlblunionother;
    EditText txtunionother;

    LinearLayout secvillageid;
    View linevillageid;
    //  TextView txtmouzaid;
    // TextView   txtvillage;
    TextView Vlblvillageid;
    EditText txtvillage_name;

    LinearLayout secq105;
    View lineq105;
    TextView Vlblq105;
    Spinner spnq105;

    LinearLayout secq106;
    View lineq106;
    TextView Vlblq106;
    Spinner spnq106;
    LinearLayout secq107;
    View lineq107;
    TextView Vlblq107;
    Spinner spnq107;


    LinearLayout secinterviewer_date;
    View lineinterviewer_date;
    TextView Vlblinterviewer_date;
    EditText dtpinterviewer_date;

    LinearLayout secq109;
    View lineq109;
    TextView Vlbq109;
    EditText txtq109;

    Button cmdSave;
    //Button cmdSave1;
    static String TableName;

    static String STARTTIME = "";
    static String DEVICEID = "";
    static String ENTRYUSER = "";
    //MySharedPreferences sp;
    static String lavel = "";
    static String Zila = "";
    static String UZila = "";
    static String UN = "";
    static String WD = "";


    //Bundle IDbundle;
    // static String IDNO = "";
    // static String SLNo = "";
    //static String Type = "";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.identifications_screening_checklist);
            C = new Connection(this);
            g = Global.getInstance();
            //level_id
            STARTTIME = g.CurrentTime24();
            DEVICEID = C.ReturnSingleValue("select ifnull(deviceno,'9999') as deviceno from deviceno");
            ;
            ENTRYUSER = C.ReturnSingleValue("Select ifnull(userid,'999') as deviceno  from login");
            lavel = C.ReturnSingleValue("select ifnull(level_id,'0') as level_id from providerdb");
            Zila = C.ReturnSingleValue("select ifnull(zillaid,'0') as zillaid from providerdb");
            UZila = C.ReturnSingleValue("select ifnull(upazilaid,'0') as upazilaid from providerdb");
            // UN = C.ReturnSingleValue("select ifnull(unionid,'0') as unionid from providerdb");
            // WD = C.ReturnSingleValue("select ifnull(ward,'0') as ward from providerdb");
            if (g.getCallFrom().equalsIgnoreCase("update")) {

            } else {
                if (lavel.equalsIgnoreCase("3")) {
                    UN = C.ReturnSingleValue("select ifnull(unionid,'0') as unionid from providerdb");
                    WD = C.ReturnSingleValue("select ifnull(ward,'0') as ward from providerdb");
                } else if (lavel.equalsIgnoreCase("2")) {
                    UN = C.ReturnSingleValue("select ifnull(unionid,'0') as unionid from providerdb");

                } else if (lavel.equalsIgnoreCase("1")) {

                }
            }
            ((ImageButton) findViewById(R.id.cmdBack)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    g.setlevel(lavel);
                    g.setDistrict(Zila);
                    g.setUpazila(UZila);
                    // g.setUnion(UN);
                    // g.setward(WD);
                    if (g.getCallFrom().equalsIgnoreCase("update")) {

                    } else {
                        if (lavel.equalsIgnoreCase("3")) {
                            g.setUnion(UN);
                            g.setward(WD);
                        } else if (lavel.equalsIgnoreCase("2")) {
                            g.setUnion(UN);
                        } else if (lavel.equalsIgnoreCase("1")) {
                            g.setUnion("");
                            g.setward("");
                        }
                    }

//                    Intent f1 = new Intent(Section_1_Screening_Checklist_Idf.this, schecklist_home.class);
//                    startActivity(f1);

                    finish();
                }
            });
            ((ImageButton) findViewById(R.id.cmdClose)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    g.setlevel(lavel);
                    g.setDistrict(Zila);
                    g.setUpazila(UZila);
                    //g.setUnion(UN);
                    // g.setward(WD);
                    if (g.getCallFrom().equalsIgnoreCase("update")) {

                    } else {
                        if (lavel.equalsIgnoreCase("3")) {
                            g.setUnion(UN);
                            g.setward(WD);
                        } else if (lavel.equalsIgnoreCase("2")) {
                            g.setUnion(UN);
                        } else if (lavel.equalsIgnoreCase("1")) {
                            g.setUnion("");
                            g.setward("");
                        }
                    }

//                    Intent f1 = new Intent(Section_1_Screening_Checklist_Idf.this, schecklist_home.class);
//                    startActivity(f1);

                    finish();
                }
            });

            // Type=ENTRYUSER;

            //IDbundle = getIntent().getExtras();
            // IDNO = IDbundle.getString("idno");

            TableName = "section_1_screening_checklist_idf";

            turnGPSOn();
            //GPS Location
            FindLocation();
            Double.toString(currentLatitude);
            Double.toString(currentLongitude);
            secidno = (LinearLayout) findViewById(R.id.secidno);
            lineidno = (View) findViewById(R.id.lineidno);
            Vlblidno = (TextView) findViewById(R.id.Vlblidno);
            txtidno = (TextView) findViewById(R.id.txtidno);


            seczillaid = (LinearLayout) findViewById(R.id.seczillaid);
            linezillaid = (View) findViewById(R.id.linezillaid);
            Vlblzillaid = (TextView) findViewById(R.id.Vlblzillaid);
            spnzillaid = (Spinner) findViewById(R.id.spnzillaid);

            // spnzillaid.setAdapter(C.getArrayAdapterMultiline("select DISTINCT (zillaid ||'- '||  zillaname) as zilla from zilla where zillaid='" + g.getDistrict() + "'"));


            secupazilaid = (LinearLayout) findViewById(R.id.secupazilaid);
            lineupazilaid = (View) findViewById(R.id.lineupazilaid);
            Vlblupazilaid = (TextView) findViewById(R.id.Vlblupazilaid);
            spnupazilaid = (Spinner) findViewById(R.id.spnupazilaid);
            // spnupazilaid.setAdapter(C.getArrayAdapterMultiline("select upazilaid||'- '||upazilaname as upazila from upazila where zillaid='" + g.getDistrict() + "' and upazilaid='" + g.getUpazila()+ "'"));//in('09','12','13','14') order by EvCode asc"));


            secunionid = (LinearLayout) findViewById(R.id.secunionid);
            lineunionid = (View) findViewById(R.id.lineunionid);
            Vlblunionid = (TextView) findViewById(R.id.Vlblunionid);
            spnunionid = (Spinner) findViewById(R.id.spnunionid);


            secunionother = (LinearLayout) findViewById(R.id.secunionother);
            lineunionother = (View) findViewById(R.id.lineunionother);
            Vlblunionother = (TextView) findViewById(R.id.Vlblunionother);
            txtunionother = (EditText) findViewById(R.id.txtunionother);

            secvillageid = (LinearLayout) findViewById(R.id.secvillageid);
            linevillageid = (View) findViewById(R.id.linevillageid);
            Vlblvillageid = (TextView) findViewById(R.id.Vlblvillageid);
            // txtmouzaid=(TextView) findViewById(R.id.txtmouzaid);
            // txtvillage=(TextView) findViewById(R.id.txtvillage);

            txtvillage_name = (EditText) findViewById(R.id.txtvillage_name);
            txtslno = (TextView) findViewById(R.id.txtslno);


            secq105 = (LinearLayout) findViewById(R.id.secq105);
            lineq105 = (View) findViewById(R.id.lineq105);
            Vlblq105 = (TextView) findViewById(R.id.Vlblq105);
            spnq105 = (Spinner) findViewById(R.id.spnq105);


            if (g.getCallFrom().equalsIgnoreCase("update")) {
                spnzillaid.setAdapter(C.getArrayAdapterMultiline("select DISTINCT (zillaid ||'- '||  zillaname) as zilla from zilla where zillaid"));

            } else {
                spnzillaid.setAdapter(C.getArrayAdapterMultiline("select DISTINCT (zillaid ||'- '||  zillaname) as zilla from zilla where zillaid='" + g.getDistrict() + "'"));
            }

            if (g.getCallFrom().equalsIgnoreCase("update")) {
                spnupazilaid.setAdapter(C.getArrayAdapterMultiline("select DISTINCT upazilaid||'- '||upazilaname as upazila from upazila"));

            } else {
                spnupazilaid.setAdapter(C.getArrayAdapterMultiline("select DISTINCT upazilaid||'- '||upazilaname as upazila from upazila where zillaid='" + g.getDistrict() + "' and upazilaid='" + g.getUpazila() + "'"));


            }

            if (g.getCallFrom().equalsIgnoreCase("update")) {

                spnunionid.setAdapter(C.getArrayAdapterMultiline("select DISTINCT unionid ||'- '||unionname as unions from unions"));

            } else {
                // String UNID=C.ReturnSingleValue("select cast(unionid as varchar(2)) from unions where zillaid='" + g.getDistrict() + "' and upazilaid='" + g.getUpazila() + "' and unionid='" + g.getUnion() + "'");
                spnunionid.setAdapter(C.getArrayAdapterMultiline("select DISTINCT unionid ||'- '||unionname as unions from unions where  zillaid='" + g.getDistrict() + "' and upazilaid='" + g.getUpazila() + "' and unionid='" + g.getUnion() + "'"));


            }


            if (g.getCallFrom().equalsIgnoreCase("update")) {
                spnq105.setAdapter(C.getArrayAdapterMultiline("SELECT DISTINCT ward_no||'-'||'ওয়ার্ড' AS word FROM [cluster]")); //ward_no

            } else {
                spnq105.setAdapter(C.getArrayAdapterMultiline("SELECT DISTINCT ward_no||'-'||'ওয়ার্ড' AS word FROM [cluster]  where  zillaid='" + g.getDistrict() + "' and upazilaid='" + g.getUpazila() + "' and unionid='" + g.getUnion() + "' and ward_no='" + g.getward() + "'")); //ward_no


            }


            secq106 = (LinearLayout) findViewById(R.id.secq106);
            lineq106 = (View) findViewById(R.id.lineq106);
            Vlblq106 = (TextView) findViewById(R.id.Vlblq106);
            spnq106 = (Spinner) findViewById(R.id.spnq106);


            if (g.getlevel().equalsIgnoreCase("1")) {
                secunionid.setVisibility(View.GONE);
                lineunionid.setVisibility(View.GONE);
                secq105.setVisibility(View.GONE);
                lineq105.setVisibility(View.GONE);
                //spnq105.setSelection();
                if (g.getProvType().equalsIgnoreCase("8") ||
                        g.getProvType().equalsIgnoreCase("9") ||
                        g.getProvType().equalsIgnoreCase("10") ||
                        g.getProvType().equalsIgnoreCase("13")
                ) {
                    spnq106.setAdapter(C.getArrayAdapterMultiline("Select '---সিলেক্ট করুন--' as facilityname union select providerid||'- '||facilityname as clinic from providerdb "));

                } else {
                    spnq106.setAdapter(C.getArrayAdapterMultiline("Select '---সিলেক্ট করুন--' as clinic union select code||'- '||cname as clinic from codelist where  typename='sc1' and code in('1','2')"));

                }
            }
            if (g.getlevel().equalsIgnoreCase("2")) {
                secunionid.setVisibility(View.VISIBLE);
                lineunionid.setVisibility(View.VISIBLE);
                secq105.setVisibility(View.GONE);
                lineq105.setVisibility(View.GONE);
                if (g.getProvType().equalsIgnoreCase("8") ||
                        g.getProvType().equalsIgnoreCase("9") ||
                        g.getProvType().equalsIgnoreCase("10") ||
                        g.getProvType().equalsIgnoreCase("13")
                ) {
                    spnq106.setAdapter(C.getArrayAdapterMultiline("Select '---সিলেক্ট করুন--' as facilityname union select providerid||'- '||facilityname as clinic from providerdb "));

                } else {
                    spnq106.setAdapter(C.getArrayAdapterMultiline("Select '---সিলেক্ট করুন--' as clinic union select code||'- '||cname as clinic from codelist where  typename='sc1' and code in('3')"));
                }
            }

            if (g.getlevel().equalsIgnoreCase("3")) {
                secunionid.setVisibility(View.VISIBLE);
                lineunionid.setVisibility(View.VISIBLE);
                secq105.setVisibility(View.VISIBLE);
                lineq105.setVisibility(View.VISIBLE);
                if (g.getProvType().equalsIgnoreCase("2")

                ) {
                    spnq106.setAdapter(C.getArrayAdapterMultiline("Select '---সিলেক্ট করুন--' as facilityname union select providerid||'- '||facilityname as clinic from providerdb "));

                } else {
                    spnq106.setAdapter(C.getArrayAdapterMultiline("Select '---সিলেক্ট করুন--' as clinic union select code||'- '||cname as clinic from codelist where  typename='sc1' and code in('4')"));
                }
            }

            secq107 = (LinearLayout) findViewById(R.id.secq107);
            lineq107 = (View) findViewById(R.id.lineq107);
            Vlblq107 = (TextView) findViewById(R.id.Vlblq107);
            spnq107 = (Spinner) findViewById(R.id.spnq107);

            if (g.getCallFrom().equalsIgnoreCase("update")) {

                //  spnq107.setAdapter(C.getArrayAdapterMultiline(" Select '---সিলেক্ট করুন--' as codelist union select code||'- '||cname as clinic from codelist where typename='type'"));

                String typename = C.ReturnSingleValue("select ifnull(typename,'') as typename from codelist where typename in('dg1','dg2','dg3') and code='" + g.getProviderDeg() + "'");
                spnq107.setAdapter(C.getArrayAdapterMultiline("Select '---সিলেক্ট করুন--' as clinic union select code||'- '||cname as clinic from codelist where typename='" + typename + "'"));


            } else {
                spnq106.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                        // g.setCallFrom("");
                        //  spnq107.setAdapter(C.getArrayAdapterMultiline(" Select '---সিলেক্ট করুন--' as codelist union select code||'- '||cname as clinic from codelist where typename='type'"));
                        String[] q2 = spnq106.getSelectedItem().toString().split("-");

                        if (q2[0].equals("1") || q2[0].equals("2")) {

                            spnq107.setAdapter(C.getArrayAdapterMultiline("Select '---সিলেক্ট করুন--' as clinic union select code||'- '||cname as clinic from codelist where typename in('dg1') and code='" + g.getProvType() + "'"));
                        }
//||q2[0].equals("6")||q2[0].equals("7") ||q2[0].equals("5")
                        else if (q2[0].equals("3")) {

                            spnq107.setAdapter(C.getArrayAdapterMultiline("Select '---সিলেক্ট করুন--' as clinic union select code||'- '||cname as clinic from codelist where typename in('dg2') and code='" + g.getProvType() + "'"));
                        } else if (q2[0].equals("4")) {

                            spnq107.setAdapter(C.getArrayAdapterMultiline("Select '---সিলেক্ট করুন--' as clinic union select code||'- '||cname as clinic from codelist where typename in('dg3') and code='" + g.getProvType() + "'"));
                        } else {
                            spnq107.setAdapter(C.getArrayAdapterMultiline("Select '---সিলেক্ট করুন--' as clinic union select code||'- '||cname as clinic from codelist where typename in('dg3','dg2') and code='" + g.getProvType() + "'"));

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }
                });
            }
            secinterviewer_date = (LinearLayout) findViewById(R.id.secinterviewer_date);
            lineinterviewer_date = (View) findViewById(R.id.lineinterviewer_date);
            Vlblinterviewer_date = (TextView) findViewById(R.id.Vlblinterviewer_date);
            dtpinterviewer_date = (EditText) findViewById(R.id.dtpinterviewer_date);

            secq109 = (LinearLayout) findViewById(R.id.secq109);
            lineq109 = (View) findViewById(R.id.lineq109);
            Vlbq109 = (TextView) findViewById(R.id.Vlbq109);
            txtq109 = (EditText) findViewById(R.id.txtq109);

            if (g.getCallFrom().equalsIgnoreCase("")) {
                spnq107.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        String[] zid = spnzillaid.getSelectedItem().toString().split("-");
                        String[] upid = spnupazilaid.getSelectedItem().toString().split("-");
                        //String[] unid = spnunionid.getSelectedItem().toString().split("-");
                        //String[] cluster = spnq106.getSelectedItem().toString().split("-");
                        String[] type = spnq107.getSelectedItem().toString().split("-");
                        String idno = "";
                        String IncrementNo = "";

                        if (spnq107.getSelectedItemPosition() != 0) {

                            // if(upid[0].equalsIgnoreCase("88")) {
  /*                          IncrementNo = IncrementNumber_new(zid[0], "99", type[0], g.getDeviceNo());
                            txtslno.setText(IncrementNo);
                            String Device_no = C.ReturnSingleValue("select ifnull(deviceno,'99') as deviceno from deviceno");

                            String upazilaid = "";
                            if (upid[0].length() == 1) {
                                upazilaid = "0" + upid[0];
                            } else {
                                upazilaid = upid[0];
                            }



                            //String unionid="99";

                            String IncrementNumber;

                            if (IncrementNo.length() == 1) {
                                IncrementNumber = "0" + IncrementNo;
                            } else {
                                IncrementNumber = IncrementNo;
                            }


                            // SLNo=IncrementNo;
                            idno = Device_no+zid[0]+upazilaid + type[0]+IncrementNumber;
                            txtidno.setText(idno);
                         //   txtinterviewer_id.setText(C.ReturnSingleValue("Select userid||' - '||username from login"));

                      //  }
                    else {*/

                            IncrementNo = IncrementNumber_new(zid[0], upid[0], type[0], g.getDeviceNo());
                            txtslno.setText(IncrementNo);
                            String Device_no = C.ReturnSingleValue("select ifnull(deviceno,'99') as deviceno from deviceno");
                            String upazilaid = "";
                            if (upid[0].length() == 1) {
                                upazilaid = "0" + upid[0];
                            } else {
                                upazilaid = upid[0];
                            }


                            String IncrementNumber;

                            if (IncrementNo.length() == 1) {
                                IncrementNumber = "0" + IncrementNo;
                            } else {
                                IncrementNumber = IncrementNo;
                            }
                            // SLNo=IncrementNo;
                            // idno = upazilaid + unionid + IncrementNumber + Device_no;
                            idno = Device_no + zid[0] + upazilaid + type[0] + IncrementNumber;
                            txtidno.setText(idno);
                            //  txtinterviewer_id.setText(C.ReturnSingleValue("Select userid||' - '||username from login"));

                            // }
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }
                });
            }

            dtpinterviewer_date.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_RIGHT = 2;
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (dtpinterviewer_date.getRight() - dtpinterviewer_date.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            VariableID = "btninterviewer_date";
                            showDialog(DATE_DIALOG);
                            return true;
                        }
                    }
                    return false;
                }
            });


            //Hide all skip variables
/*        seczillaid.setVisibility(View.GONE);
        linezillaid.setVisibility(View.GONE);
        secupazilaid.setVisibility(View.GONE);
        lineupazilaid.setVisibility(View.GONE);
        secunionid.setVisibility(View.GONE);
        lineunionid.setVisibility(View.GONE);
        //secvillageid.setVisibility(View.GONE);
        //linevillageid.setVisibility(View.GONE);
        secq105.setVisibility(View.GONE);
        lineq105.setVisibility(View.GONE);
        secq106.setVisibility(View.GONE);
        lineq106.setVisibility(View.GONE);
        secunionother.setVisibility(View.GONE);
        lineunionother.setVisibility(View.GONE);

        secq107.setVisibility(View.GONE);
        lineq107.setVisibility(View.GONE);
        secq2.setVisibility(View.GONE);
        lineq2.setVisibility(View.GONE);*/
            if (g.getCallFrom().equalsIgnoreCase("update")) {
                txtidno.setText(g.getIdNo());

                DataSearch(g.getIdNo());

            }


            cmdSave = (Button) findViewById(R.id.cmdSave);
            cmdSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DataSave();

                }
            });


        } catch (Exception e) {
            MessageBox(Section_1_Screening_Checklist_Idf.this, e.getMessage());
            return;
        }
    }

    private String IncrementNumber_new(String zillaid, String upazilaid, String type, String deviceid) {

        //
        String SQL = "";
        SQL = "Select (ifnull(max(cast(slno as int)),0)+1)slno from section_1_screening_checklist_idf";
        // SQL += " where zillaid='" + zillaid + "' and upazilaid='"+upazilaid+ "' and unionid='"+unionid+ "' and mouzaid='"+mouzaid+ "' and villageid='"+villageid+ "' and deviceid='"+deviceid+"'"; //deviceid
        SQL += " where zillaid='" + zillaid + "' and upazilaid='" + upazilaid + "' and q107='" + type + "' and deviceid='" + deviceid + "'"; //deviceid
        String Increment = C.ReturnSingleValue(SQL);
        return Increment;
    }


    private void DataSave() {
        try {

            String DV = "";
            // String[] zid = spnzillaid.getSelectedItem().toString().split("-");

    /*    if(spnzillaid.getSelectedItemPosition()==0  & seczillaid.isShown())
          {
            Connection.MessageBox(Section_1_Screening_Checklist_Idf.this, "101. জেলা  সিলেক্ট করুন ।");
            spnzillaid.requestFocus();
            return;
          }
         if(spnupazilaid.getSelectedItemPosition()==0  & secupazilaid.isShown())
          {
            Connection.MessageBox(Section_1_Screening_Checklist_Idf.this, "102. উপজেলা সিলেক্ট করুন ।");
            spnupazilaid.requestFocus();
            return;
          }
         if(spnunionid.getSelectedItemPosition()==0  & secunionid.isShown())
          {
            Connection.MessageBox(Section_1_Screening_Checklist_Idf.this, "103. ইউনিয়ন  সিলেক্ট করুন ।");
            spnunionid.requestFocus();
            return;
          }

        if(spnq105.getSelectedItemPosition()==0  & secq105.isShown())
        {
            Connection.MessageBox(Section_1_Screening_Checklist_Idf.this, "105. ওয়ার্ড নম্বর সিলেক্ট করুন ।");
            spnq105.requestFocus();
            return;
        }*/

            if (spnq106.getSelectedItemPosition() == 0 & secq106.isShown()) {
                MessageBox(Section_1_Screening_Checklist_Idf.this, "106. সেবা কেন্দ্রের নাম সিলেক্ট করুন ।");
                spnq106.requestFocus();
                return;
            }

            if (spnq107.getSelectedItemPosition() == 0 & secq107.isShown()) {
                MessageBox(Section_1_Screening_Checklist_Idf.this, "107. সেবা প্রদানকারী পদবী সিলেক্ট করুন ।");
                spnq107.requestFocus();
                return;
            }

            if (txtidno.getText().toString().length() == 0 & secidno.isShown()) {
                MessageBox(Section_1_Screening_Checklist_Idf.this, "IDNo খালি হতে পারে না ।");
                txtidno.requestFocus();
                return;
            }

            DV = Global.DateValidate(dtpinterviewer_date.getText().toString());
            if (DV.length() != 0 & secinterviewer_date.isShown()) {
                MessageBox(Section_1_Screening_Checklist_Idf.this, "108. স্বাক্ষাতকার গ্রহণের তারিখ ফাঁকা");
                dtpinterviewer_date.requestFocus();
                return;
            }


            if (txtq109.getText().toString().length() == 0 & secq109.isShown()) {
                MessageBox(Section_1_Screening_Checklist_Idf.this, "109. সাক্ষাৎকার গ্রহণকারীর নাম ফাঁকা ।");
                txtq109.requestFocus();
                return;
            }

            String SQL = "";
            RadioButton rb;


            Section_1_Screening_Checklist_Idf_DataModel objSave = new Section_1_Screening_Checklist_Idf_DataModel();
            objSave.setidno(txtidno.getText().toString());
            objSave.setslno(txtslno.getText().toString());
            //objSave.setq2((spnq2.getSelectedItemPosition() == 0 ? "" : SelectedSpinnerValue(spnq2.getSelectedItem().toString(), "-")));
            //objSave.setq3((spnq3.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnq3.getSelectedItem().toString(), "-")));

            if (g.getlevel().equalsIgnoreCase("1")) {
                objSave.setzillaid(SelectedSpinnerValue(spnzillaid.getSelectedItem().toString(), "-"));
                objSave.setupazilaid(SelectedSpinnerValue(spnupazilaid.getSelectedItem().toString(), "-"));
                objSave.setq105("");
            }

            if (g.getlevel().equalsIgnoreCase("2")) {
                objSave.setzillaid(SelectedSpinnerValue(spnzillaid.getSelectedItem().toString(), "-"));
                objSave.setupazilaid(SelectedSpinnerValue(spnupazilaid.getSelectedItem().toString(), "-"));
                objSave.setunionid(SelectedSpinnerValue(spnunionid.getSelectedItem().toString(), "-"));
                objSave.setunionid("");
                objSave.setq105("");
            }

            if (g.getlevel().equalsIgnoreCase("3")) {
                objSave.setzillaid(SelectedSpinnerValue(spnzillaid.getSelectedItem().toString(), "-"));
                objSave.setupazilaid(SelectedSpinnerValue(spnupazilaid.getSelectedItem().toString(), "-"));
                objSave.setunionid(SelectedSpinnerValue(spnunionid.getSelectedItem().toString(), "-"));
                objSave.setq105(SelectedSpinnerValue(spnq105.getSelectedItem().toString(), "-"));

            }

            objSave.setunion_name(txtunionother.getText().toString());
            //objSave.setvill_name(txtvillage_name.getText().toString());

            //objSave.setq106("");
            objSave.setq106((spnq106.getSelectedItemPosition() == 0 ? "" : SelectedSpinnerValue(spnq106.getSelectedItem().toString(), "-")));
            // objSave.setq106((spnq106.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnq106.getSelectedItem().toString(), "-")));
            objSave.setq107((spnq107.getSelectedItemPosition() == 0 ? "" : SelectedSpinnerValue(spnq107.getSelectedItem().toString(), "-")));
            // String[] interviewer_id =txtinterviewer_id.getText().toString().split("-");
            // objSave.setinterviewer_id(interviewer_id[0]);
            objSave.setq108(dtpinterviewer_date.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpinterviewer_date.getText().toString()) : dtpinterviewer_date.getText().toString());
            objSave.setq109(txtq109.getText().toString());
            objSave.setEnDt(Global.DateTimeNowYMDHMS());
            //STARTTIME
            objSave.setStartTime(STARTTIME);
            objSave.setEndTime(g.CurrentTime24());
            objSave.setDeviceID(DEVICEID);
            objSave.setEntryUser(ENTRYUSER); //from data entry user list
            objSave.setmodifyDate(Global.DateTimeNowYMDHMS());
            //objSave.setmodifyDate(Global.DateTimeNowYMDHMS());
            objSave.setLat(Double.toString(currentLatitude));
            objSave.setLon(Double.toString(currentLongitude));

            String status = objSave.SaveUpdateData(this);
            if (status.length() == 0) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("res", "");
                setResult(Activity.RESULT_OK, returnIntent);

                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                //MessageBox(Section_1_Screening_Checklist_Idf.this, "Saved Successfully");


            } else {
                Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
                //MessageBox(Section_1_Screening_Checklist_Idf.this, status);
                return;
            }


            g.setlevel(lavel);
            g.setDistrict(Zila);
            g.setUpazila(UZila);
            if (g.getCallFrom().equalsIgnoreCase("update")) {

            } else {
                if (lavel.equalsIgnoreCase("3")) {
                    g.setUnion(UN);
                    g.setward(WD);
                } else if (lavel.equalsIgnoreCase("2")) {
                    g.setUnion(UN);
                } else if (lavel.equalsIgnoreCase("1")) {
                    g.setUnion("");
                    g.setward("");
                }
            }
            g.setIdNo(txtidno.getText().toString());
            g.setVDate(dtpinterviewer_date.getText().toString());
            // g.setProviderDeg(Global.Left())

            //finish();
            Intent f1 = new Intent(getApplicationContext(), Section_2_Vaccinations_Info.class);
            startActivity(f1);


        } catch (Exception e) {
            MessageBox(Section_1_Screening_Checklist_Idf.this, e.getMessage());
            return;
        }
    }

    private void DataSearch(String idno) {
        try {

            RadioButton rb;
            Section_1_Screening_Checklist_Idf_DataModel d = new Section_1_Screening_Checklist_Idf_DataModel();
            String SQL = "Select * from " + TableName + "  Where idno='" + idno + "'";
            List<Section_1_Screening_Checklist_Idf_DataModel> data = d.SelectAll(this, SQL);
            for (Section_1_Screening_Checklist_Idf_DataModel item : data) {
                txtidno.setText(item.getidno());
                txtslno.setText(item.getslno());
                // spnq2.setSelection(SpinnerItemPositionAnyLength(spnq2, item.getq2()));
                // spnq3.setSelection(Connection.SpinnerItemPositionAnyLength(spnq3, item.getq3()));
                spnzillaid.setSelection(SpinnerItemPositionAnyLength(spnzillaid, item.getzillaid()));
                spnupazilaid.setSelection(SpinnerItemPositionAnyLength(spnupazilaid, item.getupazilaid()));
                spnunionid.setSelection(SpinnerItemPositionAnyLength(spnunionid, item.getunionid()));
                txtunionother.setText(item.getunion_name());
                // txtvillage_name.setText(item.getvill_name());
                spnq105.setSelection(SpinnerItemPositionAnyLength(spnq105, item.getq105()));
                spnq106.setSelection(SpinnerItemPositionAnyLength(spnq106, item.getq106()));
                spnq107.setSelection(SpinnerItemPositionAnyLength(spnq107, item.getq107()));
                dtpinterviewer_date.setText(item.getq108().toString().length() == 0 ? "" : Global.DateConvertDMY(item.getq108()));
                txtq109.setText(item.getq109());
                //txtinterviewer_id.setText(item.getinterviewer_id());


            }
        } catch (Exception e) {
            MessageBox(Section_1_Screening_Checklist_Idf.this, e.getMessage());
            return;
        }


    }


    protected Dialog onCreateDialog(int id) {
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mDateSetListener, g.mYear, g.mMonth - 1, g.mDay);
            case TIME_DIALOG:
                return new TimePickerDialog(this, timePickerListener, hour, minute, false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;
            EditText dtpDate;


            dtpDate = (EditText) findViewById(R.id.dtpinterviewer_date);
            if (VariableID.equals("btninterviewer_date")) {
                dtpDate = (EditText) findViewById(R.id.dtpinterviewer_date);
            }
            dtpDate.setText(new StringBuilder()
                    .append(Global.Right("00" + mDay, 2)).append("/")
                    .append(Global.Right("00" + mMonth, 2)).append("/")
                    .append(mYear));
        }
    };
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;
            EditText tpTime;


         /*   tpTime = (EditText)findViewById(R.id.txtq111);
            if (VariableID.equals("btnq111"))
            {
                tpTime = (EditText)findViewById(R.id.txtq111);
            }
            tpTime.setText(new StringBuilder().append(Global.Right("00"+hour,2)).append(":").append(Global.Right("00"+minute,2)));
*/
        }
    };
/* private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
   public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
      hour = selectedHour; minute = selectedMinute;
      EditText tpTime;


         tpTime.setText(new StringBuilder().append(Global.Right("00"+hour,2)).append(":").append(Global.Right("00"+minute,2)));

   }
 };*/


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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
}

void updateLocation(Location location) {
    currentLocation  = location;
    currentLatitude  = currentLocation.getLatitude();
    currentLongitude = currentLocation.getLongitude();
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

    if(provider!=null && provider.contains("gps")){ //if gps is enabled
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
    //turnGPSOff();
}
}