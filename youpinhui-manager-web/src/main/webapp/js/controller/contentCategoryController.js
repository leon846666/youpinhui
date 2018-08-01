 //Controller
app.controller('contentCategoryController' ,function($scope,$controller   ,contentCategoryService){	
	
	$controller('baseController',{$scope:$scope});//inheritance
	
    //test case , get all the data return a Json
	$scope.findAll=function(){
		contentCategoryService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//pagination
	$scope.findPage=function(page,rows){			
		contentCategoryService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the search result
			}			
		);
	}
	
	//find one object 
	$scope.findOne=function(id){				
		contentCategoryService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//save 
	$scope.save=function(){				
		var serviceObject; 				
		if($scope.entity.id!=null){//if it contains an id
			serviceObject=contentCategoryService.update( $scope.entity ); //update   
		}else{
			serviceObject=contentCategoryService.add( $scope.entity  );//add
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
		contentCategoryService.dele( $scope.selectIds ).success(
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
		contentCategoryService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the total result
			}			
		);
	}
    
});	
