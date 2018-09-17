 //Controller
app.controller('userController' ,function($scope,$controller   ,userService){	
	
	$scope.reg=function(){
      
        if($scope.password!=$scope.entity.password){
            alert("please make sure your passwords are the same")
            return;
        }


       userService.add($scope.entity,$scope.verifiCode).success(
           function(response){
               alert(response.message);
           }
       ); 
    }
	
	//send verification code
	
	$scope.sendCode=function(){
		alert($scope.entity.phone);
		if($scope.entity.phone==null||$scope.entity.phone==""){
			alert("you must enter your phone number")
			return;
		}
		
		
		userService.send($scope.entity.phone).success(
				function(response){
					alert(response.message);
				}
		)
	}
    
});	
