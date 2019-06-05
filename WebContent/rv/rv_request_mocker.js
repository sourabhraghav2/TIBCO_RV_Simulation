angular.module('app').controller('rv_request_mocker',
		function($scope, $http, $interval) {
			$scope.form={}
			$scope.form.service = "7505";
			$scope.form.network = "92.120.227.119";
			$scope.form.deamon = "tcp:92.120.227.119:7505";
			$scope.form.type = "ReqResp";
			$scope.form.subject = "ATBK.REQ";
			$scope.form.reqName="Dummy"
			$scope.form.modelName="Dummy"
			
			$scope.structure={}
			$scope.selectedModel=function(model,selectedIndex,modelName){
				console.log(model);
				$scope.form.modelName=modelName;
				$scope.structure.requestList=model;
				$scope.structure.selectedModelIndex=selectedIndex;
			}
			$scope.selectedRequest=function(selected,selectedIndex,modelName){
				console.log(selected);
				$scope.form.reqName=modelName
				$scope.form=retainPrevious($scope.form,selected);
				$scope.structure.selectedRequestIndex=selectedIndex;
			}
			
			retainPrevious=function(prevData,newData){
				newData.reqName=prevData.reqName
				newData.modelName=prevData.modelName
				return newData;
			}
			
			$scope.initializeStructure = function() {

				
				$scope.structure.status = "About to connect >>";
				$scope.form.error=false;
				$http.get('../rest/rv_req/initStructure').success(
						function(data, status, headers, config) {

							$scope.structure.status = $scope.structure.status + data.responseDesc;
							if (data.responseCode) {
								$scope.structure.inputStructure = JSON.parse(data.responseData);
							}
						}).error(
						function(data, status, headers, config, statusText) {
							$scope.structure.message = "Fail"
							$scope.structure.error=true

						});

			}
			
			$scope.convertVfeiToJson=function(object){
				console.log('convertVfeiToJson')
				
				$scope.form.error=false;
				$http.post('../rest/parser/vfeiToJson', object.vfeiCmd).success(
						function(data, status, headers, config) {

							$scope.form.status = $scope.form.status+ data.responseDesc;
							if (data.responseCode) {
								obj= JSON.parse(data.responseData);
								$scope.form.messageData =JSON.stringify(obj.DATA);
								$scope.form.subject =obj.Subject;
								
								
							}else{
								$scope.form.status=$scope.form.status+" Server not able to process the request!!"
							}
						})
						.error(
								function(data, status, headers, config,
										statusText) {
									$scope.form.status = $scope.form.status+"Fail"

								});
				
			}
			$scope.convertJsonToVfei=function(object,vfeiType){
				console.log('convertVfeiToJson')
				var data={
					
					'subject':object.subject,
					'messageData':object.messageData,
					'vfeiType':vfeiType
				}
				$scope.form.error=false;
				$http.post('../rest/parser/jsonToVfei', data).success(
						function(data, status, headers, config) {

							$scope.form.status = $scope.form.status+ data.responseDesc;
							if (data.responseCode) {
								
								$scope.form.vfeiCmd=data.responseData;
								
								
								
							}else{
								$scope.form.status=$scope.form.status+" Server not able to process the request!!"
							}
						})
						.error(
								function(data, status, headers, config,
										statusText) {
									$scope.form.status = $scope.form.status+"Fail"

								});
				
			}
			$scope.addRequest=function(object){
				console.log('inside addRequest :: ')
				console.log(object)
				var data = {
						'service' : object.service,
						'network' : object.network,
						'deamon' : object.deamon,
						'subject' : object.subject,
						'messageData' : object.messageData,
						'reqName':object.reqName,
						'modelName':object.modelName,
						'type':object.type,
						'output':object.output
					}
				$scope.form.error=false;
				$http.post('../rest/rv_req/addRequest', object).success(
						function(data, status, headers, config) {

							$scope.form.status = $scope.form.status+ data.responseDesc;
							if (data.responseCode) {
//								$scope.form.output = (data.responseData);
								
								$scope.initializeStructure();
								$scope.form.status = $scope.form.status + data.responseDesc;
							}else{
								$scope.form.status=$scope.form.status+" Server not able to process the request!!"
							}
						})
						.error(
								function(data, status, headers, config,
										statusText) {
									$scope.form.status = $scope.form.status+"Fail"

								});
				
			}

			$scope.sendData = function(object) {

				
				var data = {
					'service' : object.service,
					'network' : object.network,
					'deamon' : object.deamon,
					'subject' : object.subject,
					'data' : object.messageData
				}

				if (object.type == "Event") {
					$scope.form.status = "About to connect >>";
					$scope.form.error=false;
					$http.post('../rest/rv_req/sendEvent', data).success(
							function(data, status, headers, config) {

								$scope.form.status = $scope.form.status
										+ data.responseDesc;
								if (data.responseCode) {
									$scope.form.output = (data.responseData);
								}else{
									$scope.form.status=$scope.form.status+" Server not able to process the request!!"
									$scope.form.error=true;
								}
							})
							.error(
									function(data, status, headers, config,
											statusText) {
										$scope.form.status = $scope.form.status+"Fail"

									});

				} else {
					$scope.form.error=false;
					$scope.form.status = "About to connect ";
					$http.post('../rest/rv_req/sendRequest', data).success(
							function(data, status, headers, config) {

								$scope.form.status = $scope.form.status
										+ data.responseDesc;
								if (data.responseCode) {
									$scope.form.output = (data.responseData);
								}else{
									$scope.form.status=$scope.form.status+" Server not able to process the request!!"
									$scope.form.error=true;
								}
							})
							.error(
									function(data, status, headers, config,
											statusText) {
										$scope.form.status= $scope.form.status+"Fail"

									});
				}

			}

		});