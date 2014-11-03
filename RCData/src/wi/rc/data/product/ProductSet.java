/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.rc.data.product;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import wi.core.Constants;
import wi.core.util.FileUtil;
import wi.core.util.json.AbsBaseJson;
import static wi.core.util.json.AbsBaseJson.fromJson;
import wi.rc.data.category.Category;
import wi.rc.data.order.Order;

/**
 *
 * @author 10307905
 */
public class ProductSet extends AbsBaseJson{
    
    public static final double _VERSION = 1.0;

    @Since(1.0) @SerializedName("product")
    public Product mProducr;
    @Since(1.0) @SerializedName("product_price")
    public List<ProductPrice> mProductPrice;
    @Since(1.0) @SerializedName("product_category")
    public List<Category> mProductCategory;
 
    public static ProductSet loadFromStream(InputStream in) {
        try {
            String content = FileUtil.readStreamAsString(in, Charset.forName(Constants.DEFAULT_CHARSET));
            return ProductSet.fromJson(_VERSION, content);
        } catch (Exception ex) {
            return new ProductSet();
        }
    }

    public static ProductSet fromJson(double version, String json) {
        return (ProductSet) fromJson(version, json, ProductSet.class);
    }
    
    public void init() {
        // do nothing
    }
}
