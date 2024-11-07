package DataSync;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by angsuman on 5/19/2016.
 */
public class DataClass_SQL_Update {

    @SerializedName("method")
    String method;

    @SerializedName("SecutiryCode")
    String SecutiryCode;

    @SerializedName("SQL")
    List<String> SQL;
}
