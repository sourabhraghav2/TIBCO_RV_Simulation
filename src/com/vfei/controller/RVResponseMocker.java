package com.vfei.controller;

import java.io.IOException;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dataprocessor.DataProcessor;
import com.io.ReaderWriter;
import com.rv.MiniRVListener;
import com.rv.MiniRVSender;
import com.verification.Verification;
import com.vfei.dmh.MiniDMH;


@RestController
@RequestMapping("/rv_resp")
public class RVResponseMocker {
	public static String filePath="data/ResponseMockingStructure.json";
	MiniRVListener listener=null;
	@RequestMapping(value =	 "/sendRequest", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String sendRequest(@RequestBody  String input) {
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
			result= (String) rv.sendRequestMessage().getFieldByIndex(0).data;
			
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
	
	
	@RequestMapping(value =	 "/restoreFile", method = {RequestMethod.GET},produces = "text/plain; charset=utf-8")
	public  String restoreFile() throws Exception {
		ReaderWriter.writeToFile(filePath, "");
		return "Done!";
	}
	
	
	@RequestMapping(value =	 "/startRVListener", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String startRVListener(@RequestBody  String input) {
		System.out.println("input:: "+input);
		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		ObjectMapper om=new ObjectMapper();
		String result="Started ";
		try {
		
			JsonNode node=om.readTree(input);
			result="";
			if(listener!=null){
				listener.destroy();
			}
			listener=new MiniRVListener(node.get("service").getTextValue(), node.get("network").getTextValue(), node.get("deamon").getTextValue(),node.get("fullSubject").getTextValue());
			(new Thread(listener)).start();
			DataProcessor.initializeSubjectResponse();
			outputData= result;
			response.put("responseCode", true);
			response.put("responseDesc", "Started successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {
			 
			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());
			
		}
		System.out.println("Output:: "+response.toString());
		return response.toString();

	}
	@RequestMapping(value =	 "/stopRVListener", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String stopRVListener(@RequestBody  String input) {
		System.out.println("input:: "+input);
		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		
		String result=">";
		try {
		
			
			result="Destroyed ";
			if(listener!=null){
				listener.destroy();
			}
			
		
			outputData= result;
			response.put("responseCode", true);
			response.put("responseDesc", "Stopped successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {
			 
			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());
			
		}
		System.out.println("Output:: "+response.toString());
		return response.toString();

	}
	
	@RequestMapping(value =	 "/addRequest", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String addRequest(@RequestBody  String input) {
		System.out.println("input:: "+input);
		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		ObjectMapper om=new ObjectMapper();
		String result=">";
		try {
			ObjectNode inputNode=(ObjectNode)om.readTree(input);
			result="";
			
			
			String fileData=ReaderWriter.readFileAsString(filePath);
			
			ObjectNode structureNode=(ObjectNode)om.readTree(fileData);
			System.out.println("Existing Json  :: "+structureNode);
			String subject=inputNode.get("subject").getTextValue();
			String modelName=inputNode.get("modelName").getTextValue();
			String responseString=inputNode.get("responseJson").getTextValue();
			
			
			
			ObjectNode responseJson=(ObjectNode)om.readTree(responseString);
			inputNode.put("responseJson", responseJson);
			
			if(Verification.verifySubject(subject,modelName)){
				if(!structureNode.has(modelName)){
					structureNode.put(modelName, JsonNodeFactory.instance.objectNode());
				}
				ObjectNode modelList=(ObjectNode) structureNode.get(modelName);
				/*inputNode.put("responseJson",om.readTree(inputNode.get("responseJson")+""));*/
				Iterator<String> fields = modelList.getFieldNames();
				boolean removeOrNot=false;
				String removeSubject="";
				while(fields.hasNext()){
					String stroredSubject=fields.next();
					if(DataProcessor.excludeMachineName(stroredSubject, true).equalsIgnoreCase(DataProcessor.excludeMachineName(subject, true))){
						System.out.println("Found Sub "+stroredSubject);
						removeSubject=stroredSubject;
						removeOrNot=true;
						break;
					}
				}
				if(removeOrNot){
					System.out.println("Removing Subject :"+removeSubject);
					modelList.remove(removeSubject);
				}
				modelList.put(subject, inputNode);
				
				System.out.println("Modified Json  :: "+structureNode);
				ReaderWriter.writeToFile(filePath, String.valueOf(structureNode));
				outputData= " Added successfull! ";
				response.put("responseCode", true);
				response.put("responseDesc", "Added successfull! ");
				response.put("responseData", outputData);
			}else{
				 
				response.put("responseCode", false);
				response.put("responseDesc", "Incorrect subject or server not started !");
			}
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