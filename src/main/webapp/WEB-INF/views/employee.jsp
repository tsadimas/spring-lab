<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Spring MVC Form Handling</title>
    <style>
.error {
    color: #ff0000;
    font-style: italic;
    font-weight: bold;
}
</style>
</head>
<body>
<h2>Employee Information</h2>
<form:form method="POST"  modelAttribute="newEmployee" action="/lab2/addEmployee">
   <table>   
      <c:if test="${newEmployee.id != 0}" >
   <tr>
        <td><form:label path="id">Id</form:label></td>
        <td><form:input path="id" readonly="true"/>
        </td>
    </tr>
    </c:if>
     <tr>
        <td><form:label path="name">Name</form:label></td>
        <td><form:input path="name" /></td>
        <td><form:errors path="name" cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="role">Role</form:label></td>
        <td><form:input path="role" /></td>
        <td><form:errors path="role" cssClass="error" /></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Submit"/>
        </td>
    </tr>
</table> 
</form:form>
</body>
</html>