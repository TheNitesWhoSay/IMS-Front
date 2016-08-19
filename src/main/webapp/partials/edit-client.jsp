<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
		<spring:bind path="name">
			<div class="col-sm-9 form-group ${status.error ? 'has-error' : '' }">
				<label for="name">Client Name</label>
				<form:input path="name" cssClass="form-control" />
				<form:errors cssClass="help-block" path="name" />
			</div>
		</spring:bind>
		<spring:bind path="clientType">
			<div class="col-sm-3 form-group ${status.error ? 'has-error' : '' }">
				<label for="clientType">Client Type</label>
				<form:select path="clientType" cssClass="form-control" items="${clientTypes}" />
				<form:errors cssClass="help-block" path="clientType" />
			</div>
		</spring:bind>
	</div>
	<spring:bind path="email">
		<div class="form-group ${status.error ? 'has-error' : '' }">
			<label for="email">Client Email</label>
			<form:input path="email" cssClass="form-control" />
			<form:errors cssClass="help-block" path="email" />
		</div>
	</spring:bind>
	<spring:bind path="pointOfContactName">
		<div class="form-group ${status.error ? 'has-error' : '' }">
			<label for="pointOfContactName">Point of Contact Name</label>
			<form:input path="pointOfContactName" cssClass="form-control" />
			<form:errors cssClass="help-block" path="pointOfContactName" />
		</div>
	</spring:bind>
	<div class="form-group row">
		<spring:bind path="phoneNumber">
			<div class="col-sm-6 form-group ${status.error ? 'has-error' : '' }">
				<label for="phoneNumber">Phone Number</label>
				<form:input path="phoneNumber" cssClass="form-control" />
				<form:errors cssClass="help-block" path="phoneNumber" />
			</div>
		</spring:bind>
		<spring:bind path="faxNumber">
			<div class="col-sm-6 form-group ${status.error ? 'has-error' : '' }">
				<label for="faxNumber">Fax Number</label>
				<form:input path="faxNumber" cssClass="form-control" />
				<form:errors cssClass="help-block" path="faxNumber" />
			</div>
		</spring:bind>
	</div>
	<spring:bind path="address.address1">
		<div class="form-group ${status.error ? 'has-error' : '' }">
			<form:hidden path="address.id"/>
			<label for="address1">Address 1</label>
			<form:input path="address.address1" cssClass="form-control" />
			<form:errors cssClass="help-block" path="address.address1" />
		</div>
	</spring:bind>
	<spring:bind path="address.address2">
		<div class="form-group ${status.error ? 'has-error' : '' }">
			<label for="address2">Address 2</label>
			<form:input path="address.address2" cssClass="form-control" />
			<form:errors cssClass="help-block" path="address.address2" />
		</div>
	</spring:bind>
	<div class="form-group row">
		<spring:bind path="address.city">
			<div class="col-sm-8 form-group ${status.error ? 'has-error' : '' }">
				<label for="city">City</label>
				<form:input path="address.city" cssClass="form-control" />
				<form:errors cssClass="help-block" path="address.city" />
			</div>
		</spring:bind>
		<spring:bind path="address.state">
			<div class="col-xs-4 col-sm-2 form-group ${status.error ? 'has-error' : '' }">
				<label for="state">State</label>
				<form:select path="address.state" cssClass="form-control" items="${states}" />
				<form:errors cssClass="help-block" path="address.state" />
			</div>
		</spring:bind>
		<spring:bind path="address.zip">
			<div class="col-xs-8 col-sm-2 form-group ${status.error ? 'has-error' : '' }">
				<label for="zip">ZIP</label>
				<form:input path="address.zip" cssClass="form-control" />
				<form:errors cssClass="help-block" path="address.zip" />
			</div>
		</spring:bind>
	</div>
	<form:button cssClass="btn btn-primary" type="submit">Submit</form:button>
</form:form>