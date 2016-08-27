var currencyFormat = num => num.toLocaleString("en-US", {style: "currency", currency: "USD"});

var invoiceLineTemplate = `
	<tr>
		<td><%= lineNumber %></td>
		<td>(<%= product.shortName %>) <%= product.name %> - <%= product.description %></td>
		<td><%= currencyFormat(unitPrice) %></td>
		<td><%= quantityOrdered %></td>
		<td><%= currencyFormat(unitPrice * quantityOrdered) %></td>
	</tr>
`

var invoiceLine = _.template(invoiceLineTemplate);

var invoiceTemplate = `
<div class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Order Details: <%= invoice.orderNumber %></h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-sm-6">
						<p><b><%= invoice.client.name %></b></p>
						<p><%= invoice.client.address.address1 %></p>
						<%= invoice.client.address.address2 ? '<p>' + invoice.client.address.address2 + '</p>' : '' %>
						<p><%= invoice.client.address.city %>, <%= invoice.client.address.state.abbreviation %> <%= invoice.client.address.zip %></p>
					</div>
					<div class="col-sm-6 text-right">
						<p><%= invoice.purchaseDate %></p>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>Line</th>
									<th>Description</th>
									<th>Unit Cost</th>
									<th>Quantity</th>
									<th>Total Cost</th>
								</tr>
							</thead>
							<tbody>
								<% invoice.orderLines.forEach(line => print(invoiceLine(line))) %>
							</tbody>
							<tfoot>
								Total
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
`

var singleClientTemplate = `
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Client Details: <%= client.name %></h4>
			</div>
			<div class="modal-body">
			
				<table class="table table-default table-hover">
				<tr>
					<td>Client ID</td>
					<td><%= client.id %></td>
				</tr>
				<tr>
					<td>Client Name</td>
					<td><%= client.name %></td>
				</tr>
				<tr>
					<td>Point of Contact</td>
					<td><%= client.pointOfContactName %></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><%= client.email %></td>
				</tr>
				<tr>
					<td>Phone Number</td>
					<td><%= client.phoneNumber %></td>
				</tr>
				<tr>
					<td>Fax Number</td>
					<td><%= client.faxNumber %></td>
				</tr>
				<tr>
					<td>Address</td>
					<td><%= client.address.formattedAddress %></td>
				</tr>
				<tr>
					<td>Type</td>
					<td><%= client.clientType.type %></td>
				</tr>
				</table>
				<button class="btn btn-primary" id="edit-client-<%= client.id %>">Edit Client</button>
			</div>
		</div>
	</div>
</div>
`

var editClientTemplate = `
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Edit Client: <%= client.name %></h4>
			</div>
			<div class="modal-body">
			
				<form method="post" action="/IMS-Front/api/clients/new" class="form-horizontal" id="client-form">
					<div class="form-group">
							<div class="col-sm-9 ">
								<label for="name">Client Name</label>
								<input type="text" value="<%= client.name %>" class="form-control" name="name" id="name">
							</div>
						
						
							<div class="col-sm-3 ">
								<label for="clientType">Client Type</label>
								<select class="form-control" name="clientType" id="clientType">
									<option value="Retailer">Retailer</option>
									<option value="Supplier">Supplier</option></select>
								
							</div>
						
					</div>
					
						<div class="form-group ">
							<div class="col-sm-12">
								<label for="email">Client Email</label>
								<input type="text" value="<%= client.email %>" class="form-control" name="email" id="email">
								
							</div>
						</div>
					
					
						<div class="form-group ">
							<div class="col-sm-12">
								<label for="pointOfContactName">Point of Contact Name</label>
								<input type="text" value="<%= client.pointOfContactName %>" class="form-control" name="pointOfContactName" id="pointOfContactName">
								
							</div>
						</div>
					
					<div class="form-group">
						
							<div class="col-sm-6 ">
								<label for="phoneNumber">Phone Number</label>
								<input type="text" value="<%= client.phoneNumber %>" class="form-control" name="phoneNumber" id="phoneNumber">
								
							</div>
						
						
							<div class="col-sm-6 ">
								<label for="faxNumber">Fax Number</label>
								<input type="text" value="<%= client.faxNumber %>" class="form-control" name="faxNumber" id="faxNumber">
								
							</div>
						
					</div>
					
						<div class="form-group ">
							<div class="col-sm-12">
								<input type="hidden" value="<%= client.address.id %>" name="address.id" id="address.id">
								<label for="address1">Address 1</label>
								<input type="text" value="<%= client.address.address1 %>" class="form-control" name="address.address1" id="address.address1">
								
							</div>
						</div>
					
					
						<div class="form-group ">
							<div class="col-sm-12">
								<label for="address2">Address 2</label>
								<input type="text" value="<%= client.address.address2 %>" class="form-control" name="address.address2" id="address.address2">
								
							</div>
						</div>
					
					<div class="form-group">
						
							<div class="col-sm-8 ">
								<label for="city">City</label>
								<input type="text" value="<%= client.address.city %>" class="form-control" name="address.city" id="address.city">
								
							</div>
						
						
							<div class="col-xs-4 col-sm-2 ">
								<label for="state">State</label>
								<select class="form-control" name="address.state" id="address.state">
								<option value="AK">AK</option><option value="AL">AL</option><option value="AR">AR</option><option value="AZ">AZ</option><option value="CA">CA</option><option value="CO">CO</option><option value="CT">CT</option><option value="DE">DE</option><option value="FL">FL</option><option value="GA">GA</option><option value="HI">HI</option><option value="IA">IA</option><option value="ID">ID</option><option value="IL">IL</option><option value="IN">IN</option><option value="KS">KS</option><option value="KY">KY</option><option value="LA">LA</option><option value="MA">MA</option><option value="MD">MD</option><option value="ME">ME</option><option value="MI">MI</option><option value="MN">MN</option><option value="MN">MN</option><option value="MO">MO</option><option value="MS">MS</option><option value="MT">MT</option><option value="NC">NC</option><option value="ND">ND</option><option value="NE">NE</option><option value="NH">NH</option><option value="NJ">NJ</option><option value="NM">NM</option><option value="NV">NV</option><option value="NY">NY</option><option value="OH">OH</option><option value="OK">OK</option><option value="OR">OR</option><option value="PA">PA</option><option value="RI">RI</option><option value="SC">SC</option><option value="SD">SD</option><option value="TN">TN</option><option value="TX">TX</option><option value="UT">UT</option><option value="VA">VA</option><option value="VT">VT</option><option value="WA">WA</option><option value="WI">WI</option><option value="WV">WV</option><option value="WY">WY</option></select>
								</select>
							</div>
						
						
							<div class="col-xs-8 col-sm-2 ">
								<label for="zip">ZIP</label>
								<input type="text" value="<%= client.address.zip %>" class="form-control" name="address.zip" id="address.zip">
								
							</div>
						
					</div>
					<button value="Submit" type="submit" class="btn btn-primary">Submit</button>
				</form>
				<button class="btn btn-primary" id="edit-client-<%= client.id %>">Edit Client</button>
			</div>
		</div>
	</div>
</div>
`

var modalTemplate = `
<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Select Product</h4>
			</div>
			<div class="modal-body">
				
			</div>
		</div>
	</div>
</div>
`