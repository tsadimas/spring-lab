<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Employee Application 
</h1>
<section>
<table>
	<tr>
	<th> id </th>
	<th> name </th>
	<th> role </th>
	</tr>
	<c:forEach items="${employees}" var="employee"> 
  <tr>
    <td>${employee.id}</td>
    <td>${employee.name}</td>
    <td>${employee.role}</td>
    <td> <a href="/lab2/edit/${employee.id}">Edit employee</a></td>
    <td><a href="<c:url value='/remove/${employee.id}'/> " > Delete </a></td>
  </tr>
</c:forEach>
</table>
</section>
<a href="<c:url value='/employee'/>">Add Employee</a>
</body>
</html>

