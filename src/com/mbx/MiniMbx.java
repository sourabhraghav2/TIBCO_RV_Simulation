package com.mbx;

import com.hume.DMH.DmhClient;

public class MiniMbx {
	
	DmhClient dmhClient = new DmhClient();
	public MiniMbx(String network,String picInstance) throws Exception{
		dmhClient.init(network, dmhClient.groupnamePort(picInstance));	
	}
	
	public String doXact(String mbxName,String mbxInputData) throws Exception{
		String reply = dmhClient.doXact(mbxName, mbxInputData);
		return reply;
	}
	public void sendPut(String mbxName,String mbxInputData) throws Exception{
		
		dmhClient.put(mbxName, mbxInputData);
		
	}

/*	dmhClient.init("165.114.28.223", dmhClient.groupnamePort("Pic_QFN_1"));
	String reply = dmhClient.doXact("DBM", "telect mid from emon_machine");
	System.out.println(reply);
*/
	
}
