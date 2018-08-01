 //Controller
app.controller('goodsController' ,function($scope,$controller,$location  ,goodsService,uploadService,itemCatService,typeTemplateService){	
	
	$controller('baseController',{$scope:$scope});//inheritance
	
	$scope.entity={goodsDesc:{itemImages:[],specificationItems:[]} }

	$scope.status=['Unreviewed','Reviewed','Not Approved','Closed'];

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
	$scope.findOne=function(){
	 var id=$location.search()['id'];	
		if(id==null){
			return ;
		}
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;	
				editor.html($scope.entity.goodsDesc.introduction);

				$scope.entity.goodsDesc.itemImages=JSON.parse($scope.entity.goodsDesc.itemImages);
				//alert($scope.entity.goodsDesc.itemImages);
				$scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.entity.goodsDesc.customAttributeItems);
				//console.log($scope.entity.goodsDesc.customAttributeItems);
				$scope.entity.goodsDesc.specificationItems=JSON.parse($scope.entity.goodsDesc.specificationItems);

				for(i=0;i<$scope.entity.itemList.length;i++){
					$scope.entity.itemList[i].spec=JSON.parse($scope.entity.itemList[i].spec);
				}

			}
		);				
	}
	
	//save 
	$scope.save=function(){		
		$scope.entity.goodsDesc.introduction=editor.html();			
		var serviceObject; 				
		if($scope.entity.goods.id!=null){//if it contains an id
			serviceObject=goodsService.update( $scope.entity ); //update   
		}else{
			serviceObject=goodsService.add( $scope.entity  );//add
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					alert("goods updated");
					location.href="goods.html";
				}else{
					alert(response.message);
				}
			}		
		);				
	}
		//save 
		$scope.add=function(){	
			
			console.log($scope.entity);
			goodsService.add( $scope.entity  ).success(
				function(response){
					if(response.success){
						
					}else{
						alert(response.message);
					}
				}		
			);				
		}
		
	
	 
	//batch delete
	$scope.dele=function(){			
		//receive the selected id from checkbox 	
		alert($scope.selectIds[0]);
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
		).error(
				function(){
					alert("error in upload")
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
	$scope.$watch("entity.goods.category1Id",function(newValue,oldValue){
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
	$scope.$watch("entity.goods.category2Id",function(newValue,oldValue){
		$scope.entity.goods.typeTemplateId=null;
		itemCatService.findByParentId(newValue).success(
			function(response){
				$scope.itemCateList3 = response;
			}
		)
	}
	)	
		//get the template id from the selected level3category 
	$scope.$watch("entity.goods.category3Id",function(newValue,oldValue){
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
				if($location.search()['id']==null){
					$scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.typeTemplate.customAttributeItems);
				}
			}
		)
		typeTemplateService.findSpecList($scope.entity.goods.typeTemplateId).success(
			function(response){
				$scope.specList= response;
			}
		)
			
	}
	)

	//check if the specName & options exist in the specification.items

	$scope.checkAttributeValue=function(specName,optionName){
		var items=$scope.entity.goodsDesc.specificationItems;
		var obj=	$scope.searchObjectByKey(items,"attributeName",specName);
		if(obj!=null){
			if(obj.attributeValue.indexOf(optionName)>=0){ //if found, should be checked
				return true;
			}else{
				return false;
			}

		}else{  // else return unchecked
			return false;
		}
		
	}

	$scope.updateSpecOptions=function($event,name,value){
		//the obj format
	// $scope.entity.goodsDesc.specificationItems=[]
	//$scope.entity.goodsDesc.specificationItems = [{"attributeName":"xx","attributeValue":["xxx","xxx"]}]
		var obj=$scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,"attributeName",name);
		//alert(obj)

		if(obj!=null){
			if($event.target.checked){
				obj.attributeValue.push(value);
			}else{
				obj.attributeValue.splice( obj.attributeValue.indexOf(value),1);
				if(obj.attributeValue.length==0){
					$scope.entity.goodsDesc.specificationItems.splice(
						$scope.entity.goodsDesc.specificationItems.indexOf(obj.attributeValue),1);
				}
			}
		}
		else{
			$scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]})
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
		var items=$scope.entity.goodsDesc.specificationItems;
		
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

	$scope.catItemList=[];

	
	$scope.findCatList=function(){
		 itemCatService.findAll().success(
			function(response){
				for(var i=0;i<response.length;i++){
					$scope.catItemList[response[i].id]=response[i].name;
				}
			}
		)
	}

	$scope.offMarket=function(){
		alert("123")
		goodsService.offMarket($scope.selectIds).success(
			function(response){
				if(response){
					$scope.reloadList();
				}else{
					alert(response.message);
				}
			}
		)
	}



})