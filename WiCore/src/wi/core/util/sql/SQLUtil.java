/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.core.util.sql;

import java.util.Set;

/**
 *
 * @author hermeschang
 */
public class SQLUtil {
    public final static String genInsertSQLString(Set<String> keys) {
        StringBuilder sbOrder = new StringBuilder();
        StringBuilder sbOrderParam = new StringBuilder();
        StringBuilder sbOrderValue = new StringBuilder();

        boolean isFirst = true;
        for (String key : keys) {
            if (isFirst) {
                isFirst = false;
            } else {
                sbOrderParam.append(", ");
                sbOrderValue.append(", ");
            }
            sbOrderParam.append(key);
            sbOrderValue.append("?");
        }
        sbOrder.append("INSERT INTO order");
        sbOrder.append("(");
        sbOrder.append(sbOrderParam);
        sbOrder.append(")");
        sbOrder.append(" VALUES ");
        sbOrder.append("(");
        sbOrder.append(sbOrderValue);
        sbOrder.append(")");

        return sbOrder.toString();
    }
}
