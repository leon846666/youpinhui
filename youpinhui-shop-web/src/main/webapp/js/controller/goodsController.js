 //Controller
app.controller('goodsController' ,function($scope,$controller   ,goodsService,uploadService,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//inheritance
	
    //test case , get all the data return a Json
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//pagination
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the search result
			}			
		);
	}
	
	//find one object 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//save 
	$scope.save=function(){				
		var serviceObject; 				
		if($scope.entity.id!=null){//if it contains an id
			serviceObject=goodsService.update( $scope.entity ); //update   
		}else{
			serviceObject=goodsService.add( $scope.entity  );//add
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
		//save 
		$scope.add=function(){	
			$scope.entity.goodsDesc.introduction=editor.html();			
			goodsService.add( $scope.entity  ).success(
				function(response){
					if(response.success){
						alert("added a new goods");
						$scope.entity={};
						$scope.reloadList();//reload page
						editor.html('');
					}else{
						alert(response.message);
					}
				}		
			);				
		}
		
	
	 
	//batch delete
	$scope.dele=function(){			
		//receive the selected id from checkbox 		
		goodsService.dele( $scope.selectIds ).success(
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
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//update the total result
			}			
		);
	}

	$scope.uploadImage=function(){
		uploadService.uploadImage().success(
			function(response){
				if(response.success){
					$scope.image_entity.url=response.message; 
				}else{
					alert(response.message)
				}
			}
		)
	}
	$scope.entity={goods:{},goodsDesc:{itemImages:[]} }
	$scope.add_images=function(){
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
	}

	$scope.deleImages=function(index){
		$scope.entity.goodsDesc.itemImages.splice(index,1);
	}

	$scope.findItemCate=function(){
		itemCatService.findByParentId(0).success(
			function(response){
				$scope.itemCateList1 = response;
			}
		)
	}

	// get the level2  itemCategory
	$scope.$watch("entity.goods.categort1Id",function(newValue,oldValue){
		$scope.itemCateList3={};
		$scope.entity.goods.typeTemplateId=null;
		itemCatService.findByParentId(newValue).success(
			function(response){
				$scope.itemCateList2 = response;
			}
		)
	}
	)	

	//get the level 3 item Category
	$scope.$watch("entity.goods.categort2Id",function(newValue,oldValue){
		$scope.entity.goods.typeTemplateId=null;
		itemCatService.findByParentId(newValue).success(
			function(response){
				$scope.itemCateList3 = response;
			}
		)
	}
	)	
		//get the template id from the selected level3category 
	$scope.$watch("entity.goods.categort3Id",function(newValue,oldValue){
		itemCatService.findOne(newValue).success(
			function(response){
				$scope.entity.goods.typeTemplateId=response.typeId;
			}
		)
		
	}
	)


})