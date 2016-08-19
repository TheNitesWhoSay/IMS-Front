

<!-- Edit Product -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
	<c:when test="${param.isAdding == true}"><h2>Add Product</h2></c:when>
	<c:otherwise><h2>Edit Product</h2></c:otherwise>
</c:choose>

<form:form action="editProduct.do" method="post" commandName="product">
<table border="1">
  <tr><td align="right">UPC:</td><td><form:input path="upc" /></td></tr>
  <tr><td align="right">Name:</td><td><form:input path="name" /></td></tr>
  <tr><td align="right">Short-Name:</td><td><form:input path="shortName" /></td></tr>
  <tr><td align="right">Description:</td><td><form:input path="description" /></td></tr>
  <tr><td align="right">Pack Size:</td><td><form:input path="packSize" /></td></tr>
  <tr><td align="right">Reorder Quantity:</td><td><form:input path="reorderQuantity" /></td></tr>
  <tr><td align="right">Unit Cost:</td><td><form:input path="unitCost" /></td></tr>
  <tr><td align="right">Retail Price:</td><td><form:input path="retailPrice" /></td></tr>
  <tr><td align="right">Product Weight:</td><td><form:input path="weight" /></td></tr>
  <tr>
    <td align="right">Categories:</td>
    <td>
      <form:select name="categories" path="categories" multiple="true">
    	<c:forEach var="desc" items="${listOfCategories}">
    	  <form:option value="${desc.description}"></form:option>
    	</c:forEach>
	  </form:select>
    </td>
  </tr>
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

<!-- End Edit Product -->

