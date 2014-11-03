/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.rc.data.order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Date;

import wi.core.Constants;
import wi.core.util.FileUtil;
import wi.core.util.json.AbsBaseJson;

/**
 *
 * @author hermeschang
 */
public class Order extends AbsBaseJson {

    public static final double _VERSION = 1.0;

    @Since(1.0) @SerializedName("order_id")
    public Long mOrderId;
    @Since(1.0) @SerializedName("customer_id")
    public int mCustomerId;
    @Since(1.0) @SerializedName("company_id")
    public int mCompanyId;
    @Since(1.0) @SerializedName("store_id")
    public int mStoreId;
    @Since(1.0) @SerializedName("pos_id")
    public int mPosId;
    @Since(1.0) @SerializedName("employee_id")
    public int mEmployeeId;
    @Since(1.0) @SerializedName("ncode")
    public String mNcode;
    @Since(1.0) @SerializedName("total_amount")
    public int mTotalAmount;
    @Since(1.0) @SerializedName("order_datetime")
    public java.sql.Date mOrderDatetime;
//  @Since(1.0) @SerializedName("log_datetime")
//  public transient java.sql.Date mLogDatetime;
    @Since(1.0) @SerializedName("status")
    public String mStatus;
    @Since(1.0) @SerializedName("pos_order_id")
    public String mPosOrderId;
    @Since(1.0) @SerializedName("memo")
    public String mMemo;

    public static Order loadFromStream(InputStream in) {
        try {
            String content = FileUtil.readStreamAsString(in, Charset.forName(Constants.DEFAULT_CHARSET));
            return Order.fromJson(content);
        } catch (Exception ex) {
            return new Order();
        }
    }

    public static Order fromJson(String json) {
        return (Order) fromJson(_VERSION, json, Order.class);
    }
    
    public void init() {
        // do nothing
    }
}
