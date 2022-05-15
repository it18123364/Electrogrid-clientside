<%@ page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
	<html>
		<head>
		<meta charset="ISO-8859-1">
		<title>Payment Management</title>
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery.min.js"></script>
		<script src="Components/payments.js"></script>
		
		</head>
	<body>
	 <nav class="navbar navbar-dark bg-dark" style="justify-content: center">
            <h3 style="color: white">ElectroGrid</h3>
     </nav>
      
	<br>
	<div class="container" style="max-width: 90%">
	<div style="text-align: center">
		<h1>Payment Management</h1><br></br>
	</div>
	<div class="row">
	<div class="col-4"> 
		
			<form id="formPayment" name="formPayment">
				 Customer Name: 
				 <input id="customerName" name="customerName" type="text" 
				 class="form-control">
				 <br> Bill ID: 
				 <input id="billId" name="billId" type="text" 
				 class="form-control">
				 <br> Card No: 
				 <input id="cardNo" name="cardNo" type="text" 
				 class="form-control">
				 <br> CVV: 
				 <input id="cvv" name="cvv" type="text" 
				 class="form-control">
				 <br>Expired Date: 
				 <input id="expiredDate" name="expiredDate" type="text" 
				 class="form-control">
				 <br>Paid Amount (Rs.): 
				 <input id="payAmount" name="payAmount" type="text" 
				 class="form-control">
				 <br>
				 <input id="btnSave" name="btnSave" type="button" value="Save" 
				 class="btn btn-info btn-lg">
				 <input type="hidden" id="hidPaymentIDSave" 
				 name="hidPaymentIDSave" value="">
				 
			</form>
			<br>
		<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>
		
		<br>
		</div>
		<div id="divPaymentGrid" class="col-8" style="text-align: center">
		<br>
			 <%
			 Payment paymentObj = new Payment(); 
			 out.print(paymentObj.readPayments()); 
			 %>
		</div>
	</div> </div> 
		
	</body>
	</html>