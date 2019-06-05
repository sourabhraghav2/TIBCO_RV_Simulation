package com.vfei.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.io.ReaderWriter;
import com.rv.MiniRVSender;
import com.tibco.tibrv.TibrvMsg;
import com.vfei.dmh.MiniDMH;


@RestController
@RequestMapping("/rv_req")
public class RVRequestMocker {
	String filePath="data/RequestMockingStructure.json";
	@RequestMapping(value =	 "/sendRequest", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String sendRequest(@RequestBody  String input) {
		System.out.println("input:: "+input);
		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		ObjectMapper om=new ObjectMapper();
		String result=">";
		try {
		
			JsonNode node=om.readTree(input);
			result="Empty Response";
			
			MiniRVSender rv=new MiniRVSender(node.get("service").getTextValue(), node.get("network").getTextValue(), node.get("deamon").getTextValue());
			rv.setSubject(node.get("subject").getTextValue());
			rv.setMessage(node.get("data").getTextValue());
			TibrvMsg rvResponse = rv.sendRequestMessage();
			if(rvResponse.getNumFields()>0 && rvResponse!=null){
				result= (String)rvResponse .getFieldByIndex(0).data;
			}
			outputData= result;
			response.put("responseCode", true);
			response.put("responseDesc", "Response recieve successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {
			 
			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());
			
		}
		System.out.println("Output:: "+response.toString());
		return response.toString();

	}
	/*public static void main(String[] args) throws JsonProcessingException, IOException {
		ObjectMapper om=new ObjectMapper();
		JsonNode node=		om.readTree("{\"service\":\"7501\",\"network\":\"92.120.227.119\",\"deamon\":\"tcp:92.120.227.119:7505\",\"subject\":\"ATBK.REQ\",\"data\":\"any\",\"type\":\"Event\"}");
		
	}*/
	@RequestMapping(value =	 "/addRequest", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String addRequest(@RequestBody  String input) {
		System.out.println("input:: "+input);
		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		ObjectMapper om=new ObjectMapper();
		String result=">";
		try {
			JsonNode inputNode=om.readTree(input);
			result="";
			
			
			String fileData=ReaderWriter.readFileAsString(filePath);
			
			ObjectNode structureNode=(ObjectNode)om.readTree(fileData);
			System.out.println("Existing Json  :: "+structureNode);
			String reqName=inputNode.get("reqName").getTextValue();
			String modelName=inputNode.get("modelName").getTextValue();
			
			if(!structureNode.has(modelName)){
				structureNode.put(modelName, JsonNodeFactory.instance.objectNode());
			}
			ObjectNode modelList=(ObjectNode) structureNode.get(modelName);

			modelList.put(reqName, inputNode);
			/*if(!modelList.has(reqName)){
				
			}*/
			System.out.println("Modified Json  :: "+structureNode);
			ReaderWriter.writeToFile(filePath, String.valueOf(structureNode));
			outputData= " Added successfull! ";
			response.put("responseCode", true);
			response.put("responseDesc", "Added successfull! ");
			response.put("responseData", outputData);
		} catch (Exception e) {
			 
			response.put("responseCode", false);
			response.put("responseDesc", "Error >> "+e.getMessage());
			
		}
		System.out.println("Output:: "+response.toString());
		return response.toString();
		

	}
	
	@RequestMapping(value =	 "/sendEvent", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String sendEvent(@RequestBody  String input) {
		System.out.println("input:: "+input);
		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		ObjectMapper om=new ObjectMapper();
		String result=">";
		try {
			JsonNode node=om.readTree(input);
			result="";
			
			MiniRVSender rv=new MiniRVSender(node.get("service").getTextValue(), node.get("network").getTextValue(), node.get("deamon").getTextValue());
			rv.setSubject(node.get("subject").getTextValue());
			rv.setMessage(node.get("data").getTextValue());
			result=  String.valueOf(rv.sendEventMessage());
		
			outputData= result;
			response.put("responseCode", true);
			response.put("responseDesc", "Response recieve successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {
			 
			response.put("responseCode", false);
			response.put("responseDesc", "Error >> "+e.getMessage());
			
		}
		System.out.println("Output:: "+response.toString());
		return response.toString();
		

	}
	@RequestMapping(value =	 "/restoreFile", method = {RequestMethod.GET},produces = "text/plain; charset=utf-8")
	public  String restoreFile() throws Exception {
	
		ReaderWriter.writeToFile(filePath, "");
		return "Done!";
	}
	
	@RequestMapping(value =	 "/initStructure", method = {RequestMethod.GET},produces = "text/plain; charset=utf-8")
	public  String initializeStructure() {

		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		
		String result="";
		try {
			
			
//			ReaderWriter.writeToFile(filePath, "I have written");
			String fileData=ReaderWriter.readFileAsString(filePath);
			
			result=  fileData;
		
			outputData= result;
			response.put("responseCode", true);
			response.put("responseDesc", "Initialize successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {
			 
			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());
			
		}
		System.out.println("Output:: "+response.toString());
		return response.toString();

	}
	

}