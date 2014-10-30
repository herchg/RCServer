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
public class Product extends AbsBaseJson {
    
    public static final double _VERSION = 1.0;

    @Since(1.0) @SerializedName("product_id")
    public Integer mProductId;
    @Since(1.0) @SerializedName("company_id")
    public int mCompanyId;
    @Since(1.0) @SerializedName("name")
    public String mName;
    @Since(1.0) @SerializedName("name_4_short")
    public String mName4Short;
    @Since(1.0) @SerializedName("description")
    public String mDescription;
    @Since(1.0) @SerializedName("description_4_short")
    public String mDescription4Short;
    @Since(1.0) @SerializedName("product_code")
    public String mProductCode;
    @Since(1.0) @SerializedName("barcode")
    public String mBarcode;
    @Since(1.0) @SerializedName("category_id")
    public int mCategoryId;
    @Since(1.0) @SerializedName("option0")
    public String mOption0;
    @Since(1.0) @SerializedName("option1")
    public String mOption1;
    @Since(1.0) @SerializedName("option2")
    public String mOption2;
    @Since(1.0) @SerializedName("option3")
    public String mOption3;
    @Since(1.0) @SerializedName("option4")
    public String mOption4;
    @Since(1.0) @SerializedName("option5")
    public String mOption5;
    @Since(1.0) @SerializedName("option6")
    public String mOption6;
    @Since(1.0) @SerializedName("option7")
    public String mOption7;
    @Since(1.0) @SerializedName("option8")
    public String mOption8;
    @Since(1.0) @SerializedName("option9")
    public String mOption9;
    @Since(1.0) @SerializedName("status")
    public String mStatus;
    
    public static Product loadFromStream(InputStream in) {
        try {
            String content = FileUtil.readStreamAsString(in, Charset.forName(Constants.DEFAULT_CHARSET));
            return Product.fromJson(content);
        } catch (Exception ex) {
            return new Product();
        }
    }

    public static Product fromJson(String json) {
        return (Product)fromJson(_VERSION, json, Product.class);
    }
    
    public void init() {
        // do nothing
    }
}
