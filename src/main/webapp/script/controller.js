function weatherEvent(){

	var city = $("#city").val();
	
	retrieveWeather(city);
}

function activitiesEvent(){
	
	var city = $("#city").val();
	
	loadMuseums(city);
}

function restaurantesEvent(){
	
	var city = $("#city").val();
	
	loadRestaurantes(city);
}



function retrieveFlights(origin){
	
	$.ajax({
    	type: "GET",
        url: "http://localhost:8080/server/rest/dailyFlights/" + origin,        
        dataType: 'json',
        async: false,
        success: function(flights){
        
        	loadFlights(flights);
        } 
    });	
}

function loadFlights(flights){
	
	alert(flights[0].flightNumber);
	
	var placesDiv = $("#content");
	placesDiv.empty();
	
	placesDiv.append("<div id=\"flights\"></div>");
	placesDiv =  $("#flights");
	
	for(var i=0;i<flights.length;i++){
	
		var flight = flights[i];
		placesDiv.append("<div id=" + flight.flightNumber + " class=\"flight\">" + flight.flightNumber + "</div>");
	}
}