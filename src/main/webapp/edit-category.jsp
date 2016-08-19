

<!-- Edit Category -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
	<c:when test="${param.isAdding == true}"><h2>Add Category</h2></c:when>
	<c:otherwise><h2>Edit Category</h2></c:otherwise>
</c:choose>

<form:form action="editCategory.do" method="post" commandName="category">
<table border="1">
<%--   <tr><td align="right">ID:</td><td><form:input path="id" /></td></tr> --%>
  <tr><td align="right">Description:</td><td><form:input path="description" /></td></tr>
<!--   <tr> -->
<!--     <td align="right">Products:</td> -->
<!--     <td> -->
<%--       <form:select name="products" path="products" multiple="true"> --%>
<%--     	<c:forEach var="product" items="${listOfProducts}"> --%>
<%--     	  <form:option value="${product.upc}"></form:option> --%>
<%--     	</c:forEach> --%>
<%-- 	  </form:select> --%>
<!--     </td> -->
<!--   </tr> -->
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

<!-- End Edit Category -->

