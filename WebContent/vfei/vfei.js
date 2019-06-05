angular.module('app').controller('vfei', function($scope, $http,$interval) {
	
	
	$scope.request = function(data) {
		$scope.message= null
		if(data){
			$scope.status="Connecting  >  ";
			$http.post('../rest/controller/logToCode', data)
			.success(function(data, status, headers, config) {
				$scope.status=$scope.status+data.responseDesc;
				if(data.responseCode){
					$scope.output = data.responseData
				}
			}).error(function(data, status, headers, config, statusText) {
				$scope.message= "Fail"
				
			});
		}else{
			$scope.message="Please fill the input box"
		}

	};
});