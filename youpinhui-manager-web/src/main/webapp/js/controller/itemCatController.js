 //Controller
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService,typeTemplateService){	
	
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
		//	alert("update")
			serviceObject=itemCatService.update( $scope.entity ); //update   
		}else{
			$scope.entity.parentId=$scope.parentId;
		
			serviceObject=itemCatService.add( $scope.entity  );//add
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					 
		        	$scope.findByParentId($scope.parentId);//reload page
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//batch delete
	$scope.dele=function(){			
		//receive the selected id from checkbox 		
		//alert($scope.selectIds)
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.findByParentId($scope.parentId);//reload page
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
	
    $scope.parentId=0;
	$scope.findByParentId=function(parentId){
	
		$scope.parentId=parentId;
		itemCatService.findByParentId(parentId).success(
			function(response){
				$scope.list=response;
			}
		)
	}

	$scope.level=1;
	$scope.setLevel=function(value){
		$scope.level=value;
	}

	$scope.changeCate=function(p_entity){
		if($scope.level==1){
			$scope.entity_1=null;
			$scope.entity_2=null;
		}
		if($scope.level==2){
			$scope.entity_1=p_entity;
			$scope.entity_2=null;
		}1
		if($scope.level==3){
			$scope.entity_2=p_entity;
		}

		$scope.findByParentId(p_entity.id);

	}

	$scope.options={type_template:[]};
	$scope.findTypeList =function(){
		
		typeTemplateService.selectOptionList().success(
			function(response){
				$scope.options={type_template:response};
				//alert($scope.options['type_template'])
				$("#typeTempList").select2({
					data:$scope.options['type_template']
				})
			}
		)
	}



});	
