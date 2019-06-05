package com.verification;

import java.util.Iterator;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import com.dataprocessor.DataProcessor;
import com.io.ReaderWriter;
import com.rv.MiniRVListener;
import com.vfei.controller.RVResponseMocker;
import com.vfei.exception.VerificationSubjectFailed;

public class Verification {

	public static boolean verifySubject(String input,String modelName) throws Exception {
		System.out.println("inside Verification"); 
		
		if(MiniRVListener.fullSubject!=null&& input!=null){
			String verStr=MiniRVListener.fullSubject.substring(0,MiniRVListener.fullSubject.length()-1);
			if(input.startsWith(verStr) ){
/*				if(!DataProcessor.subjectResponse.containsKey(input)&& verifySubjectInModelName(modelName,input)){
					return  true;
				}else throw new VerificationSubjectFailed("Subject or model name already existing!");
*/
				return  true;
				}else throw new VerificationSubjectFailed("Instance name and subject does notmatch");
		}else throw new VerificationSubjectFailed("Listener not started!");
		
		
		
	}

	private static boolean verifySubjectInModelName(String modelName,String subject) throws Exception {
		ObjectMapper om=new ObjectMapper();
		String fileData=ReaderWriter.readFileAsString(RVResponseMocker.filePath);
		
		ObjectNode structureNode=(ObjectNode)om.readTree(fileData);
		
		if(structureNode.has(modelName)){
			for(JsonNode eachSubject: structureNode.get(modelName)){
				String modelSubject=eachSubject.get("subject").asText();
				System.out.println("modelSubject : "+modelSubject);
				System.out.println("input Subject : "+DataProcessor.excludeMachineName(subject,true));
				
				
			}
		}
		
		
			return structureNode.has(modelName);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(verifySubjectInModelName("ENR_LM502","ATBK.REQ.DUMMY.MATERIAL_VALIDATE.MLENR003"));
	}

}
