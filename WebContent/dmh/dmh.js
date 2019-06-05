angular.module('app').controller('dmh', function($scope, $http,$interval) {
	
	$scope.initDMH=function(host,port){
		var data={'host':host,
				'port':port}
		$scope.status="";
		$http.post('../rest/dmh/init', data)
		.success(function(data, status, headers, config) {
			$scope.status=$scope.status+data.responseDesc;
			if(data.responseCode){
				$scope.status = $scope.status+data.responseData
				$scope.waiting="."
				$scope.output="";
				$scope.refresh();	
			}
		}).error(function(data, status, headers, config, statusText) {
			$scope.message= "Fail"
			
		});
	}
	
	$scope.refresh=function(){
		if($scope.waiting.length==10){
			$scope.waiting=".";
		}else{
			$scope.waiting=$scope.waiting+".";
		}
		$http.post('../rest/dmh/checkMsg')
		.success(function(data, status, headers, config) {
			$scope.refresh();
			if(data.responseCode && data.responseData){
				$scope.output = $scope.output+data.responseData
			}
		}).error(function(data, status, headers, config, statusText) {
			$scope.message= "Fail"
			
		});
        
      
	}
	
});