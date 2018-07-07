app.controller('baseController',function($scope){
	 
    $scope.selectIds=[];
    $scope.searchEntity={};

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
    $scope.selectUpdate=function($event,id){
        if($event.target.checked){
            $scope.selectIds.push(id);
        }else{
          var index= $scope.selectIds.indexOf(id);
          $scope.selectIds.splice(index,1);
        }
    }
       
	
})