package com.rv;

import org.codehaus.jackson.JsonNode;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvRvdTransport;

public class MiniRVSender {

	private TibrvRvdTransport transport;
	private TibrvMsg tibrvMsg = new TibrvMsg();

	public MiniRVSender(String service, String network, String deamon) throws TibrvException {
		Tibrv.open();
		transport = new TibrvRvdTransport(service, network, deamon);
	}
	public void setSubject(String subject) throws TibrvException{
		tibrvMsg.setSendSubject(subject);
	}
	public void setMessage(String data) throws TibrvException {
		tibrvMsg.add("DATA", data);
	}
	public  TibrvMsg sendRequestMessage() throws TibrvException {
		return transport.sendRequest(tibrvMsg,300);
	}
	public  boolean sendEventMessage() throws TibrvException {
		 transport.send(tibrvMsg);
		 return true;
	}
}
