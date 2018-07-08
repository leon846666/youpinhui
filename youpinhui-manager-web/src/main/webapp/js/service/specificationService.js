//Sevice
app.service('specificationService',function($http){
	    	
	 //test case , get all the data return a Json
	this.findAll=function(){
		return $http.get('../specification/findAll.do');		
	}
	//pagination
	this.findPage=function(page,rows){
		return $http.get('../specification/findPage.do?page='+page+'&rows='+rows);
	}
	//find one object
	this.findOne=function(id){
		alert("id : "+id);
		return $http.get('../specification/findOne.do?id='+id);
	}
	//add 
	this.add=function(entity){
		return  $http.post('../specification/add.do',entity );
	}
	//update 
	this.update=function(entity){
		return  $http.post('../specification/update.do',entity );
	}
	//batch delete
	this.dele=function(ids){
		return $http.get('../specification/delete.do?ids='+ids);
	}
	//search
	this.search=function(page,rows,searchEntity){
		return $http.post('../specification/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});
