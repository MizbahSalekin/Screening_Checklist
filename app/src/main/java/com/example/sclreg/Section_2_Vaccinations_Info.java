package com.example.sclreg;


import static Common.Connection.MessageBox;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import Common.Connection;
import Common.Global;


public class Section_2_Vaccinations_Info extends Activity {
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
    private int mDay;
    private int mMonth;
    private int mYear;
    static final int DATE_DIALOG = 1;
    static final int TIME_DIALOG = 2;

    Connection C;
    Global g;
    SimpleAdapter dataAdapter;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    // TextView lblHeading;
        /* LinearLayout seclblsec-2;
         View linelblsec-2;*/
    LinearLayout secidno;
    View lineidno;
    TextView Vlblidno;
    EditText txtidno;
    LinearLayout secq201;
    View lineq201;
    TextView Vlblq201;
    EditText txtq201;
    LinearLayout secq202;
    View lineq202;
    TextView Vlblq202;
    EditText dtpq202;
    //LinearLayout secq202a;
    // View lineq202a;
    TextView Vlblq202a;
    CheckBox chkq202a;
    LinearLayout secq202b;
    View lineq202b;
    LinearLayout secq202b1;
    View lineq202b1;
    TextView Vlblq202b1;
    EditText txtq202b1;
    //LinearLayout secq202b2;
    // View lineq202b2;
    TextView Vlblq202b2;
    EditText txtq202b2;
    // LinearLayout secq202b3;
    // View lineq202b3;
    TextView Vlblq202b3;
    EditText txtq202b3;
    LinearLayout secq203;
    View lineq203;
    TextView Vlblq203;
    RadioGroup rdogrpq203;

    RadioButton rdoq2031;
    RadioButton rdoq2032;
    //RadioButton rdoq2033;

    LinearLayout secq204;
    View lineq204;
    LinearLayout secq204a;
    View lineq204a;
    TextView Vlblq204a;
    CheckBox chkq204a;
    LinearLayout secq204b;
    View lineq204b;
    TextView Vlblq204b;
    CheckBox chkq204b;
    LinearLayout secq204c;
    View lineq204c;
    TextView Vlblq204c;
    CheckBox chkq204c;
    LinearLayout secq204d;
    View lineq204d;
    TextView Vlblq204d;
    CheckBox chkq204d;
    LinearLayout secq204e;
    View lineq204e;
    TextView Vlblq204e;
    CheckBox chkq204e;
    LinearLayout secq204f;
    View lineq204f;
    TextView Vlblq204f;
    CheckBox chkq204f;
    LinearLayout secq204g;
    View lineq204g;
    TextView Vlblq204g;
    CheckBox chkq204g;
    LinearLayout secq204h;
    View lineq204h;
    TextView Vlblq204h;
    CheckBox chkq204h;
    LinearLayout secq204i;
    View lineq204i;
    TextView Vlblq204i;
    CheckBox chkq204i;
    LinearLayout secq204j;
    View lineq204j;
    TextView Vlblq204j;
    CheckBox chkq204j;
    LinearLayout secq204k;
    View lineq204k;
    TextView Vlblq204k;
    CheckBox chkq204k;
    LinearLayout secq204l;
    View lineq204l;
    TextView Vlblq204l;
    CheckBox chkq204l;
    LinearLayout secq204x;
    View lineq204x;
    TextView Vlblq204x;
    CheckBox chkq204x;
    LinearLayout secq204x1;
    View lineq204x1;
    TextView Vlblq204x1;
    EditText txtq204x1;
    LinearLayout secq205;
    View lineq205;
    LinearLayout secq205a;
    View lineq205a;
    TextView Vlblq205a;
    CheckBox chkq205a;
    LinearLayout secq205b;
    View lineq205b;
    TextView Vlblq205b;
    CheckBox chkq205b;
    LinearLayout secq205c;
    View lineq205c;
    TextView Vlblq205c;
    CheckBox chkq205c;
    LinearLayout secq205d;
    View lineq205d;
    TextView Vlblq205d;
    CheckBox chkq205d;
    LinearLayout secq205e;
    View lineq205e;
    TextView Vlblq205e;
    CheckBox chkq205e;
    LinearLayout secq205f;
    View lineq205f;
    TextView Vlblq205f;
    CheckBox chkq205f;
    LinearLayout secq205x;
    View lineq205x;
    TextView Vlblq205x;
    CheckBox chkq205x;
    LinearLayout secq206;
    View lineq206;
    LinearLayout secq206a;
    View lineq206a;
    TextView Vlblq206a;
    EditText txtq206a;
    LinearLayout secq206b;
    View lineq206b;
    TextView Vlblq206b;
    EditText txtq206b;
    LinearLayout secq206c;
    View lineq206c;
    TextView Vlblq206c;
    EditText txtq206c;

    LinearLayout secq206d;
    View lineq206d;
    TextView Vlblq206d;
    EditText txtq206d;

    LinearLayout secrefcenter;
    View linerefcenter;
    TextView Vlblrefcenter;
    CheckBox chkrefcenter;

    LinearLayout secupazilaid;
    View lineupazilaid;
    TextView Vlblupazilaid;
    Spinner spnupazilaid;

    LinearLayout secunionid;
    View lineunionid;
    TextView Vlblunionid;
    Spinner spnunionid;

    LinearLayout secwardid;
    View linewardid;
    TextView Vlblwardid;
    Spinner spnwardid;

    LinearLayout secsubblockid;
    View linesubblockid;
    TextView Vlblsubblockid;
    Spinner spnsubblockid;

    /* LinearLayout secepisubblock;
     TextView txtepisubblock;
*/


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
    Bundle IDbundle;
    static String IDNO = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.section_2_vaccinations_info);
            // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            C = new Connection(this);
            g = Global.getInstance();
            DEVICEID = C.ReturnSingleValue("select ifnull(deviceno,'9999') as deviceno from deviceno");
            ;
            ENTRYUSER = C.ReturnSingleValue("Select ifnull(userid,'9999') as userid  from login");
            lavel = C.ReturnSingleValue("select ifnull(level_id,'0') as level_id from providerdb");
            Zila = C.ReturnSingleValue("select ifnull(zillaid,'0') as zillaid from providerdb");
            UZila = C.ReturnSingleValue("select ifnull(upazilaid,'0') as upazilaid from providerdb");
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

            STARTTIME = g.CurrentTime24();
            ImageButton cmdBack = (ImageButton) findViewById(R.id.cmdBack);
            cmdBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
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

//                    Intent f1 = new Intent(Section_2_Vaccinations_Info.this, Section_1_Screening_Checklist_Idf.class);
//                    startActivity(f1);

                    finish();
                }
            });

            ImageButton cmdClose = (ImageButton) findViewById(R.id.cmdClose);
            cmdClose.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
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

