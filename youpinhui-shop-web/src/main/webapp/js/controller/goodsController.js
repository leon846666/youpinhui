 //Controller
app.controller('goodsController' ,function($scope,$controller   ,goodsService,uploadService,itemCatService,typeTemplateService){	
	
	$controller('baseController',{$scope:$scope});//inheritance
	
	$scope.entity={goods:{},goodsDesc:{itemImages:[],specficationItems:[]} }

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

		//search the brand name by the typeTemplateID, because all the brands is matched by the templateId in the 
		// tb_type_template 
	$scope.$watch("entity.goods.typeTemplateId",function(newValue,oldValue){
	
		typeTemplateService.findOne(newValue).success(
			function(response){
				//alert(newValue);
				$scope.typeTemplate=response;
				//console.log($scope.typeTemplate);
				$scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);
				$scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.typeTemplate.customAttributeItems);
				//alert(	$scope.typeTemplate.customAttributeItems);
			}
		)
		typeTemplateService.findSpecList($scope.entity.goods.typeTemplateId).success(
			function(response){
				$scope.specList= response;
			}
		)
			
	}
	)

	$scope.updateSpecOptions=function($event,name,value){
		//the obj format
	// $scope.entity.goodsDesc.specficationItems=[]
	//$scope.entity.goodsDesc.specficationItems = [{"attributeName":"xx","attributeValue":["xxx","xxx"]}]
		var obj=$scope.searchObjectByKey($scope.entity.goodsDesc.specficationItems,"attributeName",name);
		//alert(obj)
		if(obj!=null){
			if($event.target.checked){
				obj.attributeValue.push(value);
			}else{
				obj.attributeValue.splice( obj.attributeValue.indexOf(value),1);
				if(obj.attributeValue.length==0){
					$scope.entity.goodsDesc.specficationItems.splice(
						$scope.entity.goodsDesc.specficationItems.indexOf(obj.attributeValue),1);
				}
			}
		}
		else{
			$scope.entity.goodsDesc.specficationItems.push({"attributeName":name,"attributeValue":[value]})
		}
	}

	// create SKU list 
	
	$scope.createItemList=function(){

		// a init variable 
		$scope.entity.itemList=[
			{ 
				spec:{},
				price:0, 
				number:9999,
				status:'0',
				isDefault:'0'
			 }];
		var items=$scope.entity.goodsDesc.specficationItems;
		
		for (let i = 0; i < items.length; i++) {
			$scope.entity.itemList=	addColumn(	$scope.entity.itemList,items[i].attributeName, items[i].attributeValue);
		}
	}

	addColumn=function(list,columnName,columnValues){
		var newList=[];
	
		for (let i = 0; i < list.length; i++) {
			var oldRow=list[i];
			for (let j = 0; j < columnValues.length; j++) {
				//deep clone
				var newRow =  JSON.parse(JSON.stringify(oldRow));
				newRow.spec[columnName]=columnValues[j];
				newList.push(newRow);
			}
		}
		return newList;
	}

})