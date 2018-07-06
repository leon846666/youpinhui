app.controller("brandController",function($scope,$http,brandService){
    
    $scope.selectIds=[];
    $scope.searchEntity={};

    $scope.findAllBrand=function(){
        brandService.findAllBrand().success(
            function(response){
                $scope.list=response;
            }		
        )
        
    }
    $scope.reloadList=function(){
         //切换页码  
        $scope.search( $scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }
    
    $scope.paginationConf = {
         currentPage: 1,
         totalItems: 10,
         itemsPerPage: 10,
         perPageOptions: [10, 20, 30, 40, 50],
         onChange: function(){
                     $scope.reloadList();//重新加载
 }
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
 
  $scope.selectUpdate=function($event,id){
      if($event.target.checked){
          $scope.selectIds.push(id);
      }else{
        var index= $scope.selectIds.indexOf(id);
        $scope.selectIds.splice(index,1);
      }
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

