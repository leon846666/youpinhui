app.controller('indexController',function($scope,loginService){

    //display the current login user name
    $scope.getLoginUserName=function(){
        loginService.getLoginUsername().success(
            function(response){
                $scope.loginName=response.loginName;
            }
        )
    }

})