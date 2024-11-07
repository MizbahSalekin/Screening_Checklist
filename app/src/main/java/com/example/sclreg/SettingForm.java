package com.example.sclreg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import Common.Connection;
import Common.Global;

public class SettingForm extends Activity {
    Connection C;
    private ProgressDialog progress;
    String message = "";
    int jumpTime = 0;

    LinearLayout secUN;
    LinearLayout secWard;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.devicesetting);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            C = new Connection(this);
            final Spinner spnFac_level = (Spinner) findViewById(R.id.spnFac_level);

            secUN=(LinearLayout)findViewById(R.id.secUN);
            secWard=(LinearLayout)findViewById(R.id.secWard);

            SpinnerItem(spnFac_level, "select (code ||'-'||  cname) as cname from codelist where  typename in('sc') order by code desc");

            final Spinner spnDist = (Spinner) findViewById(R.id.spnDist);
            //SpinnerItem(spnDist, "SELECT (\"ZILLAID\" ||'-'||  \"ZILLANAME\") AS Zilla FROM \"Zilla\" where \"ZILLAID\" in(93,36) order by \"ZILLAID\" desc ");

            final Spinner spnUpz = (Spinner) findViewById(R.id.spnUpz);
           // final Spinner spnUN = (Spinner) findViewById(R.id.spnUN);
           // final Spinner spnWard = (Spinner) findViewById(R.id.spnWard);
            final Spinner spnUN = (Spinner) findViewById(R.id.spnUN);
            final Spinner spnWard = (Spinner) findViewById(R.id.spnWard);
            final Spinner spnProvider = (Spinner) findViewById(R.id.spnProvider);
            final Spinner spnProviderCode = (Spinner) findViewById(R.id.spnProviderCode);

        /*    String SQL_PType = "";

                if(ProjectSetting_reg.CurrentSystem.equals("MO"))
                SQL_PType = "select provtype ||'-'||typename from providertype where provtype in('11')";
            else
                SQL_PType = "select provtype ||'-'||typename from providertype";

            SpinnerItem(spnProvider, SQL_PType);*/



         /*   */



            spnFac_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    //String[] zid = spnDist.getSelectedItem().toString().split("-");
                    // SpinnerItem(spnUpz, "select \"UPAZILAID\"||'-'||\"UPAZILANAME\" from \"Upazila\" where \"ZILLAID\"='" + zid[0] + "'");
                    // SpinnerItem(spnUpz, "select upazilaid||'-'||upazilaname from upazila where zillaid='" + zid[0] + "'");
                    String[] facid= spnFac_level.getSelectedItem().toString().split("-");
                    if(facid[0].equalsIgnoreCase("1"))
                    {
                        secUN.setVisibility(View.GONE);
                        secWard.setVisibility(View.GONE);
                        spnUN.setSelection(0);
                        spnWard.setSelection(0);
                        String SQL_PType = "";

                        SpinnerItem(spnDist, "select (zillaid ||'-'||  zillaname) as zilla from zilla where  zillaid in(75,32,84,90,89) order by zillaid desc");

                        spnDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                String[] zid = spnDist.getSelectedItem().toString().split("-");
                                // SpinnerItem(spnUpz, "select \"UPAZILAID\"||'-'||\"UPAZILANAME\" from \"Upazila\" where \"ZILLAID\"='" + zid[0] + "'");
                                SpinnerItem(spnUpz, "select upazilaid||'-'||upazilaname from upazila where zillaid='" + zid[0] + "'");

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });

                        SQL_PType = "select provtype ||'-'||typename from providertype where provtype in('8','9','10','22','11','12','13','14','17','16')";
                        SpinnerItem(spnProvider, SQL_PType);

                        spnProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                String[] facid= spnFac_level.getSelectedItem().toString().split("-");
                                String[] zid = spnDist.getSelectedItem().toString().split("-");
                                String[] upid = spnUpz.getSelectedItem().toString().split("-");
                                String[] ptype = spnProvider.getSelectedItem().toString().split("-");
                                String SQLStr = "select providerid||'-'||provname from providerdb where  zillaid='" + zid[0] + "' and upazilaid='" + upid[0]  + "' and provtype='" + ptype[0] + "' and level_id='" + facid[0] + "' and active='1'";
                                SpinnerItem(spnProviderCode, SQLStr);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });
                    }
                    else if(facid[0].equalsIgnoreCase("2"))
                    {
                        secUN.setVisibility(View.VISIBLE);
                        secWard.setVisibility(View.GONE);
                         spnWard.setSelection(0);
                        SpinnerItem(spnDist, "select (zillaid ||'-'||  zillaname) as zilla from zilla where  zillaid in(75,32,84,90,89) order by zillaid desc");

                        spnDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                String[] zid = spnDist.getSelectedItem().toString().split("-");
                                // SpinnerItem(spnUpz, "select \"UPAZILAID\"||'-'||\"UPAZILANAME\" from \"Upazila\" where \"ZILLAID\"='" + zid[0] + "'");
                                SpinnerItem(spnUpz, "select upazilaid||'-'||upazilaname from upazila where zillaid='" + zid[0] + "'");

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });
                        String SQL_PType = "";
                        SQL_PType = "select provtype ||'-'||typename from providertype where provtype in('8','9','10','22','11','12','13','14','17','16')";
                        SpinnerItem(spnProvider, SQL_PType);

                        spnUpz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                String[] zid = spnDist.getSelectedItem().toString().split("-");
                                String[] upid = spnUpz.getSelectedItem().toString().split("-");
                                //String[] ptype = spnProvider.getSelectedItem().toString().split("-");
                                SpinnerItem(spnUN, "select unionid ||'-'||unionname from unions where zillaid='" + zid[0] + "' and upazilaid='" + upid[0] + "'");

                            }



                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });

                        spnProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                try {
                                    String[] facid= spnFac_level.getSelectedItem().toString().split("-");
                                    String[] zid = spnDist.getSelectedItem().toString().split("-");
                                    String[] upid = spnUpz.getSelectedItem().toString().split("-");
                                    String[] uid = spnUN.getSelectedItem().toString().split("-");
                                    String[] ptype = spnProvider.getSelectedItem().toString().split("-");
                                   // String[] Ward = spnWard.getSelectedItem().toString().split("-");
                                    String SQLStr = "select providerid||'-'||provname from providerdb where  zillaid='" + zid[0] + "' and upazilaid='" + upid[0] + "' and unionid='" + uid[0]  + "' and provtype='" + ptype[0] + "' and level_id='" + facid[0] + "' and active='1'";
                                    SpinnerItem(spnProviderCode, SQLStr);



                                }catch(Exception ex){

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });


                    }
                    else if(facid[0].equalsIgnoreCase("3"))
                    {

                        secUN.setVisibility(View.VISIBLE);
                        secWard.setVisibility(View.VISIBLE);
                        String SQL_PType = "";
                        SpinnerItem(spnDist, "select (zillaid ||'-'||  zillaname) as zilla from zilla where  zillaid in(75,32,84,90,89) order by zillaid desc");

                        spnDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                String[] zid = spnDist.getSelectedItem().toString().split("-");
                                // SpinnerItem(spnUpz, "select \"UPAZILAID\"||'-'||\"UPAZILANAME\" from \"Upazila\" where \"ZILLAID\"='" + zid[0] + "'");
                                SpinnerItem(spnUpz, "select upazilaid||'-'||upazilaname from upazila where zillaid='" + zid[0] + "'");

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });

                        SQL_PType = "select provtype ||'-'||typename from providertype where provtype in('15','2')";
                        SpinnerItem(spnProvider, SQL_PType);

                        spnUpz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                String[] zid = spnDist.getSelectedItem().toString().split("-");
                                String[] upid = spnUpz.getSelectedItem().toString().split("-");
                                //String[] ptype = spnProvider.getSelectedItem().toString().split("-");
                                SpinnerItem(spnUN, "select unionid ||'-'||unionname from unions where  zillaid='" + zid[0] + "' and upazilaid='" + upid[0] + "'");

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });
                        spnUN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                            String[] zid = spnDist.getSelectedItem().toString().split("-");
                                            String[] upid = spnUpz.getSelectedItem().toString().split("-");
                                            //String[] ptype = spnProvider.getSelectedItem().toString().split("-");
                                            String[] uid = spnUN.getSelectedItem().toString().split("-");
                                            //String SQLStr1 = "select distinct ward||'-Number' as code from providerdb where  zillaid='" + zid[0] + "' and upazilaid='" + upid[0] + "' and unionid='" + uid[0]  + "'";
                                            String SQLStr1 = "select code ||'-'||cname from codelist where typename in('wd')";
                                            SpinnerItem(spnWard, SQLStr1);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parentView) {
                                            // your code here
                                        }
                                    });
                        spnWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                try {
                                    String[] facid= spnFac_level.getSelectedItem().toString().split("-");
                                    String[] zid = spnDist.getSelectedItem().toString().split("-");
                                    String[] upid = spnUpz.getSelectedItem().toString().split("-");
                                    String[] uid = spnUN.getSelectedItem().toString().split("-");
                                    String[] ptype = spnProvider.getSelectedItem().toString().split("-");
                                    String[] Ward = spnWard.getSelectedItem().toString().split("-");

                                    String SQLStr = "select providerid||'-'||provname as code from providerdb where  zillaid='" + zid[0] + "' and upazilaid='" + upid[0] + "' and unionid='" + uid[0] + "' and ward='" + Ward[0] + "' and provtype='" + ptype[0]+ "' and level_id='" + facid[0]  + "' and active='1'";
                                    SpinnerItem(spnProviderCode, SQLStr);



                                }catch(Exception ex){

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });
                    }

                    else if(facid[0].equalsIgnoreCase("0"))
                    {

                        secUN.setVisibility(View.VISIBLE);
                        secWard.setVisibility(View.VISIBLE);
                        String SQL_PType = "";
                        SpinnerItem(spnDist, "select (zillaid ||'-'||  zillaname) as zilla from zilla where  zillaid in(26) order by zillaid desc");

                        spnDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                String[] zid = spnDist.getSelectedItem().toString().split("-");
                                // SpinnerItem(spnUpz, "select \"UPAZILAID\"||'-'||\"UPAZILANAME\" from \"Upazila\" where \"ZILLAID\"='" + zid[0] + "'");
                               // SpinnerItem(spnUpz, "select upazilaid||'-'||upazilaname from upazila where zillaid='" + zid[0] + "'");
                                SpinnerItem(spnUpz, "select (code ||'-'||  cname) as cname from codelist where  code in('301','302') and  typename='" + zid[0] + "'");


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });

                        spnUpz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                               // String[] zid = spnDist.getSelectedItem().toString().split("-");
                               // String[] upid = spnUpz.getSelectedItem().toString().split("-");
                                //String[] ptype = spnProvider.getSelectedItem().toString().split("-");
                               // SpinnerItem(spnUN, "select unionid ||'-'||unionname from unions where zillaid='" + zid[0] + "' and upazilaid='" + upid[0] + "'");
                                SpinnerItem(spnUN, "select (code ||'-'||  cname) as cname from codelist where  code in('5') and typename in('302')");

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });

                        spnUN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                String[] zid = spnDist.getSelectedItem().toString().split("-");
                                String[] upid = spnUpz.getSelectedItem().toString().split("-");
                                //String[] ptype = spnProvider.getSelectedItem().toString().split("-");
                                String[] uid = spnUN.getSelectedItem().toString().split("-");
                                //String SQLStr1 = "select distinct ward||'-Number' from providerdb where  zillaid='" + zid[0] + "' and upazilaid='" + upid[0] + "' and unionid='" + uid[0]  + "'";
                                String SQLStr1 = "select code ||'-'||cname from codelist where typename in('wd')";

                                SpinnerItem(spnWard, SQLStr1);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });

                        SQL_PType = "select provtype ||'-'||typename from providertype where provtype in('16')";
                        SpinnerItem(spnProvider, SQL_PType);

                        spnWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                try {
                                    String[] facid= spnFac_level.getSelectedItem().toString().split("-");
                                    String[] zid = spnDist.getSelectedItem().toString().split("-");
                                    String[] upid = spnUpz.getSelectedItem().toString().split("-");
                                    String[] uid = spnUN.getSelectedItem().toString().split("-");
                                    String[] ptype = spnProvider.getSelectedItem().toString().split("-");
                                    String[] Ward = spnWard.getSelectedItem().toString().split("-");

                                    String SQLStr = "select providerid||'-'||provname as code from providerdb where  zillaid='" + zid[0] + "' and upazilaid='" + upid[0] + "' and unionid='" + uid[0] + "' and ward='" + Ward[0] + "' and provtype='" + ptype[0]+ "' and level_id='" + facid[0]  + "' and active='1'";
                                    SpinnerItem(spnProviderCode, SQLStr);



                                }catch(Exception ex){

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
            });





            secUN.setVisibility(View.GONE);
            secWard.setVisibility(View.GONE);
            Button cmdSave = (Button) findViewById(R.id.cmdSave);
            cmdSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    try {
                        String SQLStr = "";
                        final String[] Flavel= spnFac_level.getSelectedItem().toString().split("-");
                        final String[] PCode = Connection.split(spnProviderCode.getSelectedItem().toString(), '-');
                        final String[] zid = spnDist.getSelectedItem().toString().split("-");
                        final String[] upid = spnUpz.getSelectedItem().toString().split("-");
                       // final String[] uid = spnUN.getSelectedItem().toString().split("-");
                       // final String[] wid = spnWard.getSelectedItem().toString().split("-");
                        final String[] ptype = spnProvider.getSelectedItem().toString().split("-");
                       //and unionid='" + uid[0] + "'
                        SQLStr = "Select * from \"providerdb\" where zillaid='" + zid[0] + "' and upazilaid='" + upid[0] +
                                "'  and \"provtype\"='" + ptype[0] + "' and \"providerid\"='" + PCode[0] + "' and \"active\"='1' and \"devicesetting\"='1'";
                        //Looper.prepare();
                        String AreaCode = C.DataStringJSON(SQLStr);
                        if (AreaCode.equals("2")) {
                            Connection.MessageBox(SettingForm.this, "This is not a valid information for device setting or information not available for this provider.");
                            return;
                        }
                        String ResponseString = "Status:";
                        progress = new ProgressDialog(SettingForm.this);
                        progress.setMessage("Downloading");
                        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progress.setIcon(R.drawable.logo_of_icddrb);
                        progress.setIndeterminate(false);
                        progress.setCancelable(false);
                        progress.setProgress(0);

                        progress.show();

                        final Thread t = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    message = "Downloading";
                                    jumpTime = 1;
                                    //UpdateProgressBar(progress, progressHandler, 1, message);
                                    Global.getInstance().setProgressMessage(message);
                                    progressHandler.sendMessage(progressHandler.obtainMessage());
                                    //C.RebuildDatabase(zid[0], upid[0], uid[0], ptype[0], PCode[0], progress, message, progressHandler, jumpTime);
                                    if(Flavel[0].equalsIgnoreCase("1")) {
                                        C.RebuildDatabase(Flavel[0], zid[0], upid[0], "", "", ptype[0], PCode[0], progress, message, progressHandler, jumpTime, false);//DownloadOnlyAppropriateRecords = false

                                    }

                                 else if(Flavel[0].equalsIgnoreCase("2")) {
                                        final String[] uid = spnUN.getSelectedItem().toString().split("-");
                                        C.RebuildDatabase(Flavel[0], zid[0], upid[0], uid[0], "", ptype[0], PCode[0], progress, message, progressHandler, jumpTime, false);//DownloadOnlyAppropriateRecords = false

                                    }

                                    else if(Flavel[0].equalsIgnoreCase("3")) {
                                        final String[] uid = spnUN.getSelectedItem().toString().split("-");
                                        final String[] wid = spnWard.getSelectedItem().toString().split("-");
                                        C.RebuildDatabase(Flavel[0], zid[0], upid[0], uid[0], wid[0], ptype[0], PCode[0], progress, message, progressHandler, jumpTime, false);//DownloadOnlyAppropriateRecords = false

                                    }

                                    else if(Flavel[0].equalsIgnoreCase("0")) {
                                        final String[] uid = spnUN.getSelectedItem().toString().split("-");
                                        final String[] wid = spnWard.getSelectedItem().toString().split("-");
                                        C.RebuildDatabase(Flavel[0], zid[0], upid[0], uid[0], wid[0], ptype[0], PCode[0], progress, message, progressHandler, jumpTime, false);//DownloadOnlyAppropriateRecords = false

                                    }

                                    progress.dismiss();

                                    //Call Login Form
                                    finish();
                                    Intent f1 = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(f1);

                                }
                                catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    //e.printStackTrace();
                                    Log.e("Error from Setting Form", e.getMessage());
                                    Connection.MessageBox(SettingForm.this, e.getMessage());
                                    return;
                                }
                                //}
                                // Looper.loop();
                            }

                        };
                        t.start();



                    } catch (Exception ex) {
                        Connection.MessageBox(SettingForm.this, ex.getMessage());
                        return;
                    }
                }

                Handler progressHandler = new Handler() {
                    public void handleMessage(Message msg) {
                        /*progress.setMessage(message);*/
                        progress.setMessage(Global.getInstance().getProgressMessage());
                        progress.incrementProgressBy(jumpTime);


                    }


                };


            });
        } catch (Exception ex) {
            Connection.MessageBox(SettingForm.this, ex.getMessage());
            return;
        }


    }


    private void SpinnerItem(Spinner SpinnerName, String SQL) {
        List<String> listItem = new ArrayList<String>();
        listItem = C.DataListJSON(SQL);
        ArrayAdapter<String> adptrList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listItem);
        SpinnerName.setAdapter(adptrList);
    }
