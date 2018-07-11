//Sevice
app.service('sellerService',function($http){
	    	
	 //test case , get all the data return a Json
	this.findAll=function(){
		return $http.get('../seller/findAll.do');		
	}
	//pagination
	this.findPage=function(page,rows){
		return $http.get('../seller/findPage.do?page='+page+'&rows='+rows);
	}
	//find one object
	this.findOne=function(id){
		return $http.get('../seller/findOne.do?id='+id);
	}
	//add 
	this.add=function(entity){
		return  $http.post('../seller/add.do',entity );
	}
	//update 
	this.update=function(entity){
		return  $http.post('../seller/update.do',entity );
	}
	//batch delete
	this.dele=function(ids){
		return $http.get('../seller/delete.do?ids='+ids);
	}
	//search
	this.search=function(page,rows,searchEntity){
		return $http.post('../seller/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});
