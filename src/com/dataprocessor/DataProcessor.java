package com.dataprocessor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import com.io.ReaderWriter;
import com.vfei.controller.RVResponseMocker;

public class DataProcessor {
/*	public static void main(String[] args) throws Exception {
		DataProcessor.initializeSubjectResponse();
	}*/

	public static Map<String,JsonNode> subjectResponse=null;
	public static boolean initializeSubjectResponse() throws Exception {
		subjectResponse=new HashMap<>();
		ObjectMapper om=new ObjectMapper();
		String fileData=ReaderWriter.readFileAsString(RVResponseMocker.filePath);
		
		ObjectNode structureNode=(ObjectNode)om.readTree(fileData);
		Iterator<Entry<String, JsonNode>> fieldsNameItr = structureNode.getFields();
		while(fieldsNameItr.hasNext()){
			Entry<String, JsonNode> eachModel = fieldsNameItr.next();
			Iterator<Entry<String, JsonNode>> subjectItr = eachModel.getValue().getFields();
			while(subjectItr.hasNext()){
				Entry<String, JsonNode> eachSubject = subjectItr.next();
				subjectResponse.put(excludeMachineName(eachSubject.getKey(),false), eachSubject.getValue());
			}
			
		}
		System.out.println("subjectResponse: "+om.writeValueAsString(subjectResponse)); 
		
		return true;

	}
	public static String excludeMachineName(String subject,boolean exOrNot) {
		
		if(exOrNot){
			String subjectList[]=subject.split("\\.");
			String lastWord=subjectList[subjectList.length-1];
			subject=subject.replace("."+lastWord, "");
		}
		
		return subject;
	}
	public static void main(String[] args) {
		System.out.println(excludeMachineName("ATBK.REQ.DUMMY.MAP_QUERY.DAESC204",false));
	}
}
