package com;

import java.sql.*;

public class ConnectionModel {

	

		public  Connection connect() {
			
			String dbDriver = "com.mysql.jdbc.Driver";
	        String dbURL = "jdbc:mysql://localhost:3306/";
	        String dbName = "ConnectionService";
	        String dbUsername = "root";
	        String dbPassword = "";
			Connection conn = null;
				
			try {
				Class.forName("com.mysql.jdbc.Driver");
				//DB connection details
				conn = DriverManager.getConnection(dbURL+dbName,dbUsername,dbPassword);
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			return conn;
		}
	

    
    
    //method to insert data
    public String insertConnection(String connectionID, String customerID,String Units, String status, String type) {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "INSERT INTO Connection (connectionID,customerID,status,type,units) VALUES (?,?,?,?,?)";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setString(1, connectionID);
        	preparedStatement.setString(2, customerID);
        	preparedStatement.setString(3, status);
        	preparedStatement.setString(4, type);
        	preparedStatement.setString(4, Units);
        	
        	
        	//execute the SQL statement
        	preparedStatement.execute();
        	conn.close();

			String newConnection = readConnections(); 
			Output = "{\"status\":\"success\", \"data\": \"" + newConnection + "\"}";
        	
    	} catch(Exception e) {
			Output = "{\"status\":\"error\", \"data\": \"Failed to insert the Connection\"}";
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    //method to update data
    public String updateConnection(String connectionID, String units, String status) {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "UPDATE connection SET units = ?,status = ? WHERE connetionID = ?";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setInt(3, Integer.parseInt(connectionID));
        	preparedStatement.setString(1, units);
        	preparedStatement.setString(2, status);
     
        	
        	
        	//execute the SQL statement
        	preparedStatement.executeUpdate();
        	conn.close();
        	
        	String newConnection = readConnections(); 
      		Output = "{\"status\":\"success\", \"data\": \"" + newConnection + "\"}";
        	
    	} catch(Exception e) {
    		Output = "{\"status\":\"error\", \"data\":\"Failed to update the connection.\"}"; 
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    
    //method to read data
    public String readConnections() {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "SELECT * FROM connection";
        	
        	//executing the SQL query
        	Statement statement = conn.createStatement();
        	ResultSet resultSet = statement.executeQuery(query);
        	
        	// Prepare the HTML table to be displayed
    		Output = "<table border='1'><tr><th>Connection ID</th>" +"<th>Customer ID</th><th>Units</th>"
    		+ "<th>type</th>"+ "<th>status</th>"
    		+ "<th>Update</th><th>Remove</th></tr>";
        	
        	while(resultSet.next()) {
        		String connectionID = Integer.toString(resultSet.getInt("connetionID"));
        		String customerID = resultSet.getString("customerID");
        		String units = resultSet.getString("Units");
        		String status = resultSet.getString("status");
        		String type = resultSet.getString("type");
        		
        		// Add a row into the HTML table
        		Output += "<tr><td>" + connectionID + "</td>"; 
        		Output += "<td>" + customerID + "</td>"; 
        		Output += "<td>" + units + "</td>"; 
        		Output += "<td>" + status + "</td>";
        		Output += "<td>" + type + "</td>";
        		
        		// buttons
        		Output += "<td>"
						+ "<input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-sm btn-secondary' data-itemid='" + connectionID + "'>"
						+ "</td>" 
        				+ "<td>"
						+ "<input name='btnRemove' type='button' value='Remove' class='btn btn-sm btn-danger btnRemove' data-itemid='" + connectionID + "'>"
						+ "</td></tr>";
        	}

        	conn.close();
        	
        	// Complete the HTML table
        	Output += "</table>";
        	
    	} catch(Exception e) {
    		Output = "Failed to read the connections";
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    //method to delete data
    public String deleteConnection(String connectionID) {
    	String Output = "";
    	Connection conn = connect();
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "DELETE FROM connection WHERE connectionID = ?";
        	
        	//binding data to the SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setInt(1, Integer.parseInt(connectionID));
        	
        	//executing the SQL statement
        	preparedStatement.execute();
        	conn.close();
        	
        	String newConnection = readConnections(); 
      		Output = "{\"status\":\"success\", \"data\": \"" + newConnection + "\"}"; 
        	
    	} catch(Exception e) {
			Output = "{\"status\":\"error\", \"data\":\"Failed to delete the connection.\"}";
    		System.err.println(e.getMessage());
    	}
    	return Output;
    }
}