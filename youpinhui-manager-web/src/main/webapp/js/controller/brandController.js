app.controller("brandController",function($scope,$controller,$http,brandService){
  
	$controller('baseController',{$scope:$scope});
	
    $scope.findAllBrand=function(){
        brandService.findAllBrand().success(
            function(response){
                $scope.list=response;
            }		
        )
        
    }
   
  
   $scope.findPage=function(page,size){
        brandService.findPage(page,size).success(
                function(response){
                    $scope.list=response.rows;
                    $scope.paginationConf.totalItems=response.total;
                })
    }
    
    
  $scope.save=function(){
     var obj =null;
        if($scope.entity.id!=null){
            obj = brandService.update($scope.entity);
        }else{
            obj = brandService.add($scope.entity);
        }			  
        obj.success(
              function(response){
              if(response.success){
                  $scope.reloadList();
              }else{
                  alert(response.message);
              }
      })
  }	
  
  $scope.findOne=function(id){
     brandService.findOne(id).success(
              function(response){
                  $scope.entity=response;
              })
  }
 
 
  
  
  $scope.delete=function(){
      if($scope.selectIds.length<1){
          return;
      }
    brandService.delete($scope.selectIds).success(function(response){
        if(response){
            $scope.reloadList();
            $scope.selectIds=[];
        }else{
            alert(response.message);
        }
    })
      
  }

  $scope.search=function(page,size){
        brandService.search(page,size,$scope.searchEntity).success(
                function(response){
                    $scope.list=response.rows;
                    $scope.paginationConf.totalItems=response.total;
                })
    }
})

