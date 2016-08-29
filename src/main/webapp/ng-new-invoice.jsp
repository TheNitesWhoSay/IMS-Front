<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
  <head>
  <meta content="text/html; charset=ISO-8859-1" http-equiv="Content-Type">
  <link crossorigin="anonymous" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js" type="text/javascript"></script>
  <script src="http://underscorejs.org/underscore.js" type="text/javascript"></script>
  <script src="/IMS-Front/resources/js/templates.js" type="text/javascript"></script>
  <script crossorigin="anonymous" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.8/angular.min.js" type="text/javascript"></script>

  <title>Inventory Management System</title>

  <!-- TODO: Define in separate file -->
  <style>
  	.table-link {
  		cursor: pointer;
  	}
    .invoice-line-number {
      cursor: pointer;
    }
    #edit-invoice-table tr:hover .invoice-line-number:before {
      color: red;
      content: "\2716";
    }
    #edit-invoice-table tr:hover .invoice-line-number:hover {
      background-color: #bbb;
    }
    #edit-invoice-table tr:hover .invoice-line-number span {
      display: none;
    }
  </style>
</head>

<body ng-app="app" ng-controller="InvoiceController">
  <div class="container">
    <div class="row">
      <h1>Create New Invoice</h1>
      <div class="col-sm-12">
        <form class="invoice-form">
          <div class="form-group" id="invoice-form-type-container">
            <label class="control-label" for="invoice-type">Type</label>
            <select id="invoice-type" class="form-control" ng-model="invoiceType" ng-options="option.name for option in invoiceTypes track by option.name">
              <option value="">Select invoice type...</option>
            </select>
          </div>
          <div class="form-group" id="invoice-form-client-container"  ng-show="invoiceType">
            <label class="control-label" for="invoice-client">Client</label>
            <select id="invoice-client" class="form-control" ng-model="invoice.client" ng-options="client.name for client in (clients | filter:{'clientType':{'type':invoiceType.value}}) track by client.id ">
            	<option value="">Select client...</option>
            </select>
          </div>
          <table class="table table-hover" id="edit-invoice-table" ng-show="invoice.client">
            <thead>
              <tr>
                <th style="width:5%">Line</th>
                <th style="width:70%">Product</th>
                <th style="width:10%">Unit Price</th>
                <th style="width:5%">Quantity</th>
                <th style="width:10%">Total Price</th>
              </tr>
            </thead>
            <tbody>
            	<tr ng-repeat="line in invoice.orderLines">
            		<td>{{ $index }}</td>
            		<td>{{ line.product.name }}</td>
            		<td>{{ line.unitPrice }}</td>
            		<td><input type="number" ng-model="line.quantityOrdered" value="0" min="0" max="{{line.product.stock.numInStock}}"/></td>
            		<td ng-model="" ng-bind="line.quantityOrdered * line.unitPrice"></td>
            		<td></td>
            	</tr>
            </tbody>
            <tfoot>
              <tr class="info">
                <td colspan="5" class="text-center"><a href="#" class="product-select" data-toggle="modal" data-target="#select-product-modal">Add line</a></td>
              </tr>
              <tr>
                <td colspan="4" style="text-align: right; font-weight: bold">Subtotal</td>
                <td id="invoice-subtotal">{{ invoice.subTotal | currency }}</td>
              </tr>
              <tr>
                <td colspan="4" style="text-align: right; font-weight: bold">Tax</td>
                <td id="invoice-tax">{{ invoice.taxAmount | currency }}</td>
              </tr>
              <tr>
                <td colspan="4" style="text-align: right; font-weight: bold">Total</td>
                <td id="invoice-total">{{ invoice.total | currency }}</td>
              </tr>
            </tfoot>
          </table>
          <button class="btn btn-primary" id="submit-invoice" disabled="disabled" style="display:none">Submit Invoice</button>
        </form>
      </div>
    </div>
  </div>
  <div class="modal fade" id="select-product-modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Select Product</h4>
			</div>
			<div class="modal-body">
				<table class="table table-hover">
					<tbody>
						<tr ng-repeat="product in products">
							<td class="table-link" data-dismiss="modal" ng-click="addProduct($index)">{{ product.name }}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>

<script type="text/javascript">
	var app = angular.module("app", []);
	app.controller("InvoiceController", function($scope, $http) {
		$http.get("/IMS-Front/api/clients")
			.success(response => $scope.clients = response.clients);
		$http.get("/IMS-Front/api/products")
			.success(response => $scope.products = response.products);
		$scope.invoiceTypes = [{name: "Incoming", value: "Supplier"}, {name: "Outgoing", value: "Retailer"}];
		$scope.invoiceType = null;
		
		$scope.invoice = {
			subTotal: 0,
		    taxAmount: 0,
		    total: 0,
		    orderLines: []
		}
		
		$scope.addProduct = function(i) {
			var p = $scope.products[i];
			$scope.invoice.orderLines.push({
		      compId: $scope.invoice.orderLines.length+1,
		      unitPrice: p.unitCost,
		      quantityOrdered: 0,
		      product: p
			});
			$
		}
	});
</script>
</html>