//                    Intent f1 = new Intent(Section_2_Vaccinations_Info.this, Section_1_Screening_Checklist_Idf.class);
//                    startActivity(f1);

                    finish();
                }
            });


            TableName = "Section_2_Vaccinations_Info";
            secidno = (LinearLayout) findViewById(R.id.secidno);
            lineidno = (View) findViewById(R.id.lineidno);
            Vlblidno = (TextView) findViewById(R.id.Vlblidno);
            txtidno = (EditText) findViewById(R.id.txtidno);
            txtidno.setText(g.getIdNo());
            txtidno.setEnabled(false);

            secq201 = (LinearLayout) findViewById(R.id.secq201);
            lineq201 = (View) findViewById(R.id.lineq201);
            Vlblq201 = (TextView) findViewById(R.id.Vlblq201);
            txtq201 = (EditText) findViewById(R.id.txtq201);
            secq202 = (LinearLayout) findViewById(R.id.secq202);
            lineq202 = (View) findViewById(R.id.lineq202);
            Vlblq202 = (TextView) findViewById(R.id.Vlblq202);
            dtpq202 = (EditText) findViewById(R.id.dtpq202);
            // secq202a=(LinearLayout)findViewById(R.id.secq202a);
            //  lineq202a=(View)findViewById(R.id.lineq202a);
            Vlblq202a = (TextView) findViewById(R.id.Vlblq202a);
            chkq202a = (CheckBox) findViewById(R.id.chkq202a);
            chkq202a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq202a.isChecked() == true) {
                        secq202b.setVisibility(View.VISIBLE);
                        lineq202b.setVisibility(View.VISIBLE);
                        secq202b1.setVisibility(View.VISIBLE);
                        lineq202b1.setVisibility(View.VISIBLE);
                        dtpq202.setVisibility(View.GONE);
                        dtpq202.setText("");
                    } else if (chkq202a.isChecked() == false) {
                        secq202b.setVisibility(View.GONE);
                        lineq202b.setVisibility(View.GONE);
                        secq202b1.setVisibility(View.GONE);
                        lineq202b1.setVisibility(View.GONE);
                        dtpq202.setVisibility(View.VISIBLE);
                        txtq202b1.setText("");
                        txtq202b2.setText("");
                        txtq202b3.setText("");

                    }

                }
            });

            secq202b = (LinearLayout) findViewById(R.id.secq202b);
            lineq202b = (View) findViewById(R.id.lineq202b);
            secq202b1 = (LinearLayout) findViewById(R.id.secq202b1);
            lineq202b1 = (View) findViewById(R.id.lineq202b1);
            Vlblq202b1 = (TextView) findViewById(R.id.Vlblq202b1);
            txtq202b1 = (EditText) findViewById(R.id.txtq202b1);
            // secq202b2=(LinearLayout)findViewById(R.id.secq202b2);
            //  lineq202b2=(View)findViewById(R.id.lineq202b2);
            Vlblq202b2 = (TextView) findViewById(R.id.Vlblq202b2);
            txtq202b2 = (EditText) findViewById(R.id.txtq202b2);
            // secq202b3=(LinearLayout)findViewById(R.id.secq202b3);
            // lineq202b3=(View)findViewById(R.id.lineq202b3);
            Vlblq202b3 = (TextView) findViewById(R.id.Vlblq202b3);
            txtq202b3 = (EditText) findViewById(R.id.txtq202b3);
            secq203 = (LinearLayout) findViewById(R.id.secq203);
            lineq203 = (View) findViewById(R.id.lineq203);
            Vlblq203 = (TextView) findViewById(R.id.Vlblq203);
            rdogrpq203 = (RadioGroup) findViewById(R.id.rdogrpq203);

            rdoq2031 = (RadioButton) findViewById(R.id.rdoq2031);
            rdoq2032 = (RadioButton) findViewById(R.id.rdoq2032);
            // rdoq2033 = (RadioButton) findViewById(R.id.rdoq2033);

            rdogrpq203.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int radioButtonID) {
                    String rbData = "";
                    RadioButton rb;
                    String[] d_rdogrpq203 = new String[]{"1", "2"};
                    for (int i = 0; i < rdogrpq203.getChildCount(); i++) {
                        rb = (RadioButton) rdogrpq203.getChildAt(i);
                        if (rb.isChecked()) rbData = d_rdogrpq203[i];
                    }
                    if (rbData.equalsIgnoreCase("1")) {

                        secq204.setVisibility(View.GONE);
                        lineq204.setVisibility(View.GONE);
                        secq204a.setVisibility(View.GONE);
                        lineq204a.setVisibility(View.GONE);
                        secq204b.setVisibility(View.GONE);
                        lineq204b.setVisibility(View.GONE);
                        secq204c.setVisibility(View.GONE);
                        lineq204c.setVisibility(View.GONE);
                        secq204d.setVisibility(View.GONE);
                        lineq204d.setVisibility(View.GONE);
                        secq204e.setVisibility(View.GONE);
                        lineq204e.setVisibility(View.GONE);
                        secq204f.setVisibility(View.GONE);
                        lineq204f.setVisibility(View.GONE);
                        secq204g.setVisibility(View.GONE);
                        lineq204g.setVisibility(View.GONE);
                        secq204h.setVisibility(View.GONE);
                        lineq204h.setVisibility(View.GONE);
                        secq204i.setVisibility(View.GONE);
                        lineq204i.setVisibility(View.GONE);
                        secq204j.setVisibility(View.GONE);
                        lineq204j.setVisibility(View.GONE);
                        secq204k.setVisibility(View.GONE);
                        lineq204k.setVisibility(View.GONE);
                        secq204l.setVisibility(View.GONE);
                        lineq204l.setVisibility(View.GONE);
                        secq204x.setVisibility(View.GONE);
                        lineq204x.setVisibility(View.GONE);
                        secq205.setVisibility(View.GONE);
                        lineq205.setVisibility(View.GONE);
                        secq205a.setVisibility(View.GONE);
                        lineq205a.setVisibility(View.GONE);
                        secq205b.setVisibility(View.GONE);
                        lineq205b.setVisibility(View.GONE);
                        secq205c.setVisibility(View.GONE);
                        lineq205c.setVisibility(View.GONE);
                        secq205d.setVisibility(View.GONE);
                        lineq205d.setVisibility(View.GONE);
                        secq205e.setVisibility(View.GONE);
                        lineq205e.setVisibility(View.GONE);
                        secq205f.setVisibility(View.GONE);
                        lineq205f.setVisibility(View.GONE);
                        secq205x.setVisibility(View.GONE);
                        lineq205x.setVisibility(View.GONE);
                        secq206.setVisibility(View.GONE);
                        lineq206.setVisibility(View.GONE);
                        secq206a.setVisibility(View.GONE);
                        lineq206a.setVisibility(View.GONE);
                        secq206b.setVisibility(View.GONE);
                        lineq206b.setVisibility(View.GONE);
                        secq206c.setVisibility(View.GONE);
                        lineq206c.setVisibility(View.GONE);

                        secq206d.setVisibility(View.GONE);
                        lineq206d.setVisibility(View.GONE);
                        secrefcenter.setVisibility(View.GONE);
                        linerefcenter.setVisibility(View.GONE);
                        chkq204a.setChecked(false);
                        chkq204b.setChecked(false);
                        chkq204c.setChecked(false);
                        chkq204d.setChecked(false);
                        chkq204e.setChecked(false);
                        chkq204f.setChecked(false);
                        chkq204g.setChecked(false);
                        chkq204h.setChecked(false);
                        chkq204i.setChecked(false);
                        chkq204j.setChecked(false);
                        chkq204k.setChecked(false);
                        chkq204l.setChecked(false);
                        chkq204x.setChecked(false);
                        chkq205a.setChecked(false);
                        chkq205b.setChecked(false);
                        chkq205c.setChecked(false);
                        chkq205d.setChecked(false);
                        chkq205e.setChecked(false);
                        chkq205f.setChecked(false);
                        chkq205x.setChecked(false);
                        txtq206a.setText("");
                        txtq206b.setText("");
                        txtq206c.setText("");
                        txtq206d.setText("");
                        chkrefcenter.setChecked(false);


                    } else if (rbData.equalsIgnoreCase("2")) {

                        secq204.setVisibility(View.VISIBLE);
                        lineq204.setVisibility(View.VISIBLE);
                        secq204a.setVisibility(View.VISIBLE);
                        lineq204a.setVisibility(View.VISIBLE);
                        secq204b.setVisibility(View.VISIBLE);
                        lineq204b.setVisibility(View.VISIBLE);
                        secq204c.setVisibility(View.VISIBLE);
                        lineq204c.setVisibility(View.VISIBLE);
                        secq204d.setVisibility(View.VISIBLE);
                        lineq204d.setVisibility(View.VISIBLE);
                        secq204e.setVisibility(View.VISIBLE);
                        lineq204e.setVisibility(View.VISIBLE);
                        secq204f.setVisibility(View.VISIBLE);
                        lineq204f.setVisibility(View.VISIBLE);
                        secq204g.setVisibility(View.VISIBLE);
                        lineq204g.setVisibility(View.VISIBLE);
                        secq204h.setVisibility(View.VISIBLE);
                        lineq204h.setVisibility(View.VISIBLE);
                        secq204i.setVisibility(View.VISIBLE);
                        lineq204i.setVisibility(View.VISIBLE);
                        secq204j.setVisibility(View.VISIBLE);
                        lineq204j.setVisibility(View.VISIBLE);
                        secq204k.setVisibility(View.VISIBLE);
                        lineq204k.setVisibility(View.VISIBLE);
                        secq204l.setVisibility(View.VISIBLE);
                        lineq204l.setVisibility(View.VISIBLE);
                        secq204x.setVisibility(View.VISIBLE);
                        lineq204x.setVisibility(View.VISIBLE);

                        secq205.setVisibility(View.VISIBLE);
                        lineq205.setVisibility(View.VISIBLE);
                        secq205a.setVisibility(View.VISIBLE);
                        lineq205a.setVisibility(View.VISIBLE);
                        secq205b.setVisibility(View.VISIBLE);
                        lineq205b.setVisibility(View.VISIBLE);
                        secq205c.setVisibility(View.VISIBLE);
                        lineq205c.setVisibility(View.VISIBLE);
                        secq205d.setVisibility(View.VISIBLE);
                        lineq205d.setVisibility(View.VISIBLE);
                        secq205e.setVisibility(View.VISIBLE);
                        lineq205e.setVisibility(View.VISIBLE);
                        secq205f.setVisibility(View.VISIBLE);
                        lineq205f.setVisibility(View.VISIBLE);
                        secq205x.setVisibility(View.VISIBLE);
                        lineq205x.setVisibility(View.VISIBLE);

                        secq206.setVisibility(View.VISIBLE);
                        lineq206.setVisibility(View.VISIBLE);
                        secq206a.setVisibility(View.VISIBLE);
                        lineq206a.setVisibility(View.VISIBLE);
                        secq206b.setVisibility(View.VISIBLE);
                        lineq206b.setVisibility(View.VISIBLE);
                        secq206c.setVisibility(View.VISIBLE);
                        lineq206c.setVisibility(View.VISIBLE);

                        secq206d.setVisibility(View.VISIBLE);
                        lineq206d.setVisibility(View.VISIBLE);

                        secrefcenter.setVisibility(View.VISIBLE);
                        linerefcenter.setVisibility(View.VISIBLE);


                    }

                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                    return;
                }
            });


            secq204 = (LinearLayout) findViewById(R.id.secq204);
            lineq204 = (View) findViewById(R.id.lineq204);
            secq204a = (LinearLayout) findViewById(R.id.secq204a);
            lineq204a = (View) findViewById(R.id.lineq204a);
            Vlblq204a = (TextView) findViewById(R.id.Vlblq204a);
            chkq204a = (CheckBox) findViewById(R.id.chkq204a);

            chkq204a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204a.isChecked() == true) {
                        secq204b.setVisibility(View.GONE);
                        lineq204b.setVisibility(View.GONE);
                        secq204c.setVisibility(View.GONE);
                        lineq204c.setVisibility(View.GONE);
                        secq204d.setVisibility(View.GONE);
                        lineq204d.setVisibility(View.GONE);
                        secq204e.setVisibility(View.GONE);
                        lineq204e.setVisibility(View.GONE);
                        secq204f.setVisibility(View.GONE);
                        lineq204f.setVisibility(View.GONE);
                        secq204g.setVisibility(View.GONE);
                        lineq204g.setVisibility(View.GONE);
                        secq204h.setVisibility(View.GONE);
                        lineq204h.setVisibility(View.GONE);
                        secq204i.setVisibility(View.GONE);
                        lineq204i.setVisibility(View.GONE);
                        secq204j.setVisibility(View.GONE);
                        lineq204j.setVisibility(View.GONE);
                        secq204k.setVisibility(View.GONE);
                        lineq204k.setVisibility(View.GONE);
                        secq204l.setVisibility(View.GONE);
                        lineq204l.setVisibility(View.GONE);
                        secq204x.setVisibility(View.GONE);
                        lineq204x.setVisibility(View.GONE);

                        secq204x1.setVisibility(View.GONE);
                        lineq204x1.setVisibility(View.GONE);

                        txtq204x1.setText("");

                        chkq204b.setChecked(false);
                        chkq204c.setChecked(false);
                        chkq204d.setChecked(false);
                        chkq204e.setChecked(false);
                        chkq204f.setChecked(false);
                        chkq204g.setChecked(false);
                        chkq204h.setChecked(false);
                        chkq204i.setChecked(false);
                        chkq204j.setChecked(false);
                        chkq204k.setChecked(false);
                        chkq204l.setChecked(false);
                        chkq204x.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {
                        secq204b.setVisibility(View.VISIBLE);
                        lineq204b.setVisibility(View.VISIBLE);

                        secq204b.setVisibility(View.VISIBLE);
                        lineq204b.setVisibility(View.VISIBLE);
                        secq204c.setVisibility(View.VISIBLE);
                        lineq204c.setVisibility(View.VISIBLE);
                        secq204d.setVisibility(View.VISIBLE);
                        lineq204d.setVisibility(View.VISIBLE);
                        secq204e.setVisibility(View.VISIBLE);
                        lineq204e.setVisibility(View.VISIBLE);
                        secq204f.setVisibility(View.VISIBLE);
                        lineq204f.setVisibility(View.VISIBLE);
                        secq204g.setVisibility(View.VISIBLE);
                        lineq204g.setVisibility(View.VISIBLE);
                        secq204h.setVisibility(View.VISIBLE);
                        lineq204h.setVisibility(View.VISIBLE);
                        secq204i.setVisibility(View.VISIBLE);
                        lineq204i.setVisibility(View.VISIBLE);
                        secq204j.setVisibility(View.VISIBLE);
                        lineq204j.setVisibility(View.VISIBLE);
                        secq204k.setVisibility(View.VISIBLE);
                        lineq204k.setVisibility(View.VISIBLE);
                        secq204l.setVisibility(View.VISIBLE);
                        lineq204l.setVisibility(View.VISIBLE);
                        secq204x.setVisibility(View.VISIBLE);
                        lineq204x.setVisibility(View.VISIBLE);


                    }

                }
            });

            secq204b = (LinearLayout) findViewById(R.id.secq204b);
            lineq204b = (View) findViewById(R.id.lineq204b);
            Vlblq204b = (TextView) findViewById(R.id.Vlblq204b);
            chkq204b = (CheckBox) findViewById(R.id.chkq204b);

            chkq204b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204b.isChecked() == true || chkq204c.isChecked() == true || chkq204d.isChecked() == true || chkq204e.isChecked() == true || chkq204f.isChecked() == true || chkq204g.isChecked() == true || chkq204h.isChecked() == true || chkq204i.isChecked() == true || chkq204k.isChecked() == true || chkq204l.isChecked() == true || chkq204x.isChecked() == true) {


                        chkq204a.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {


                    }

                }
            });
            secq204c = (LinearLayout) findViewById(R.id.secq204c);
            lineq204c = (View) findViewById(R.id.lineq204c);
            Vlblq204c = (TextView) findViewById(R.id.Vlblq204c);
            chkq204c = (CheckBox) findViewById(R.id.chkq204c);
            chkq204c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204b.isChecked() == true || chkq204c.isChecked() == true || chkq204d.isChecked() == true || chkq204e.isChecked() == true || chkq204f.isChecked() == true || chkq204g.isChecked() == true || chkq204h.isChecked() == true || chkq204i.isChecked() == true || chkq204k.isChecked() == true || chkq204l.isChecked() == true || chkq204x.isChecked() == true) {


                        chkq204a.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {


                    }

                }
            });
            secq204d = (LinearLayout) findViewById(R.id.secq204d);
            lineq204d = (View) findViewById(R.id.lineq204d);
            Vlblq204d = (TextView) findViewById(R.id.Vlblq204d);
            chkq204d = (CheckBox) findViewById(R.id.chkq204d);
            chkq204d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204b.isChecked() == true || chkq204c.isChecked() == true || chkq204d.isChecked() == true || chkq204e.isChecked() == true || chkq204f.isChecked() == true || chkq204g.isChecked() == true || chkq204h.isChecked() == true || chkq204i.isChecked() == true || chkq204k.isChecked() == true || chkq204l.isChecked() == true || chkq204x.isChecked() == true) {


                        chkq204a.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {


                    }

                }
            });
            secq204e = (LinearLayout) findViewById(R.id.secq204e);
            lineq204e = (View) findViewById(R.id.lineq204e);
            Vlblq204e = (TextView) findViewById(R.id.Vlblq204e);
            chkq204e = (CheckBox) findViewById(R.id.chkq204e);
            chkq204e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204b.isChecked() == true || chkq204c.isChecked() == true || chkq204d.isChecked() == true || chkq204e.isChecked() == true || chkq204f.isChecked() == true || chkq204g.isChecked() == true || chkq204h.isChecked() == true || chkq204i.isChecked() == true || chkq204k.isChecked() == true || chkq204l.isChecked() == true || chkq204x.isChecked() == true) {


                        chkq204a.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {


                    }

                }
            });
            secq204f = (LinearLayout) findViewById(R.id.secq204f);
            lineq204f = (View) findViewById(R.id.lineq204f);
            Vlblq204f = (TextView) findViewById(R.id.Vlblq204f);
            chkq204f = (CheckBox) findViewById(R.id.chkq204f);
            chkq204f.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204b.isChecked() == true || chkq204c.isChecked() == true || chkq204d.isChecked() == true || chkq204e.isChecked() == true || chkq204f.isChecked() == true || chkq204g.isChecked() == true || chkq204h.isChecked() == true || chkq204i.isChecked() == true || chkq204k.isChecked() == true || chkq204l.isChecked() == true || chkq204x.isChecked() == true) {


                        chkq204a.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {


                    }

                }
            });
            secq204g = (LinearLayout) findViewById(R.id.secq204g);
            lineq204g = (View) findViewById(R.id.lineq204g);
            Vlblq204g = (TextView) findViewById(R.id.Vlblq204g);
            chkq204g = (CheckBox) findViewById(R.id.chkq204g);
            chkq204g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204b.isChecked() == true || chkq204c.isChecked() == true || chkq204d.isChecked() == true || chkq204e.isChecked() == true || chkq204f.isChecked() == true || chkq204g.isChecked() == true || chkq204h.isChecked() == true || chkq204i.isChecked() == true || chkq204k.isChecked() == true || chkq204l.isChecked() == true || chkq204x.isChecked() == true) {


                        chkq204a.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {


                    }

                }
            });
            secq204h = (LinearLayout) findViewById(R.id.secq204h);
            lineq204h = (View) findViewById(R.id.lineq204h);
            Vlblq204h = (TextView) findViewById(R.id.Vlblq204h);
            chkq204h = (CheckBox) findViewById(R.id.chkq204h);
            chkq204h.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204b.isChecked() == true || chkq204c.isChecked() == true || chkq204d.isChecked() == true || chkq204e.isChecked() == true || chkq204f.isChecked() == true || chkq204g.isChecked() == true || chkq204h.isChecked() == true || chkq204i.isChecked() == true || chkq204k.isChecked() == true || chkq204l.isChecked() == true || chkq204x.isChecked() == true) {


                        chkq204a.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {


                    }

                }
            });
            secq204i = (LinearLayout) findViewById(R.id.secq204i);
            lineq204i = (View) findViewById(R.id.lineq204i);
            Vlblq204i = (TextView) findViewById(R.id.Vlblq204i);
            chkq204i = (CheckBox) findViewById(R.id.chkq204i);
            chkq204i.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204b.isChecked() == true || chkq204c.isChecked() == true || chkq204d.isChecked() == true || chkq204e.isChecked() == true || chkq204f.isChecked() == true || chkq204g.isChecked() == true || chkq204h.isChecked() == true || chkq204i.isChecked() == true || chkq204k.isChecked() == true || chkq204l.isChecked() == true || chkq204x.isChecked() == true) {


                        chkq204a.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {


                    }

                }
            });
            secq204j = (LinearLayout) findViewById(R.id.secq204j);
            lineq204j = (View) findViewById(R.id.lineq204j);
            Vlblq204j = (TextView) findViewById(R.id.Vlblq204j);
            chkq204j = (CheckBox) findViewById(R.id.chkq204j);
            chkq204j.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204b.isChecked() == true || chkq204c.isChecked() == true || chkq204d.isChecked() == true || chkq204e.isChecked() == true || chkq204f.isChecked() == true || chkq204g.isChecked() == true || chkq204h.isChecked() == true || chkq204i.isChecked() == true || chkq204k.isChecked() == true || chkq204l.isChecked() == true || chkq204x.isChecked() == true) {


                        chkq204a.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {


                    }

                }
            });
            secq204k = (LinearLayout) findViewById(R.id.secq204k);
            lineq204k = (View) findViewById(R.id.lineq204k);
            Vlblq204k = (TextView) findViewById(R.id.Vlblq204k);
            chkq204k = (CheckBox) findViewById(R.id.chkq204k);
            chkq204k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204b.isChecked() == true || chkq204c.isChecked() == true || chkq204d.isChecked() == true || chkq204e.isChecked() == true || chkq204f.isChecked() == true || chkq204g.isChecked() == true || chkq204h.isChecked() == true || chkq204i.isChecked() == true || chkq204k.isChecked() == true || chkq204l.isChecked() == true || chkq204x.isChecked() == true) {


                        chkq204a.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {


                    }

                }
            });
            secq204l = (LinearLayout) findViewById(R.id.secq204l);
            lineq204l = (View) findViewById(R.id.lineq204l);
            Vlblq204l = (TextView) findViewById(R.id.Vlblq204l);
            chkq204l = (CheckBox) findViewById(R.id.chkq204l);
            chkq204l.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkq204b.isChecked() == true || chkq204c.isChecked() == true || chkq204d.isChecked() == true || chkq204e.isChecked() == true || chkq204f.isChecked() == true || chkq204g.isChecked() == true || chkq204h.isChecked() == true || chkq204i.isChecked() == true || chkq204k.isChecked() == true || chkq204l.isChecked() == true || chkq204x.isChecked() == true) {


                        chkq204a.setChecked(false);


                    } else if (chkq204a.isChecked() == false) {


                    }

                }
            });
            secq204x = (LinearLayout) findViewById(R.id.secq204x);
            lineq204x = (View) findViewById(R.id.lineq204x);
            Vlblq204x = (TextView) findViewById(R.id.Vlblq204x);
            chkq204x = (CheckBox) findViewById(R.id.chkq204x);
            chkq204x.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //  if (chkq204b.isChecked() == true||chkq204c.isChecked() == true||chkq204d.isChecked() == true||chkq204e.isChecked() == true||chkq204f.isChecked() == true||chkq204g.isChecked() == true||chkq204h.isChecked() == true||chkq204i.isChecked() == true||chkq204k.isChecked() == true||chkq204l.isChecked() == true||chkq204x.isChecked() == true)
                    if (chkq204x.isChecked() == true) {

                        chkq204a.setChecked(false);
                        secq204x1.setVisibility(View.VISIBLE);
                        lineq204x1.setVisibility(View.VISIBLE);
                    } else if (chkq204x.isChecked() == false) {
                        secq204x1.setVisibility(View.GONE);
                        lineq204x1.setVisibility(View.GONE);

                        txtq204x1.setText("");


                    }

                }
            });
            secq204x1 = (LinearLayout) findViewById(R.id.secq204x1);
            lineq204x1 = (View) findViewById(R.id.lineq204x1);
            Vlblq204x1 = (TextView) findViewById(R.id.Vlblq204x1);
            txtq204x1 = (EditText) findViewById(R.id.txtq204x1);
            secq205 = (LinearLayout) findViewById(R.id.secq205);
            lineq205 = (View) findViewById(R.id.lineq205);
            secq205a = (LinearLayout) findViewById(R.id.secq205a);
            lineq205a = (View) findViewById(R.id.lineq205a);
            Vlblq205a = (TextView) findViewById(R.id.Vlblq205a);
            chkq205a = (CheckBox) findViewById(R.id.chkq205a);
            secq205b = (LinearLayout) findViewById(R.id.secq205b);
            lineq205b = (View) findViewById(R.id.lineq205b);
            Vlblq205b = (TextView) findViewById(R.id.Vlblq205b);
            chkq205b = (CheckBox) findViewById(R.id.chkq205b);
            secq205c = (LinearLayout) findViewById(R.id.secq205c);
            lineq205c = (View) findViewById(R.id.lineq205c);
            Vlblq205c = (TextView) findViewById(R.id.Vlblq205c);
            chkq205c = (CheckBox) findViewById(R.id.chkq205c);
            secq205d = (LinearLayout) findViewById(R.id.secq205d);
            lineq205d = (View) findViewById(R.id.lineq205d);
            Vlblq205d = (TextView) findViewById(R.id.Vlblq205d);
            chkq205d = (CheckBox) findViewById(R.id.chkq205d);
            secq205e = (LinearLayout) findViewById(R.id.secq205e);
            lineq205e = (View) findViewById(R.id.lineq205e);
            Vlblq205e = (TextView) findViewById(R.id.Vlblq205e);
            chkq205e = (CheckBox) findViewById(R.id.chkq205e);
            secq205f = (LinearLayout) findViewById(R.id.secq205f);
            lineq205f = (View) findViewById(R.id.lineq205f);
            Vlblq205f = (TextView) findViewById(R.id.Vlblq205f);
            chkq205f = (CheckBox) findViewById(R.id.chkq205f);
            secq205x = (LinearLayout) findViewById(R.id.secq205x);
            lineq205x = (View) findViewById(R.id.lineq205x);
            Vlblq205x = (TextView) findViewById(R.id.Vlblq205x);
            chkq205x = (CheckBox) findViewById(R.id.chkq205x);
            secq206 = (LinearLayout) findViewById(R.id.secq206);
            lineq206 = (View) findViewById(R.id.lineq206);
            secq206a = (LinearLayout) findViewById(R.id.secq206a);
            lineq206a = (View) findViewById(R.id.lineq206a);
            Vlblq206a = (TextView) findViewById(R.id.Vlblq206a);
            txtq206a = (EditText) findViewById(R.id.txtq206a);
            secq206b = (LinearLayout) findViewById(R.id.secq206b);
            lineq206b = (View) findViewById(R.id.lineq206b);
            Vlblq206b = (TextView) findViewById(R.id.Vlblq206b);
            txtq206b = (EditText) findViewById(R.id.txtq206b);

            secq206c = (LinearLayout) findViewById(R.id.secq206c);
            lineq206c = (View) findViewById(R.id.lineq206c);
            Vlblq206c = (TextView) findViewById(R.id.Vlblq206c);
            txtq206c = (EditText) findViewById(R.id.txtq206c);

            secq206d = (LinearLayout) findViewById(R.id.secq206d);
            lineq206d = (View) findViewById(R.id.lineq206d);
            Vlblq206d = (TextView) findViewById(R.id.Vlblq206d);
            txtq206d = (EditText) findViewById(R.id.txtq206d);

            secrefcenter = (LinearLayout) findViewById(R.id.secrefcenter);
            linerefcenter = (View) findViewById(R.id.linerefcenter);
            Vlblrefcenter = (TextView) findViewById(R.id.Vlblrefcenter);
            chkrefcenter = (CheckBox) findViewById(R.id.chkrefcenter);
            chkrefcenter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (chkrefcenter.isChecked() == true) {

                        secupazilaid.setVisibility(View.VISIBLE);
                        lineupazilaid.setVisibility(View.VISIBLE);
                        secunionid.setVisibility(View.VISIBLE);
                        lineunionid.setVisibility(View.VISIBLE);
                        secwardid.setVisibility(View.VISIBLE);
                        linewardid.setVisibility(View.VISIBLE);
                        secsubblockid.setVisibility(View.VISIBLE);
                        linesubblockid.setVisibility(View.VISIBLE);

                    } else if (chkrefcenter.isChecked() == false) {
                        secupazilaid.setVisibility(View.GONE);
                        lineupazilaid.setVisibility(View.GONE);
                        secunionid.setVisibility(View.GONE);
                        lineunionid.setVisibility(View.GONE);
                        secwardid.setVisibility(View.GONE);
                        linewardid.setVisibility(View.GONE);
                        secsubblockid.setVisibility(View.GONE);
                        linesubblockid.setVisibility(View.GONE);

                        spnupazilaid.setSelection(0);
                        spnunionid.setSelection(0);
                        spnwardid.setSelection(0);
                        spnsubblockid.setSelection(0);
                    }

                }
            });

            secupazilaid = (LinearLayout) findViewById(R.id.secupazilaid);
            lineupazilaid = (View) findViewById(R.id.lineupazilaid);
            Vlblupazilaid = (TextView) findViewById(R.id.Vlblupazilaid);
            spnupazilaid = (Spinner) findViewById(R.id.spnupazilaid);
            // spnupazilaid.setAdapter(C.getArrayAdapterMultiline(" Select '---সিলেক্ট করুন--' as upazila union select upazilaid||'- '||upazilaname as upazila from upazila"));
            if (g.getCallFrom().equalsIgnoreCase("update")) {

                spnupazilaid.setAdapter(C.getArrayAdapterMultiline("select upazilaid||'- '||upazilaname as upazila from upazila"));
            } else {
                spnupazilaid.setAdapter(C.getArrayAdapterMultiline("select upazilaid||'- '||upazilaname as upazila from upazila"));

            }
            secunionid = (LinearLayout) findViewById(R.id.secunionid);
            lineunionid = (View) findViewById(R.id.lineunionid);
            Vlblunionid = (TextView) findViewById(R.id.Vlblunionid);
            spnunionid = (Spinner) findViewById(R.id.spnunionid);
            if (g.getCallFrom().equalsIgnoreCase("update")) {
                spnunionid.setAdapter(C.getArrayAdapterMultiline(" Select '---সিলেক্ট করুন--' as unions union select unionid ||'- '||unionname as unions from unions where unionid>9"));

            } else {
                spnupazilaid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        //String[] zid = spnzillaid.getSelectedItem().toString().split("-");
                        // if(g.getlevel().equalsIgnoreCase("1")) {
                        String[] upid = spnupazilaid.getSelectedItem().toString().split("-");
                        //String UNID=C.ReturnSingleValue("select cast(unionid as varchar(2)) from unions where zillaid='" + g.getDistrict() + "' and upazilaid='" + g.getUpazila() + "' and unionid='" + g.getUnion() + "'");
                        spnunionid.setAdapter(C.getArrayAdapterMultiline(" Select '---সিলেক্ট করুন--' as unions union select unionid ||'- '||unionname as unions from unions where unionid>9 and upazilaid='" + upid[0] + "'"));


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }
                });
            }
            secwardid = (LinearLayout) findViewById(R.id.secwardid);
            linewardid = (View) findViewById(R.id.linewardid);
            Vlblwardid = (TextView) findViewById(R.id.Vlblwardid);
            spnwardid = (Spinner) findViewById(R.id.spnwardid);
            if (g.getCallFrom().equalsIgnoreCase("update")) {

                spnwardid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS word UNION SELECT ward_no || CASE WHEN ward_no = 1 THEN '- ওয়ার্ড 1' WHEN ward_no = 2 THEN '- ওয়ার্ড 2' WHEN ward_no = 3 THEN '- ওয়ার্ড 3' WHEN ward_no = 4 THEN '- ওয়ার্ড 1(অতিঃ 1 )' WHEN ward_no = 5 THEN '- ওয়ার্ড 2( অতিঃ 2 )' WHEN ward_no = 6 THEN '-ওয়ার্ড 3(অতিঃ 3 )' else '' end AS word FROM [cluster]"));


            } else {

                spnunionid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        // String[] zid = spnzillaid.getSelectedItem().toString().split("-");
                        String[] upid = spnupazilaid.getSelectedItem().toString().split("-");
                        String[] unid = spnunionid.getSelectedItem().toString().split("-");
                        spnwardid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS word UNION SELECT ward_no || CASE WHEN ward_no = 1 THEN '- ওয়ার্ড 1' WHEN ward_no = 2 THEN '- ওয়ার্ড 2' WHEN ward_no = 3 THEN '- ওয়ার্ড 3' WHEN ward_no = 4 THEN '- ওয়ার্ড 1(অতিঃ 1 )' WHEN ward_no = 5 THEN '- ওয়ার্ড 2( অতিঃ 2 )' WHEN ward_no = 6 THEN '-ওয়ার্ড 3(অতিঃ 3 )' else '' end AS word FROM [cluster] where  upazilaid='" + upid[0] + "' and unionid='" + unid[0] + "'"));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }
                });
            }
            secsubblockid = (LinearLayout) findViewById(R.id.secsubblockid);
            linesubblockid = (View) findViewById(R.id.linesubblockid);
            Vlblsubblockid = (TextView) findViewById(R.id.Vlblsubblockid);
            spnsubblockid = (Spinner) findViewById(R.id.spnsubblockid);

            if (g.getCallFrom().equalsIgnoreCase("update")) {

                //spnwardid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS word UNION SELECT ward_no || CASE WHEN ward_no = 1 THEN '- ওয়ার্ড 1' WHEN ward_no = 2 THEN '- ওয়ার্ড 2' WHEN ward_no = 3 THEN '- ওয়ার্ড 3' WHEN ward_no = 4 THEN '- ওয়ার্ড 1(অতিঃ 1 )' WHEN ward_no = 5 THEN '- ওয়ার্ড 2( অতিঃ 2 )' WHEN ward_no = 6 THEN '-ওয়ার্ড 3(অতিঃ 3 )' else '' end AS word FROM [cluster]"));

                // spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster] where  upazilaid like '%" + g.getUpazila() + "%' and unionid like '%" + g.getUnion() + "%' and ward_no like '%" + g.getward() + "%'"));
                spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster]  where upazilaid='" + g.getUpazila() + "' and unionid='" + g.getUnion() + "' and ward_no='" + g.getward() + "'"));
            } else {

                spnwardid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        // String[] zid = spnzillaid.getSelectedItem().toString().split("-");
                        String[] upid = spnupazilaid.getSelectedItem().toString().split("-");
                        String[] unid = spnunionid.getSelectedItem().toString().split("-");
                        String[] ward = spnwardid.getSelectedItem().toString().split("-");
                        spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster]  where  upazilaid='" + upid[0] + "' and unionid='" + unid[0] + "' and ward_no='" + ward[0] + "'"));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }
                });
            }


            // TextView txtepisubblock;
        /* if(g.getCallFrom().equalsIgnoreCase("update")) {
             spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster] where  upazilaid like '%" + g.getUpazila() + "%' and unionid like '%" + g.getUnion() + "%' and ward_no like '%" + g.getward() + "%'"));
         }*/

