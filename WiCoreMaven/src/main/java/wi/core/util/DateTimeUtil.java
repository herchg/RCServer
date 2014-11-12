/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.core.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author hermeschang
 */
public class DateTimeUtil {
    
    public final static String DEFAULT_DATETIME_FORMAT  = "yyyy-MM-dd HH:mm:ss";
    public final static String DEFAULT_DATE_FORMAT      = "yyyy-MM-dd";
    public final static String DEFAULT_TIME_FORMAT      = "HH:mm:ss";
    
    public static String format(Date date, String format) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception ex) { }
        
        return null;
    }

    public static Date parse(String date, String format) {
        try {
            return new Date(new SimpleDateFormat(format).parse(date).getTime());
        } catch (Exception ex) {}
        
        return null;
    }

}
