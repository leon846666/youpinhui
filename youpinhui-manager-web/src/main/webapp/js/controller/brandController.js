app.controller("brandController",function($scope,$http,brandService){
    $scope.findAllBrand=function(){
        brandService.findAllBrand().success(
            function(response){
                $scope.list=response;
            }		
        )
        
    }
   
    $scope.reloadList=function(){
         //change 
        $scope.findPage( $scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }
    
    $scope.paginationConf = {
         currentPage: 1,
         totalItems: 10,
         itemsPerPage: 10,
         perPageOptions: [10, 20, 30, 40, 50],
         onChange: function(){
                     $scope.reloadList();//reload
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
  
	  brandService.add($scope.entity).success(
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
 
})

