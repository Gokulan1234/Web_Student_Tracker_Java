<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Tracker App</title>

<link type="text/css" rel="stylesheet" href="css/style.css">

</head>



<body>

<div id="wrapper">
<div id="header">
<h2>Madras University</h2>
</div>
</div>


<div id="container">
<div id="content">

<input type="button" value="Add Student" 
onclick="window.location.href='add-student-form.jsp';return false;"
class="add-student-button"/>

<table>
<tr>

<th>firstname</th>
<th>lastname</th>
<th>email</th>
<th>Action</th>

</tr>
<c:forEach var="temp" items="${STUDENT_LIST }">

<!-- set up a link for each student -->
<c:url var="templink" value="StudentControllerServlet"> 
<c:param name="command" value="LOAD"/>
<c:param name="studentId" value="${temp.id }"/>

</c:url>

<!-- set up a link for Delete a student -->
<c:url var="deletelink" value="StudentControllerServlet"> 
<c:param name="command" value="DELETE"/>
<c:param name="studentId" value="${temp.id }"/>

</c:url>

<tr>
<td>${temp.getFirstName()} </td>
<td> ${temp.getLastName()}</td>
<td> ${temp.getEmail() }</td>
<td>
<a href="${templink }">Update</a>
|
<a href="${deletelink }" 
onclick="if(!(confirm('Are you sure you want to delete this student?'))) return false">
Delete</a>
</td>

</tr>
</c:forEach>

</table>
</div>

</div>

</body>
</html>