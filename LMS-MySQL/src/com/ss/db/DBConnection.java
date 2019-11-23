package com.ss.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	static Connection con = null;

	public static Connection getConnection() {
		if (con != null)
			return con;

		return getConnection("library", "root", "smoothstack");
	}

	private static Connection getConnection(String db_name, String user_name, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + db_name + "?serverTimezone=UTC",
					user_name, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}
	
	 public HashMap<Integer,List<String>> readData(String tableName) {
		  HashMap<Integer,List<String>> retData = new HashMap<>();
	      try{
	    	  con = getConnection();
	          Statement st = con.createStatement();
	          String query = "select * from " + tableName;
	          ResultSet rs = st.executeQuery(query);
	          ResultSetMetaData rdms = rs.getMetaData();
	          int colNumber = rdms.getColumnCount();
	          int Key=0;
	          while(rs.next()) {
	        	  List<String> columnData = new ArrayList<>();
	        	  for(int i=1;i<=colNumber;i++){
	            	  columnData.add(rs.getString(i));
	              }
	        	  retData.put(++Key, columnData);
	          }
	          
	      }catch(Exception e){
	          e.printStackTrace();
	      }
	      return retData;
	  }
	  
	  public void updateTable(String tableName,String columnName,int id,String newValue) {
		  try {
			  con = getConnection();
			Statement st = con.createStatement();
			String query = "update "+tableName+" set "+columnName+"='"+newValue+"'\n" + 
					"where branchId="+id+";" ;
			st.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  
	  }
	  
	  
}
