package com.vfei.controller;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dataprocessor.DataProcessor;
import com.io.ReaderWriter;
import com.mbx.MiniMbx;
import com.rv.MiniRVListener;
import com.rv.MiniRVSender;
import com.tibco.tibrv.TibrvMsg;

@RestController
@RequestMapping("/pending")
public class PendingDealer {
	public static String filePath = "data/PendingRequest.json";
	MiniRVListener listener = null;
	static String network;
	static String picInstance;
	MiniMbx mbx = null;
	MiniRVSender rv=null;
	String rvDeamon=null;
	String rvService =null;
	String rvNetwork =null;
	@RequestMapping(value = "/initApp", method = { RequestMethod.POST }, produces = "text/plain; charset=utf-8")
	public String initApp(@RequestBody String input) {
		System.out.println("input:: " + input);
		String outputData = null;
		ObjectNode response = JsonNodeFactory.instance.objectNode();
		ObjectMapper om = new ObjectMapper();
		String result = ">";
		try {

			JsonNode node = om.readTree(input);
			result = "";
			network = node.path("network").getTextValue();
			picInstance = node.path("picInstance").getTextValue();
			rvNetwork = node.path("rvNetwork").getTextValue();
			rvService = node.path("rvService").getTextValue();
			rvDeamon = node.path("rvDeamon").getTextValue();
			
			mbx = new MiniMbx(network, picInstance);

			result = network + "  :  " + picInstance;
			outputData = result;
			response.put("responseCode", true);
			response.put("responseDesc", "Response recieve successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {

			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());

		}
		System.out.println("Output:: " + response.toString());
		return response.toString();

	}

