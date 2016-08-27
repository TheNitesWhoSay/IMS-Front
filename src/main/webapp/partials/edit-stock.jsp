

<!-- Edit Stock -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>Edit Stock</h2>

<form:form action="editStock.do" method="post" commandName="stock">
  <div class="col-sm-6">
    Product:
    <form:select name="product" path="product" multiple="false" cssClass="form-control">
      <c:forEach var="prod" items="${listOfProducts}">
        <form:option value="${prod.upc}"></form:option>
      </c:forEach>
    </form:select>
  </div>
  <div class="col-sm-6">
    Amount In Stock:
    <form:input class="input" path="numInStock" cssClass="form-control" />
  </div>
  <div class="col-sm-6">
    <br />
    <form:button type="submit" cssClass="btn btn-primary">Submit</form:button>
  </div>
</form:form>

<!-- End Edit Stock -->

