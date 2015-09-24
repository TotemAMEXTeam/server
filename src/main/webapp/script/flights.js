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
	        value: flight.flightNumber + " - " + flight.destiny + " " + flight.scheduledDeparture,
	        text : flight.flightNumber + " - " + flight.destiny
	    }));
	});
}

function retrieveAirportCity(city){
	
	var regexp = /(\w+)\s-\s(.*)\((.*)\)/;
	var match = regexp.exec(city);
	var nameX = match[2];
	var name = $.trim(nameX);
	var code = match[3];
	
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

function retrieveFlightStatus(flightNo) {
	
	var regexp = /(\w+)\s-.*\s(\d+)/;
	var match = regexp.exec(flightNo);
	var num = match[1];
	var jsonDate = match[2];
	var date = new Date(parseInt(jsonDate));
	var scheduledDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+"+"+date.getHours()+":"+date.getMinutes();
	$.ajax({
    	type: "GET",
        url: "http://localhost:8080/server/rest/flight/flightStatus/"+num+"?hprevista="+scheduledDate,
        dataType: 'json',
        async: true,
        success: function(flightStatus){       
        	loadFlightStatus(flightStatus);
        } 
    });	
}

function completeZeros(number){
	
	var result = number;

	if(number.toString().length == 1){
		
		result =  "0"+number;
	}

	return result;
}

function loadFlightStatus (flightStatus) {
	var flightDiv = $("#content");
	flightDiv.empty();
	flightDiv.append("<div id=\"flightStatus\" class=\"flightStatus\"></div>");
	flightDiv = $("#flightStatus");
	flightDiv.append("<div id=" + flightStatus.flight.flightNumber + ">Identificador de vuelo: " + flightStatus.flight.flightNumber+"</div>");
	flightDiv.append("<div id=" + flightStatus.flight.origin + ">Origen: "+ flightStatus.flight.origin+ "</div>");
	flightDiv.append("<div id=" + flightStatus.flight.destiny + ">Destino: "+flightStatus.flight.destiny +"</div>");
	flightDiv.append("<div id=" + flightStatus.flight.company + ">Compania: "+flightStatus.flight.company +"</div>");
	var date = new Date((parseInt(flightStatus.flight.scheduledDeparture)));
	var scheduledDate = completeZeros(date.getDate())+"/"+completeZeros(date.getMonth()+1)+"/"+completeZeros(date.getFullYear())+" "+completeZeros(date.getHours())+":"+completeZeros(date.getMinutes());
	flightDiv.append("<div id=" + scheduledDate + ">Salida programada: "+scheduledDate +"</div>");
	flightDiv.append("<div id=" + flightStatus.status + ">Estado: "+flightStatus.status +"</div>");
	flightDiv.append("<div id=" + flightStatus.originTerminal + ">Terminal origen: "+flightStatus.originTerminal +"</div>");
	if (flightStatus.destinyTerminal != null) {
		flightDiv.append("<div id=" + flightStatus.destinyTerminal + ">Terminal destino: "+flightStatus.destinyTerminal +"</div>");
	}
	var date = new Date((parseInt(flightStatus.estimatedDeparture)));
	var estimatedDate = completeZeros(date.getDate())+"/"+completeZeros((date.getMonth()+1))+"/"+completeZeros(date.getFullYear())+" "+completeZeros(date.getHours())+":"+completeZeros(date.getMinutes())
	flightDiv.append("<div id=" + flightStatus.estimatedDate + ">Salida estimada: "+estimatedDate +"</div>");
	if (flightStatus.estimatedArrival != null) {
		var date = new Date((parseInt(flightStatus.estimatedArrival)));
		var estimatedArrival = date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()
		flightDiv.append("<div id=" + flightStatus.estimatedArrival + ">Llegada estimada: "+estimatedArrival +"</div>");
	}
	flightDiv.append("<div id=" + flightStatus.checkInCounter + ">Mostrador de checkin: "+flightStatus.checkInCounter +"</div>");
	flightDiv.append("<div id=" + flightStatus.boardingGate + ">Puerta de embarque: "+flightStatus.boardingGate +"</div>");
	if (flightStatus.hall != null) {
		flightDiv.append("<div id=" + flightStatus.hall + ">Sala de llegada: "+flightStatus.hall +"</div>");
	}
	if (flightStatus.belt != null) {
		flightDiv.append("<div id=" + flightStatus.belt + ">Cinta de llegada: "+flightStatus.belt +"</div>");
	}
}



