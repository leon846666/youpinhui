 //Controller
app.controller('specificationController' ,function($scope,$controller,specificationService){	
	
	$controller('baseController',{$scope:$scope});//inheritance
	
	//an entity contains specification and specificationOptionList
	$scope.entity={specification:{},specificationOptionList:[]};
	
    //test case , get all the data return a Json
	$scope.findAll=function(){
		specificationService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//pagination
	$scope.findPage=function(page,rows){			
		specificationService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the search result
			}			
		);
	}
	
	//find one object
	$scope.findOne=function(id){				
		specificationService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//save
	$scope.save=function(){				
		var serviceObject;			
		if($scope.entity.specification.id!=null){//if it contains an id
			serviceObject=specificationService.update( $scope.entity ); //update  
		}else{
			serviceObject=specificationService.add( $scope.entity  );//add
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
		specificationService.dele( $scope.selectIds ).success(
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
		
		specificationService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the search result
			}			
		);
	}

	
	
	$scope.addOption=function(){
		$scope.entity.specificationOptionList.push({});
	}

	$scope.deleTableRow=function(index){
		$scope.entity.specificationOptionList.splice(index,1);
	}


    
});	
