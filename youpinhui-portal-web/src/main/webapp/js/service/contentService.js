app.service('contentService',function($http){

    //find content by categoryid
    this.findByCategoryId=function(id){
        return $http.get('content/findByCategoryId.do?categoryId='+id);

    }

})