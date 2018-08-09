app.controller("searchController",function($scope,searchService){
    
    //define a searchMap object
    $scope.searchMap={'keywords':'','category':'','brand':'','spec':{}};
    $scope.search=function(){

        searchService.search($scope.searchMap).success(
            function(response){
                   $scope.resultMap=response; 
            }
        )
    }

    // add search option, change the value of searchMap object
    $scope.addSearchItem=function(key,value){

        if(key=='category'||key=='brand'){
            // if user choose category or brand 
            $scope.searchMap[key]=value;
        }else{
            // user choose specification 
            $scope.searchMap.spec[key]=value;
        }

        $scope.search();
    }

    $scope.removeSearchItem=function(key){
       
        if(key=='category'||key=='brand'){
            // if user choose category or brand 
            $scope.searchMap[key]='';
        }else{
            // user choose specification 
            delete   $scope.searchMap.spec[key];
        }
    }


})