/*             spnwardid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                     if (spnwardid.getSelectedItemPosition() > 0) {
                         String[] upid = spnupazilaid.getSelectedItem().toString().split("-");
                         String[] unid = spnunionid.getSelectedItem().toString().split("-");
                         String[] ward = spnwardid.getSelectedItem().toString().split("-");
                         spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster]  where  upazilaid='" + upid[0] + "' and unionid='" + unid[0] + "' and ward_no='" + ward[0] + "'"));

                         //spnUN.setAdapter(C.getArrayAdapter("select UNIONID||'-'||UNIONNAMEENG UN from Unions where ZILLAID='"+ Global.Left(spnDCode.getSelectedItem().toString(),2) +"' and UPAZILAID='"+ Global.Left(spnUpz.getSelectedItem().toString(),2) +"' union Select '99-Other Union'"));
                         // txtFaciName.setText(Global.Mid(spnUN.getSelectedItem().toString(),3, 20));
                         //spnUN.setSelection(DivUpazilaUnionSelect("un"));
                     } else {
                         spnsubblockid.setAdapter(null);
                     }
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parentView) {
                     // your code here

                     // spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster] where clusterid='" + g.getEPISubBlock()+ "'"));

                 }

             });*/
       /*  secepisubblock=(LinearLayout)findViewById(R.id.secepisubblock);
         txtepisubblock=(TextView) findViewById(R.id.txtepisubblock);*/
      /*   if(g.getCallFrom().equalsIgnoreCase("update")) {
            // secepisubblock.setVisibility(View.VISIBLE);
                *//*txtepisubblock.setText(C.ReturnSingleValue("SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster] where clusterid='" + g.getEPISubBlock()+ "'"));
             secsubblockid.setVisibility(View.VISIBLE);
             linesubblockid.setVisibility(View.VISIBLE);*//*





         }
         else {
             *//*secepisubblock.setVisibility(View.GONE);
             secsubblockid.setVisibility(View.VISIBLE);
             linesubblockid.setVisibility(View.VISIBLE);*//*


             spnwardid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                     //spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster]  where  upazilaid='" + upid[0] + "' and unionid='" + unid[0] + "' and ward_no='" + ward[0] + "'"));

                     String[] wardid = spnwardid.getSelectedItem().toString().split("-");
                     if (wardid[0].equalsIgnoreCase("1") || wardid[0].equalsIgnoreCase("2") || wardid[0].equalsIgnoreCase("3")
                             || wardid[0].equalsIgnoreCase("4") || wardid[0].equalsIgnoreCase("5") || wardid[0].equalsIgnoreCase("6"))

                     {
                        *//* secsubblockid.setVisibility(View.VISIBLE);
                         linesubblockid.setVisibility(View.VISIBLE);*//*
                         // String[] zid = spnzillaid.getSelectedItem().toString().split("-");
                         String[] upid = spnupazilaid.getSelectedItem().toString().split("-");
                         String[] unid = spnunionid.getSelectedItem().toString().split("-");
                         String[] ward = spnwardid.getSelectedItem().toString().split("-");
                         spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster]  where  upazilaid='" + upid[0] + "' and unionid='" + unid[0] + "' and ward_no='" + ward[0] + "'"));
                     } *//*else {
                         secsubblockid.setVisibility(View.GONE);
                         linesubblockid.setVisibility(View.GONE);
                     }*//*


                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parentView) {
                     // your code here
                 }
             });


         }*/



       /*  if(g.getCallFrom().equalsIgnoreCase("update"))
         {*/
            //spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster] "));
            // SELECT '---সিলেক্ট করুন--' AS [cluster] UNION
            // spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster] where  upazilaid like '%" + g.getUpazila() + "%' and unionid like '%" + g.getUnion() + "%' and ward_no like '%" + g.getward() +"%'"));
            //  spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster] where clusterid='" + g.getEPISubBlock()+ "'"));
             /*
             if(spnwardid.getSelectedItemPosition()>0)
             {
                 spnwardid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                         spnsubblockid.setSelection(0);
                         // String[] zid = spnzillaid.getSelectedItem().toString().split("-");
                         String[] upid = spnupazilaid.getSelectedItem().toString().split("-");
                         String[] unid = spnunionid.getSelectedItem().toString().split("-");
                         String[] ward = spnwardid.getSelectedItem().toString().split("-");
                         spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster]  where  upazilaid='" + upid[0] + "' and unionid='" + unid[0] + "' and ward_no='" + ward[0] + "'"));

                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> parentView) {
                         // your code here
                     }
                 });
             }*/

            /* String[] ward = spnwardid.getSelectedItem().toString().split("-");
            if(ward[0].equalsIgnoreCase("1")||ward[0].equalsIgnoreCase("2")||ward[0].equalsIgnoreCase("3")||ward[0].equalsIgnoreCase("4")||ward[0].equalsIgnoreCase("5")||ward[0].equalsIgnoreCase("6"))
            {
                spnwardid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        // String[] zid = spnzillaid.getSelectedItem().toString().split("-");
                        String[] upid = spnupazilaid.getSelectedItem().toString().split("-");
                        String[] unid = spnunionid.getSelectedItem().toString().split("-");
                        String[] ward = spnwardid.getSelectedItem().toString().split("-");
                        spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster]  where  upazilaid='" + upid[0] + "' and unionid='" + unid[0] + "' and ward_no='" + ward[0] + "'"));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }
                });
            }*/

            // }

       /*  else {
             spnwardid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                     // String[] zid = spnzillaid.getSelectedItem().toString().split("-");
                     String[] upid = spnupazilaid.getSelectedItem().toString().split("-");
                     String[] unid = spnunionid.getSelectedItem().toString().split("-");
                     String[] ward = spnwardid.getSelectedItem().toString().split("-");
                     spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster]  where  upazilaid='" + upid[0] + "' and unionid='" + unid[0] + "' and ward_no='" + ward[0] + "'"));

                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parentView) {
                     // your code here
                 }
             });
         }*/
        /* if(g.getCallFrom().equalsIgnoreCase("update"))
         {

            // spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster] "));
            if(spnwardid.getSelectedItemPosition()>0) {
                spnwardid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        String spnData = "";

                        try {

                            String[] upid = spnupazilaid.getSelectedItem().toString().split("-");
                            String[] unid = spnunionid.getSelectedItem().toString().split("-");
                            String[] ward = spnwardid.getSelectedItem().toString().split("-");
                            spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster]  where  upazilaid='" + upid[0] + "' and unionid='" + unid[0] + "' and ward_no='" + ward[0] + "'"));


                        } catch (Exception e) {

                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });
            }



         }

         else {*/

            // }
   /*      if(spnwardid.getSelectedItemPosition()>0)
         {
             spnwardid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                     spnsubblockid.setSelection(0);
                     // String[] zid = spnzillaid.getSelectedItem().toString().split("-");
                     String[] upid = spnupazilaid.getSelectedItem().toString().split("-");
                     String[] unid = spnunionid.getSelectedItem().toString().split("-");
                     String[] ward = spnwardid.getSelectedItem().toString().split("-");
                     spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster]  where  upazilaid='" + upid[0] + "' and unionid='" + unid[0] + "' and ward_no='" + ward[0] + "'"));

                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parentView) {
                     // your code here
                 }
             });
         }*/


      /*   if(g.getCallFrom().equalsIgnoreCase("update")) {
             // spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster] "));
             // SELECT '---সিলেক্ট করুন--' AS [cluster] UNION
             // spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster] where  upazilaid like '%" + g.getUpazila() + "%' and unionid like '%" + g.getUnion() + "%' and ward_no like '%" + g.getward() +"%'"));
             spnsubblockid.setAdapter(C.getArrayAdapterMultiline("SELECT '---সিলেক্ট করুন--' AS [cluster] UNION SELECT clusterid || '- ' ||epi_sub_block||': '|| epi_cluster_name AS cluster_name FROM [cluster] where clusterid='" + g.getEPISubBlock()+ "'"));
         }*/
            // secepisubblock.setVisibility(View.GONE);
            secq202b.setVisibility(View.GONE);
            lineq202b.setVisibility(View.GONE);
            secq202b1.setVisibility(View.GONE);
            lineq202b1.setVisibility(View.GONE);

            secq204.setVisibility(View.GONE);
            lineq204.setVisibility(View.GONE);
            secq204a.setVisibility(View.GONE);
            lineq204a.setVisibility(View.GONE);

            secq204x1.setVisibility(View.GONE);
            lineq204x1.setVisibility(View.GONE);

            secq204b.setVisibility(View.GONE);
            lineq204b.setVisibility(View.GONE);
            secq204c.setVisibility(View.GONE);
            lineq204c.setVisibility(View.GONE);
            secq204d.setVisibility(View.GONE);
            lineq204d.setVisibility(View.GONE);
            secq204e.setVisibility(View.GONE);
            lineq204e.setVisibility(View.GONE);
            secq204f.setVisibility(View.GONE);
            lineq204f.setVisibility(View.GONE);
            secq204g.setVisibility(View.GONE);
            lineq204g.setVisibility(View.GONE);
            secq204h.setVisibility(View.GONE);
            lineq204h.setVisibility(View.GONE);
            secq204i.setVisibility(View.GONE);
            lineq204i.setVisibility(View.GONE);
            secq204j.setVisibility(View.GONE);
            lineq204j.setVisibility(View.GONE);
            secq204k.setVisibility(View.GONE);
            lineq204k.setVisibility(View.GONE);
            secq204l.setVisibility(View.GONE);
            lineq204l.setVisibility(View.GONE);
            secq204x.setVisibility(View.GONE);
            lineq204x.setVisibility(View.GONE);

            secq205.setVisibility(View.GONE);
            lineq205.setVisibility(View.GONE);
            secq205a.setVisibility(View.GONE);
            lineq205a.setVisibility(View.GONE);
            secq205b.setVisibility(View.GONE);
            lineq205b.setVisibility(View.GONE);
            secq205c.setVisibility(View.GONE);
            lineq205c.setVisibility(View.GONE);
            secq205d.setVisibility(View.GONE);
            lineq205d.setVisibility(View.GONE);
            secq205e.setVisibility(View.GONE);
            lineq205e.setVisibility(View.GONE);
            secq205f.setVisibility(View.GONE);
            lineq205f.setVisibility(View.GONE);
            secq205x.setVisibility(View.GONE);
            lineq205x.setVisibility(View.GONE);

            secq206.setVisibility(View.GONE);
            lineq206.setVisibility(View.GONE);
            secq206a.setVisibility(View.GONE);
            lineq206a.setVisibility(View.GONE);
            secq206b.setVisibility(View.GONE);
            lineq206b.setVisibility(View.GONE);

            secq206c.setVisibility(View.GONE);
            lineq206c.setVisibility(View.GONE);

            secq206d.setVisibility(View.GONE);
            lineq206d.setVisibility(View.GONE);

            secrefcenter.setVisibility(View.GONE);
            linerefcenter.setVisibility(View.GONE);


            secupazilaid.setVisibility(View.GONE);
            lineupazilaid.setVisibility(View.GONE);
            secunionid.setVisibility(View.GONE);
            lineunionid.setVisibility(View.GONE);
            secwardid.setVisibility(View.GONE);
            linewardid.setVisibility(View.GONE);
            secsubblockid.setVisibility(View.GONE);
            linesubblockid.setVisibility(View.GONE);


            DataSearch(txtidno.getText().toString());

            dtpq202.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_RIGHT = 2;
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (dtpq202.getRight() - dtpq202.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            VariableID = "btnq202";
                            showDialog(DATE_DIALOG);
                            return true;
                        }
                    }
                    return false;
                }
            });
            Button cmdSave = (Button) findViewById(R.id.cmdSave);
            cmdSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    DataSave();
                }
            });
        } catch (Exception e) {
            Connection.MessageBox(Section_2_Vaccinations_Info.this, e.getMessage());
            return;
        }
    }

    private void DataSave() {
        try {

            String DV = "";

            if (txtidno.getText().toString().length() == 0 & secidno.isShown()) {
                Connection.MessageBox(Section_2_Vaccinations_Info.this, "Required field: IDNo.");
                txtidno.requestFocus();
                return;
            } else if (txtq201.getText().toString().length() == 0 & secq201.isShown()) {
                Connection.MessageBox(Section_2_Vaccinations_Info.this, "201. আপনার শিশুর নাম ফাঁকা");
                txtq201.requestFocus();
                return;
            }


//            if (chkq202a.isChecked() == false) {
//                DV = Global.DateValidate(dtpq202.getText().toString());
//                if (DV.length() != 0 & secq202.isShown()) {
//                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "202. শিশুর জন্ম তারিখ ফাঁকা");
//                    dtpq202.requestFocus();
//                    return;
//                }
//            }

            if (chkq202a.isChecked() == false) {
                DV = Global.DateValidate(dtpq202.getText().toString());

                // Parse the date to compare it with today's date
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()); // Change format as per your requirement
                    Date enteredDate = sdf.parse(dtpq202.getText().toString());
                    Date currentDate = new Date();

                    // Check if entered date is after today's date
                    if (enteredDate.after(currentDate)) {
                        Connection.MessageBox(Section_2_Vaccinations_Info.this, "ভবিষ্যতের তারিখ গ্রহণযোগ্য নয়");
                        dtpq202.requestFocus();
                        return;
                    }
                } catch (ParseException e) {
                    // Handle date parsing errors here
                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "ভুল তারিখ ফরম্যাট");
                    dtpq202.requestFocus();
                }

                if (DV.length() != 0 & secq202.isShown()) {
                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "202. শিশুর জন্ম তারিখ ফাঁকা");
                    dtpq202.requestFocus();
                    return;
                }

            }

            if (chkq202a.isChecked() == true) {

                if (txtq202b1.getText().toString().length() == 0 & secq202b1.isShown()) {
                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "Required field:202b. শিশুর বয়স (দিন) ফাঁকা.");
                    txtq202b1.requestFocus();
                    return;
                } else if (Integer.valueOf(txtq202b1.getText().toString().length() == 0 ? "0" : txtq202b1.getText().toString()) < 0 || Integer.valueOf(txtq202b1.getText().toString().length() == 0 ? "31" : txtq202b1.getText().toString()) > 31) {
                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "Value should be between 0 and 31(202b. শিশুর বয়স (দিন)).");
                    txtq202b1.requestFocus();
                    return;
                } else if (txtq202b2.getText().toString().length() == 0 & secq202b1.isShown()) {
                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "Required field:202b. শিশুর বয়স (মাস) ফাঁকা.");
                    txtq202b2.requestFocus();
                    return;
                } else if (Integer.valueOf(txtq202b2.getText().toString().length() == 0 ? "0" : txtq202b2.getText().toString()) < 0 || Integer.valueOf(txtq202b2.getText().toString().length() == 0 ? "12" : txtq202b2.getText().toString()) > 12) {
                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "Value should be between 0 and 12(202b. শিশুর বয়স (মাস)).");
                    txtq202b2.requestFocus();
                    return;
                } else if (txtq202b3.getText().toString().length() == 0 & secq202b1.isShown()) {
                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "Required field:202b. শিশুর বয়স (বছর ) ফাঁকা.");
                    txtq202b3.requestFocus();
                    return;
                } else if (Integer.valueOf(txtq202b3.getText().toString().length() == 0 ? "0" : txtq202b3.getText().toString()) < 0 || Integer.valueOf(txtq202b3.getText().toString().length() == 0 ? "2" : txtq202b3.getText().toString()) > 2) {
                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "Value should be between 0 and 2 ( 202b. শিশুর বয়স (বছর )).");
                    txtq202b3.requestFocus();
                    return;
                }


            }

            if (!rdoq2031.isChecked() & !rdoq2032.isChecked() & secq203.isShown()) {
                Connection.MessageBox(Section_2_Vaccinations_Info.this, "Select anyone options from (203. শিশুটি কি সবগুলো ইপিআই টিকা নিয়েছে?).");
                rdoq2031.requestFocus();
                return;
            }

            if (rdoq2032.isChecked() == true) {
                if (chkq204a.isChecked() == false && chkq204b.isChecked() == false && chkq204c.isChecked() == false && chkq204d.isChecked() == false && chkq204e.isChecked() == false && chkq204f.isChecked() == false && chkq204g.isChecked() == false && chkq204h.isChecked() == false && chkq204i.isChecked() == false && chkq204k.isChecked() == false && chkq204l.isChecked() == false && chkq204x.isChecked() == false) {
                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "Select anyone options(A-L/X) from( 204. আপনার শিশুকে সবগুলো টিকা দেননি কেন?");
                    chkq204b.requestFocus();
                    return;

                } else if (txtq204x1.getText().toString().length() == 0 & secq204x1.isShown()) {
                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "Required field: 204x1. নির্দিষ্ট করুন.");
                    txtq204x1.requestFocus();
                    return;
                }

                if (chkq205a.isChecked() == false && chkq205b.isChecked() == false && chkq205c.isChecked() == false && chkq205d.isChecked() == false && chkq205e.isChecked() == false && chkq205f.isChecked() == false && chkq205x.isChecked() == false) {
                    Connection.MessageBox(Section_2_Vaccinations_Info.this, "Select anyone options(A-F/X) from( 205. শিশুটি নিচের কোন টিকা পায়নি?");
                    chkq205a.requestFocus();
                    return;

                }
            } else if (txtq206a.getText().toString().length() == 0 & secq206a.isShown()) {
                Connection.MessageBox(Section_2_Vaccinations_Info.this, "Required field: 206a . মায়ের নাম ফাঁকা ");
                txtq206a.requestFocus();
                return;
            } else if (txtq206b.getText().toString().length() == 0 & secq206b.isShown()) {
                Connection.MessageBox(Section_2_Vaccinations_Info.this, "Required field:206b. বাবার নাম ফাঁকা ");
                txtq206b.requestFocus();
                return;
            } else if (txtq206c.getText().toString().length() == 0 & secq206c.isShown()) {
                Connection.MessageBox(Section_2_Vaccinations_Info.this, "Required field:206c. মোবাইল নম্বর (যদি থাকে '0' লিখুন ):.");
                txtq206c.requestFocus();
                return;
            }
            if (chkrefcenter.isChecked() == true) {

                if (spnunionid.getSelectedItemPosition() == 0 & secunionid.isShown()) {
                    MessageBox(Section_2_Vaccinations_Info.this, "209. ইউনিয়ন: সিলেক্ট করুন ।");
                    spnunionid.requestFocus();
                    return;
                }

                if (spnwardid.getSelectedItemPosition() == 0 & secwardid.isShown()) {
                    MessageBox(Section_2_Vaccinations_Info.this, "210. ওয়ার্ড নম্বর সিলেক্ট করুন ।");
                    spnwardid.requestFocus();
                    return;
                }

                if (spnsubblockid.getSelectedItemPosition() == 0 & secsubblockid.isShown()) {
                    MessageBox(Section_2_Vaccinations_Info.this, "211. ইপিআই কেন্দ্রের নাম/ব্লক সিলেক্ট করুন ।");
                    spnsubblockid.requestFocus();
                    return;
                }
            }
            String SQL = "";
            RadioButton rb;

            Section_2_Vaccinations_Info_DataModel objSave = new Section_2_Vaccinations_Info_DataModel();
            objSave.setidno(txtidno.getText().toString());

            objSave.setq201(txtq201.getText().toString());
            // objSave.setq202(dtpq202.getText().toString().length() == 0 ? "": Global.DateConvertYMD(dtpq202.getText().toString()));
            objSave.setq202(dtpq202.getText().toString().length() > 0 ? Global.DateConvertYMD(dtpq202.getText().toString()) : dtpq202.getText().toString());
            objSave.setq202a((chkq202a.isChecked() ? "1" : (secq202.isShown() ? "2" : "")));
            objSave.setq202b1(txtq202b1.getText().toString());
            objSave.setq202b2(txtq202b2.getText().toString());
            objSave.setq202b3(txtq202b3.getText().toString());
            String[] d_rdogrpq203 = new String[]{"1", "2"};
            objSave.setq203("");
            for (int i = 0; i < rdogrpq203.getChildCount(); i++) {
                rb = (RadioButton) rdogrpq203.getChildAt(i);
                if (rb.isChecked()) objSave.setq203(d_rdogrpq203[i]);
            }

            objSave.setq204a((chkq204a.isChecked() ? "1" : (secq204a.isShown() ? "2" : "")));
            objSave.setq204b((chkq204b.isChecked() ? "1" : (secq204b.isShown() ? "2" : "")));
            objSave.setq204c((chkq204c.isChecked() ? "1" : (secq204c.isShown() ? "2" : "")));
            objSave.setq204d((chkq204d.isChecked() ? "1" : (secq204d.isShown() ? "2" : "")));
            objSave.setq204e((chkq204e.isChecked() ? "1" : (secq204e.isShown() ? "2" : "")));
            objSave.setq204f((chkq204f.isChecked() ? "1" : (secq204f.isShown() ? "2" : "")));
            objSave.setq204g((chkq204g.isChecked() ? "1" : (secq204g.isShown() ? "2" : "")));
            objSave.setq204h((chkq204h.isChecked() ? "1" : (secq204h.isShown() ? "2" : "")));
            objSave.setq204i((chkq204i.isChecked() ? "1" : (secq204i.isShown() ? "2" : "")));
            objSave.setq204j((chkq204j.isChecked() ? "1" : (secq204j.isShown() ? "2" : "")));
            objSave.setq204k((chkq204k.isChecked() ? "1" : (secq204k.isShown() ? "2" : "")));
            objSave.setq204l((chkq204l.isChecked() ? "1" : (secq204l.isShown() ? "2" : "")));
            objSave.setq204x((chkq204x.isChecked() ? "1" : (secq204x.isShown() ? "2" : "")));
            objSave.setq204x1(txtq204x1.getText().toString());
            objSave.setq205a((chkq205a.isChecked() ? "1" : (secq205a.isShown() ? "2" : "")));
            objSave.setq205b((chkq205b.isChecked() ? "1" : (secq205b.isShown() ? "2" : "")));
            objSave.setq205c((chkq205c.isChecked() ? "1" : (secq205c.isShown() ? "2" : "")));
            objSave.setq205d((chkq205d.isChecked() ? "1" : (secq205d.isShown() ? "2" : "")));
            objSave.setq205e((chkq205e.isChecked() ? "1" : (secq205e.isShown() ? "2" : "")));
            objSave.setq205f((chkq205f.isChecked() ? "1" : (secq205f.isShown() ? "2" : "")));
            objSave.setq205x((chkq205x.isChecked() ? "1" : (secq205x.isShown() ? "2" : "")));
            objSave.setq206a(txtq206a.getText().toString());
            objSave.setq206b(txtq206b.getText().toString());
            objSave.setq206c(txtq206c.getText().toString());
            objSave.setq206d(txtq206d.getText().toString());
            objSave.setq207((chkrefcenter.isChecked() ? "1" : (chkrefcenter.isShown() ? "2" : "")));
            if (chkrefcenter.isChecked() == true) {
                objSave.setq208(Connection.SelectedSpinnerValue(spnupazilaid.getSelectedItem().toString(), "-"));
                objSave.setq209((spnunionid.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnunionid.getSelectedItem().toString(), "-")));
                objSave.setq210((spnwardid.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnwardid.getSelectedItem().toString(), "-")));
                objSave.setq211((spnsubblockid.getSelectedItemPosition() == 0 ? "" : Connection.SelectedSpinnerValue(spnsubblockid.getSelectedItem().toString(), "-")));
            } else {
                objSave.setq208("");
                objSave.setq209("");
                objSave.setq210("");
                objSave.setq211("");
            }
            objSave.setEnDt(Global.DateTimeNowYMDHMS());
            objSave.setStartTime(STARTTIME);
            objSave.setEndTime(g.CurrentTime24());
            objSave.setDeviceID(DEVICEID);
            objSave.setEntryUser(ENTRYUSER); //from data entry user list
            objSave.setmodifyDate(Global.DateTimeNowYMDHMS());
            //objSave.setLat(Double.toString(currentLatitude));
            //objSave.setLon(Double.toString(currentLongitude));

            String status = objSave.SaveUpdateData(this);
            if (status.length() == 0) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("res", "");
                setResult(Activity.RESULT_OK, returnIntent);
                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                //Connection.MessageBox(Section_2_Vaccinations_Info.this, "Saved Successfully");
            } else {
                Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
                //Connection.MessageBox(Section_2_Vaccinations_Info.this, status);
                return;
            }
            g.setlevel(lavel);
            g.setDistrict(Zila);
            g.setUpazila(UZila);
            g.setUnion(UN);
            g.setward(WD);
            g.setIdNo("");

            //finish();
            Intent f1 = new Intent(getApplicationContext(), schecklist_home.class);
            startActivity(f1);
        } catch (Exception e) {
            Connection.MessageBox(Section_2_Vaccinations_Info.this, e.getMessage());
            return;
        }
    }

    private void DataSearch(String idno) {
        try {

            RadioButton rb;
            Section_2_Vaccinations_Info_DataModel d = new Section_2_Vaccinations_Info_DataModel();
            String SQL = "Select * from " + TableName + "  Where idno='" + idno + "'";
            List<Section_2_Vaccinations_Info_DataModel> data = d.SelectAll(this, SQL);
            for (Section_2_Vaccinations_Info_DataModel item : data) {
                txtidno.setText(item.getidno());

                txtq201.setText(item.getq201());
                dtpq202.setText(item.getq202().toString().length() == 0 ? "" : Global.DateConvertDMY(item.getq202()));
                if (item.getq202a().equals("1")) {
                    chkq202a.setChecked(true);
                } else if (item.getq202a().equals("2")) {
                    chkq202a.setChecked(false);
                }
                txtq202b1.setText(item.getq202b1());
                txtq202b2.setText(item.getq202b2());
                txtq202b3.setText(item.getq202b3());
                String[] d_rdogrpq203 = new String[]{"1", "2"};
                for (int i = 0; i < d_rdogrpq203.length; i++) {
                    if (item.getq203().equals(String.valueOf(d_rdogrpq203[i]))) {
                        rb = (RadioButton) rdogrpq203.getChildAt(i);
                        rb.setChecked(true);
                    }
                }
                if (item.getq204a().equals("1")) {
                    chkq204a.setChecked(true);
                } else if (item.getq204a().equals("2")) {
                    chkq204a.setChecked(false);
                }
                if (item.getq204b().equals("1")) {
                    chkq204b.setChecked(true);
                } else if (item.getq204b().equals("2")) {
                    chkq204b.setChecked(false);
                }
                if (item.getq204c().equals("1")) {
                    chkq204c.setChecked(true);
                } else if (item.getq204c().equals("2")) {
                    chkq204c.setChecked(false);
                }
                if (item.getq204d().equals("1")) {
                    chkq204d.setChecked(true);
                } else if (item.getq204d().equals("2")) {
                    chkq204d.setChecked(false);
                }
                if (item.getq204e().equals("1")) {
                    chkq204e.setChecked(true);
                } else if (item.getq204e().equals("2")) {
                    chkq204e.setChecked(false);
                }
                if (item.getq204f().equals("1")) {
                    chkq204f.setChecked(true);
                } else if (item.getq204f().equals("2")) {
                    chkq204f.setChecked(false);
                }
                if (item.getq204g().equals("1")) {
                    chkq204g.setChecked(true);
                } else if (item.getq204g().equals("2")) {
                    chkq204g.setChecked(false);
                }
                if (item.getq204h().equals("1")) {
                    chkq204h.setChecked(true);
                } else if (item.getq204h().equals("2")) {
                    chkq204h.setChecked(false);
                }
                if (item.getq204i().equals("1")) {
                    chkq204i.setChecked(true);
                } else if (item.getq204i().equals("2")) {
                    chkq204i.setChecked(false);
                }
                if (item.getq204j().equals("1")) {
                    chkq204j.setChecked(true);
                } else if (item.getq204j().equals("2")) {
                    chkq204j.setChecked(false);
                }
                if (item.getq204k().equals("1")) {
                    chkq204k.setChecked(true);
                } else if (item.getq204k().equals("2")) {
                    chkq204k.setChecked(false);
                }
                if (item.getq204l().equals("1")) {
                    chkq204l.setChecked(true);
                } else if (item.getq204l().equals("2")) {
                    chkq204l.setChecked(false);
                }
                if (item.getq204x().equals("1")) {
                    chkq204x.setChecked(true);
                } else if (item.getq204x().equals("2")) {
                    chkq204x.setChecked(false);
                }
                txtq204x1.setText(item.getq204x1());
                if (item.getq205a().equals("1")) {
                    chkq205a.setChecked(true);
                } else if (item.getq205a().equals("2")) {
                    chkq205a.setChecked(false);
                }
                if (item.getq205b().equals("1")) {
                    chkq205b.setChecked(true);
                } else if (item.getq205b().equals("2")) {
                    chkq205b.setChecked(false);
                }
                if (item.getq205c().equals("1")) {
                    chkq205c.setChecked(true);
                } else if (item.getq205c().equals("2")) {
                    chkq205c.setChecked(false);
                }
                if (item.getq205d().equals("1")) {
                    chkq205d.setChecked(true);
                } else if (item.getq205d().equals("2")) {
                    chkq205d.setChecked(false);
                }
                if (item.getq205e().equals("1")) {
                    chkq205e.setChecked(true);
                } else if (item.getq205e().equals("2")) {
                    chkq205e.setChecked(false);
                }
                if (item.getq205f().equals("1")) {
                    chkq205f.setChecked(true);
                } else if (item.getq205f().equals("2")) {
                    chkq205f.setChecked(false);
                }
                if (item.getq205x().equals("1")) {
                    chkq205x.setChecked(true);
                } else if (item.getq205x().equals("2")) {
                    chkq205x.setChecked(false);
                }
                txtq206a.setText(item.getq206a());
                txtq206b.setText(item.getq206b());
                txtq206c.setText(item.getq206c());

                if (item.getq207().equals("1")) {
                    chkrefcenter.setChecked(true);
                } else if (item.getq207().equals("2")) {
                    chkrefcenter.setChecked(false);
                }

                spnupazilaid.setSelection(Connection.SpinnerItemPositionAnyLength(spnupazilaid, item.getq208()));
                spnunionid.setSelection(Connection.SpinnerItemPositionAnyLength(spnunionid, item.getq209()));
                spnwardid.setSelection(Connection.SpinnerItemPositionAnyLength(spnwardid, item.getq210()));
                spnsubblockid.setSelection(Connection.SpinnerItemPositionAnyLength(spnsubblockid, item.getq211()));

                txtq206d.setText(item.getq206d());
            }
        } catch (Exception e) {
            Connection.MessageBox(Section_2_Vaccinations_Info.this, e.getMessage());
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
            // case TIME_DIALOG:
            //    return new TimePickerDialog(this, timePickerListener, hour, minute,false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;
            EditText dtpDate;

/*
              dtpDate = (EditText)findViewById(R.id.dtpinterviewer_date);
             if (VariableID.equals("btninterviewer_date"))
              {
                //  dtpDate = (EditText)findViewById(R.id.btninterviewer_date);
              }*/
            dtpDate = (EditText) findViewById(R.id.dtpq202);
            if (VariableID.equals("btnq202")) {
                dtpDate = (EditText) findViewById(R.id.dtpq202);
            }
            dtpDate.setText(new StringBuilder()
                    .append(Global.Right("00" + mDay, 2)).append("/")
                    .append(Global.Right("00" + mMonth, 2)).append("/")
                    .append(mYear));

       /* Vlblq205a.setText("205a. দিন:"+Global.DateDifferenceDays(g.getVDate(),dtpq205.getText().toString())+" (D)");
        Vlblq205b.setText("সপ্তাহ: "+Global.DateDifferenceWeeks(g.getVDate(),dtpq205.getText().toString())+" (W)");
        String Times="";

        if(Integer.valueOf(Global.DateDifferenceDays(g.getVDate(),dtpq205.getText().toString()))<=91)
        {
            Times="1st";
        }

        else  if(Integer.valueOf(Global.DateDifferenceDays(g.getVDate(),dtpq205.getText().toString()))<=182)
        {
            Times="2nd";
        }

        else  if(Integer.valueOf(Global.DateDifferenceDays(g.getVDate(),dtpq205.getText().toString()))<=300)
        {
            Times="3rd";
        }

        else  if(Integer.valueOf(Global.DateDifferenceDays(g.getVDate(),dtpq205.getText().toString()))>300)
        {
            Times="No";
        }

        Vlblq205c.setText("Timester: "+Times);*/

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