	/*
	 * mbx whenmsg pending {mbx whenmsg again ; puts $mbxmsg ; mbx put $mbxreply
	 * "Yes"}
	 */
	@RequestMapping(value = "/getPendingMapListForMachine/{mid}", method = RequestMethod.GET)
	@ResponseBody
	public String getPendingMapListForMachine(@PathVariable("mid") String mid) throws Exception {

		String msg = "MID/A=\"DAASM051\" MTY/A=\"E\" ECD/U4=0 ETX/A=\"\" EVENT_ID/A=\"5555\" CLOCK/A=\"" + System.currentTimeMillis() + "\"";
		ObjectNode response = JsonNodeFactory.instance.objectNode();
		try {
			String reply = mbx.doXact("equip_box", msg);
			String keyValue[] = reply.split(",");
			ObjectNode modelAndMap = JsonNodeFactory.instance.objectNode();
			for (String each : keyValue) {

				String key = each.split(":")[0];
				String value = each.split(":")[2]+":"+each.split(":")[1];
				if (modelAndMap.has(key)) {
					ArrayNode list = (ArrayNode) modelAndMap.get(key);
					list.add(value);
				} else {
					ArrayNode list = JsonNodeFactory.instance.arrayNode();
					list.add(value);
					modelAndMap.put(key, list);
				}
			}
			response.put("responseCode", true);
			response.put("responseDesc", "Response recieve successfully! ");
			response.put("responseData", modelAndMap);
		} catch (Exception e) {
			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());

		}
		System.out.println("Output:: " + response.toString());
		return response.toString();
	}

	@RequestMapping(value = "/sendRequest", method = { RequestMethod.POST }, produces = "text/plain; charset=utf-8")
	public String sendRequest(@RequestBody String input) {
		System.out.println("input:: " + input);
		String outputData = null;
		ObjectNode response = JsonNodeFactory.instance.objectNode();
		ObjectMapper om = new ObjectMapper();
		String result = ">";
		try {

			JsonNode node = om.readTree(input);
			result = "";

			MiniMbx mbx = new MiniMbx(node.path("config").path("network").getTextValue(), node.path("config").path("picInstance").getTextValue());
			if (node.path("form").path("type").getTextValue().equalsIgnoreCase("mbxXact")) {
				result = mbx.doXact(node.path("form").path("mbxName").getTextValue(), node.path("form").path("mbxInputData").getTextValue());
			} else if (node.path("form").path("type").getTextValue().equalsIgnoreCase("put")) {
				mbx.sendPut(node.path("form").path("mbxName").getTextValue(), node.path("form").path("mbxInputData").getTextValue());
				result = "Sent";
			}

			outputData = result;
			response.put("responseCode", true);
			response.put("responseDesc", "Response recieve successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {

			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());

		}
		System.out.println("Output:: " + response.toString());
		return response.toString();

	}
	@RequestMapping(value = "/uploadRequestByRV", method = { RequestMethod.POST }, produces = "text/plain; charset=utf-8")
	public String uploadRequestByRV(@RequestBody String input) {
		System.out.println("input:: " + input);
		String outputData = null;
		ObjectNode response = JsonNodeFactory.instance.objectNode();
		ObjectMapper om = new ObjectMapper();
		String reply=null;
		String desc=null;
		try {
			JsonNode node = om.readTree(input);
			Iterator<String> fields = node.getFieldNames();
			while(fields.hasNext()){
				String key = fields.next();
				String value=node.get(key).getTextValue();
				String mid=value.split(":")[0];
				String type=value.split(":")[1];
				String mapId=value.split(":")[2];
				String data ="UPLOAD_MID/A=\""+mid+"\" UPLOAD_MAPID/A=\""+mapId+"\" TYPE/A=\""+type+"\"";
				String msg = "MID/A=\"DAASM051\" MTY/A=\"E\" ECD/U4=0 ETX/A=\"\" EVENT_ID/A=\"3333\" CLOCK/A=\"" + System.currentTimeMillis() + "\" "+data ;
			
				reply=mbx.doXact("equip_box", msg);
				
				
				
				String subject="ATBK.REQ.TDS.MAP_UPDATE."+mid;
				
				
				String msgData =getUpdateRequest(mapId,reply)+"";
				System.out.println("subject: "+subject);
				System.out.println("msgData: "+msgData);
				rv=new MiniRVSender(rvService , rvNetwork, rvDeamon);
				rv.setSubject(subject);
				rv.setMessage(msgData);
				
				TibrvMsg rvResponse = rv.sendRequestMessage();
				System.out.println(rvResponse);
				if(rvResponse.getNumFields()>0 && rvResponse!=null){
					desc= (String)rvResponse .getFieldByIndex(0).data;
				}
				
				outputData=key;
			}
			
			response.put("responseCode", true);
			response.put("responseDesc", desc);
			response.put("responseData", outputData);
		} catch (Exception e) {
			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());
		}
		System.out.println("Output:: " + response.toString());
		return response.toString();
	}
	
	private ObjectNode getUpdateRequest(String mapId,String msg) throws UnsupportedEncodingException {
		ObjectNode msgData = JsonNodeFactory.instance.objectNode();
		ObjectNode mapUpdateRequest= JsonNodeFactory.instance.objectNode();
		mapUpdateRequest.put("mapId", mapId);
		mapUpdateRequest.put("processStep", "");
		String base64encodedString = Base64.getEncoder().encodeToString(msg.getBytes("utf-8"));
		mapUpdateRequest.put("mapString", base64encodedString);
		msgData.put("MapUpdateRequest", mapUpdateRequest);
		return msgData;

	}
	@RequestMapping(value = "/uploadRequest", method = { RequestMethod.POST }, produces = "text/plain; charset=utf-8")
	public String uploadRequest(@RequestBody String input) {
		System.out.println("input:: " + input);
		String outputData = null;
		ObjectNode response = JsonNodeFactory.instance.objectNode();
		ObjectMapper om = new ObjectMapper();
		String result = ">";
		String reply=null;
		try {
			
			JsonNode node = om.readTree(input);
			result = "";
			Iterator<String> fields = node.getFieldNames();
			while(fields.hasNext()){
				String key = fields.next();
				String  value=node.get(key).getTextValue();
				
				String mid=value.split(":")[0];
				//String mid=node.get("mid").getTextValue();
				String type=value.split(":")[1];
				String mapId=value.split(":")[2];
				String data ="UPLOAD_MID/A=\""+mid+"\" UPLOAD_MAPID/A=\""+mapId+"\" TYPE/A=\""+type+"\"";
				String msg = "MID/A=\"DAASM051\" MTY/A=\"E\" ECD/U4=0 ETX/A=\"\" EVENT_ID/A=\"2222\" CLOCK/A=\"" + System.currentTimeMillis() + "\" "+data ;	
				reply=mbx.doXact("equip_box", msg);
				
				
			}

			outputData = reply;
			response.put("responseCode", true);
			response.put("responseDesc", "Response recieve successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {

			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());

		}
		System.out.println("Output:: " + response.toString());
		return response.toString();

	}

	@RequestMapping(value = "/restoreFile", method = { RequestMethod.GET }, produces = "text/plain; charset=utf-8")
	public String restoreFile() throws Exception {
		ReaderWriter.writeToFile(filePath, "");
		return "Done!";
	}

	@RequestMapping(value = "/startRVListener", method = { RequestMethod.POST }, produces = "text/plain; charset=utf-8")
	public String startRVListener(@RequestBody String input) {
		System.out.println("input:: " + input);
		String outputData = null;
		ObjectNode response = JsonNodeFactory.instance.objectNode();
		ObjectMapper om = new ObjectMapper();
		String result = "Started ";
		try {

			JsonNode node = om.readTree(input);
			result = "";
			if (listener != null) {
				listener.destroy();
			}
			listener = new MiniRVListener(node.get("service").getTextValue(), node.get("network").getTextValue(), node.get("deamon").getTextValue(), node.get("fullreqName").getTextValue());
			(new Thread(listener)).start();
			DataProcessor.initializeSubjectResponse();
			outputData = result;
			response.put("responseCode", true);
			response.put("responseDesc", "Started successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {

			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());

		}
		System.out.println("Output:: " + response.toString());
		return response.toString();

	}

	@RequestMapping(value = "/stopRVListener", method = { RequestMethod.POST }, produces = "text/plain; charset=utf-8")
	public String stopRVListener(@RequestBody String input) {
		System.out.println("input:: " + input);
		String outputData = null;
		ObjectNode response = JsonNodeFactory.instance.objectNode();

		String result = ">";
		try {

			result = "Destroyed ";
			if (listener != null) {
				listener.destroy();
			}

			outputData = result;
			response.put("responseCode", true);
			response.put("responseDesc", "Stopped successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {

			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());

		}
		System.out.println("Output:: " + response.toString());
		return response.toString();

	}

	@RequestMapping(value = "/addRequest", method = { RequestMethod.POST }, produces = "text/plain; charset=utf-8")
	public String addRequest(@RequestBody String input) {
		System.out.println("input:: " + input);
		String outputData = null;
		ObjectNode response = JsonNodeFactory.instance.objectNode();
		ObjectMapper om = new ObjectMapper();
		String result = ">";
		try {
			JsonNode inputNode = om.readTree(input);
			result = "";

			String fileData = ReaderWriter.readFileAsString(filePath);

			ObjectNode structureNode = (ObjectNode) om.readTree(fileData);
			System.out.println("Existing Json  :: " + structureNode);
			String reqName = inputNode.get("reqName").getTextValue();
			String modelName = inputNode.get("modelName").getTextValue();

			if (!structureNode.has(modelName)) {
				structureNode.put(modelName, JsonNodeFactory.instance.objectNode());
			}
			ObjectNode modelList = (ObjectNode) structureNode.get(modelName);

			modelList.put(reqName, inputNode);
			/*
			 * if(!modelList.has(reqName)){
			 * 
			 * }
			 */
			System.out.println("Modified Json  :: " + structureNode);
			ReaderWriter.writeToFile(filePath, String.valueOf(structureNode));
			outputData = " Added successfull! ";
			response.put("responseCode", true);
			response.put("responseDesc", "Added successfull! ");
			response.put("responseData", outputData);
		} catch (Exception e) {

			response.put("responseCode", false);
			response.put("responseDesc", "Error >> " + e.getMessage());

		}
		System.out.println("Output:: " + response.toString());
		return response.toString();

	}

	@RequestMapping(value = "/sendEvent", method = { RequestMethod.POST }, produces = "text/plain; charset=utf-8")
	public String sendEvent(@RequestBody String input) {
		System.out.println("input:: " + input);
		String outputData = null;
		ObjectNode response = JsonNodeFactory.instance.objectNode();
		ObjectMapper om = new ObjectMapper();
		String result = ">";
		try {
			JsonNode node = om.readTree(input);
			result = "";

			MiniRVSender rv = new MiniRVSender(node.get("service").getTextValue(), node.get("network").getTextValue(), node.get("deamon").getTextValue());
			rv.setSubject(node.get("reqName").getTextValue());
			rv.setMessage(node.get("data").getTextValue());
			result = String.valueOf(rv.sendEventMessage());

			outputData = result;
			response.put("responseCode", true);
			response.put("responseDesc", "Response recieve successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {

			response.put("responseCode", false);
			response.put("responseDesc", "Error >> " + e.getMessage());

		}
		System.out.println("Output:: " + response.toString());
		return response.toString();

	}

	@RequestMapping(value = "/initStructure", method = { RequestMethod.GET }, produces = "text/plain; charset=utf-8")
	public String initializeStructure() {

		String outputData = null;
		ObjectNode response = JsonNodeFactory.instance.objectNode();

		String result = "";
		try {

			// ReaderWriter.writeToFile(filePath, "I have written");
			String fileData = ReaderWriter.readFileAsString(filePath);

			result = fileData;

			outputData = result;
			response.put("responseCode", true);
			response.put("responseDesc", "Initialize successfully! ");
			response.put("responseData", outputData);
		} catch (Exception e) {

			response.put("responseCode", false);
			response.put("responseDesc", e.getMessage());

		}
		System.out.println("Output:: " + response.toString());
		return response.toString();

	}

}