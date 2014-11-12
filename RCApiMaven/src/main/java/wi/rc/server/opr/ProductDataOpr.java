/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.rc.server.opr;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.net.URLDecoder;
import javax.ws.rs.core.Response;
import wi.core.db.DBOperation;
import wi.core.db.DSConn;
import wi.core.util.json.JsonUtil;
import wi.core.util.sql.SQLUtil;
import wi.rc.server.Status;
/**
 *
 * @author Lucas
 */
public class ProductDataOpr {
    
    private static String generateSqlQueryString() {

        String sql = "SELECT p.product_id AS product_id , c.company_id AS company_id, c.name AS company_name,p.name AS name, \n" 
                    + " p.name_4_short AS name_4_short  ,p.description AS description, p.description_4_short AS description_4_short, p.product_code AS product_code, \n" 
                    + " p.barcode AS barcode, p.category_id AS category_id,ca.name AS category_name, p.option0 AS option0, p.option1 AS option1,p.option2 AS option2, \n" 
                    + " p.option3 AS option3, p.option4 AS option4, p.option5 AS option5,p.option6 AS option6, p.option7 AS option7,p.option8 AS option8,p.option9 AS option9, \n" 
                    + " p.status AS status \n" 
                    + " FROM `product` AS p \n" 
                    + " LEFT JOIN `category` AS ca ON p.category_id = ca.category_id \n"
                    + " ,company AS c \n"
                    + " WHERE p.company_id = c.company_id \n";
        
        return sql;
    }
    
    private static String generateSqlQueryDetailString() {

        String sql = "SELECT ncode, price, amount, status FROM `product_price` WHERE product_id = ?";
        
        return sql;
    }
    
    public static Response selectAllProduct(int company_id,String product_id,String category_id,String product_name , String status) {
        
        Connection conn = null;
        Response resp;
        
        String sqlString;
        int sqlCount = 1;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);           
            sqlString = generateSqlQueryString() + " AND p.company_id = ? ";
            
            //check input to add sql query string
            if(product_id != null){ sqlString += " AND p.product_id = ? ";}
            if(product_name != null){ sqlString += " AND p.name LIKE ? ";}
            if(category_id != null){ sqlString += " AND p.category_id = ? ";}
            if(status != null){ sqlString += " AND p.status = ? ";}
            
            //DESC
            sqlString += " ORDER BY p.product_id DESC";
            
            PreparedStatement pStmt = conn.prepareStatement(sqlString);
            
            pStmt.setInt(sqlCount, company_id);
            sqlCount ++;
            
