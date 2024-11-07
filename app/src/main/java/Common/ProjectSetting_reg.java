package Common;

/**
 * Created by Nisan on 19/07/2024.
 */
public class ProjectSetting_reg {
    public static final String USER_AGENT_WebAPI = "Mozilla/5.0";
    public static String Organization 	    = "ICDDRB";

    //Database
    public static String DatabaseFolder     = "SCheekLDatabase";
    //public static String DatabaseName     = "FPCSDatabase.db";
   // public static final String ProjectType  = "FWAUnitWise";//VillageWise  FWAUnitWise
   // public static final String ProjectType1 = "HAWardWise"; //VillageWise  Ward Wise

   public static final String APIName      = "gavi_clhub";
   //public static final String APIName      = "fpcs_dev";



    //Document folder:
   // public static final String eCS_Document_Folder = "cs_apps_document";
   // public static final String PROVIDER_TYPE_HA   = "2";
     //public static final String PROVIDER_TYPE_HA  = "4";
     // static final String PROVIDER_TYPE_Man  = "6";
   // private static String HA_SYSTEM  = "ha";
    private static String CS_SYSTEM = "uuw";
    //private static String CS_SYSTEM1 = "SACMO";
    //private static String CS_SYSTEM2 = "FWV";
    //private static String CS_SYSTEM3 = "N_midwife";
    //private static String CS_SYSTEM4 = "CHCP";
   // private static String CS_SYSTEM1 = "sup";
   // private static final String Server_URL_ICDDRB = "http://mchdrhis.icddrb.org:8080";
  //  private static final String Server_URL_ICDDRB = "http://emis.icddrb.org:8080";
    //private static final String Server_URL_SAVE   = "http://mamoni.net:8080";
   // private static final String Server_URL_ICDDRB = "http://172.26.26.10:8080";

    private static final String Server_URL_ICDDRB = "http://pwc.icddrb.org:8080";

    //public static final String Server_URL_ICDDRB ="http://localhost:8084";
    //Need to change for the different System
    //==============================================================================================
   // public static final String NewVersionDate = "23/01/2018";
    public static final String CurrentSystem  = CS_SYSTEM;
    public static final String Server_URL     = Server_URL_ICDDRB;
    //==============================================================================================


    public static String DatabaseName         = "SCheekLDatabase_"+ CurrentSystem +".db";

  //  public static final String URL_WebAPI     = Server_URL + "/" + APIName + "/apidata/DataSync";

 //   public static String NewVersionName       = "ecs_update" + "_"+ CurrentSystem;
  //  public static String UpdatedSystem        = Server_URL + "/" + eCS_Document_Folder + "/" + NewVersionName + ".txt";

  //  public static final String PRSAndServiceModule ="ServiceModule"; //PRS ServiceModule
}