<%@page import="com.ConnectionModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery-3.2.1.min.js"></script>
        <script src="Components/items.js"></script>
		<title>Connection Service</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col">
					<h1>Connection Service</h1>
					<form id="formItem" name="formItem" method="POST" action="Connection.jsp">
						Connection ID: 
						<input 
							id="connectionID" 
							name="itemCode" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						status: 
						<input 
							id="status" 
							name="itemPrice" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Type:
						<input 
							id="type" 
							name="itemDesc" 
							type="text" 
							class="form-control form-control-sm"
						><br>
						
						Units :
						<input 
							id="units" 
							name="itemDesc" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						<input 
							id="btnSave" 
							name="btnSave" 
							type="button" 
							value="Save" 
							class="btn btn-primary"
						>

						<input type="hidden" name="hidItemIDSave" id="hidItemIDSave" value="">
					</form>
				
					<br>
					<div id="alertSuccess" class="alert alert-success"></div>
					<div id="alertError" class="alert alert-danger"></div>
					<br>
					<div id="divConGrid">
						<%
							ConnectionModel connection = new ConnectionModel(); 
							out.print(connection.readConnections());
						%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>