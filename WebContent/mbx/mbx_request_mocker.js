angular.module('app').controller(
		'rv_request_mocker',
		function($scope, $http, $interval) {
			$scope.form={}
			$scope.config={}
			$scope.config.fullSubject="ATBK.REQ.DUMMY.>"
			$scope.config.picInstance = "Pic_QFN_1";
			$scope.config.network = "165.114.28.223";
			$scope.config.deamon = "tcp:92.120.227.119:7505";
			
			$scope.config.startStopToggle=false;
			
			$scope.form.type = "ReqResp";
			$scope.form.type="put"
			$scope.form.subject = $scope.config.fullSubject;
			/*$scope.form.reqName="Dummy"*/
			$scope.form.modelName="MODEL_1"
			
			$scope.structure={}
			
			
			$scope.startRVListener=function(data){
				console.log('Inside :: startRVListener');
				
				/*data.instance="DUMMY"*/
				$scope.structure.status = "About to connect >>";
				$http.post('../rest/mbx_req/startRVListener',data).success(
						function(data, status, headers, config) {

							$scope.form.status = "About to  connect :: ";
							$scope.config.startStopToggle=!$scope.config.startStopToggle;
							$scope.form.status =$scope.form.status +data.responseDesc; 
							$scope.form.responseJson=data.responseData;
						}).error(
						function(data, status, headers, config, statusText) {
							$scope.structure.message = "Fail"
							$scope.form.status =$scope.form.status +"Failed !";
						});

				
				
				
			}
			$scope.stopRVListener=function(){
				console.log('Inside :: stopRVListener');

				
				
				$scope.structure.status = "About to connect >>";
				$http.post('../rest/mbx_req/stopRVListener',{}).success(
						function(data, status, headers, config) {

							$scope.form.status = "About to  connect :: ";
							if (data.responseCode) {
								$scope.config.startStopToggle=!$scope.config.startStopToggle;
								$scope.form.status =$scope.form.status +data.responseDesc; 
								/*$scope.structure.inputStructure = JSON.parse(data.responseData);*/
								
							}
						}).error(
						function(data, status, headers, config, statusText) {
							$scope.structure.message = "Fail"
							$scope.form.status =$scope.form.status +"Failed !";
						});

			}
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
				if(!angular.isString(newData.responseJson)){
					newData.responseJson=JSON.stringify(newData.responseJson)
				}
				
				return newData;
			}
			
			$scope.initializeStructure = function() {

				$scope.structure.status = "About to connect >>";
				$http.get('../rest/mbx_req/initStructure').success(
						function(data, status, headers, config) {

							$scope.structure.status = $scope.structure.status + data.responseDesc;
							if (data.responseCode) {
								$scope.structure.inputStructure = JSON.parse(data.responseData);
							}
						}).error(
						function(data, status, headers, config, statusText) {
							$scope.structure.message = "Fail"

						});

			}
			
			$scope.convertVfeiToJson=function(object){
				console.log('convertVfeiToJson')
				
				
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
				
				$scope.form.status = "About to connect :: ";
				$scope.form.error=false;
				$http.post('../rest/mbx_req/addRequest', object).success(
						function(data, status, headers, config) {
							
							$scope.form.status = $scope.form.status+ data.responseDesc;
							if (data.responseCode) {
								
								$scope.form.output = (data.responseData);
								
								$scope.initializeStructure();
								$scope.form.status = $scope.form.status + data.responseDesc;
								
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
				
				
			}

					$scope.sendRequest = function(form,config) {
						
						var data ={
								'form':form,
								'config':config
						}
						$scope.form.status = "About to connect >>";
						$scope.form.error=false;
						$http.post('../rest/mbx_req/sendRequest', data)
							.success(
									function(data, status, headers, config) {
										$scope.form.status = $scope.form.status
												+ data.responseDesc;
										if (data.responseCode) {
											$scope.form.responseJson = (data.responseData);
										} else {
											$scope.form.status = $scope.form.status
													+ " Server not able to process the request!!"
										}
									})
							.error(
									function(data, status, headers, config,
											statusText) {
										$scope.form.status = $scope.form.status
												+ "Fail"

									});

					}

		});