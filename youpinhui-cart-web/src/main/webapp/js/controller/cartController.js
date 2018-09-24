 //Controller
app.controller('cartController' ,function($scope,$controller   ,cartService){	
	
	$scope.findCartList=function()
	{
		cartService.findCartList().success(
				function(response){
					$scope.cartList=response;
					sum();
				}
		)
	}
	
	$scope.addGoodsToCartList=function(itemId,number)
	{
		cartService.addGoodsToCartList(itemId,number).success(
				function(response){
					if(response.success){
						$scope.findCartList();
					}else{
						alert(response.message);
					}
				}
		)
	}
	
	sum=function(){
		$scope.totalNum=0;
		$scope.totalPrice=0;
		
		for(var i=0;i< $scope.cartList.length;i++){
			var cart=$scope.cartList[i]; 
			for(var j =0;j<cart.orderItemList.length;j++){
				var orderItem=cart.orderItemList[j];
				$scope.totalNum+=orderItem.num;
				$scope.totalPrice+=orderItem.totalFee;
			}
		}
	}
	
	
    
});	
