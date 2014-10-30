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

import wi.core.Constants;
import wi.core.util.FileUtil;
import wi.core.util.json.AbsBaseJson;

/**
 *
 * @author hermeschang
 */
public class OrderDetail extends AbsBaseJson {
    
    public static final double _VERSION = 1.0;

    @Since(1.0) @SerializedName("order_id")
    public long mOrderId;
    @Since(1.0) @SerializedName("product_id")
    public int mProductId;
    @Since(1.0) @SerializedName("price")
    public int mPrice;
    @Since(1.0) @SerializedName("amount")
    public int mAmount;
    @Since(1.0) @SerializedName("total_amount")
    public int mTotalAmount;

    public static OrderDetail loadFromStream(InputStream in) {
        try {
            String content = FileUtil.readStreamAsString(in, Charset.forName(Constants.DEFAULT_CHARSET));
            return OrderDetail.fromJson(content);
        } catch (Exception ex) {
            return new OrderDetail();
        }
    }

    public static OrderDetail fromJson(String json) {
        return (OrderDetail) fromJson(_VERSION, json, OrderDetail.class);
    }
    
    public void init() {
        // do nothing
    }
}
