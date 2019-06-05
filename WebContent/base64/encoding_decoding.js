angular.module('app').controller('vfei', function($scope, $http,$interval) {
	
	
	$scope.encode = function(text) {
		console.log('Data :',text);
		console.log('Encoded : ',btoa(text)); 
		$scope.base64=btoa(text)+""
		
	}
	$scope.decode= function(base64) {
		console.log('Data :',base64);
		console.log('Decoded : ',atob(base64));
		$scope.text=atob(base64)+""
	}
	
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
				finalString=finalString+"\n"+map.substring(start,end);
			else
				break;
			
			
			start=end+1;
		}
		
		
		console.log(finalString)
		$scope.output=finalString;
	}
});