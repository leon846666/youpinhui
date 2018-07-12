 //Controller
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//inheritance
	
    //test case , get all the data return a Json
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//pagination
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the search result
			}			
		);
	}
	
	//find one object 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//save 
	$scope.save=function(){				
		var serviceObject; 				
		if($scope.entity.id!=null){//if it contains an id
			serviceObject=itemCatService.update( $scope.entity ); //update   
		}else{
			serviceObject=itemCatService.add( $scope.entity  );//add
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					 
		        	$scope.reloadList();//reload page
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//batch delete
	$scope.dele=function(){			
		//receive the selected id from checkbox 		
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//reload page
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//define a search entity
	
	//search
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the total result
			}			
		);
	}
    
	$scope.findByParentId=function(parentId){
		itemCatService.findByParentId(parentId).success(
			function(response){
				$scope.list=response;
			}
		)
	}
});	
