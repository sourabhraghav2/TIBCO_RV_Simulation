package com.vfei.controller;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adapter.RVAdapter;



@RestController
@RequestMapping("/parser")
public class ParsingController {
	
	@RequestMapping(value =	 "/vfeiToJson", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String vfeiToJson(@RequestBody  String input) {
		System.out.println("input:: "+input);
		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		
		try {
			ObjectNode converted=RVAdapter.convertVfeiToJson(input);
			outputData= converted.toString();
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
	@RequestMapping(value =	 "/jsonToVfei", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String jsonToVfei(@RequestBody  String input) {
		System.out.println("input:: "+input);
		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		ObjectMapper om=new ObjectMapper();
		
		try {
			JsonNode node=om.readTree(input);
			String subject=node.get("subject").getTextValue();
			String data=node.get("messageData").getTextValue();
			String type=node.get("vfeiType").getTextValue();
			outputData= RVAdapter.convertJsonToVfei(subject, data,type);
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
	
}