//Sevice
app.service('typeTemplateService',function($http){
	    	
	 //test case , get all the data return a Json
	this.findAll=function(){
		return $http.get('../typeTemplate/findAll.do');		
	}
	//pagination
	this.findPage=function(page,rows){
		return $http.get('../typeTemplate/findPage.do?page='+page+'&rows='+rows);
	}
	//find one object
	this.findOne=function(id){
		return $http.get('../typeTemplate/findOne.do?id='+id);
	}
	//add 
	this.add=function(entity){
		return  $http.post('../typeTemplate/add.do',entity );
	}
	//update 
	this.update=function(entity){
		return  $http.post('../typeTemplate/update.do',entity );
	}
	//batch delete
	this.dele=function(ids){
		return $http.get('../typeTemplate/delete.do?ids='+ids);
	}
	//search
	this.search=function(page,rows,searchEntity){
		return $http.post('../typeTemplate/search.do?page='+page+"&rows="+rows, searchEntity);
	}
	
	this.selectOptionList=function(){
		return $http.get('../typeTemplate/selectOptionList.do');
	}
	this.findSpecList=function(id){
		return $http.get('../typeTemplate/findSpecList.do?id='+id);
	}

});
