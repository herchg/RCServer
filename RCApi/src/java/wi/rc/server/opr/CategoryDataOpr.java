/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.rc.server.opr;

import com.google.gson.JsonSyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import javax.ws.rs.core.Response;
import wi.core.db.DSConn;
import wi.rc.data.category.Category;

/**
 *
 * @author 10307905
 */
public class CategoryDataOpr {

    public class DBInfo {

        public class CategoryInfo {

            public final static String FIELD_NAME_CATEGORY_ID = "category_id";
            public final static String FIELD_NAME_COMPANY_ID = "company_id";
            public final static String FIELD_NAME_DISCRIPTION = "description";
            public final static String FIELD_NAME_DISCRIPTION_4_SHORT = "description_4_short";
            public final static String FIELD_NAME_NAME = "name";
            public final static String FIELD_NAME_NAME_4_SHORT = "name_4_short";
            public final static String FIELD_NAME_OPTION_0 = "option0";
            public final static String FIELD_NAME_OPTION_1 = "optio1";
            public final static String FIELD_NAME_OPTION_2 = "option2";
            public final static String FIELD_NAME_OPTION_3 = "option3";
            public final static String FIELD_NAME_OPTION_4 = "option4";
            public final static String FIELD_NAME_OPTION_5 = "option5";
            public final static String FIELD_NAME_OPTION_6 = "option6";
            public final static String FIELD_NAME_OPTION_7 = "option7";
            public final static String FIELD_NAME_OPTION_8 = "option8";
            public final static String FIELD_NAME_OPTION_9 = "option9";
            public final static String FIELD_NAME_PARENT_CATEGORY_ID = "parent_category_id";
            public final static String FIELD_NAME_STATUS = "status";

            public final static String SQL_CATEGORY_SELECT
                    = "SELECT category_id, company_id, description, description_4_short, name, name_4_short, "
                    + "option0, option1, option2, option3, option4, option5, option6, option7, option8, option9, parent_category_id, status FROM rc.category";

            public final static String SQL_CATEGORY_INSERT
                    = "INSERT INTO rc.category (category_id, company_id, description, description_4_short, name, name_4_short, option0, option1, option2, option3, option4, option5, option6, option7, option8, option9, parent_category_id, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            public final static String SQL_CATEGORY_DELETE
                    = "DELETE FROM rc.category WHERE category_id = ?";
        }
    }

