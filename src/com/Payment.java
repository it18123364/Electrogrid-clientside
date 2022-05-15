package com;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

//import model.table;

public class Payment {
	
	private Connection connect() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// this sample 1
	
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/payments?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root", "");
			
			//For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertPayment(String customerName, String billId, String cardNo, String cvv, String expiredDate, String payAmount)
	{ 
		 String output = ""; 
		try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 { 
		 return "Error while connecting to the database for inserting"; 
		 } 
		 
		 // create a prepared statement
		 String query = " insert into payment (`pID`,`customerName`,`billId`,`cardNo`,`cvv`,`expiredDate`,`payAmount`)"+" values (?, ?, ?, ?, ?, ?, ?)"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 // binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, customerName);
		 preparedStmt.setString(3, billId);
		 preparedStmt.setString(4, cardNo);
		 preparedStmt.setString(5, cvv);
		 preparedStmt.setString(6, expiredDate);
		 preparedStmt.setDouble(7, Double.parseDouble(payAmount));
		 
		 //execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 
		 String newPayments = readPayments(); 
		 output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
		  
		 } 
		catch (Exception e) 
		 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while inserting the payments.\"}"; 
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
	}
	
	public String readPayments()
	{ 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 { 
		 return "Error while connecting to the database for reading."; 
		 } 
		 
		 // Prepare the html table to be displayed
		 output = "<table id='paytable' class='table table-bordered table-hover' cellpadding='0' cellspacing='0' width='100%'><thead class='thead-dark'><tr><th>Customer Name</th><th>Bill ID</th><th>Card No</th><th>CVV</th><th>Expired Date</th><th>Paid Amount(Rs.)</th><th>Paid Date & Time</th><th>Update</th><th>Remove</th></tr></thead>"; 
		 String query = "select * from payment"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query);
		 
		 // iterate through the rows in the result set
		 while (rs.next()) 
		 { 
		 String pID = Integer.toString(rs.getInt("pID"));
		 String customerName = rs.getString("customerName");
		 String billId = rs.getString("billId");
		 String cardNo = rs.getString("cardNo");
		 String cvv = rs.getString("cvv");
		 String expiredDate = rs.getString("expiredDate");
		 String payAmount = Double.toString(rs.getDouble("payAmount"));
		 String payDate =rs.getString("payDate");
		 
		 // Add a row into the html table 
		 output += "<tbody><tr><td>" + customerName + "</td>";
		 output += "<td>" + billId + "</td>";
		 output += "<td>" + cardNo + "</td>";
		 output += "<td>" + cvv + "</td>";
		 output += "<td>" + expiredDate + "</td>";
		 output += "<td>" + payAmount + "</td>";
		 output += "<td>" + payDate + "</td>";
		 
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-success' data-paymentid='" + pID +"'></td>"
				 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-paymentid='" + pID +"'></td></tr></tbody>"; 
		 } 
		 con.close(); 
		 
		 // Complete the html table
		 output += "</table>"; 
		 } 
		catch (Exception e) 
		 { 
		 output = "Error while reading the payments."; 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}
	
	public String updatePayment(String pID, String customerName, String billId, String cardNo, String cvv, String expiredDate, String payAmount) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE payment SET customerName=?,billId=?,cardNo=?,cvv=?,expiredDate=?,payAmount=?" + "WHERE pID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, customerName);
			preparedStmt.setString(2, billId);
			preparedStmt.setString(3, cardNo);
			preparedStmt.setString(4, cvv);
			preparedStmt.setString(5, expiredDate);
			preparedStmt.setDouble(6, Double.parseDouble(payAmount));
			preparedStmt.setInt(7, Integer.parseInt(pID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayments = readPayments(); 
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}
	
	public String deletePayment(String pID) 
	{ 
		 String output = ""; 
		try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 { 
		 return "Error while connecting to the database for deleting."; 
		 } 
		 
		 // create a prepared statement
		 String query = "delete from payment where pID=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(pID)); 
		 
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 String newPayments = readPayments(); 
		 output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";

		 } 
		catch (Exception e) 
		 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}"; 
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
	}


	 
}
