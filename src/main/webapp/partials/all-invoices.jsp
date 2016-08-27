<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h2>Purchase Orders</h2>
<table class="table table-default table-hover" id="invoices-table">
	<thead>
		<tr>
			<th>Order Number</th>
			<th>Client</th>
			<th>Type</th>
			<th>Amount</th>
			<th>Order Date</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="invoice" items="${invoices}">
			<tr id="invoice-${invoice.orderNumber}" class="table-link">
				<td><c:out value="${invoice.orderNumber}" /></td>
				<td><c:out value="${invoice.client.name}" /></td>
				<td><c:out value="${invoice.client.clientType.type eq 'Supplier' ? 'Incoming' : 'Outgoing'}" /></td>
				<td><fmt:formatNumber value="${invoice.total}" type="currency" /></td>
				<td><fmt:formatDate value="${invoice.purchaseDate}" dateStyle="short" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<a class="btn btn-primary" href="/IMS-Front/invoices/new">New Purchase Order</a>
<script type="text/javascript" src="/IMS-Front/resources/js/invoices.js"></script>