app.controller("itemController",function($scope){
    

	$scope.specificationItems={};

    $scope.number=1;

    $scope.addNumber=function(x){

    	    $scope.number+=x;

    	if($scope.number<1){
    		$scope.number=1;
    	}

    }

    $scope.selectSpecification=function(key,value){
    	$scope.specificationItems[key]=value;
    	searchSku();
    }
    
    $scope.isSelected=function(key,value){
    	if($scope.specificationItems[key]==value){
    		return true;
    	}else{
    		return false;
    	}
    	;
    }

    $scope.sku={};

    $scope.loadSku=function(){
    	$scope.sku=skuList[0];
    	$scope.specificationItems=JSON.parse(JSON.stringify($scope.sku.spec));
    }


    //check if the chosen specification is equal to the spec in skuList
    matchSpec=function(map1,map2){

        for(var k in map1){
            if(map1[k]!=map2[k]){
                return false;
            }
        }

        for(var k in map2){
            if(map2[k]!=map1[k]){
                return false;
            }
        }

        return true;
    }


    //searchSku
    searchSku=function(){

        for(var i=0;i<skuList.length;i++){
            if(matchSpec(skuList[i].spec,$scope.specificationItems)){
            	$scope.sku=skuList[i]
            	return;

            }
        }
        $scope.sku={id:'n/a',title:'item not available',price:'n/a'}
    }


    //add to cart
    $scope.addToCart=function(){
    	alert("id "+$scope.sku.id);
    }

})