/**
 * 
 */

// Fetch start date off page or fetch it from the server
function getStartDate() {
	// if date selected
	//		return selected date
	// else
	//		return fetch inventory date
}

function getInventoryValueStartDate() {
	return (new Date()).getUTCDate()-getDailyInventoryValues().length;
}

function getInventoryValueInterval() {
	return 1000 * 60 * 60 * 24 // one day
}

function getInventoryValueEndDate() {
	return (new Date()).getUTCDate();
}

function showDailyInventoryValues(dailyInventoryValues) {
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
			data: dailyInventoryValues,
			pointStart: (new Date()).getUTCDate(),
			pointInterval: getInventoryValueInterval() // one day
		}]
	});
}

function showInventoryLevels(inventoryLevels) {
	var nameArray = [];
	var inStockArray = [];
	var missingArray = [];
	for ( var i=0; i<inventoryLevels.length; i++ ) {
		var stock = inventoryLevels[i];
		if ( stock != null ) {
			var product = stock.product;
			if ( product != null ) {
				nameArray.push(product.name);
				inStockArray.push(stock.numInStock);
				var missing = product.reorderQuantity-stock.numInStock;
				if ( missing < 1 )
					missing = null;
				missingArray.push(missing);
			}
		}
	}
	
    $('#inventoryLevel').highcharts({
        chart: { type: 'column' },
        title: { text: 'Stock Levels' },
        xAxis: {
            categories: nameArray
        },
        yAxis: {
            min: 0,
            title: { text: 'Stock Level' },
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    color:
                    	(Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                }
            }
        },
        legend: {
            align: 'right',
            x: -30,
            verticalAlign: 'top',
            y: 25,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
            borderColor: '#CCC',
            borderWidth: 1,
            shadow: false
        },
        tooltip: {
            headerFormat: '<b>{point.x}</b><br/>',
            pointFormat: '{series.name}: {point.y}'
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true,
                    color: 'white',
                    style: { textShadow: '0 0 3px black' }
                }
            }
        },
        series: [{
            name: 'Needed',
            data: missingArray,
            color: '#aa0000'
        }, {
            name: 'In-Stock',
            data: inStockArray
        }]
    });
}

function loadAndShowDailyInventoryValues() {
	// Fetch inventory values since the given date from the application server
	
	$.getJSON('/IMS-Front/api/inventory/dailyValues',
	function(dailyValues) {
		$("#loading").hide();
		$("#generateReport").show();
		$("#reportType").show();
		$("#inventoryValue").show();
		showDailyInventoryValues(dailyValues);
	});
}

function loadAndShowInventoryLevels() {
	$.getJSON('/IMS-Front/api/inventory/inventoryLevels',
	function(inventoryLevels) {
		$("#loading").hide();
		$("#generateReport").show();
		$("#reportType").show();
		$("#inventoryLevel").show();
		showInventoryLevels(inventoryLevels);
	});
}

$(document).ready(function() {
	
	$("#loading").hide();
	$("#inventoryValue").hide();
	$("#inventoryLevel").hide();
	document.getElementById("generateReport").addEventListener("click", function(){
		var list = document.getElementById("reportType")
		var selection = list.options[list.selectedIndex].text;
		console.log(selection);
		
		$("#loading").show();
		
		$("#generateReport").hide();
		$("#reportType").hide();
		
		$("#inventoryValue").hide();
		$("#inventoryLevel").hide();
		
		if ( selection == 'Inventory Value' ) {
			loadAndShowDailyInventoryValues();
		}
		else if ( selection == 'Stock Levels') {
			loadAndShowInventoryLevels();
		} else {
			
		}
		
		
	});
});


