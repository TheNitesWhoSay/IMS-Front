

<!-- Reports -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Reports...</h2>

<p>Inventory Value</p>
<div id="inventoryValue" style="width:100%; height:400px;"></div>

<p>Balance Sheet</p>


<p>Profit Statement</p>

<script>
function getDailyInventoryValues(startDate) {
	// TODO: Dirty; replace with AJAX if time permits...
	return [ <c:forEach var="num" items="${dailyInventoryValues}">
				<c:out value="${num}" />,
			 </c:forEach> ];
}
</script>
<script src="resources/js/reports.js"></script>

<!-- End Reports -->

