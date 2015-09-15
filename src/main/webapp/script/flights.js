/**
 * 
 */
var cities = [];
var flights = [];

function retrieveFlights(origin){
	
	$.ajax({
    	type: "GET",
        url: "http://localhost:8080/server/rest/flight/dailyFlights/" + origin,        
        dataType: 'json',
        async: false,
        success: function(flights){
        
        	loadFlights(flights);
        } 
    });	
}

function loadFlights(flights){

	$.each(flights, function (i, flight) {
	    $('#flight').append($('<option>', { 
	        value: flight.destiny,
	        text : flight.flightNumber + " - " + flight.destiny 
	    }));
	});
}

function retrieveAirportCity(city){
	
	var regexp = /(.*)\((.*)\)/;
	var match = regexp.exec(city);
	var name = match[1];
	var code = match[2];
	
	$.ajax({
    	type: "GET",
        url: "http://localhost:8080/server/rest/airport/retrieveCity",
        data:{
        	name: name,
        	code: code},
        dataType: 'text',
        async: true,
        success: loadCityName
    });
}

function loadCityName(cityName){
	
	$("#city").val(cityName);	
}