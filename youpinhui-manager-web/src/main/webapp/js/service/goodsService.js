//Sevice
app.service('goodsService',function($http){
	    	
	 //test case , get all the data return a Json
	this.findAll=function(){
		return $http.get('../goods/findAll.do');		
	}
	//pagination
	this.findPage=function(page,rows){
		return $http.get('../goods/findPage.do?page='+page+'&rows='+rows);
	}
	//find one object
	this.findOne=function(id){
		return $http.get('../goods/findOne.do?id='+id);
	}
	//add 
	this.add=function(entity){
		return  $http.post('../goods/add.do',entity );
	}
	//update 
	this.update=function(entity){
		return  $http.post('../goods/update.do',entity );
	}
	//batch delete
	this.dele=function(ids){
		alert(ids);
		return $http.get('../goods/delete.do?ids='+ids);
	}
	//search
	this.search=function(page,rows,searchEntity){
		return $http.post('../goods/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
	this.updateStatus=function(ids,status){
		return $http.get('../goods/updateStatus.do?ids='+ids+"&status="+status);
	}
});
