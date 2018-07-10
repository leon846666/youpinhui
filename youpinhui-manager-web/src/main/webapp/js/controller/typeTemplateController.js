 //Controller
app.controller('typeTemplateController' ,function($scope,$controller   ,typeTemplateService,brandService,specificationService){	
	
	$controller('baseController',{$scope:$scope});//inheritance
	
	$scope.brandList={data:[]};

    //test case , get all the data return a Json
	$scope.findAll=function(){
		typeTemplateService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//pagination
	$scope.findPage=function(page,rows){			
		typeTemplateService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the search result
			}			
		);
	}
	
	//find one object 
	$scope.findOne=function(id){				
		typeTemplateService.findOne(id).success(
			function(response){
				$scope.entity= response;	
				$scope.entity.brandIds=JSON.parse($scope.entity.brandIds);		
				$scope.entity.specIds=JSON.parse($scope.entity.specIds);	
				$scope.entity.customAttributeItems=JSON.parse($scope.entity.customAttributeItems);	
				
			}
		);				
	}
	
	//save 
	$scope.save=function(){				
		var serviceObject; 				
		if($scope.entity.id!=null){//if it contains an id
			serviceObject=typeTemplateService.update( $scope.entity ); //update   
		}else{
			serviceObject=typeTemplateService.add( $scope.entity  );//add
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
		typeTemplateService.dele( $scope.selectIds ).success(
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
		typeTemplateService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the total result
			}			
		);
	}

	$scope.findBrandList=function(){
		brandService.selectOptionList().success(
			function(response){
				$scope.brandList={data:response};
			}
		)
	}

	$scope.specList={data:[]};
	$scope.findSpecList =function(){
		specificationService.selectOptionList().success(
			function(response){
				$scope.specList={data:response};
			}
		)
	}

	//add an row 
	$scope.addTableRow=function(){
		$scope.entity.customAttributeItems.push({});
	}

	//delete an row 
	$scope.deleteRow=function(index){
		$scope.entity.customAttributeItems.splice(index,1);
	}
	
});	
