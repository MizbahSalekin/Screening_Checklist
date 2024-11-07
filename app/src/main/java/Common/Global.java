package Common;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Global {
    private static Global instance;
    public static synchronized Global getInstance()
    {
        if(instance==null){
            instance=new Global();
        }
        return instance;
    }

	public static char VariableSeperator = '^';
	public static String Organization 	 = ProjectSetting_reg.Organization;

	//public static String DatabaseFolder     = ProjectSetting_reg.DatabaseFolder;
	public static String DatabaseName       = ProjectSetting_reg.DatabaseName;
	//public static final String ProjectType  = "FWAUnitWise";//VillageWise  FWAUnitWise
	//public static final String ProjectType1 = "HAWardWise"; //VillageWise  Ward Wise
	//public static  String ReportName;
	//public static final String PRSAndServiceModule = ProjectSetting_cs.PRSAndServiceModule;
	public static final String Server_URL   = ProjectSetting_reg.Server_URL;
	private static final String APIName     = ProjectSetting_reg.APIName;


	//Main LMIS
	//private static final String APINameLMIS = "CcahWebservice_lmis";

	//Development DB
	public static final String URL_WebAPI = Server_URL + "/" + APIName + "/apidata/DataSync";

     // Local
	//public static final String URL_WebAPI ="http://localhost:8084/apidata/DataSync";


	//LMIS url
	//public static final String URL_WebAPILMIS = "http://mchdrhis.icddrb.org:8080/" + APINameLMIS + "/apidata/DataSync";

	public static final String USER_AGENT_WebAPI = "Mozilla/5.0";

	//Development apk Folder
	public static String NewVersionName    = "ePWCUpdate";
	public static String UpdatedSystemCS = Server_URL + "/" + APIName + "/UpdateCS/" + Global.NewVersionName + ".txt";



    //Database
	//Need to open this DB




/*	//Database

     public static char VariableSeperator = '^';
    public static String Organization 	 = "ICDDRB";
	//...........................................................................................................
	public static String DatabaseName   = "FPCADatabase.db";
	public static String DatabaseFolder = "FPCADatabase";



	public static final String ProjectType ="FWAUnitWise";//VillageWise  FWAUnitWise





	//icddrb
	public static final String Server_URL = "http://mchdrhis.icddrb.org:8080"; // icddrb
	private static final String APIName   = "fpca";   //icddrb Main Server
	//private static final String APIName = "fpca_dev"; //icddrb development Server



	//Development DB
	public static final String URL_WebAPI = Server_URL + "/" + APIName + "/apidata/DataSync";


	public static final String USER_AGENT_WebAPI = "Mozilla/5.0";

	//Development apk Folder
	public static String NewVersionName    = "FPCAUpdate";
	public static String UpdatedSystemFPCA = Server_URL + "/" + APIName + "/UpdateFPCA/" + Global.NewVersionName + ".txt";*/



	private String _UserID;

	private String _progressMessage;
	public void setProgressMessage(String progressMessage){this._progressMessage = progressMessage;}
	public String getProgressMessage(){ return this._progressMessage;}

	public void setUserID(String UserID){this._UserID = UserID;}
	public String getUserID(){ return this._UserID;}


    private String _LoginProvType; public void setLoginProvType(String LoginProvType){this._LoginProvType = LoginProvType;} public String getLoginProvType(){ return this._LoginProvType;}
    private String _LoginProvCode; public void setLoginProvCode(String LoginProvCode){this._LoginProvCode = LoginProvCode;} public String getLoginProvCode(){ return this._LoginProvCode;}

    //Global Variable
	//-------------------------------------------------------------------------
	 private String _UserId;
	 public void setUserId(String UserId){this._UserId = UserId;}
	 public String getUserId(){ return this._UserId;}
	//-------------------------------------------------------------------------
	 private String _DeviceNo;
	 public void setDeviceNo(String DeviceNo){this._DeviceNo = DeviceNo;}
	 public String getDeviceNo(){ return this._DeviceNo;}
	//-------------------------------------------------------------------------
	private String _HealthID;
	public void setHealthID(String HealthID){this._HealthID = HealthID;}
	public String getHealthID(){ return this._HealthID;}

	//-------------------------------------------------------------------------
	 private String _IdNo;
	 public void setIdNo(String IdNo){this._IdNo = IdNo;}
	 public String getIdNo(){ return this._IdNo;}

	//-------------------------------------------------------------------------
	private String _ProviderDeg;
	public void setProviderDeg(String ProviderDeg){this._ProviderDeg = ProviderDeg;}
	public String getProviderDeg(){ return this._ProviderDeg;}
	 //-------------------------------------------------------------------------
	 private String _RegistrationDate;
	 public void setRegistrationDate(String RegistrationDate){this._RegistrationDate = RegistrationDate;}
	 public String getRegistrationDate(){ return this._RegistrationDate;}
	//-------------------------------------------------------------------------
	 private String _CallFrom;
	 public void setCallFrom(String CallFrom){this._CallFrom = CallFrom;}
	 public String getCallFrom(){ return this._CallFrom;}
	 //-------------------------------------------------------------------------

	private String _VDate;
	public void setVDate(String VDate){this._VDate = VDate;}
	public String getVDate(){ return this._VDate;}

	//-------------------------------------------------------------------------

	private String _EPISubBlock;
	public void setEPISubBlock(String EPISubBlock){this._EPISubBlock = EPISubBlock;}
	public String getEPISubBlock(){ return this._EPISubBlock;}

//----------------------------------
    private String _AreaDate;
    public void setAreaDate(String AreaDate){this._AreaDate = AreaDate;}
    public String getAreaDate(){ return this._AreaDate;}



    private String _AreaDateM;
    public void setAreaDateM(String AreaDateM){this._AreaDateM = AreaDateM;}
    public String getAreaDateM(){ return this._AreaDateM;}

    private String _AreaUnitValue;
    public void setAreaUnitValue(String AreaUnitValue){this._AreaUnitValue = AreaUnitValue;}
    public String getAreaUnitValue(){ return this._AreaUnitValue;}



	 //Area Variable
	 private String _Div; public void setDivision(String Div){this._Div = Div;} public String getDivision(){ return this._Div;}
	 private String _Dist; public void setDistrict(String Dist){this._Dist = Dist;} public String getDistrict(){ return this._Dist;}
	 private String _Upz; public void setUpazila(String Upz){this._Upz = Upz;} public String getUpazila(){ return this._Upz;}
	 private String _UN; public void setUnion(String UN){this._UN = UN;} public String getUnion(){ return this._UN;}
	 private String _ProvType; public void setProvType(String ProvType){this._ProvType = ProvType;} public String getProvType(){ return this._ProvType;}
	 private String _ProvCode; public void setProvCode(String ProvCode){this._ProvCode = ProvCode;} public String getProvCode(){ return this._ProvCode;}
	 private String _ward; public void setward(String ward){this._ward = ward;} public String getward(){ return this._ward;}
	 private String _level; public void setlevel(String level){this._level = level;} public String getlevel(){ return this._level;}



	private String _Mouza; public void setMouza(String Mouza){this._Mouza = Mouza;} public String getMouza(){ return this._Mouza;}
	 private String _Vill; public void setVillage(String Vill){this._Vill = Vill;} public String getVillage(){ return this._Vill;}
	 private String _VillName; public void setVillageName(String VillName){this._VillName = VillName;} public String getVillageName(){ return this._VillName;}
	private String _TotalHousehold; public void setTotalHousehold(String TotalHousehold){this._TotalHousehold = TotalHousehold;} public String getTotalHousehold(){ return this._TotalHousehold;}

	private String _Idno; public void setIdno(String Idno){this._Idno = Idno;} public String getIdno(){ return this._Idno;}

	private String _scheduleYear;

	public void setscheduleYear(String scheduleYear) {
		this._scheduleYear = scheduleYear;
	}

	public String getscheduleYear() {
		return this._scheduleYear;
	}
	 private String _WardNew; public void setWardNew(String WardNew){this._WardNew = WardNew;} public String getWardNew(){ return this._WardNew;}
	 private String _WardOld; public void setWardOld(String WardOld){this._WardOld = WardOld;} public String getWardOld(){ return this._WardOld;}
	 private String _FWAUnit; public void setFWAUnit(String FWAUnit){this._FWAUnit = FWAUnit;} public String getFWAUnit(){ return this._FWAUnit;}
	 private String _EPIBlock; public void setEPIBlock(String EPIBlock){this._EPIBlock = EPIBlock;} public String getEPIBlock(){ return this._EPIBlock;}
	private String _FWAUnitForReport; public void setFWAUnitForReport(String FWAUnitForReport){this._FWAUnitForReport = FWAUnitForReport;} public String getFWAUnitForRepor(){ return this._FWAUnitForReport;}
	private String _EPIDate;

	public void setEPIDate(String EPIDate) {
		this._EPIDate = EPIDate;
	}

	public String getEPIDate() {
		return this._EPIDate;
	}
	 private String _HHNo; public void setHouseholdNo(String HHNo){this._HHNo = HHNo;} public String getHouseholdNo(){ return this._HHNo;}
	 private String _SNo; public void setSerialNo(String SNo){this._SNo = SNo;} public String getSerialNo(){ return this._SNo;}
	//setMasterTableSource
	private String _MSource; public void setMasterTableSource(String MSource){this._MSource = MSource;} public String getMasterTableSource(){ return this._MSource;}
	private String _BRIDNo;

	public void setBRIDNo(String BRIDNo) {
		this._BRIDNo = BRIDNo;
	}

	public String getBRIDNo() {
		return this._BRIDNo;
	}

	private String _WPProvCode;
	public void setWPProvCode(String WPProvCode){this._WPProvCode = WPProvCode;}
	public String getWPProvCode(){ return this._WPProvCode;}

	private String _WPDate;
	public void setWPDate(String WPDate){this._WPDate = WPDate;}
	public String getWPDate(){ return this._WPDate;}

	private String _WPDes;
	public void setWPDes(String WPDes){this._WPDes = WPDes;}
	public String getWPDes(){ return this._WPDes;}

	 //Immunization
	 private String _imuCode; public void setImuCode(String imuCode){this._imuCode = imuCode;} public String getImuCode(){ return this._imuCode;}
    private String _imuDose; public void setimuDose(String imuDose){this._imuDose = imuDose;} public String getimuDose(){ return this._imuDose;}
	 //String Function
	 //...........................................................................................................
     public static String Right(String text, int length) {
  	   return text.substring(text.length() - length, text.length());
     }
     public static String Left(String text, int length){
          return text.substring(0, length);
    }
     public static String Mid(String text, int start, int end){
          return text.substring(start, end);
     }
     public static String Mid(String text, int start){
          return text.substring(start, text.length() - start);
     }

	private String _SLno;
	public void setSLno(String SLno){this._SLno = SLno;}
	public String getSLno(){ return this._SLno;}

    Calendar c = Calendar.getInstance();
    public int mYear = c.get(Calendar.YEAR);
    public int mMonth = c.get(Calendar.MONTH)+1;
    public int mDay = c.get(Calendar.DAY_OF_MONTH);


    //Year, Month
    public static String CurrentYear()
    {
    	Calendar c = Calendar.getInstance();
        String Y = String.valueOf(c.get(Calendar.YEAR));
        return Y;
    }

    public static String CurrentMonth()
    {
    	Calendar c = Calendar.getInstance();
        int mMonth = c.get(Calendar.MONTH)+1;

        String M = Right("00" + String.valueOf(mMonth), 2);

        return M;
    }
    //Date now
    //...........................................................................................................
    //Format: YYYY-MM-DD
    public static String DateNowYMD()
    {
        Calendar c = Calendar.getInstance();
        int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1;
        int mDay   = c.get(Calendar.DAY_OF_MONTH);

        String M = Right("00"+String.valueOf(mMonth),2);
        String Y = String.valueOf(mYear);
        String D = Right("00" + String.valueOf(mDay), 2);

        String CurrentDateYYYYMMDD = String.valueOf(Y)+"-"+String.valueOf(M)+"-"+String.valueOf(D);

    	return CurrentDateYYYYMMDD;
    }



    //Format: DD/MM/YYYY
    public static String DateNowDMY()
    {
        Calendar c = Calendar.getInstance();
        int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1;
        int mDay   = c.get(Calendar.DAY_OF_MONTH);

        String M = Right("00"+String.valueOf(mMonth),2);
        String Y = String.valueOf(mYear);
        String D = Right("00"+String.valueOf(mDay),2);

        String CurrentDateDDMMYYYY = String.valueOf(D)+"/"+String.valueOf(M)+"/"+String.valueOf(Y);

    	return CurrentDateDDMMYYYY;
    }

    public static String DateTimeNowYMDHMS()
    {
        Calendar c = Calendar.getInstance();
        int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1;
        int mDay   = c.get(Calendar.DAY_OF_MONTH);

        String M = Right("00" + String.valueOf(mMonth), 2);
        String Y = String.valueOf(mYear);
        String D = Right("00" + String.valueOf(mDay), 2);

        String second = Right("00"+String.valueOf(c.get(Calendar.SECOND)),2);
        String minute = Right("00" + String.valueOf(c.get(Calendar.MINUTE)), 2);
        String hour   = Right("00"+String.valueOf(c.get(Calendar.HOUR_OF_DAY)),2);  //24 hour format

        String CurrentDateTimeYMD = String.valueOf(Y)+"-"+String.valueOf(M)+"-"+String.valueOf(D)+" "+ String.valueOf(hour)+":"+ String.valueOf(minute)+":"+ String.valueOf(second);

    	return CurrentDateTimeYMD;
    }

	public static String GenerateProviderId(String ProviderId)
	{

			Calendar c = Calendar.getInstance();
			int mYear  = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH)+1;
			int mDay   = c.get(Calendar.DAY_OF_MONTH);

			String M = Right("00" + String.valueOf(mMonth), 2);
			String Y = String.valueOf(mYear);
			String D = Right("00" + String.valueOf(mDay), 2);

			String second = Right("00"+String.valueOf(c.get(Calendar.SECOND)),2);
			String minute = Right("00" + String.valueOf(c.get(Calendar.MINUTE)), 2);
			String hour   = Right("00"+String.valueOf(c.get(Calendar.HOUR_OF_DAY)),2);  //24 hour format

			String CurrentDateTimeYMD = String.valueOf(Y)+""+String.valueOf(M)+""+String.valueOf(D)+""+ String.valueOf(hour)+""+ String.valueOf(minute)+""+ String.valueOf(second);

			return CurrentDateTimeYMD;
        //return (ProviderId + CurrentDateTimeYMD);


	}

	public static String getDateTimeStringMs()
	{

		Calendar c = Calendar.getInstance();
		int mYear  = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH)+1;
		int mDay   = c.get(Calendar.DAY_OF_MONTH);

		String M = Right("00" + String.valueOf(mMonth), 2);
		String Y = String.valueOf(mYear);
		String D = Right("00" + String.valueOf(mDay), 2);

		String second = Right("00"+String.valueOf(c.get(Calendar.SECOND)),2);
		String minute = Right("00" + String.valueOf(c.get(Calendar.MINUTE)), 2);
		String hour   = Right("00"+String.valueOf(c.get(Calendar.HOUR_OF_DAY)),2);  //24 hour format

		String CurrentDateTimeYMD = String.valueOf(Y)+""+String.valueOf(M)+""+String.valueOf(D)+""+ String.valueOf(hour)+""+ String.valueOf(minute)+""+ String.valueOf(second);

		return CurrentDateTimeYMD;
		//return (ProviderId + CurrentDateTimeYMD);


	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
    public static String GenerateProviderIdForInjectable1(String ProviderId)
    {

        Calendar c = Calendar.getInstance();
        int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1;
        int mDay   = c.get(Calendar.DAY_OF_MONTH);

        String M = Right("00" + String.valueOf(mMonth), 2);
        String Y = String.valueOf(mYear);
        String D = Right("00" + String.valueOf(mDay), 2);

        String second = Right("00"+String.valueOf(c.get(Calendar.SECOND)),2);
        String minute = Right("00" + String.valueOf(c.get(Calendar.MINUTE)), 2);
        String hour   = Right("00"+String.valueOf(c.get(Calendar.HOUR_OF_DAY)),2);  //24 hour format

        String CurrentDateTimeYMD = String.valueOf(Y)+""+String.valueOf(M)+""+String.valueOf(D)+""+ String.valueOf(hour);

        return (ProviderId + CurrentDateTimeYMD);


    }
	public static int daysReturn(int m,int y)
	{
		int m1=m;
		int y1=y;
		String str=" "+ m1+"-"+y1;
		System.out.println(str);

		SimpleDateFormat sd=new SimpleDateFormat("MM-yyyy");

		try {
			Date d=sd.parse(str);
			System.out.println(d);
			Calendar c=Calendar.getInstance();
			c.setTime(d);
			return c.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;

	}


	//09 Sep 2017
	public static int getNumFridays(int currentMonth, int currentYear, int dayToFindCount) {
		Calendar calendar = Calendar.getInstance();
		// Note that month is 0-based in calendar, bizarrely.
		calendar.set(currentYear, currentMonth - 1, 1);
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		int count = 0;
		for (int day = 1; day <= daysInMonth; day++) {
			calendar.set(currentYear, currentMonth - 1, day);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek == dayToFindCount) {
				count++;
				// Or do whatever you need to with the result.
			}
		}
		return count;
	}
    public static String GenerateProviderIdForInjectable2(String ProviderId)
    {

        Calendar c = Calendar.getInstance();
        int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1;
        int mDay   = c.get(Calendar.DAY_OF_MONTH);

        String M = Right("00" + String.valueOf(mMonth), 2);
        String Y = String.valueOf(mYear);
        String D = Right("00" + String.valueOf(mDay), 2);

        String second = Right("00"+String.valueOf(c.get(Calendar.SECOND)),2);
        String minute = Right("00" + String.valueOf(c.get(Calendar.MINUTE)), 2);
        String hour   = Right("00"+String.valueOf(c.get(Calendar.HOUR_OF_DAY)),2);  //24 hour format

        String CurrentDateTime = String.valueOf(minute)+""+ String.valueOf(second);

        return CurrentDateTime;


    }

    public static String DateTimeNowDMYHMS()
    {
        Calendar c = Calendar.getInstance();
        int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1;
        int mDay   = c.get(Calendar.DAY_OF_MONTH);

        String M = Right("00"+String.valueOf(mMonth),2);
        String Y = String.valueOf(mYear);
        String D = Right("00"+String.valueOf(mDay),2);

        String second = Right("00"+String.valueOf(c.get(Calendar.SECOND)),2);
        String minute = Right("00"+String.valueOf(c.get(Calendar.MINUTE)),2);
        String hour   = Right("00"+String.valueOf(c.get(Calendar.HOUR_OF_DAY)),2);  //24 hour format

        String CurrentDateTimeDMY = String.valueOf(D)+"-"+String.valueOf(M)+"-"+String.valueOf(Y)+" "+ String.valueOf(hour)+":"+ String.valueOf(minute)+":"+ String.valueOf(second);

    	return CurrentDateTimeDMY;
    }

    //Time Now
    //...........................................................................................................
    public String CurrentTime24()
    {
    	Calendar TM = Calendar.getInstance();
    	//return Right("00"+String.valueOf(TM.get(Calendar.HOUR_OF_DAY)),2)+":"+ Right("00"+String.valueOf(TM.get(Calendar.MINUTE)),2)+":"+ Right("00"+String.valueOf(TM.get(Calendar.SECOND)),2);
    	return Right("00"+String.valueOf(TM.get(Calendar.HOUR_OF_DAY)),2)+":"+ Right("00"+String.valueOf(TM.get(Calendar.MINUTE)),2);
    }


    //Date Converter: dd/mm/yyyy to yyyy-mm-dd
    public static String DateConvertYMD(String DateString)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.
        Date dateObject;
        String date="";
	    try{
		    dateObject = formatter.parse(DateString);
		    date = new SimpleDateFormat("yyyy-MM-dd").format(dateObject);
	    }

	    catch (ParseException e)
	        {
	            e.printStackTrace();
	        }
	    return date;
    }

  //Date Converter: yyyy-mm-dd to dd/mm/yyyy
    public static String DateConvertDMY(String DateString)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Make sure user insert date into edittext in this format.
        Date dateObject;
        String date="";
	    try{
		    dateObject = formatter.parse(DateString);
		    date = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);
	    }

	    catch (ParseException e)
	        {
	            e.printStackTrace();
	        }
	    return date;
    }

    //Add days: format: YYYY-MM-DD
    public static String addDaysYMD(String date, int days)
    {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return sdf.format(cal.getTime());
    }


    //Add days: format: DD/MM/YYYY
    public static String addDays(String date, int days)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return sdf.format(cal.getTime());
    }

    //Add days: format: DD-MM-YYYY
	public static String addDaysDMY(String date, int days)
    {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return sdf.format(cal.getTime());
    }

	//difference between two data
	//End date  : dd/mm/yyyy
	//Start date: dd/mm/yyyy
	//...........................................................................................................
	public static int DateDifferenceDays(String EndDateDDMMYYYY,String StartDateDDMMYYYY)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int age = 0;
			int diffInDays = 0;
			Date VD;
			Date BD;
			try {
				VD = sdf.parse(Global.DateConvertYMD(EndDateDDMMYYYY));
				BD = sdf.parse(Global.DateConvertYMD(StartDateDDMMYYYY));
				diffInDays = (int) ((VD.getTime() - BD.getTime())/ (1000 * 60 * 60 * 24));
				//age = (int)(diffInDays/365.25);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return diffInDays;
		}

	public static int DateDifferenceWeeks(String EndDateDDMMYYYY,String StartDateDDMMYYYY)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int age = 0;
		int diffInDays = 0;
		Date VD;
		Date BD;
		try {
			VD = sdf.parse(Global.DateConvertYMD(EndDateDDMMYYYY));
			BD = sdf.parse(Global.DateConvertYMD(StartDateDDMMYYYY));
			diffInDays = (int) ((VD.getTime() - BD.getTime())/ (1000 * 60 * 60 * 24));
			age = (int)(diffInDays/7);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return age;
	}


	public static int DateDifferenceMonth(String EndDateDDMMYYYY,String StartDateDDMMYYYY)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int age = 0;
			int diffInDays = 0;
			Date VD;
			Date BD;
			try {
				VD = sdf.parse(Global.DateConvertYMD(EndDateDDMMYYYY));
				BD = sdf.parse(Global.DateConvertYMD(StartDateDDMMYYYY));
				diffInDays = (int) ((VD.getTime() - BD.getTime())/ (1000 * 60 * 60 * 24));
				age = (int)(diffInDays/30.40);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return age;
		}

	public static int DateDifferenceYears(String EndDateDDMMYYYY,String StartDateDDMMYYYY)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int age = 0;
			int diffInDays = 0;
			Date VD;
			Date BD;
			try {
				VD = sdf.parse(Global.DateConvertYMD(EndDateDDMMYYYY));
				BD = sdf.parse(Global.DateConvertYMD(StartDateDDMMYYYY));
				diffInDays = (int) ((VD.getTime() - BD.getTime())/ (1000 * 60 * 60 * 24));
				age = (int)(diffInDays/365.25);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return age;
		}

    //Date field validate
    //...........................................................................................................
	public static String DateValidate(String DateString)
	{
		String DT=DateString;
		String Message = "";

		if(DT.length()!=10)
		{
			Message = "তারিখ ১০ digit হতে হবে [dd/mm/yyyy].";
		}
		else if(!DT.substring(2, 3).equals("/") | !DT.substring(5, 6).equals("/"))
		{
			Message = "তারিখ সঠিক নয়.";
		}
		else
		{
			String D=DT.substring(0,2);
			String M=DT.substring(3,5);
			Calendar c = Calendar.getInstance();
			int currentYear = c.get(Calendar.YEAR);

			int Y= Integer.parseInt(DT.substring(6,10));


			//Date format check
			//-------------------------------------------------------------------------------------------
			if(Integer.parseInt(M)<1 | Integer.parseInt(M)>12)
			{
				Message = "মাসের সংখ্যা 01 - 12 হতে হবে.";
			}
			else if(Integer.parseInt(D)<1 | Integer.parseInt(D)>31)
			{
				Message = "দিনের সংখ্যা 01 - 31 হতে হবে.";
			}
			else if(Y > currentYear | Y < 1900)
			{
				Message = "বছরের সংখ্যা 1900 - "+ String.valueOf(currentYear)+" হতে হবে.";
			}

			// only 1,3,5,7,8,10,12 has 31 days
			else if (D.equals("31") &&
	        		 (M.equals("4") || M .equals("6") || M.equals("9") ||
	                  M.equals("11") || M.equals("04") || M .equals("06") ||
	                  M.equals("09"))) {
				Message = "তারিখ সঠিক নয়.";
	         }
	         else if (M.equals("2") || M.equals("02")) {
	              //leap year
	             if(Y % 4==0){
	                  if(D.equals("30") || D.equals("31")){
	                	  Message = "তারিখ সঠিক নয়.";
	                  }
	                  else{
	                	  //valid=true;
	                  }
	             }
	             else{
	                 if(D.equals("29")||D.equals("30")||D.equals("31")){
	                	 Message = "তারিখ সঠিক নয়.";
	                 }
	                 else{
	                	 //valid=true;
	                 }
	             }
	         }

	         else{
	        	 //valid=true;
	         }

			//Validation check
			//-------------------------------------------------------------------------------------------
	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	        Date dateObject;
	        try {
	        	Global g=new Global();
				dateObject = formatter.parse(DateString);
	            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            String formattedDate = sdf.format(c.getTime());
	            Date currentDate = sdf.parse(DateNowDMY());
	            Date DateValue = sdf.parse(DateString);

	            if(DateValue.after(currentDate))
		          {
	                int mYear = c.get(Calendar.YEAR);
	                int mMonth = c.get(Calendar.MONTH)+1;
	                int mDay = c.get(Calendar.DAY_OF_MONTH);

	            	String MM   = Right("00"+String.valueOf(c.get(Calendar.MONTH)+1),2);
	                String YYYY = String.valueOf(c.get(Calendar.YEAR));
	                String DD   = Right("00"+String.valueOf(c.get(Calendar.DAY_OF_MONTH)),2);
					  Message = "আজকের কিংবা পূর্ববর্তী তারিখ হতে হবে["+ (String.valueOf(DD)+"/"+String.valueOf(MM)+"/"+String.valueOf(YYYY)) +"] অগ্রিম তারিখ দেয়া যাবে না।";

					  //Message = "তারিখ আজকের তারিখের["+ (String.valueOf(DD)+"/"+String.valueOf(MM)+"/"+String.valueOf(YYYY)) +"]  সমান অথবা কম হতে হবে।";
		          }
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		return Message;

	}

	//System date check
    public static String TodaysDateforCheck()
    {
        Calendar c = Calendar.getInstance();
        int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1;
        int mDay   = c.get(Calendar.DAY_OF_MONTH);

        String CurrentDateDDMMYYYY = String.valueOf(mYear)+""+String.valueOf(mMonth)+""+String.valueOf(mDay);

    	return CurrentDateDDMMYYYY;
    }


    //Getting spinner item position with code
	public static int SpinnerItemPosition(Spinner spn,int CodeLength, String Value)
	{
		int pos = 0;
		if(Value.length()!=0)
		{
		    for(int i=0;i<spn.getCount();i++)
		    {
		    	if(spn.getItemAtPosition(i).toString().length()!=0)
		    	{
			    	if(Global.Left(spn.getItemAtPosition(i).toString(),CodeLength).equalsIgnoreCase(Value))
			    	{
			    		pos = i;
			    		i   = spn.getCount();
			    	}
		    	}
		    }
		}
	    return pos;
	}

    /*
     Pass Value to spinner to set spinner item
      */
    public static void SetSpinnerItem(Spinner spn,String Value)
    {
        int pos = 0;
        if(Value.length()!=0)
        {
            for(int i=0;i<spn.getCount();i++)
            {
                if(spn.getItemAtPosition(i).toString().length()!=0)
                {
                    if(Global.Left(spn.getItemAtPosition(i).toString(),Value.length()).equalsIgnoreCase(Value))
                    {
                        spn.setSelection(i);
                        break;
                        /*pos = i;
                        i   = spn.getCount();*/
                    }
                }
            }
        }

    }

	//...........................................................................................................
	// GPS Coordinates
	//...........................................................................................................
	public static String decimalToDMS(double coord) {
        String output, degrees, minutes, seconds;

        // gets the modulus the coordinate divided by one (MOD1).
        // in other words gets all the numbers after the decimal point.
        // e.g. mod = 87.728056 % 1 == 0.728056
        //
        // next get the integer part of the coord. On other words the whole number part.
        // e.g. intPart = 87

        double mod = coord % 1;
        int intPart = (int)coord;

        //set degrees to the value of intPart
        //e.g. degrees = "87"

        degrees = String.valueOf(intPart);

        // next times the MOD1 of degrees by 60 so we can find the integer part for minutes.
        // get the MOD1 of the new coord to find the numbers after the decimal point.
        // e.g. coord = 0.728056 * 60 == 43.68336
        //      mod = 43.68336 % 1 == 0.68336
        //
        // next get the value of the integer part of the coord.
        // e.g. intPart = 43

        coord = mod * 60;
        mod = coord % 1;
        intPart = (int)coord;
        if (intPart < 0) {
           // Convert number to positive if it's negative.
           intPart *= -1;
        }

        // set minutes to the value of intPart.
        // e.g. minutes = "43"
        minutes = String.valueOf(intPart);

        //do the same again for minutes
        //e.g. coord = 0.68336 * 60 == 41.0016
        //e.g. intPart = 41
        coord = mod * 60;
        intPart = (int)coord;
        if (intPart < 0) {
           // Convert number to positive if it's negative.
           intPart *= -1;
        }

        // set seconds to the value of intPart.
        // e.g. seconds = "41"
        seconds = String.valueOf(intPart);

        // I used this format for android but you can change it
        // to return in whatever format you like
        // e.g. output = "87/1,43/1,41/1"
        //output = degrees + "/1," + minutes + "/1," + seconds + "/1";

        //Standard output of DÃ‚Â°MÃ¢â‚¬Â²SÃ¢â‚¬Â³
        //output = degrees + "Ã‚Â°" + minutes + "'" + seconds + "\"";
        	output = degrees + "," + minutes + "," + seconds;

        return output;
		}

       /*
        * Conversion DMS to decimal
        *
        * Input: latitude or longitude in the DMS format ( example: N 43Ã‚Â° 36' 15.894")
        * Return: latitude or longitude in decimal format
        * hemisphereOUmeridien => {W,E,S,N}
        *
        */
        public double DMSToDecimal(String hemisphereOUmeridien,double degres,double minutes,double secondes)
        {
                double LatOrLon=0;
                double signe=1.0;
 
                if((hemisphereOUmeridien.equals("W"))||(hemisphereOUmeridien.equals("S"))) {signe=-1.0;}                
                LatOrLon = signe*(Math.floor(degres) + Math.floor(minutes)/60.0 + secondes/3600.0);
 
                return(LatOrLon);               
        }

    public String getDay(String dateStr){

        Date date = null;
        String day=null;

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");

            day = simpleDateFormat.format(date).toUpperCase();
            if(day.equalsIgnoreCase("SUNDAY"))
            {
                day = "রবিবার";
            }
            if(day.equalsIgnoreCase("MONDAY"))
            {

                day = "সোমবার";
            }
            if(day.equalsIgnoreCase("TUESDAY"))
            {

                day = "মঙ্গলবার";
            }
            if(day.equalsIgnoreCase("WEDNESDAY"))
            {
                day = "বুধবার";
            }
            if(day.equalsIgnoreCase("THURSDAY"))
            {
                day = "বৃহস্পতিবার";
            }
            if(day.equalsIgnoreCase("FRIDAY"))
            {

                day = "শুক্রবার";
            }
            if(day.equalsIgnoreCase("SATURDAY"))
            {
                day = "শনিবার";
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return day;
    }


    public static String GetIfImmunizationisGivenForAParticularDate(Connection c, String HealthId, String imuDate)
    {
        String sql = "select systementrydate FROM immunizationHistory WHERE HealthId = '" + HealthId +"' AND imuDate='" + imuDate + "'";
        String result = c.ReturnSingleValue(sql);
        return result;

    }
	/* /The following method is written by Angsuman to determine wheather the web server is active for
	Server related task. Call this before any server operation.
// You can determine on HTTP return code received. 200 is success.
	 */
	public static boolean IsWebServerAvailable() {
		HttpURLConnection connection = null;

		try {
			URL u = new URL(URL_WebAPI);
			connection = (HttpURLConnection) u.openConnection();
			connection.setRequestMethod("GET");
			int code = connection.getResponseCode();
			System.out.println("" + code);
			if(code == 200)
				return true;
			else
				return false;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
  /*  public static String Getclassfication(Connection c,String classCode)
    {
        String sql = "select classficationName FROM classfication WHERE classficationCode = '" + classCode +"'";
        String result = c.ReturnSingleValue(sql);
        return result;

    }*/
    //pregNo
    private String _PregNo;
    public void setPregNo(String PregNo){this._PregNo = PregNo;}
    public String getPregNo(){ return this._PregNo;}

    //ChildNo
    private String _ChildNo;
    public void setChildNo(String ChildNo){this._ChildNo = ChildNo;}
    public String getChildNo(){ return this._ChildNo;}

    private String _epiSchedulerId;
    public void setepiSchedulerId(String epiSchedulerId){this._epiSchedulerId = epiSchedulerId;}
    public String getepiSchedulerId(){ return this._epiSchedulerId;}

    private String _epiSubBlockId;
    public void setepiSubBlockId(String epiSubBlockId){this._epiSubBlockId = epiSubBlockId;}
    public String getepiSubBlockId(){ return this._epiSubBlockId;}

    private String _epiScheduleDate;
    public void setepiScheduleDate(String epiScheduleDate){this._epiScheduleDate = epiScheduleDate;}
    public String getepiScheduleDate(){ return this._epiScheduleDate;}

    private String _epiCenterName;
    public void setepiCenterName(String epiCenterName){this._epiCenterName = epiCenterName;}
    public String getepiCenterName(){ return this._epiCenterName;}

    private String _epiName;
    public void setepiName(String epiName){this._epiName = epiName;}
    public String getepiName(){ return this._epiName;}


    private String _epiSex;
    public void setepiSex(String epiSex){this._epiSex = epiSex;}
    public String getepiSex(){ return this._epiSex;}

    private String _epimName;
    public void setepimName(String epimName){this._epimName = epimName;}
    public String getepimName(){ return this._epimName;}

    private String _epifName;
    public void setepifName(String epiName){this._epifName = epiName;}
    public String getepifName(){ return this._epifName;}

    private String _DOB;
    public void setDOB(String DOB){this._DOB = DOB;}
    public String getDOB(){ return this._DOB;}

	private String _ELCONo;
	public void setELCONo(String ELCONo){this._ELCONo = ELCONo;}
	public String getELCONo(){ return this._ELCONo;}




    private String _epiCallForm;
    public void setepiCallForm(String epiCallForm){this._epiCallForm = epiCallForm;}
    public String getepiCallForm(){ return this._epiCallForm;}

    private String _AVDate;
    public void setAVDate(String AVDate){this._AVDate = AVDate;}
    public String getAVDate(){ return this._AVDate;}

    private String _AHHNO;
    public void setAHHNO(String AHHNO){this._AHHNO = AHHNO;}
    public String getHHNO(){ return this._AHHNO;}
    private String _AAge;
    public void setAAge(String AAge){this._AAge = AAge;}
    public String getAAge(){ return this._AAge;}

    private String _AgeY;
    public void setAgeY(String AgeY){this._AgeY = AgeY;}
    public String getAgeY(){ return this._AgeY;}

    private String _AgeM;
    public void setAgeM(String AgeM){this._AgeM = AgeM;}
    public String getAgeM(){ return this._AgeM;}

    private String _AgeD;
    public void setAgeD(String AgeD){this._AgeD = AgeD;}
    public String getAgeD(){ return this._AgeD;}

    private String _AName;
    public void setAName(String AName){this._AName = AName;}
    public String getAName(){ return this._AName;}
    private String _AAgeMDY;
    public void setAAgeMDY(String AAgeMDY){this._AAgeMDY = AAgeMDY;}
    public String getAAgeMDY(){ return this._AAgeMDY;}

    private String _ASex;
    public void setASex(String ASex){this._ASex = ASex;}
    public String getASex(){ return this._ASex;}

    private String _AMElco;
    public void setAMElco(String AMElco){this._AMElco = AMElco;}
    public String getAMElco(){ return this._AMElco;}


    private String _AGroup;
    public void setAGroup(String AGroup){this._AGroup = AGroup;}
    public String getAGroup(){ return this._AGroup;}

    private String _AAgeD;
    public void setAAgeD(String AAgeD){this._AAgeD = AAgeD;}
    public String getAAgeD(){ return this._AAgeD;}

    private String _AAgeM;
    public void setAAgeM(String AAgeM){this._AAgeM = AAgeM;}
    public String getAAgeM(){ return this._AAgeM;}

    private String _AAgeY;
    public void setAAgeY(String AAgeY){this._AAgeY = AAgeY;}
    public String getAAgeY(){ return this._AAgeM;}

    private String _GeneratedId;
    public void setGeneratedId(String GeneratedId){this._GeneratedId = GeneratedId;}
    public String getGeneratedId(){ return this._GeneratedId;}


    private String _MotherName;
    public void setMotherName(String MotherName){this._MotherName = MotherName;}
    public String getMotherName(){ return this._MotherName;}

    private String _FatherName;
    public void setFatherName(String FatherName){this._FatherName = FatherName;}
    public String getFatherName(){ return this._FatherName;}

    private String _HusName;
    public void setHusName(String HusName){this._HusName = HusName;}
    public String getHusName(){ return this._HusName;}

   // private String get_HHNo



//    public static List<String> ReadTextFile(String fileName)
//    {
//        List<String> dataList = new ArrayList<String>();
//        BufferedReader br;
//        try {
//            String fileLoc= Global.DatabaseFolder + File.separator +  fileName;
//            File sdcard = Environment.getExternalStorageDirectory();
//            File file = new File(sdcard,fileLoc);
//
//            br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null) {
//                dataList.add(line);
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally{
//            //br.close();
//        }
//
//        return dataList;
//    }
}

