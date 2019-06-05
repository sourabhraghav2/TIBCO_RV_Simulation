package com.adapter;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import com.hume.DMH.Vfei2Map;
import com.nxp.dmh.ETX;
import com.nxp.dmh.VFEI;
import com.nxp.rvgateway.Parser;
import com.nxp.rvgateway.Request;
import com.nxp.rvgateway.Response;

public class RVAdapter {
	
	public static void mainVfeiToJson(String[] args) throws Exception {
		System.out.println("Start");
		String vfei="CMD/A=\"MES.MATERIAL_VALIDATE\" eqpName/A=\"ENR506\" materialId/A=\"765765\" materialPart/A=\"7654564\"";
		System.out.println("Input : "+vfei);
		System.out.println(RVAdapter.convertVfeiToJson(vfei));;
	}
	public static ObjectNode convertVfeiToJson(String vfeiMessage) throws Exception {
		String jsonMessage=null;
		ObjectNode resultJson=JsonNodeFactory.instance.objectNode();
		String msg=vfeiMessage;
		HashMap<String, String> vfeiMap = new HashMap<>();
		 try {
             Vfei2Map.parse(msg, vfeiMap);
             String CMD = vfeiMap.remove(VFEI.CMD.name());
             String eqpName = vfeiMap.get(VFEI.eqpName.name());
             String requestName=CMD.split("\\.")[1];
             String sendSubject = "ATBK.REQ.".concat(CMD).concat("."+eqpName);
             
             Request request = Request.get(requestName);
             jsonMessage= request.function().apply(vfeiMap);
             resultJson.put("Subject",sendSubject);
             resultJson.put("DATA",(new ObjectMapper()).readTree(jsonMessage));
         } catch (Exception e) {
        	 System.err.println(ETX.VFEI_INVALID_FORMAT);
             
         }

		 
		 return resultJson;
	}
	
	public static void mainJsonToVfei(String[] args) {
		String input="{\"RecipeInquireResponse\":{\"errorCode\":\"50002\",\"errorText\":\"EQUIP_CANNOT_DO_NOW\",\"eqpName\":\"MLENR006\",\"ppid\":\"96_BARE1\",\"recipeFullpath\":\"\"}}";
		System.out.println("Input: "+input);
		String subject="ATBK.REQ.EI.RCP_SETUP_INQUIRE.MLENR006";
		System.out.println("Subject : "+subject);
		System.out.println(RVAdapter.convertJsonToVfei(subject,input,"command"));;
		
	}
	public static String convertJsonToVfei(String subject,String input,String type) {
    	String resultVfei=null;
        String[] sendSubjectArray = subject.split("\\.");
        
        if (sendSubjectArray.length > 4 ) {
        	
            Response response = Response.get(sendSubjectArray[3]);
            
            if (response == null) {
            	System.err.println("1. Error >>  Null ");
                
            } else {
                String message =  input;
				
				if (message.isEmpty()) {
					System.err.println("2. Error >>  Empty ");
				} else {
				    try {
				        JSONObject request = new JSONObject(message);
				        JSONArray names = request.names();
				        
				        if (names == null) {
				        	System.err.println("3. Error >>  Null ");
				        } else {
				            String requestName = names.optString(0);
				            
				            if (requestName.isEmpty()) {
				            	System.err.println("4. Error >>  Empty");
				            } else {
				                JSONObject requestBody = request.optJSONObject(requestName);
				                
				                if (requestBody == null) {
				                	System.err.println("5. Error >>  Null ");
				                } else {
				                    StringBuilder vfeiBuilder = new StringBuilder(VFEI.EVENT_ID.toString(sendSubjectArray[3]));
				                    if(type.equalsIgnoreCase("command")){
				                    	vfeiBuilder = new StringBuilder(VFEI.CMD.toString(sendSubjectArray[2]+"."+sendSubjectArray[3])+" "+VFEI.eqpName.toString(sendSubjectArray[4]));
				                    }else{
				                    	vfeiBuilder = new StringBuilder(VFEI.EVENT_ID.toString(sendSubjectArray[3]));
				                    }
				                    vfeiBuilder.append(" ").append(Parser.toVFEIString(requestBody, false)).append(" timeout/U4=").append(Integer.toString(120 - 2)).append("000");
				                    
				                    resultVfei=vfeiBuilder.toString();
				                }
				            }
				        }
				    } catch (JSONException e) {
				    	System.err.println("6. Error >>  "+e);
				    }
				}
            }
        } else {
        	System.err.println("7. Error >>  length<");
            
        }
		return resultVfei;
    }

}
