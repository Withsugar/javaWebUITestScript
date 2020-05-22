package com.pku.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
	private static Connection conn = null;

	private static PreparedStatement ps = null;

	private static ResultSet rs = null;
	
	private static Properties config = null;
    
	public static void initConfig(){
		config = new Properties();
		try {
			InputStreamReader fn = new InputStreamReader(new FileInputStream(System.getProperty("user.dir")+"//resources//config//CONFIG.properties"),"UTF-8");
			config.load(fn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * 返回当前数据库连接.
     */
    public static java.sql.Connection getConnection() {
    	initConfig();
    	try{

    		Class.forName(config.getProperty("DBDRIVER"));//注册驱动

    		conn=DriverManager.getConnection(config.getProperty("DBURL"),config.getProperty("DBUSER"),config.getProperty("DBPASSWORD"));//获得连接对象

    		}catch(ClassNotFoundException e){//捕获驱动类无法找到异常

    		e.printStackTrace();

    		}catch(SQLException e){//捕获SQL异常

    		e.printStackTrace();

    		}

    		return conn;
    }
    
  //查询数据

    public ResultSet executeQuery(String sql)throws Exception{

    try{

    conn=getConnection();

    ps=conn.prepareStatement(sql);

    rs=ps.executeQuery(sql);

    return rs;

    }catch(SQLException e){

    throw new SQLException("select data Exception: "+e.getMessage());

    }catch(Exception e){

    throw new Exception("System error: "+e.getMessage());

    }finally {
       closeResources(conn,ps,rs);
    }

    }
    
  //插入数据

    public int executeInsert(String sql)throws Exception{

    int num=0;//计数

    try{

    conn=getConnection();

    ps=conn.prepareStatement(sql);

    num=ps.executeUpdate();

    }catch(SQLException sqle){

    throw new SQLException("insert data Exception: "+sqle.getMessage());

    }finally{
    	closeResources(conn,ps,rs);
    }

    return num;

    }
    
  //删除数据

    public int executeDelete(String sql)throws Exception{

    int num=0;//计数

    try{

    conn=getConnection();

    ps=conn.prepareStatement(sql);

    num=ps.executeUpdate();

    }catch(SQLException sqle){

    throw new SQLException("delete data Exception: "+sqle.getMessage());

    }finally{
    	closeResources(conn,ps,rs);
    }

    return num;

    }

  //修改数据

    public int executeUpdate(String sql)throws Exception{

    int num=0;//计数

    try{

    conn=getConnection();

    ps=conn.prepareStatement(sql);

    num=ps.executeUpdate();

    }catch(SQLException sqle){

    throw new SQLException("update data Exception: "+sqle.getMessage());

    }finally{
    	closeResources(conn,ps,rs);
    }

    return num;

    }
    /** 
     * 释放资源 
     * @param conn 
     * @param pstmt 
     * @param rs 
     */  
    public static void closeResources(Connection conn,PreparedStatement pstmt,ResultSet rs) {  
        if(null != rs) {  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally {  
                if(null != pstmt) {  
                    try {  
                        pstmt.close();  
                    } catch (SQLException e) {  
                        e.printStackTrace();  
                        throw new RuntimeException(e);  
                    } finally {  
                        if(null != conn) {  
                            try {  
                                conn.close();  
                            } catch (SQLException e) {  
                                e.printStackTrace();  
                                throw new RuntimeException(e);  
                            }  
                        }  
                    }  
                }  
            }  
        }  
    }  
}

