<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<h2>Manage Categories</h2>
<jsp:include page="/partials/list-categories.jsp"></jsp:include>
 <jsp:include page="/partials/edit-category.jsp">
   <jsp:param name="isAdding" value="${true}"/>
 </jsp:include>