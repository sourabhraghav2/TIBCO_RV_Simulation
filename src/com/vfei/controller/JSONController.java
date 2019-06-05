package com.vfei.controller;


import java.io.IOException;

import javax.xml.soap.Node;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vfei.dmh.MiniDMH;
import com.vfei.exception.IncorrectExpressionException;
import com.vfei.parser.Parser;


@RestController
@RequestMapping("/controller")
public class JSONController {

	/*@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public  String getShopInJSON(@PathVariable String name) {


		return "Hi";

	}
	
	public static void main(String[] args) throws IncorrectExpressionException {
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		response.put("responseCode", true);
		String output=Parser.convertFromVfeiToString("L:5{A:1{0}A:16{CheckWorkOrderId}L:1{A:12{N0P739KJL000}}L:3{L:3{A:8{Operator}A:5{10914}U1:1{0}}L:3{A:10{MaterialId}A:0{}U1:1{0}}L:3{A:4{12nc}A:0{}U1:1{0}}}L:0{}}");
		System.out.println(output);
	}*/
	@RequestMapping(value =	 "/logToCode", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String getRequestInJSON(@RequestBody  String input) {

		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		
		try {
			outputData= Parser.convertFromVfeiToString(input);
			response.put("responseCode", true);
			response.put("responseDesc", "Converted successfully!");
			response.put("responseData", outputData);
		} catch (IncorrectExpressionException e) {
			 
			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());
			
		}
		
		return response.toString();

	}
	@RequestMapping(value =	 "/dmh", method = {RequestMethod.POST},produces = "text/plain; charset=utf-8")
	public  String getDmhInJSON(@RequestBody  String input) {

		String outputData=null;
		ObjectNode response=JsonNodeFactory.instance.objectNode();
		ObjectMapper om=new ObjectMapper();
		try {
			JsonNode node=om.readTree(input);
			
			MiniDMH minidmh=new MiniDMH(node.get("host").getTextValue(),Integer.valueOf(node.get("port").getTextValue()),"MY_DUMMY");
			
			String result="Established ";
		
			outputData= result;
			response.put("responseCode", true);
			response.put("responseDesc", "Converted successfully!");
			response.put("responseData", outputData);
		} catch (Exception e) {
			 
			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());
			
		}
		
		return response.toString();

	}

	/*
	@RequestMapping(value = "/model/{name}", method = RequestMethod.GET)
	public  String getNewInJSON(@PathVariable String name) {

		
		return "Hello";

	}*/

}