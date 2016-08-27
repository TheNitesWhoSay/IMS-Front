$(document).ready(function() {
	
	var invoiceTpl = _.template(invoiceTemplate);
	
	$("#invoices-table").on("click", "tbody tr", function() {
		var id = $(this).attr("id").split("-")[1];
		$.ajax({
			method: "get",
			url: "/IMS-Front/api/invoices/id/" + id,
			success: function(response) {
				$(invoiceTpl(response)).modal();
			}
		});
		//window.location = id ? "/IMS-Front/clients/id/" + id : "/IMS-Front/clients";
	});
});