

<!-- List Stocks -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table">
  <tr>
    <th>UPC</th>
    <th>Name</th>
    <th>Amount In Stock</th>
  </tr>
  <c:forEach var="product" items="${listOfProducts}">
    <tr>
      <td><c:out value="${product.upc}"></c:out></td>
      <td><c:out value="${product.name}"></c:out></td>
      <td><c:out value="${product.stock.numInStock}"></c:out></td>
    </tr>
  </c:forEach>
</table>

<!-- End List Stocks -->

