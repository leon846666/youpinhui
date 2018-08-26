var app = angular.module("youpinhui",[]);

//filter
app.filter('trustHtml',['$sce',function($sce){

    return function(data){// send data that needs to be filtered
        return $sce.trustAsHtml(data);  //return the data has been filtered
    }
}])