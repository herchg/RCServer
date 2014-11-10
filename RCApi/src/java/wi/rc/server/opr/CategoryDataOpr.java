/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.rc.server.opr;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import wi.core.db.DBOperation;
import wi.core.db.DSConn;
import wi.core.util.json.JsonUtil;
import wi.core.util.sql.SQLUtil;
import wi.rc.server.Status;

/**
 *
 * @author 10307905
 */
public class CategoryDataOpr {

    private static String generateSqlQueryString() {

        String sql = "SELECT category_id, parent_category_id, company_id, name, name_4_short, description, description_4_short, \n"
                + " option0, option1, option2, option3, option4, option5, option6, option7, option8, option9, status \n"
                + " FROM `category` ";
        return sql;
    }

    public static Response selectAllCategory(int company_id,String category_id,String category_name) {

        Connection conn = null;
        Response resp;
        
        String sqlString;
        int sqlCount = 1;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            
            sqlString = generateSqlQueryString() + " WHERE company_id = ? ";

            if(category_id != null){ sqlString += " AND category_id = ? ";}
            if(category_name != null){ sqlString += " AND category_name LIKE ? ";}
            
            PreparedStatement pStmt = conn.prepareStatement(sqlString);
            
            pStmt.setInt(sqlCount, company_id);
            sqlCount ++;
            
            if(category_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(category_id));
                sqlCount ++;
            }
           
            if(category_name != null){ 
                pStmt.setString(sqlCount, "%" + category_name + "%" );
                sqlCount ++;
            }
            
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonCategory = JsonUtil.toJsonArray(rs);
                jsonResult.add("category", jsonCategory);
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


    public static Response insertCategory(String jsonString) {

        Connection conn = null;
        Response resp;
        boolean ret = true;

        long category_id = -1;
        JsonObject jsonResult = new JsonObject();

        String sql = null;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapCategory = (Map<String, Object>) map.get("category");

            sql = SQLUtil.genInsertSQLString("`category`", mapCategory.keySet());
            PreparedStatement stmtCategory = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            int count = 1;
            for (String key : mapCategory.keySet()) {
                Object value = mapCategory.get(key);
                stmtCategory.setObject(count, value);
                count++;
            }

            if (stmtCategory.executeUpdate() > 0) {
                // execute success
                // get product_id
                ResultSet rs = stmtCategory.getGeneratedKeys();
                if (rs.next()) {
                    category_id = rs.getLong(1);
                    // gen result
                    jsonResult.addProperty("category_id", category_id);
                    DBOperation.close(rs);
                } else {
                    ret = false;
                }
            } else {
                // execute failure
                ret = false;
            }

            // check ret and commit or rollback
            if (ret) {
                conn.commit();
                resp = Response.status(Response.Status.CREATED).entity(jsonResult.toString()).build();
            } else {
                conn.rollback();
                resp = Response.status(Response.Status.BAD_REQUEST).build();
                DBOperation.close(stmtCategory);
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

    public static Response updateCategory(int companyId, long categoryId, String jsonString) {

        Response resp;

        Connection conn = null;
        boolean ret = true;

        JsonObject jsonResult = new JsonObject();
        String sql = null;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapCategorySet = (Map<String, Object>) map.get("category");
            Map<String, Object> mapCategoryWhere = new LinkedHashMap<String, Object>();

            mapCategoryWhere.put("category_id", categoryId);
            mapCategoryWhere.put("company_id", companyId);

            mapCategorySet.remove("category_id");// 避免Category Set statement裡面有category_id
            mapCategorySet.remove("company_id");

            sql = SQLUtil.genUpdateSQLString("`category`", mapCategorySet, mapCategoryWhere);
            PreparedStatement stmtCategory = conn.prepareStatement(sql);

            if (stmtCategory.executeUpdate() < 0) {
                ret = false;
            }

            jsonResult.addProperty("category_id", categoryId);
            DBOperation.close(stmtCategory);
            
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

    public static Response deleteCategory(int company_id, int category_id) {

        Response resp;
        Connection conn = null;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtCategory = conn.prepareStatement("UPDATE `category` SET  status = ? WHERE company_id = ? AND category_id = ?");
            stmtCategory.setString(1, Status.Deleted.getValue().toString());
            stmtCategory.setLong(2, company_id);
            stmtCategory.setLong(3, category_id);
            if (stmtCategory.executeUpdate() > 0) {
                resp = Response.status(Response.Status.OK).build();
            } else {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            }
            DBOperation.close(stmtCategory);
            
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
