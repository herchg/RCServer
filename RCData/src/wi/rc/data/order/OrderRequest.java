/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.rc.data.order;

import java.util.List;
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
public class OrderRequest extends AbsBaseJson {
    
    private static final double _VERSION = 1.0;

    @Since(1.0) @SerializedName("order")
    public Order mOrder;
    @Since(1.0) @SerializedName("order_detail")
    public List<OrderDetail> mOrderDetail;
 
    public static OrderRequest loadFromStream(InputStream in) {
        try {
            String content = FileUtil.readStreamAsString(in, Charset.forName(Constants.DEFAULT_CHARSET));
            return OrderRequest.fromJson(content);
        } catch (Exception ex) {
            return new OrderRequest();
        }
    }

    public static OrderRequest fromJson(String json) {
        Gson gson = new GsonBuilder().setVersion(_VERSION).create();
        OrderRequest data = gson.fromJson(json, OrderRequest.class);
        data.init();
        return data;
    }
    
    private void init() {
        // do nothing
    }
}
