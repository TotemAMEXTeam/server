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

function retrieveWeather(city){
	
	$.ajax({
    	type: "GET",
        url: "http://localhost:8080/server/rest/weather/retrieveForecast/" + city,        
        dataType: 'json',
        async: false,
        success: function(forecast){
        
        	loadWeather(forecast);
        } 
    });	
}

function loadWeather(forecast5Days){
	
	var placesDiv = $("#content");
	placesDiv.empty();
	
	placesDiv.append("<div id=\"forecastList\"></div>");
	placesDiv =  $("#forecastList");
	
	for(var i=0;i<forecast5Days.length;i++){
	
		var forecast = forecast5Days[i];
		
		var date = new Date(parseInt(forecast.timestamp));
		
		placesDiv.append("<span id=" + forecast.timestamp + " class=\"forecast\"></span>");
		
		var activityContentDiv = $("#" + forecast.timestamp);
		
		activityContentDiv.append("<div>Fecha: " + date + "</div>");
		activityContentDiv.append("<div>T. max: " + forecast.temperature.max + "</div>");
		activityContentDiv.append("<div>T. min: " + forecast.temperature.min + "</div>");
		activityContentDiv.append("<div>Humedad: " + forecast.humidity + "</div>");
		activityContentDiv.append("<div>Presión: " + forecast.pressure + "</div>");
		activityContentDiv.append("<div>Resumen: " + forecast.summary.description + "</div>");
		activityContentDiv.append("<img src=\"http://openweathermap.org/img/w/" + forecast.summary.icon  + ".png\" class=\"icon\"/>");
	}
}

function loadMuseums(city){
	
	$.ajax({
    	type: "GET",
        url: "http://localhost:8080/server/rest/geo/locate/" + city,        
        dataType: 'json',
        async: false,
        success: function(coord){
        
        	retrieveActivities(coord);
        } 
    });
}

function loadRestaurantes(city){
	
	$.ajax({
    	type: "GET",
        url: "http://localhost:8080/server/rest/geo/locate/" + city,        
        dataType: 'json',
        async: false,
        success: function(coord){
        
        	retrieveRestaurantes(coord);
        } 
    });
}

function retrieveRestaurantes(coords){
	 
    $.ajax({
    	type: "GET",
        url: "http://localhost:8080/server/rest/places/restaurants/",
        data: coords,
        dataType: 'json',
        async: false,
        success: loadActivities
    });
}

function retrieveActivities(coords){
 
    $.ajax({
    	type: "GET",
        url: "http://localhost:8080/server/rest/places/activities/",
        data: coords,
        dataType: 'json',
        async: false,
        success: loadActivities
    });
}

function loadActivities(data){
	
	var placesDiv = $("#content");
	placesDiv.empty();
	
	for(var i=0;i<data.length;i++){
	
		var activity = data[i];

		placesDiv.append("<div id=" + activity.id + " class=\"activity\"><div id=contentDiv" + activity.id + " class=\"content\"></div><div id=photoDiv" + activity.id + " class=\"photo\"></div></div>");
		
		var activityContentDiv = $("#contentDiv" + activity.id);
		
		activityContentDiv.append("<div>Nombre: " + activity.name + "</div>");
		activityContentDiv.append("<div>Dirección: " + activity.addressString + "</div>");
		activityContentDiv.append("<div>Precio: " + activity.priceLevel + "</div>");
		activityContentDiv.append("<div>Puntuación: " + activity.rating + "</div>");
		
		if(activity.photoId != null){
		
			var activityPhotoDiv = $("#photoDiv" + activity.id);		
			activityPhotoDiv.append("<img id=photo" + activity.id + " src=\"http://localhost:8080/server/rest/places/image/" + activity.photoId  + "\" class=\"photoImg\"/>");
		}
	}
	
	
	
	
	
	
	//<img id="photo" alt="Fotografia" src=""/>
	
	//
}