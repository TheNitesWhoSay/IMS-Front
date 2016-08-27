

<!-- Edit Category -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
	<c:when test="${param.isAdding == true}"><h2>Add Category</h2></c:when>
	<c:otherwise><h2>Edit Category</h2></c:otherwise>
</c:choose>

<form:form action="editCategory.do" method="post" commandName="category">
  <div>
    Description:
    <form:input path="description" />
  </div>
  <br />
  <div>
    <form:button type="submit" cssClass="btn btn-primary">Submit</form:button>
  </div>
</form:form>

<!-- End Edit Category -->

