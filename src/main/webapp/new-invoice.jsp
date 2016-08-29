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

<body>
  <div class="container">
    <div class="row">
      <h1>Create New Invoice</h1>
      <div class="col-sm-12">
        <form class="invoice-form">
          <div class="form-group" id="invoice-form-type-container">
            <label class="control-label" for="invoice-type">Type</label>
            <select id="invoice-type" class="form-control" disabled>
              <option>Select invoice type...</option>
              <option>Incoming</option>
              <option>Outgoing</option>
            </select>
          </div>
          <div class="form-group" id="invoice-form-client-container"  style="display: none">
            <label class="control-label" for="invoice-client">Client</label>
            <select id="invoice-client" class="form-control"></select>
          </div>
          <table class="table table-hover" id="edit-invoice-table" style="display: none">
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
            </tbody>
            <tfoot>
              <tr class="info">
                <td colspan="5" class="text-center"><a href="#" class="product-select">Add line</a></td>
              </tr>
              <tr>
                <td colspan="4" style="text-align: right; font-weight: bold">Subtotal</td>
                <td id="invoice-subtotal">$0.00</td>
              </tr>
              <tr>
                <td colspan="4" style="text-align: right; font-weight: bold">Tax</td>
                <td id="invoice-tax">$0.00</td>
              </tr>
              <tr>
                <td colspan="4" style="text-align: right; font-weight: bold">Total</td>
                <td id="invoice-total">$0.00</td>
              </tr>
            </tfoot>
          </table>
          <button class="btn btn-primary" id="submit-invoice" disabled="disabled" style="display:none">Submit Invoice</button>
        </form>
      </div>
    </div>
  </div>
</body>