            if(product_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(product_id));
                sqlCount ++;
            }
            
            if(product_name != null){ 
                pStmt.setString(sqlCount, "%" + URLDecoder.decode(product_name, "UTF-8") + "%");
                sqlCount ++;
            }
            
            if(category_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(category_id));
                sqlCount ++;
            }
            
            if(status != null){ 
                pStmt.setString(sqlCount, status);
                sqlCount ++;
            }

            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonArray jsonResult = new JsonArray();
                while (rs.next()) {
                    int productId = rs.getInt("product_id");

                    // back because next
                    rs.previous();

                    JsonObject jsonRow = new JsonObject();
                    JsonElement jsonProduct = JsonUtil.toJsonElement(rs);
                    jsonRow.add("product", jsonProduct);

                    if (productId > 0 ) {
                        PreparedStatement stmtProductPrice = conn.prepareStatement(generateSqlQueryDetailString());
                        stmtProductPrice.setLong(1, productId);
                        ResultSet rsProductPrice = stmtProductPrice.executeQuery();
                        JsonElement elementProductPrice = JsonUtil.toJsonArray(rsProductPrice);
                        jsonRow.add("product_price", elementProductPrice);
                        
                        DBOperation.close(stmtProductPrice,rsProductPrice);
                    }
                    jsonResult.add(jsonRow);
                }
                resp = Response.status(Response.Status.OK).entity(jsonResult.toString()).build();
            }
            
            DBOperation.close(pStmt,rs);
            
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
        }
        
        
        return resp;
    }
    
    
    public static Response insertProduct(String jsonString) {

        Connection conn = null;
        Response resp;
        boolean ret = true;

        long product_id = -1;
        JsonObject jsonResult = new JsonObject();

        String sql = null;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapProduct = (Map<String, Object>) map.get("product");

            sql = SQLUtil.genInsertSQLString("`product`", mapProduct.keySet());
            PreparedStatement stmtProduct = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            int count = 1;
            for (String key : mapProduct.keySet()) {
                Object value = mapProduct.get(key);
                stmtProduct.setObject(count, value);
                count++;
            }

            if (stmtProduct.executeUpdate() > 0) {
                // execute success
                // get product_id
                ResultSet rs = stmtProduct.getGeneratedKeys();
                if (rs.next()) {
                    product_id = rs.getLong(1);
                }

                PreparedStatement stmtProductPrice = null;
                Map<String, Object> mapProductPrice = (Map<String, Object>) map.get("product_price");
                // add product_id
                mapProductPrice.put("product_id", product_id);
                // prepare sql and statement
                if (stmtProductPrice == null) {
                    sql = SQLUtil.genInsertSQLString("`product_price`", mapProductPrice.keySet());
                    stmtProductPrice = conn.prepareStatement(sql);
                }
                count = 1;
                for (String key : mapProductPrice.keySet()) {
                    Object value = mapProductPrice.get(key);
                    stmtProductPrice.setObject(count, value);
                    count++;
                }
                if (stmtProductPrice.executeUpdate() < 0) {
                    ret = false;
                }
                // gen result
                jsonResult.addProperty("product_id", product_id);

                DBOperation.close(stmtProductPrice);
                DBOperation.close(rs);
            } else {
                // execute failure
                ret = false;
            }
            DBOperation.close(stmtProduct);  

            // check ret and commit or rollback
            if (ret) {
                conn.commit();
                resp = Response.status(Response.Status.CREATED).entity(jsonResult.toString()).build();
            } else {
                conn.rollback();
                resp = Response.status(Response.Status.BAD_REQUEST).build();
                
            }
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
        }

        return resp;
    }
    
    public static Response updateProduct(int company_id ,int product_id , String jsonString) {
        Response resp;

        Connection conn = null;
        boolean ret = true;

        JsonObject jsonResult = new JsonObject();
        String sql = null;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapProductSet = (Map<String, Object>) map.get("product");
            Map<String, Object> mapOProductWhere = new LinkedHashMap<String, Object>();

            mapOProductWhere.put("company_id", company_id);
            mapOProductWhere.put("product_id", product_id);

            mapProductSet.remove("company_id");// 避免Order Set statement裡面有company_id
            mapProductSet.remove("product_id");// 避免Order Set statement裡面有product_id

            sql = SQLUtil.genUpdateSQLString("`product`", mapProductSet, mapOProductWhere);
            PreparedStatement stmtOrder = conn.prepareStatement(sql);

            if (stmtOrder.executeUpdate() > 0) {
                // execute success
                //
                // update product price , unfinished
                //
                Map<String, Object> mapProductPriceSet = (Map<String, Object>) map.get("product_price");

                // add order_id
                Map<String, Object> mapProductPriceWhere = new LinkedHashMap<String, Object>();

                mapProductPriceWhere.put("product_id", product_id);

                mapProductPriceSet.remove("product_id");// 避免OrderDetail set statement裡面有product_id

                // prepare sql and statement
                sql = SQLUtil.genUpdateSQLString("`product_price`", mapProductPriceSet, mapProductPriceWhere);
                PreparedStatement stmtOrderDetail = conn.prepareStatement(sql);
                if (stmtOrderDetail.executeUpdate() < 0) {
                    ret = false;
                }
                
                // gen result
                jsonResult.addProperty("product_id", product_id);
                
                DBOperation.close(stmtOrderDetail);
            } else {
                // execute failure
                ret = false;
            }

            DBOperation.close(stmtOrder);
            
            // check ret and commit or rollback
            if (ret) {
                resp = Response.status(Response.Status.OK).entity(jsonResult.toString()).build();
            } else {
                resp = Response.status(Response.Status.BAD_REQUEST).entity(sql).build();
            }
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
        }

        return resp;
    }
    
    public static Response deleteProduct(int company_id,int product_id) {
        
        Response resp;
        Connection conn = null;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtProduct = conn.prepareStatement("UPDATE `product` SET  status = ? WHERE company_id = ? AND product_id = ?");
            stmtProduct.setString(1, Status.Deleted.getValue().toString());
            stmtProduct.setLong(2, company_id);
            stmtProduct.setLong(3, product_id);
            if (stmtProduct.executeUpdate() > 0) {
                resp = Response.status(Response.Status.OK).build();
            } else {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            }
            
            DBOperation.close(stmtProduct);
            
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
        }

        return resp;
    }
}
