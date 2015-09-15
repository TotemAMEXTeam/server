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

	/*
	var placesDiv = $("#content");
	placesDiv.empty();
	
	placesDiv.append("<div id=\"flights\"></div>");
	placesDiv =  $("#flights");
	
	for(var i=0;i<flights.length;i++){
	
		var flight = flights[i];
		placesDiv.append("<div id=" + flight.flightNumber + " class=\"flight\">" + flight.flightNumber + "</div>");
	}
	*/
	$.each(flights, function (i, flight) {
	    $('#flight').append($('<option>', { 
	        value: flight.destiny,
	        text : flight.flightNumber 
	    }));
	});
}