<script type="text/javascript">
  var currencyFormat = num => num.toLocaleString("en-US", {style: "currency", currency: "USD"});

  $(document).ready(function() {

    _.templateSettings = {
      interpolate: /\{\{(.+?)\}\}/g
    };

    var getFormattedProductName = function(product) {
      var p = product;
      var tpl = _.template("({{ shortName }}) {{ name }} - {{ description }}");
      return tpl(product);
    }

    var clients, products;

    // TODO: Change this to pull from somewhere
    var globalTax = 0.07;

    var invoiceLine = {
      compId: {},
      unitPrice: 0.0,
      quantityOrdered: 0,
      product: {}
    }

    var runningInvoice = {
      subTotal: 0,
      taxAmount: 0,
      total: 0,
      orderLines: [],
      reset: function() {
        this.subTotal = 0;
        this.taxAmount = 0;
        this.total = 0;
        this.orderLines = [];
      },
      addProduct: function(product) {
        line = _.create(invoiceLine);
        line.product = product;
        line.compId.lineNumber = this.orderLines.length + 1;
        line.unitPrice = product.unitCost;
        this.orderLines.push(line);
        return line;
      },
      updateQuantity: function(index, qty) {
        this.orderLines[index].quantityOrdered = parseInt(qty);
        this._updateTotal();
      },
      removeProduct: function(index) {
        this.orderLines.splice(index, 1);
        this._updateLineNumbers();
        this._updateTotal();
      },
      filterQty: function() {
        this.orderLines = this.orderLines.filter(line => line.quantityOrdered >= 0);
        this._updateLineNumbers();
      },
      _updateLineNumbers: function() {
        this.orderLines.map((line, i) => {
          line.compId.lineNumber = i+1;
          return line;
        });
      },
      _updateTotal: function() {
        this.subTotal = this.orderLines.reduce( ((p,c) => p+c.unitPrice*c.quantityOrdered), 0);
        this.taxAmount = this.subTotal * globalTax;
        this.total = this.subTotal + this.taxAmount;
      }
    }

    var updateTable = function() {
      $("#edit-invoice-table tbody tr").each(function(i) {
        var line = runningInvoice.orderLines[i];
        $(this).find(".invoice-line-number span").text(line.compId.lineNumber);
        $(this).find(".invoice-total-item-cost").text(currencyFormat(line.unitPrice * line.quantityOrdered));
      });
      $("#invoice-subtotal").text(currencyFormat(runningInvoice.subTotal));
      $("#invoice-tax").text(currencyFormat(runningInvoice.taxAmount));
      $("#invoice-total").text(currencyFormat(runningInvoice.total));
      if (runningInvoice.total > 0) {
        $("#submit-invoice").prop("disabled", false);
      }
      else {
        $("#submit-invoice").prop("disabled", true);
      }
    }

    var setupForm = function() {

      // Enable first form element
      $(".invoice-form #invoice-type").prop("disabled", false);

      // Add event listener for type change
      $("#invoice-type").change(function() {
        // If filler element selected, clear form and do nothing
        if ($("#invoice-type").prop("selectedIndex") === 0) {
          $("#invoice-client").empty();
          $("#invoice-form-client-container").hide();
          $("#edit-invoice-table").hide();
          $("#edit-invoice-table tbody").empty();
          runningInvoice.reset();
          return;
        }
        // Otherwise, populate dropdown with applicable clients
        type = $("#invoice-type").val();
        cform = $("#invoice-client");
        $("#invoice-client").empty();
        $("<option>Select a client...</option>").appendTo("#invoice-client");
        clients
          .filter(c => c.clientType.type === (type === "Incoming" ? "Supplier" : "Retailer"))
          .forEach(c => $("<option></option>").val(c.id).text(c.name).appendTo(cform));
        $("#invoice-form-client-container").show();
      });

      // Add event listener for client change
      $("#invoice-client").change(function() {
        if ($("#invoice-client").prop("selectedIndex") === 0) {
          $("#edit-invoice-table").hide();
          $("#edit-invoice-table tbody").empty();
          runningInvoice.reset();
          return;
        }

        $("#edit-invoice-table").show();
        $("#submit-invoice").show();
      });

      $("#edit-invoice-table").on("click", ".product-select", function() {
        var deferred = $.Deferred();
        var modal = $(modalTemplate);
        var modalBody = modal.find(".modal-body");
        var productTable = $(`<table class="table table-hover" id="product-list"></table>`).appendTo(modalBody);
        var selectedProducts = runningInvoice.orderLines.map(line => line.product);
        products
          .filter(product => selectedProducts.indexOf(product) === -1 && ($("#invoice-type").val() === "Incoming" || product.stock.numInStock > 0))
          .forEach(function(product) {
            var tr = $("<tr></tr>");
            tr.addClass("table-link").attr("id", "add-product-" + product.upc);
            var td = $("<td></td>").appendTo(tr);
            td.text(getFormattedProductName(product));
            tr.appendTo(productTable);
          });
          productTable.on("click", "[id^=add-product-]", function() {
            var id = $(this).attr("id").split("-")[2];
            deferred.resolve(products.find(p => p.upc.toString() === id));
            modal.modal("hide");
          });
        modal.on("hide", () => deferred.fail() );
        modal.modal();
        deferred.always( () => productTable.off("click") );
        deferred.done(function(product) {
          var line = runningInvoice.addProduct(product);
          console.log(line);
          var row = $("<tr></tr>");
          var col1 = $("<td></td>").addClass("invoice-line-number text-center")
          $("<span></span>").appendTo(col1).text(line.compId.lineNumber);
          var col2 = $("<td></td>").text(getFormattedProductName(line.product)).addClass("invoice-product-name");
          var col3 = $("<td></td>").text(currencyFormat(line.unitPrice)).addClass("invoice-unit-cost");
          var col4 = $("<td></td>");
          $(`<input type="number" style="width:60px" class="invoice-line-qty-select" value="0" />`).appendTo(col4);
          var col5 = $("<td></td>").text(currencyFormat(line.unitPrice * line.quantityOrdered)).addClass("invoice-total-item-cost");
          row.append(col1).append(col2).append(col3).append(col4).append(col5);
          row.appendTo("#edit-invoice-table tbody");
        });
      });

      $("#edit-invoice-table").on("change", ".invoice-line-qty-select", function() {
        if (!parseInt($(this).val()) || $(this).val() < 0) {
          $(this).val(0);
        }
        var lineNum = $("#edit-invoice-table .invoice-line-qty-select").index(this);
        var currStock = runningInvoice.orderLines[lineNum].product.stock.numInStock;
        if ($(this).val() > currStock) {
          $(this).val(currStock);
        }
        runningInvoice.updateQuantity(lineNum, parseInt($(this).val()));
        updateTable();
      });

      $("#edit-invoice-table").on("click", ".invoice-line-number", function() {
        var lineNum = $("#edit-invoice-table .invoice-line-number").index(this);
        console.log(lineNum+1); // TODO: lineNum not getting defined?
        $("#edit-invoice-table tbody tr:nth-child(" + (lineNum+1) + ")").remove();
        runningInvoice.removeProduct(lineNum);
        updateTable();
      });

      $("#submit-invoice").click(function() {
        if (runningInvoice.total <= 0) return;
        runningInvoice.filterQty();
        runningInvoice.orderLines.forEach(line => {
          var upc = line.product.upc;
          line.product = { upc: upc };
          console.log(line);
        });
        var clientId = $("#invoice-client").val();
        runningInvoice.client = clients.find(client => client.id.toString() === clientId);
        console.log(JSON.stringify(runningInvoice));
        console.log(runningInvoice);
        $.ajax({
          method: "post",
          url: "/IMS-Front/api/invoices",
          data: JSON.stringify(runningInvoice),
          contentType: "application/json; charset=utf-8",
          dataType: "json",
          processData: false,
          success: function() {
            window.location = "/IMS-Front/invoices"
          }
        });
      });
    }

    // Load clients and products
    $.when(
      $.get({
        url: "/IMS-Front/api/clients",
        success: function(response) { clients = response.clients; }
      }),
      $.get({
        url: "/IMS-Front/api/products",
        success: function(response) { products = response.products; }
      })
    ).then(setupForm);

  });
</script>
</html>
