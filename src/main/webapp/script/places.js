/**
 * 
 */
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
		activityContentDiv.append("<div>Direccion: " + activity.addressString + "</div>");
		activityContentDiv.append("<div>Tlf: " + activity.phone + "</div>");
		activityContentDiv.append("<div>Precio: " + activity.priceLevel + " Puntuacion: " + activity.rating + "</div>");		
		
		if(activity.photoId != null){
		
			var activityPhotoDiv = $("#photoDiv" + activity.id);		
			activityPhotoDiv.append("<img id=photo" + activity.id + " src=\"http://localhost:8080/server/rest/places/image/" + activity.photoId  + "\" class=\"photoImg\"/>");
		}
	}
}