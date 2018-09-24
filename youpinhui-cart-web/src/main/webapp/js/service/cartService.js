//Sevice
app.service('cartService',function($http){


	this.findCartList=function(){
		return $http.get("../cart/findCartList.do");
	}
	
	this.addGoodsToCartList=function(itemId,num){
		return $http.get("../cart/addGoodsToCartList.do?itemId="+itemId+"&number="+num)
	}
  
});
