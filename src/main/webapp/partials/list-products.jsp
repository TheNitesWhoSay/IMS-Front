

<!-- List Products -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table class="table">
  <tr>
    <th>UPC</th>
    <th>Name</th>
    <th>Short Name</th>
    <th>Description</th>
    <th>Pack Size</th>
    <th>Reorder Quantity</th>
    <th>Unit Cost</th>
    <th>Retail Price</th>
    <th>Weight</th>
    <th>Category</th>
    <th>Image</th>
  </tr>
  <c:forEach var="product" items="${listOfProducts}">
    <tr>
      <td><c:out value="${product.upc}"></c:out></td>
      <td><c:out value="${product.name}"></c:out></td>
      <td><c:out value="${product.shortName}"></c:out></td>
      <td><c:out value="${product.description}"></c:out></td>
      <td><c:out value="${product.packSize}"></c:out></td>
      <td><c:out value="${product.reorderQuantity}"></c:out></td>
      <td><fmt:formatNumber value="${product.unitCost}" type="currency"/></td>
      <td><fmt:formatNumber value="${product.retailPrice}" type="currency"/></td>
      <td><c:out value="${product.weight}"></c:out></td>
      <td>
        <c:set var="hasPreviousCategories" value="${false}"></c:set>
        <c:forEach var="category" items="${product.categories}">
          <c:out value="${category.description}"></c:out>
            <c:if test="${hasPreviousCategories}">, </c:if>
          <c:set var="hasPreviousCategories" value="${true}"></c:set>
        </c:forEach>
      </td>
      <td>
        <c:choose>
          <c:when test="${product.image != null &&
          				  (not empty product.image.address)}">
            <a href="${product.image.address}">View Image</a>
          </c:when>
          <c:otherwise>
            <jsp:include page="add-image.jsp">
	      	  <jsp:param name="upc" value="${product.upc}" />
	        </jsp:include>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </c:forEach>
</table>

<!-- End List Products -->

