<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-default table-hover">
	<tr>
		<td>Name</td>
		<td><c:out value="${client.name}" /></td>
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
		<td>ID Passed</td>
		<td><c:out value="${client.id}" /></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
	</tr>
</table>