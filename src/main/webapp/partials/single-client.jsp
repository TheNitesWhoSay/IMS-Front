<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Details for Client - <c:out value="${client.name}" /></h2>
<table class="table table-default table-hover">
	<tr>
		<td>Client ID</td>
		<td><c:out value="${client.id}" /></td>
	</tr>
	<tr>
		<td>Client Name</td>
		<td><c:out value="${client.name}" /></td>
	</tr>
	<tr>
		<td>Point of Contact</td>
		<td><c:out value="${client.pointOfContactName}" /></td>
	</tr>
	<tr>
		<td>Email</td>
		<td><c:out value="${client.email}" /></td>
	</tr>
	<tr>
		<td>Phone Number</td>
		<td><c:out value="${client.phoneNumber}" /></td>
	</tr>
	<tr>
		<td>Fax Number</td>
		<td><c:out value="${client.faxNumber}" /></td>
	</tr>
	<tr>
		<td>Address</td>
		<td><c:out value="${client.address}" /></td>
	</tr>
	<tr>
		<td>Type</td>
		<td><c:out value="${client.clientType}" /></td>
	</tr>
</table>
<button class="btn btn-primary" id="edit-client-${client.id}">Edit Client</button>

<script type="text/javascript">
	$(document).ready(function() {
		$("[id^=edit-client]").click(function() {
			var id = $(this).attr("id").split("-")[2];
			window.location = id ? "/IMS-Front/clients/id/" + id + "/edit" : "/IMS-Front/clients";
		});
	});
</script>