    public static Response selectCategory() {

        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtOrder = conn.prepareStatement(CategoryDataOpr.DBInfo.CategoryInfo.SQL_CATEGORY_SELECT);
            ResultSet rs = stmtOrder.executeQuery();

            if (rs.next()) {

                Category category = new Category();

                category.mCatrgoryId = rs.getLong(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_CATEGORY_ID);
                category.mCompanyId = rs.getInt(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_COMPANY_ID);
                category.mDiscription = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_DISCRIPTION);
                category.mDescription4Short = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_DISCRIPTION_4_SHORT);
                category.mName = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_NAME);
                category.mName4Short = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_NAME_4_SHORT);
                category.mOption0 = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_OPTION_0);
                category.mOption1 = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_OPTION_1);
                category.mOption2 = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_OPTION_2);
                category.mOption3 = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_OPTION_3);
                category.mOption4 = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_OPTION_4);
                category.mOption5 = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_OPTION_5);
                category.mOption6 = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_OPTION_6);
                category.mOption7 = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_OPTION_7);
                category.mOption8 = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_OPTION_8);
                category.mOption9 = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_OPTION_9);
                category.mParentCategoryId = rs.getLong(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_PARENT_CATEGORY_ID);
                category.mStatus = rs.getString(CategoryDataOpr.DBInfo.CategoryInfo.FIELD_NAME_STATUS);

                resp = Response.status(Response.Status.CREATED).entity(category.toJson(Category._VERSION)).build();
            } else {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }
        return resp;
    }

    public static Response insertCategory(String jsonCategorySet) {

        Category category;

        Connection conn = null;
        Response resp;
        boolean ret = true;
        try {
            category = Category.fromJson(Category._VERSION, jsonCategorySet);
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);
            PreparedStatement stmtCategory = conn.prepareStatement(CategoryDataOpr.DBInfo.CategoryInfo.SQL_CATEGORY_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);

            stmtCategory.setInt(1, category.mCompanyId);
            if (category.mDiscription == null) {
                stmtCategory.setNull(2, Types.VARCHAR);
            } else {
                stmtCategory.setString(2, category.mDiscription);
            }
            if (category.mDescription4Short == null) {
                stmtCategory.setNull(3, Types.VARCHAR);
            } else {
                stmtCategory.setString(3, category.mDescription4Short);
            }
            if (category.mName4Short == null) {
                stmtCategory.setNull(4, Types.VARCHAR);
            } else {
                stmtCategory.setString(4, category.mName);
            }
            if (category.mName4Short == null) {
                stmtCategory.setNull(5, Types.VARCHAR);
            } else {
                stmtCategory.setString(5, category.mName4Short);
            }
            if (category.mOption0 == null) {
                stmtCategory.setNull(6, Types.VARCHAR);
            } else {
                stmtCategory.setString(6, category.mOption0);
            }
            if (category.mOption1 == null) {
                stmtCategory.setNull(7, Types.VARCHAR);
            } else {
                stmtCategory.setString(7, category.mOption1);
            }
            if (category.mOption2 == null) {
                stmtCategory.setNull(8, Types.VARCHAR);
            } else {
                stmtCategory.setString(8, category.mOption2);
            }
            if (category.mOption3 == null) {
                stmtCategory.setNull(9, Types.VARCHAR);
            } else {
                stmtCategory.setString(9, category.mOption3);
            }
            if (category.mOption4 == null) {
                stmtCategory.setNull(10, Types.VARCHAR);
            } else {
                stmtCategory.setString(10, category.mOption4);
            }
            if (category.mOption5 == null) {
                stmtCategory.setNull(11, Types.VARCHAR);
            } else {
                stmtCategory.setString(11, category.mOption5);
            }
            if (category.mOption6 == null) {
                stmtCategory.setNull(12, Types.VARCHAR);
            } else {
                stmtCategory.setString(12, category.mOption6);
            }
            if (category.mOption7 == null) {
                stmtCategory.setNull(13, Types.VARCHAR);
            } else {
                stmtCategory.setString(13, category.mOption7);
            }
            if (category.mOption8 == null) {
                stmtCategory.setNull(14, Types.VARCHAR);
            } else {
                stmtCategory.setString(14, category.mOption8);
            }
            if (category.mOption9 == null) {
                stmtCategory.setNull(15, Types.VARCHAR);
            } else {
                stmtCategory.setString(15, category.mOption9);
            }
            stmtCategory.setLong(16, category.mParentCategoryId);
            if (category.mOption0 == null) {
                stmtCategory.setNull(17, Types.VARCHAR);
            } else {
                stmtCategory.setString(17, category.mOption0);
            }

            if (stmtCategory.executeUpdate() > 0) {
                // execute success
                ResultSet rs = stmtCategory.getGeneratedKeys();
                if (rs.next()) {
                    category.mCatrgoryId = rs.getLong(1);
                }
            } else {
                // execute failure
                ret = false;
            }
            if (ret) {
                conn.commit();
                resp = Response.status(Response.Status.CREATED).entity(category.toJson(Category._VERSION)).build();
            } else {
                conn.rollback();
                resp = Response.status(Response.Status.BAD_REQUEST).build();
            }

        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }
        return resp;
    }

    public static Response updateCategory(int category_id, String jsonCategorySet) {

        Category category;

        Connection conn = null;
        Response resp;
        boolean ret = true;
        String sql = "UPDATE rc.category SET";

        try {
            category = Category.fromJson(Category._VERSION, jsonCategorySet);

            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            if (category.mCatrgoryId != null) {
                sql += " category_id = " + category.mCatrgoryId;
            }
            if (category.mCompanyId != -1) {
                sql += " company_id = " + category.mCatrgoryId;
            }
            if (category.mDiscription != null) {
                sql += " description = " + category.mCatrgoryId;
            }
            if (category.mDescription4Short != null) {
                sql += " description_4_short = " + category.mCatrgoryId;
            }
            if (category.mName != null) {
                sql += " name = " + category.mCatrgoryId;
            }
            if (category.mName4Short != null) {
                sql += " name_4_short = " + category.mCatrgoryId;
            }
            if (category.mOption0 != null) {
                sql += " option0 = " + category.mOption0;
            }
            if (category.mOption1 != null) {
                sql += " option1 = " + category.mOption1;
            }
            if (category.mOption2 != null) {
                sql += " option2 = " + category.mOption2;
            }
            if (category.mOption3 != null) {
                sql += " option3 = " + category.mOption3;
            }
            if (category.mOption4 != null) {
                sql += " option04 = " + category.mOption4;
            }
            if (category.mOption5 != null) {
                sql += " option05 = " + category.mOption5;
            }
            if (category.mOption6 != null) {
                sql += " option06 = " + category.mOption6;
            }
            if (category.mOption7 != null) {
                sql += " option07= " + category.mOption7;
            }
            if (category.mOption8 != null) {
                sql += " option08 = " + category.mOption8;
            }
            if (category.mOption9 != null) {
                sql += " option09 = " + category.mOption9;
            }
            if (category.mParentCategoryId != null) {
                sql += " parent_category_id = " + category.mParentCategoryId;
            }
            if (category.mStatus != null) {
                sql += " status = " + category.mStatus;
            }

            sql += " WHERE id = " + category_id;
            PreparedStatement stmtCategory = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            if (stmtCategory.executeUpdate() < 0) {
                ret = false;
            }
            if (ret) {
                conn.commit();
                resp = Response.status(Response.Status.CREATED).entity(category.toJson(Category._VERSION)).build();
            } else {
                conn.rollback();
                resp = Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }

        return resp;
    }

    public static Response deleteCategory(int categoryId) {

        Connection conn = null;
        Response resp;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtCategory = conn.prepareStatement(CategoryDataOpr.DBInfo.CategoryInfo.SQL_CATEGORY_DELETE);
            // "DELETE FROM rc.category WHERE category_id = ?";
            stmtCategory.setLong(1, categoryId);
            stmtCategory.execute();
            resp = Response.status(Response.Status.CREATED).entity(null).build();

        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }
        return resp;
    }
}
