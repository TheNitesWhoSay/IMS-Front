/**
 * 
 */

function fetchEarliestInvoiceDate() {
	
}

// Fetch start date off page or fetch it from the server
function getStartDate() {
	// if date selected
	//		return selected date
	// else
	//		return fetch inventory date
}

// Fetch inventory values since the given date from the application server
//function getDailyInventoryValues(startDate) {
//	// TODO: Replace with AJAX...
//	console.log(startDate);
//	return
//		[
//		 
//		 29.9, 71.5, 106.4, 129.2, 144.0, 176.0,
//		 135.6, 148.5, 216.4, 194.1, 95.6, 54.4
//		 
//		 ];
//}

function getInventoryValueStartDate() {
	return (new Date()).getUTCDate()-getDailyInventoryValues().length;
}

function getInventoryValueInterval() {
	return 1000 * 60 * 60 * 24 // one day
}

function getInventoryValueEndDate() {
	return (new Date()).getUTCDate();
}

$(function () { // Setup inventory value chart
	$('#inventoryValue').highcharts({
		title: { text: 'Inventory Value over Time' },
		chart: { renderTo: 'container', },
		xAxis: { type: 'datetime' },
		yAxis: {
			type: 'linear',
			title: { text: 'USD' }
		},
		series: [{
			name: 'Inventory Value',
			data: getDailyInventoryValues(),
			pointStart: (new Date()).getUTCDate(),
			pointInterval: getInventoryValueInterval() // one day
		}]
	});
});

