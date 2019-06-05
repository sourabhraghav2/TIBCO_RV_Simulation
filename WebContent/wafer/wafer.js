angular.module('app').controller('vfei', function($scope, $http,$interval) {
	
	
	$scope.request = function(data,divLength) {
		console.log(divLength)
		console.log('data :',data)
		divLength=parseInt(divLength)
		$scope.message= null
		if(data && divLength){
			
			$scope.divideLineByLength(data,divLength);
		}else{
			$scope.message="Please fill the input box"
		}

	};
	
	$scope.divideLineByLength = function( map, divLength) {
		console.log('lenght : ',divLength)
		var length=map.length;
		var start=0;
		var end;
		var finalString="";
		while(true){
			
			end=start+divLength;
			console.log(start,' ',end)
			if(end<length && start<length)
				finalString=finalString+"\n "+map.substring(start,end);
			else
				break;
			
			
			start=end;
		}
		
		
		console.log(finalString)
		$scope.output=finalString;
	}
});