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

    $scope.jsonToString=function(jsonString,key){

        var json = JSON.parse(jsonString);
        var value="";
        for (let i = 0; i < json.length; i++) {
          if(i>0){
              value+=","
          }
            value+=json[i][key];
        }
        return value;
    }

    // a function to search if a list contains a certain key -value.
    // if it contains return the object in the list
    // if it doesn't return null;
    $scope.searchObjectByKey=function(list,key,keyValue){
        for (let index = 0; index < list.length; index++) {
          
            if(list[index][key]==keyValue){
                return list[index];
            }
        }
        return null;
    }
       
	
})