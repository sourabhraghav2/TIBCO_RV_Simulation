package com.vfei.controller;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vfei.dmh.MiniDMH;


@RestController
@RequestMapping("/dmh")
public class CreateAndCheckMsg {

	@RequestMapping(value =	 "/init", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String getDmhInJSON(@RequestBody  String input) {

		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		ObjectMapper om=new ObjectMapper();
		String result=">";
		try {
			JsonNode node=om.readTree(input);
			result=">> About to initialize";
			MiniDMH minidmh=new MiniDMH(node.get("host").getTextValue(),Integer.valueOf(node.get("port").getTextValue()),"MY_DUMMY");
			
			result= result+minidmh.getLog();
		
			outputData= result;
			response.put("responseCode", true);
			response.put("responseDesc", "Initialize  successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {
			 
			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());
			
		}
		
		return response.toString();

	}
	@RequestMapping(value =	 "/checkMsg", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String checkMsg() {

		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		try {
			
			Thread.sleep(1000);
			
			outputData=MiniDMH.message;
			MiniDMH.message="";
			response.put("responseCode", true);
			response.put("responseDesc", "Listener enavled successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {
			 
			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());
			
		}
		
		return response.toString();

	}

}