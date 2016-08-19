

<!-- Edit Stock -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>Edit Stock</h2>
<form:form action="editStock.do" method="post" commandName="stock">
<table border="1">
<%--   <tr><td align="right">ID:</td><td><form:input path="id" /></td></tr> --%>
<%--   <tr><td align="right">Description:</td><td><form:input path="description" /></td></tr> --%>
  <tr>
    <td align="right">Product:</td>
    <td>
      <form:select name="product" path="product" multiple="false">
    	<c:forEach var="prod" items="${listOfProducts}">
    	  <form:option value="${prod.upc}"></form:option>
    	</c:forEach>
	  </form:select>
    </td>
  </tr>
  <tr><td align="right">Amount In Stock: </td><td><form:input path="numInStock" /></td></tr>
  <tr><td></td><td align="right"><input type="submit" value="Submit" /></td></tr>
</table>
</form:form>
<!--   				 path=bean property -->
<%--     UPC: <form:input path="upc" /> --%>
<%--     	<form:errors path="name" cssClass="error" /><br /> --%>
<!--     					  could do: element="div" -->
<%--     Name: <form:input path="name" /> --%>
<%--     	<form:errors path="age" cssClass="error" /><br /> --%>
<%--     Description: <form:input path="description" /> --%>
<%--     	<form:errors path="email" cssClass="error" /><br /> --%>
<!--     <input type="submit" value="Submit" /> -->
<%-- </form:form> --%>

<!-- End Edit Stock -->

