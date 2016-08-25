

<!-- Reports -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Generate Reports</h2>


<select id="reportType" class="form-control" name="reportType">
   <option value="inventoryValue">Inventory Value</option>
   <option value="inventoryLevel">Stock Levels</option>
</select>
<br />
<button class="btn btn-primary" id="generateReport" type="button">Generate Report</button>
<br />
<br />
<br />
<div id="loading">
  <img src="resources/img/loading.gif" />
</div>
<!-- InvValue -->
<!-- InvLevel -->
<!-- Biggest Supplier -->
<!-- Biggest Retailer -->
<!-- Biggest Sellers -->
<div id="inventoryValue" style="width:100%; height:400px;"></div>
<div id="inventoryLevel" style="width:100%; height:400px;"></div>

<script src="resources/js/reports.js"></script>

<!-- End Reports -->

