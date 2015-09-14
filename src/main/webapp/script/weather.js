/**
 * 
 */

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
	
	placesDiv.append("<div id=\"forecastList\"> </div>");
	placesDiv =  $("#forecastList");
		
	for(var i=0;i<forecast5Days.length;i++){
	
		var forecast = forecast5Days[i];
		
		var date = new Date(parseInt(forecast.timestamp));
		placesDiv.append("<div id=" + forecast.timestamp + " class=\"forecast\"> </div>");
		
		var activityContentDiv = $("#" + forecast.timestamp);
		
		activityContentDiv.append("<div class=\"forecastTitle\">" + date.getDate() + "/" + date.getMonth() + "/" + date.getFullYear() + "</div>");
		activityContentDiv.append("<div>Temp: " + Math.round(forecast.temperature.max) + " - " + Math.round(forecast.temperature.min) + " Hum: " + forecast.humidity + "%</div>");
		activityContentDiv.append("<div>Precip: " + Math.round(forecast.precipitation.rain*100)/100 + "mm Viento: " + Math.round(forecast.wind.speed) + " Km/h " + forecast.wind.headingString + "</div>");
		activityContentDiv.append("<div>" + capitalizeFirstLetter(forecast.summary.description) + "</div>");
		activityContentDiv.append("<img src=\"http://openweathermap.org/img/w/" + forecast.summary.icon  + ".png\" class=\"icon\"/>");
	}
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}