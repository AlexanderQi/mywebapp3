/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

/**
 *
 * @author Qi
 */
public class Mydao {

    private static Context ctx;
    private static DataSource ds;

    public static synchronized void Init(){
        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mydata");
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    private static ResultSet Query(String sql) {
        ResultSet rs = null;
        Connection conn = null;
        Statement stat = null;
        try {
            if (ds == null) {
                Init();
            }
            conn = ds.getConnection();
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return rs;
    }
    
    public static int Update(String sql){
        Connection conn = null;
        Statement stat = null;
        int r=0;
        try {
            if (ds == null) {
                Init();
            }
            conn = ds.getConnection();
            stat = conn.createStatement();
            r = stat.executeUpdate(sql);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return r;
    }

    public static String QueryToJson(String sql) {
        ResultSet rs = Query(sql);
        JSONArray array = new JSONArray();// json数组
        try {
            // 获取列数  
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 遍历ResultSet中的每条数据  
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();
                // 遍历每一列  
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    String value = rs.getString(columnName);
                    jsonObj.put(columnName, value);
                }
                array.add(jsonObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                Statement st = rs.getStatement();
                Connection conn = st.getConnection();
                st.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
       return array.toString();
    }
}
