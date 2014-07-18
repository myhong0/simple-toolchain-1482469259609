<!-- **********************************************************************************
 *  Copyright 2014 IBM
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
********************************************************************************** -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bluemix.hangman.model.Category" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Bluemix - Hangman Game</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="style.css" />
	<script src="index.js"></script>
</head>
<body>
	<div id="container" >
		<div id="header"><h1>Welcome to Hangman!</h1></div>
		<div id="menu">
			<select onChange="javascript:loadWord(this.value);">
				<option value="">Select category</option>
				<%  List<Category> categories = (List<Category>) request.getAttribute("categories");
					for(int index=0; index<categories.size(); index++){
		 		%>
					<option value="<%=categories.get(index).getId()%>"><%=categories.get(index).getName() %></option>
				<%
					}
		 		%>
		 	</select>
		</div>
		<div id="content">
			<img id="hangmanImage" style="visibility:hidden">
			<br><br>
			<table id="wordTable"> 
			</table>
			<br>
			<table id="lettersTable">
			</table>
		</div>
	</div>
</body>
</html>