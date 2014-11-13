/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.core.util.sql;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author hermeschang
 */
public class SQLUtil {
    

    public final static String genInsertSQLString(String tableName, Set<String> keys) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbParam = new StringBuilder();
        StringBuilder sbValue = new StringBuilder();

        boolean isFirst = true;
        for (String key : keys) {
            if (isFirst) {
                isFirst = false;
            } else {
                sbParam.append(", ");
                sbValue.append(", ");
            }
            sbParam.append(key);
            sbValue.append("?");
        }
        sb.append("INSERT INTO ");
        sb.append(tableName);
        sb.append("(");
        sb.append(sbParam);
        sb.append(")");
        sb.append(" VALUES ");
        sb.append("(");
        sb.append(sbValue);
        sb.append(")");

        return sb.toString();
    }
    
    public final static String genUpdateSQLString(String tableName, Map<String, Object> changeKeyValues, Map<String, Object> conditionKeyValues) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbChange = new StringBuilder();
        StringBuilder sbCondtion = new StringBuilder();

        boolean isFirst = true;
        // change part
        for (String key : changeKeyValues.keySet()) {
            Object value = changeKeyValues.get(key);
            if (isFirst) {
                isFirst = false;
            } else {
                sbChange.append(", ");
            }
            sbChange.append(key);
            sbChange.append("=");
            sbChange.append("'");
            sbChange.append(value);
            sbChange.append("'");
        }
        
        isFirst = true;
        // condition part
        for (String key : conditionKeyValues.keySet()) {
            Object value = conditionKeyValues.get(key);
            if (isFirst) {
                isFirst = false;
            } else {
                sbCondtion.append(" AND ");
            }
            sbCondtion.append(key);
            sbCondtion.append("=");
            sbCondtion.append("'");
            sbCondtion.append(value);
            sbCondtion.append("'");
        }

        sb.append("UPDATE ");
        sb.append(tableName);
        sb.append(" SET ");
        sb.append(sbChange);
        sb.append(" WHERE ");
        sb.append(sbCondtion);
        return sb.toString();
    }
}
