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

function selectFlight(){
	
	retrieveAirportCity($("#flight").val()); 
}

function flightStatusEvent() {
	retrieveFlightStatus($("#flight").val());
}

function languageExpressionsEvent() {
	retrieveLanguageExpressions($("#city").val());
}