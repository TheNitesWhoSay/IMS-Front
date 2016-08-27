<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <h2>Manage Products</h2>
 <jsp:include page="/partials/list-products.jsp"></jsp:include>
  <jsp:include page="/partials/edit-product.jsp">
   <jsp:param name="isAdding" value="${true}"/>
 </jsp:include>