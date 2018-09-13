 //Controller
app.controller('userController' ,function($scope,$controller   ,userService){	
	
	$scope.reg=function(){
        alert("asd");
        if($scope.password!=$scope.entity.password){
            alert("please make sure your passwords are the same")
            return;
        }


       userService.add($scope.entity).success(
           function(response){
               alert(response.message);
           }
       ); 
    }
    
});	
