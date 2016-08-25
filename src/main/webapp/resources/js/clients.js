var stateList = new Array("AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY");
var clientTypeList = new Array("Incoming", "Outgoing");

var Address = function() {
	this.id = id;
	this.address1 = address1;
	this.address2 = address2;
	this.city = city;
	this.state = state;
	this.zip = zip;
}

var Client = function() {
	this.id = id;
	this.name = name;
	this.email = email;
	this.pointOfContactName = pointOfContactName;
	this.phoneNumber = phoneNumber;
	this.faxNumber = faxNumber;
	this.address = address;
	this.clientType = clientType;
}

$(document).ready(function() {
	
	var singleClient = _.template(singleClientTemplate);
	var editClient = _.template(editClientTemplate);
	
	$("#clients-table").on("click", "tbody tr", function() {
		var d = _.template(singleClientTemplate);
		var id = $(this).attr("id").split("-")[1];
		$.ajax({
			method: "get",
			url: "/IMS-Front/api/clients/id/" + id,
			success: function(response) {
				$(singleClient(response)).modal();
			}
		});
		//window.location = id ? "/IMS-Front/clients/id/" + id : "/IMS-Front/clients";
	});
	
	$("body").on("click", "[id^=edit-client]", function() {
		var id = $(this).attr("id").split("-")[2];
		/*
		$.ajax({
			method: "get",
			url: "/IMS-Front/api/clients/id/" + id,
			success: function(response) {
				$(editClient(response)).modal();
				$(#address.state option[value=${response.client.address.state.abbreviation}]`).attr("selected", "selected");
			}
		});
		*/
		window.location = id ? "/IMS-Front/clients/id/" + id + "/edit" : "/IMS-Front/clients";
	});
	*/
});