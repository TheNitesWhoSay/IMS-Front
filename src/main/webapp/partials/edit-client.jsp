<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
	<c:when test="${client.id ne 0}">
		<h2>Edit Client - <c:out value="${client.name}" /></h2>
	</c:when>
	<c:otherwise>
		<h2>New Client</h2>
	</c:otherwise>
</c:choose>
<form:form modelAttribute="client">
	<div class="form-group row">
		<div class="col-sm-9">
			<label for="name">Client Name</label>
			<form:input path="name" cssClass="form-control" />
			<form:errors cssClass="help-text" path="name" />
		</div>
		<div class="col-sm-3">
			<label for="name">Client Type</label>
			<form:select path="clientType" cssClass="form-control" items="${clientTypes}" />
			<form:errors cssClass="help-text" path="clientType" />
		</div>
	</div>
	<div class="form-group">
		<label for="email">Client Email</label>
		<form:input path="email" cssClass="form-control" />
		<form:errors cssClass="help-text" path="email" />
	</div>
	<div class="form-group">
		<label for="pointOfContactName">Point of Contact Name</label>
		<form:input path="pointOfContactName" cssClass="form-control" />
		<form:errors cssClass="help-text" path="pointOfContactName" />
	</div>
	<div class="form-group row">
		<div class="col-sm-6">
			<label for="phoneNumber">Phone Number</label>
			<form:input path="phoneNumber" cssClass="form-control" />
			<form:errors cssClass="help-text" path="phoneNumber" />
		</div>
		<div class="col-sm-6">
			<label for="faxNumber">Fax Number</label>
			<form:input path="faxNumber" cssClass="form-control" />
			<form:errors cssClass="help-text" path="faxNumber" />
		</div>
	</div>
	<div class="form-group">
		<form:hidden path="address.id"/>
		<label for="address1">Address 1</label>
		<form:input path="address.address1" cssClass="form-control" />
		<form:errors cssClass="help-text" path="address.address1" />
	</div>
	<div class="form-group">
		<label for="address2">Address 2</label>
		<form:input path="address.address2" cssClass="form-control" />
		<form:errors cssClass="help-text" path="address.address2" />
	</div>
	<div class="form-group row">
		<div class="col-sm-8">
			<label for="city">City</label>
			<form:input path="address.city" cssClass="form-control" />
			<form:errors cssClass="help-text" path="address.city" />
		</div>
		<div class="col-sm-2">
			<label for="state">State</label>
			<form:select path="address.state" cssClass="form-control" items="${states}" />
			<form:errors cssClass="help-text" path="address.state" />
		</div>
		<div class="col-sm-2">
			<label for="zip">ZIP</label>
			<form:input path="address.zip" cssClass="form-control" />
			<form:errors cssClass="help-text" path="address.zip" />
		</div>
	</div>
	<form:button cssClass="btn btn-primary" type="submit">Submit</form:button>
</form:form>