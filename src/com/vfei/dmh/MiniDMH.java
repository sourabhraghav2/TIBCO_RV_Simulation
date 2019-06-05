package com.vfei.dmh;

import com.hume.DMH.DmhClient;
import com.hume.DMH.DmhClientItf;
import com.hume.DMH.DmhReceiveItf;

public class MiniDMH implements DmhReceiveItf {
	private DmhClient dmhClient = new DmhClient();
	private String log = "start >>";
	public static String message;

	

	
	public MiniDMH(String host, int port, String mbxName) throws Exception {

		// log=log+"About to Aborted " ;
		// abortConnection(mbxName);
		// log=log+"Aborted :: " + mbxName;
		init(host, port);
		log = log + "Initiated :: " + host + " :: " + port;
		establishConnection(mbxName);
		log = log + "Established ::  " + mbxName;

	}

	public String getLog() {

		return log;

	}

	public void setLog(String log) {
		this.log = log;
	}

	private void abortConnection(String mbxName) throws Exception {
		dmhClient.disarm(mbxName);
		dmhClient.put("DBM", "close sub RV_GW to emon_machine");
		dmhClient.disarm(mbxName);
		dmhClient.setLostServer(null);
		dmhClient.disconnect();

	}

	private void establishConnection(String mbxName) throws Exception {
		dmhClient.setDefaultTimeout(120);
		dmhClient.whenMsg(mbxName, this);
		dmhClient.put("DBM", "open sub RV_GW to emon_machine sendto=RV_GW insert delete *");

	}

	private void init(String host, int port) throws Exception {

		dmhClient.init(host, port);

	}

	@Override
	public void dmhReceive(DmhClientItf dmh, String msg, String destBoxName, String replyBoxName) throws Exception {
		dmh.whenMsgAgain();
		this.message = msg;
		System.out.println("Message coming :: " + msg);
		dmh.put(replyBoxName, "I am responding!!");

	}

}
