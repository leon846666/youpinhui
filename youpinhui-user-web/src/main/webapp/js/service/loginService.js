
//Sevice
app.service('loginService',function($http){


    //add 
	this.getLoginName=function(){
		return $http.get('../login/getLoginName.do');
	}
	
});
