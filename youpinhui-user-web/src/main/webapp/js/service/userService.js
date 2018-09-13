//Sevice
app.service('userService',function($http){


    //add 
	this.add=function(entity){
		return  $http.post('../user/add.do',entity );
	}	
	  	
});
