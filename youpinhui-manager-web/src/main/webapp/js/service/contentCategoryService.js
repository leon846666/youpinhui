//Sevice
app.service('contentCategoryService',function($http){
	    	
	 //test case , get all the data return a Json
	this.findAll=function(){
		return $http.get('../contentCategory/findAll.do');		
	}
	//pagination
	this.findPage=function(page,rows){
		return $http.get('../contentCategory/findPage.do?page='+page+'&rows='+rows);
	}
	//find one object
	this.findOne=function(id){
		return $http.get('../contentCategory/findOne.do?id='+id);
	}
	//add 
	this.add=function(entity){
		return  $http.post('../contentCategory/add.do',entity );
	}
	//update 
	this.update=function(entity){
		return  $http.post('../contentCategory/update.do',entity );
	}
	//batch delete
	this.dele=function(ids){
		return $http.get('../contentCategory/delete.do?ids='+ids);
	}
	//search
	this.search=function(page,rows,searchEntity){
		return $http.post('../contentCategory/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});
