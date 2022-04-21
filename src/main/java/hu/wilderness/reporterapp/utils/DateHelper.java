package hu.wilderness.reporterapp.utils;

/*
 * https://stackoverflow.com/questions/21162753/jdbc-resultset-i-need-a-getdatetime-but-there-is-only-getdate-and-gettimestamp
 */

import hu.wilderness.reporterapp.service.ParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    @Autowired
    private ParameterService parameterService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static Date getDateTime(ResultSet rs, String time) {
        try {
            Timestamp timestamp = rs.getTimestamp(time);
            if (timestamp != null) {
                return new Date(timestamp.getTime());
            }
        } catch (Exception e) {}
        return null;
    }


    public static Date addigHoursToDate(Date date, int hours){
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.HOUR, hours);
        return date = cl.getTime();
    }

    private Date getPartDate(String dateFromDatabse, int defaultValue) {
        int seconds = parameterService.getIntegerParameter(dateFromDatabse, defaultValue);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, seconds*-1);
        Date result = cal.getTime();
        log.debug("Setting deletion before: "+ result);
        return result;
    }

}