/*
    private List<String> AreaList(String SQL)
    {
        String DataArray[] = null;
        DownloaAreadData d = new DownloaAreadData();
        d.setContext(getApplicationContext());
        d.Method_Name = "DownloadData";
        d.SQLStr = SQL;

        try {
            DataArray = d.execute("").get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<String> stringList = new ArrayList<String>(Arrays.asList(DataArray));

        return stringList;
    }*/

   /* public class DownloaAreadData extends AsyncTask<String, Void, String[]> {
        String Response = "";
        private Context context;
        private ProgressDialog dialog;

        public void setContext(Context contextf){
            context = contextf;
        }

        //Method Name
        public String Method_Name;
        public String SQLStr;

        //public String WSDL_TARGET_NAMESPACE = Global.Namespace;
        //public String SOAP_ACTION = Global.Namespace+Method_Name;
        //public String SOAP_ADDRESS =Global.Soap_Address;

        String[] Data=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SettingForm.this);
            dialog.setMessage("Synchronizing Database ...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(String... urls) {

            try {
                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,Method_Name);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);

                request.addProperty("SQL",SQLStr);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport.call(Global.Namespace+Method_Name, envelope);
                SoapObject result = (SoapObject)envelope.getResponse();


                Data=new String[result.getPropertyCount()];
                for(int i=0;i<result.getPropertyCount();i++)
                {
                    Data[i]=result.getProperty(i).toString();
                }
                return Data;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return Data;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            dialog.dismiss();
        }

    }*/


    private class DataSyncTask extends AsyncTask<String, String, Void> {
        String Response = "";
        private Context context;
        private ProgressDialog dialog;

        public void setContext(Context contextf) {
            context = contextf;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SettingForm.this);
            dialog.setMessage("Synchronizing Database ...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }


        protected void onProgressUpdate(String... progress) {
            dialog.setProgress(Integer.parseInt(progress[0]));
            //publishProgress(progress);

        }

        /**
         * This is where YOU do YOUR work. There's nothing for me to write here
         * you have to fill this in. Make your HTTP request(s) or whatever it is
         * you have to do to get your updates in here, because this is run in a
         * separate thread
         */
        @Override
        protected Void doInBackground(String... params) {
            String[] P = Connection.split(params[0], '^');
/*
            String SQLStr = "";
            SQLStr = "Select zillaid from ProviderDB where";
            SQLStr += " zillaid   ='" + P[0] + "' and";
            SQLStr += " upazilaid ='" + P[1] + "' and";
            SQLStr += " unionid   ='" + P[2] + "' and";
            SQLStr += " provtype  ='" + P[3] + "' and";
            SQLStr += " provcode  ='" + P[4] + "' and";
            SQLStr += " active='1' and DeviceSetting='1'";

            String AreaCode = C.ReturnResult("Existence", SQLStr);
            if (AreaCode.equals("2")) {
                Response = "This is not a valid information for device setting or information not available for this provider.";
            }*/

            //Rebuild database
            //C.RebuildDatabase(P[0], P[1], P[2], P[3], P[4]);

            //Response = "done";

            return null;
        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
            finish();
            Intent f1 = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(f1);
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //dialog.dismiss();
        //mWakeLock.release();
    }


    ProgressDialog dialog;
    Integer count = 0;

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
//            dialog = new ProgressDialog(SettingForm.this);
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
//                File file = Environment.getExternalStorageDirectory();
//
//                file.mkdirs();
//                File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + Global.DatabaseFolder + File.separator + "zilla.txt");
//
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
//                String filaName = "village.txt";
//                C.ExecuteSQLFromFile(filaName);
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