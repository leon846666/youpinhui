 //Controller
app.controller('contentController' ,function($scope,$controller ,uploadService,contentCategoryService  ,contentService){	
	
	$controller('baseController',{$scope:$scope});//inheritance
	
    //test case , get all the data return a Json
	$scope.findAll=function(){
		contentService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//pagination
	$scope.findPage=function(page,rows){			
		contentService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the search result
			}			
		);
	}
	
	//find one object 
	$scope.findOne=function(id){				
		contentService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//save 
	$scope.save=function(){				
		var serviceObject; 				
		if($scope.entity.id!=null){//if it contains an id
			serviceObject=contentService.update( $scope.entity ); //update   
		}else{
			serviceObject=contentService.add( $scope.entity  );//add
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
		alert($scope.selectIds)		
		contentService.dele( $scope.selectIds ).success(
			
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
		contentService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the total result
			}			
		);
	}

	//uploadFile
	$scope.uploadImage=function(){
		uploadService.uploadImage().success(
			function(response){
				if(response.success){
					$scope.entity.pic=response.message; 
				}else{
					alert(response.message)
				}
			}
		)
	}

	//find content category List

	$scope.findContentCateList=function(){
		contentCategoryService.findAll().success(
			function(response){
				
					$scope.contentCateList=response;
				
			}
		)
	}

	$scope.status=['invalid','valid'];
    
});	
