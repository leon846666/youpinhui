app.controller("searchController",function($scope,$location,searchService){
    
    //define a searchMap object
    $scope.searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':40,'sortField':'','sortWay':''};
    
    //load key word
    $scope.loadKeywords=function(){
    	$scope.searchMap.keywords=$location.search()['keywords'];
    	$scope.search();
    }
    
    //check if the keyword is a brand
    $scope.keywordsIsBrand=function(){		
        for(var i=0;i< $scope.resultMap.brandList.length;i++){			
            if( $scope.searchMap.keywords.indexOf( $scope.resultMap.brandList[i].text )>=0  ){
                return true;				
            }			
        }
        return false;
    }

  
    //define a pagination label
    $scope.searchBySort=function(sortField,sortWay){
        $scope.searchMap.sortField=sortField;
        $scope.searchMap.sortWay=sortWay;
        $scope.search();

    }
    //search 
    $scope.search=function(){
        $scope.searchMap.pageNo=parseInt( $scope.searchMap.pageNo);
        searchService.search($scope.searchMap).success(
            function(response){
                   $scope.resultMap=response; 
                   console.log($scope.resultMap.brandList);
                  // $scope.searchMap.pageNo=1;
                   buildPagination();
                  
                   
            }
        )
    }
    $scope.isFirstPage=function(){
        if($scope.searchMap.pageNo==1){
            return true;
        }
        return false;
    }

    $scope.isLastPage=function(){
        if($scope.searchMap.pageNo==$scope.resultMap.totalPage){
            return true;
        }
        return false;
    }


    buildPagination=function(){
        $scope.pageLabel=[];
        var firstPage=1;
        var lastPage= $scope.resultMap.totalPage;

        if($scope.resultMap.totalPage>5){

            if($scope.searchMap.pageNo<=3){
                lastPage=5;
            }else if($scope.searchMap.pageNo>=$scope.resultMap.totalPage-2){
                firstPage=$scope.resultMap.totalPage-4;
            }else{
                firstPage=$scope.searchMap.pageNo-2;
                lastPage=$scope.searchMap.pageNo+2;
            }

        }


        for (let index = firstPage; index <=  lastPage; index++) {
         $scope.pageLabel.push(index);
        }
    }

    $scope.searchByClickPage=function(page){
        if(page<1|| page>$scope.resultMap.totalPage){
            return;
        }
        $scope.searchMap.pageNo=page;
        $scope.search();
    }

    // add search option, change the value of searchMap object
    $scope.addSearchItem=function(key,value){

        if(key=='category'||key=='brand'||key=='price'){
            // if user choose category or brand 
            $scope.searchMap[key]=value;
        }else{
            // user choose specification 
            $scope.searchMap.spec[key]=value;
        }

        $scope.search();
    }

    $scope.removeSearchItem=function(key){
       
        if(key=='category'||key=='brand'||key=='price'  ){
            // if user choose category or brand 
            $scope.searchMap[key]='';
        }else{
            // user choose specification 
            delete   $scope.searchMap.spec[key];
        }
    }


})