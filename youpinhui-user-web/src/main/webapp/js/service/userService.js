//Sevice
app.service('userService',function($http){


    //add 
	this.add=function(entity,verifiCode){
		return  $http.post('../user/add.do?verifiCode='+verifiCode,entity );
	}	
	//send verification code
	
	this.send=function(phone){
		alert(phone)
		return $http.get('../user/sendVerifiCode.do?phone='+phone);
	}
});
