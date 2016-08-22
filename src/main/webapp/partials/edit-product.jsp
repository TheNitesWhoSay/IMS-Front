<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:choose>
	<c:when test="${param.isAdding == true}"><h2>Add Product</h2></c:when>
	<c:otherwise><h2>Edit Product</h2></c:otherwise>
</c:choose>

<form:form action="editProduct.do" modelAttribute="product" cssClass="form-horizontal">
	<spring:bind path="name">
		<div class="form-group ${status.error ? 'has-error' : '' }">
			<div class="col-sm-12">
				<label for="name">Product Name</label>
				<form:input path="name" cssClass="form-control" />
				<form:errors cssClass="help-block" path="name" />
			</div>
		</div>
	</spring:bind>
	<div class="form-group">
		<spring:bind path="upc">
			<div class="col-sm-6 ${status.error ? 'has-error' : '' }">
				<label for="upc">UPC</label>
				<form:input path="upc" cssClass="form-control" />
				<form:errors cssClass="help-block" path="upc" />
			</div>
		</spring:bind>
		<spring:bind path="shortName">
			<div class="col-sm-6 ${status.error ? 'has-error' : '' }">
				<label for="shortName">Short Name</label>
				<form:input path="shortName" cssClass="form-control" />
				<form:errors cssClass="help-block" path="shortName" />
			</div>
		</spring:bind>
	</div>
	<spring:bind path="description">
		<div class="form-group ${status.error ? 'has-error' : '' }">
			<div class="col-sm-12">
				<label for="description">Product Description</label>
				<form:input path="description" cssClass="form-control" />
				<form:errors cssClass="help-block" path="description" />
			</div>
		</div>
	</spring:bind>
	<div class="form-group">
		<spring:bind path="packSize">
			<div class="col-sm-8 ${status.error ? 'has-error' : '' }">
				<label for="packSize">Pack Size</label>
				<form:input path="packSize" cssClass="form-control" />
				<form:errors cssClass="help-block" path="packSize" />
			</div>
		</spring:bind>
		<spring:bind path="reorderQuantity">
			<div class="col-sm-4 ${status.error ? 'has-error' : '' }">
				<label for="reorderQuantity">Reorder Quantity</label>
				<form:input path="reorderQuantity" cssClass="form-control" />
				<form:errors cssClass="help-block" path="reorderQuantity" />
			</div>
		</spring:bind>
	</div>
	<div class="form-group ${status.error ? 'has-error' : '' }">
		<spring:bind path="unitCost">
			<div class="col-sm-4">
				<label for="unitCost">Unit Cost</label>
				<form:input path="unitCost" cssClass="form-control" />
				<form:errors cssClass="help-block" path="unitCost" />
			</div>
		</spring:bind>
		<spring:bind path="retailPrice">
			<div class="col-sm-4">
				<label for="retailPrice">Retail Price</label>
				<form:input path="retailPrice" cssClass="form-control" />
				<form:errors cssClass="help-block" path="retailPrice" />
			</div>
		</spring:bind>
		<spring:bind path="weight">
			<div class="col-sm-4">
				<label for="weight">Product Weight</label>
				<form:input path="weight" cssClass="form-control" />
				<form:errors cssClass="help-block" path="weight" />
			</div>
		</spring:bind>
	</div>
	<spring:bind path="categories">
		<div class="form-group ${status.error ? 'has-error' : '' }">
			<div class="col-sm-12">
				<label for="categories">Categories</label>
				<form:select path="categories" cssClass="form-control" multiple="true" items="${listOfCategories}" />
				<form:errors cssClass="help-block" path="categories" />
			</div>
		</div>
	</spring:bind>
	<form:button cssClass="btn btn-primary" type="submit">Submit</form:button>
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

