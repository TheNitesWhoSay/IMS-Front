<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Clients</h2>
<table class="table table-default table-hover" id="clients-table">
	<thead>
		<tr>
			<th>Name</th>
			<th>Email</th>
			<th>Phone</th>
			<th>Type</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="client" items="${clients}">
			<tr id="client-${client.id}" class="table-link">
				<td><c:out value="${client.name}" /></td>
				<td><c:out value="${client.email}" /></td>
				<td><c:out value="${client.phoneNumber}" /></td>
				<td><c:out value="${client.clientType}" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<a class="btn btn-primary" href="/IMS-Front/clients/new">Add Client</a>
<script type="text/javascript" src="/IMS-Front/resources/js/clients.js"></script>