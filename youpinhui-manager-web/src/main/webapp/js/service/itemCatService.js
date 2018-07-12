//Sevice
app.service('itemCatService',function($http){
	    	
	 //test case , get all the data return a Json
	this.findAll=function(){
		return $http.get('../itemCat/findAll.do');		
	}
	//pagination
	this.findPage=function(page,rows){
		return $http.get('../itemCat/findPage.do?page='+page+'&rows='+rows);
	}
	//find one object
	this.findOne=function(id){
		return $http.get('../itemCat/findOne.do?id='+id);
	}
	//add 
	this.add=function(entity){
		return  $http.post('../itemCat/add.do',entity );
	}
	//update 
	this.update=function(entity){
		return  $http.post('../itemCat/update.do',entity );
	}
	//batch delete
	this.dele=function(ids){
		return $http.get('../itemCat/delete.do?ids='+ids);
	}
	//search
	this.search=function(page,rows,searchEntity){
		return $http.post('../itemCat/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
	
	//findByParentId
	this.findByParentId=function(parentId){
		return $http.post('../itemCat/findByParentId.do?parentId='+parentId);
	}    	
	
	
	
	
});
