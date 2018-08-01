app.service("uploadService",function($http){

    this.uploadImage=function(){
         
        var formData= new FormData();
        formData.append('file',file.files[0]); // the name of upload input 

        return $http({
            url:'../upload.do',
            method:'post',
            data:formData,
            headers:{'Content-Type':undefined},
            transformRequest:angular.identity

        })


    }
})