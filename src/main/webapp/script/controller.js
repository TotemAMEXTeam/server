function clickEvent(){
	
	var city = $("#city").val();
	
	loadCity(city);
}

function loadCity(city){
	
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
	
	if(data.length == 0){
		
		return;
	}

	var index = 0;	
	while(index < data.length && data[index].photoId == null){}
	
	if(index == data.length && data.length > 0){
		
		index = 0;
	}
		
	var activity = data[index];
	
	$("#name").text(activity.name);
	$("#address").text(activity.addressString);
	$("#priceLevel").text(activity.priceLevel);
	$("#rating").text(activity.rating);
	
	$("#photo").attr("src", "http://localhost:8080/server/rest/places/image/" + activity.photoId);
}