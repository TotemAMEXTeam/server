/**
 * 
 */

var language = "";

function retrieveLanguageExpressions (city) {
	$.ajax({
    	type: "GET",
        url: "http://localhost:8080/server/rest/language/retrieve?city=" + city,        
        dataType: 'json',
        async: false,
        success: function(language) {
	        retrieveExpressions(language);
        } 
    });	
}

function retrieveExpressions (language) {
	$.ajax({
    	type: "GET",
        url: "http://localhost:8080/server/rest/language/retrieveExpressions?originLanguage=es&destinyLanguage=" + language.code,        
        dataType: 'json',
        async: false,
        success: function(expressions) {
	        paintExpressions(expressions);
        } 
    });
}

function paintExpressions (data) {
	var expDiv = $("#content");
	expDiv.empty();
	
	for(var i=0;i<data.length;i++){
	
		var expression = data[i];

		expDiv.append("<div id=" + expression.id + " class=\"expression\"><div id=contentDiv" + expression.id + " class=\"content\"></div></div>");
		
		var expressionDiv = $("#contentDiv" + expression.id);
		
		expressionDiv.append("<div>" + expression.destinyExpression + "</div>");
		expressionDiv.append("<div>" + expression.originExpression + "</div>");
		expressionDiv.append("<div>" + expression.explanation + "</div>");
	}
}