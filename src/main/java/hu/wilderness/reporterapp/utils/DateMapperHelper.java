package hu.wilderness.reporterapp.utils;

/*
 * https://stackoverflow.com/questions/21162753/jdbc-resultset-i-need-a-getdatetime-but-there-is-only-getdate-and-gettimestamp
 */

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

public class DateMapperHelper {
    public static Date getDateTime(ResultSet rs, String time) {
        try {
            Timestamp timestamp = rs.getTimestamp(time);
            if (timestamp != null) {
                return new Date(timestamp.getTime());
            }
        } catch (Exception e) {}
        return null;
    }
}