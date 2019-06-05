package com.rv;

import org.codehaus.jackson.JsonNode;

import com.dataprocessor.DataProcessor;
import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvListener;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgCallback;
import com.tibco.tibrv.TibrvRvdTransport;

public class MiniRVListener implements TibrvMsgCallback ,Runnable {
	String site = "ATBK";
	TibrvListener tibrvListener;
	boolean startFlag=true;
	public static String fullSubject;
	public MiniRVListener(String service, String network, String demon, String fullSubject) throws TibrvException {
		
		if (tibrvListener != null) {
			tibrvListener.destroy();
		}
		Tibrv.open();
			TibrvRvdTransport transport = new TibrvRvdTransport(service, network, demon);
			System.out.println("Transport :: "+transport.toString());
			transport.setDescription("RV_GW@" + "PIC_QFN_1  (Dummy:" + fullSubject + " : " + System.getProperty("com.sun.management.jmxremote.port") + ")");
//			MiniRVListener. fullSubject = site.concat(".REQ." + subjectPrefix + ".>");
			MiniRVListener. fullSubject = fullSubject;
			tibrvListener = new TibrvListener(Tibrv.defaultQueue(), this, transport, fullSubject, null);
			System.out.println("RV Listener :: "+tibrvListener.toString());
		System.out.println("End");
	}

	
	private void start() throws TibrvException {
		System.out.println("Listener started !");
		while (startFlag) {
			try {
				System.out.println("Waiting !");
				Tibrv.defaultQueue().dispatch();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	public void onMsg(TibrvListener listener, TibrvMsg msg) {
		TibrvMsg replyMsg = new TibrvMsg();
		try {
			System.out.println("Recieved Message :: " + msg);
			System.out.println("Send  Subject:: " + msg.getSendSubject());
			System.out.println("Excluded SubjectName : "+DataProcessor.excludeMachineName(msg.getSendSubject(),false));
			JsonNode data = DataProcessor.subjectResponse.get(DataProcessor.excludeMachineName(msg.getSendSubject(),false));
			System.out.println("Data  :: " + data);
			if(data!=null && data.get("responseJson")!=null){
				System.out.println("responseJson : "+data.get("responseJson").getTextValue());
				replyMsg.add("DATA", data.get("responseJson").toString());
			}else{
				System.out.println("No mocking  Response");
			}
			
			
			listener.getTransport().sendReply(replyMsg, msg);
		} catch (TibrvException e) {

			e.printStackTrace();
		}

	}

	public void destroy() {

		if (tibrvListener != null && tibrvListener.isValid()) {
			System.out.println("Stopped  !");
			tibrvListener.destroy();
			startFlag=false;
		}
	}


	@Override
	public void run() {
		try {
			start();
		} catch (TibrvException e) {
			
			e.printStackTrace();
		}
		
	}

}
