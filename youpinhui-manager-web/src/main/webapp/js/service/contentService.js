//Sevice
app.service('contentService',function($http){
	    	
	 //test case , get all the data return a Json
	this.findAll=function(){
		return $http.get('../content/findAll.do');		
	}
	//pagination
	this.findPage=function(page,rows){
		return $http.get('../content/findPage.do?page='+page+'&rows='+rows);
	}
	//find one object
	this.findOne=function(id){
		return $http.get('../content/findOne.do?id='+id);
	}
	//add 
	this.add=function(entity){
		return  $http.post('../content/add.do',entity );
	}
	//update 
	this.update=function(entity){
		return  $http.post('../content/update.do',entity );
	}
	//batch delete
	this.dele=function(ids){
		return $http.get('../content/delete.do?ids='+ids);
	}
	//search
	this.search=function(page,rows,searchEntity){
		return $http.post('../content/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});
