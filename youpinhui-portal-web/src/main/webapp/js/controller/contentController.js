app.controller('contentController',function($scope,$location,contentService){

    $scope.contentList=[];
    $scope.findByCategoryId=function(id){
        contentService.findByCategoryId(id).success(
            function(response){
                $scope.contentList[id]=response;
            }
        )

    }

    $scope.search=function(keywords){

    	location.href="http://localhost:9104/search.html#?keywords="+keywords;
    	
    }

})