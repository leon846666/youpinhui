app.service("brandService",function($http){
				
    this.findAllBrand=function(){
        return $http.get("../brand/findAll.do");
    }
    this.findPage=function(page,size){
        return $http.get('../brand/findPage.do?page='+page+'&size='+size);
    }
    this.add=function(entity){
        return $http.post('../brand/add.do',entity);
    }

    this.findOne=function(id){
        return  $http.get('../brand/findOne.do?id='+id);
    }
   

})