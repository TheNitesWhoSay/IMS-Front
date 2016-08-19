

<!-- List Products -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table">
  <tr>
    <th>ID</th>
    <th>Description</th>
    <th>Products</th>
  </tr>
  <c:forEach var="category" items="${listOfCategories}">
    <tr>
      <td><c:out value="${category.id}"></c:out></td>
      <td><c:out value="${category.description}"></c:out></td>
      <td>
        <c:set var="hasPreviousProducts" value="${false}"></c:set>
        <c:forEach var="product" items="${category.products}">
          <c:out value="${product.upc}"></c:out>:
          <c:out value="${product.description}"></c:out>
            <c:if test="${hasPreviousCategories}">, </c:if>
          <c:set var="hasPreviousCategories" value="${true}"></c:set>
        </c:forEach>
      </td>
    </tr>
  </c:forEach>
</table>

<!-- End List Products -->

