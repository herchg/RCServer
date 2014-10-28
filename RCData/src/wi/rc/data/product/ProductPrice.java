/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.rc.data.product;

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
public class ProductPrice extends AbsBaseJson {
    
    private static final double _VERSION = 1.0;

    @Since(1.0) @SerializedName("product_id")
    public int mProductId;
    @Since(1.0) @SerializedName("ncode")
    public int mNcode;
    @Since(1.0) @SerializedName("price")
    public int mPrice;
    @Since(1.0) @SerializedName("amount")
    public int mAmount;
    @Since(1.0) @SerializedName("status")
    public Character mStatus;

    public static ProductPrice loadFromStream(InputStream in) {
        try {
            String content = FileUtil.readStreamAsString(in, Charset.forName(Constants.DEFAULT_CHARSET));
            return ProductPrice.fromJson(content);
        } catch (Exception ex) {
            return new ProductPrice();
        }
    }

    public static ProductPrice fromJson(String json) {
        Gson gson = new GsonBuilder().setVersion(_VERSION).create();
        ProductPrice data = gson.fromJson(json, ProductPrice.class);
        data.init();
        return data;
    }
    
    private void init() {
        // do nothing
    }
}
