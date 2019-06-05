package com.text;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hume.DMH.Vfei2Map;

public class Demo {
	private static final Map<String, String> NameValues = new HashMap<String, String>();
	private static final Map<Integer, String> waferXToOther = new HashMap<>();
	public static void main(String[] args) throws Exception {
		String list = " FX=\"108\" FY=\"47\" TX=\"0\" TY=\"0\"/> <T FX=\"107\" FY=\"47\" TX=\"0\" TY=\"1\"/> <T FX=\"106\" FY=\"47\" TX=\"0\" TY=\"2\"/> <T FX=\"105\" FY=\"47\" TX=\"0\" TY=\"3\"/> <T FX=\"104\" FY=\"47\" TX=\"0\" TY=\"4\"/> <T FX=\"103\" FY=\"47\" TX=\"0\" TY=\"5\"/> <T FX=\"109\" FY=\"47\" TX=\"1\" TY=\"0\"/> <T FX=\"110\" FY=\"47\" TX=\"1\" TY=\"1\"/> <T FX=\"111\" FY=\"47\" TX=\"1\" TY=\"2\"/> <T FX=\"112\" FY=\"47\" TX=\"1\" TY=\"3\"/> <T FX=\"113\" FY=\"47\" TX=\"1\" TY=\"4\"/> <T FX=\"114\" FY=\"47\" TX=\"1\" TY=\"5\"/> <T FX=\"120\" FY=\"47\" TX=\"2\" TY=\"0\"/> <T FX=\"119\" FY=\"47\" TX=\"2\" TY=\"1\"/> <T FX=\"118\" FY=\"47\" TX=\"2\" TY=\"2\"/> <T FX=\"117\" FY=\"47\" TX=\"2\" TY=\"3\"/> <T FX=\"116\" FY=\"47\" TX=\"2\" TY=\"4\"/> <T FX=\"115\" FY=\"47\" TX=\"2\" TY=\"5\"/> <T FX=\"121\" FY=\"47\" TX=\"3\" TY=\"0\"/> <T FX=\"122\" FY=\"47\" TX=\"3\" TY=\"1\"/> <T FX=\"123\" FY=\"47\" TX=\"3\" TY=\"2\"/> <T FX=\"124\" FY=\"47\" TX=\"3\" TY=\"3\"/> <T FX=\"125\" FY=\"47\" TX=\"3\" TY=\"4\"/> <T FX=\"126\" FY=\"47\" TX=\"3\" TY=\"5\"/> <T FX=\"132\" FY=\"47\" TX=\"4\" TY=\"0\"/> <T FX=\"131\" FY=\"47\" TX=\"4\" TY=\"1\"/> <T FX=\"130\" FY=\"47\" TX=\"4\" TY=\"2\"/> <T FX=\"129\" FY=\"47\" TX=\"4\" TY=\"3\"/> <T FX=\"128\" FY=\"47\" TX=\"4\" TY=\"4\"/> <T FX=\"127\" FY=\"47\" TX=\"4\" TY=\"5\"/> <T FX=\"133\" FY=\"47\" TX=\"5\" TY=\"0\"/> <T FX=\"134\" FY=\"47\" TX=\"5\" TY=\"1\"/> <T FX=\"135\" FY=\"47\" TX=\"5\" TY=\"2\"/> <T FX=\"136\" FY=\"47\" TX=\"5\" TY=\"3\"/> <T FX=\"137\" FY=\"47\" TX=\"5\" TY=\"4\"/> <T FX=\"138\" FY=\"47\" TX=\"5\" TY=\"5\"/> <T FX=\"138\" FY=\"46\" TX=\"6\" TY=\"0\"/> <T FX=\"139\" FY=\"46\" TX=\"6\" TY=\"1\"/> <T FX=\"140\" FY=\"46\" TX=\"6\" TY=\"2\"/> <T FX=\"141\" FY=\"47\" TX=\"6\" TY=\"3\"/> <T FX=\"140\" FY=\"47\" TX=\"6\" TY=\"4\"/> <T FX=\"139\" FY=\"47\" TX=\"6\" TY=\"5\"/> <T FX=\"137\" FY=\"46\" TX=\"7\" TY=\"0\"/> <T FX=\"135\" FY=\"46\" TX=\"7\" TY=\"1\"/> <T FX=\"134\" FY=\"46\" TX=\"7\" TY=\"2\"/> <T FX=\"133\" FY=\"46\" TX=\"7\" TY=\"3\"/> <T FX=\"132\" FY=\"46\" TX=\"7\" TY=\"4\"/> <T FX=\"131\" FY=\"46\" TX=\"7\" TY=\"5\"/> <T FX=\"125\" FY=\"46\" TX=\"8\" TY=\"0\"/> <T FX=\"126\" FY=\"46\" TX=\"8\" TY=\"1\"/> <T FX=\"127\" FY=\"46\" TX=\"8\" TY=\"2\"/> <T FX=\"128\" FY=\"46\" TX=\"8\" TY=\"3\"/> <T FX=\"129\" FY=\"46\" TX=\"8\" TY=\"4\"/> <T FX=\"130\" FY=\"46\" TX=\"8\" TY=\"5\"/> <T FX=\"124\" FY=\"46\" TX=\"9\" TY=\"0\"/> <T FX=\"123\" FY=\"46\" TX=\"9\" TY=\"1\"/> <T FX=\"122\" FY=\"46\" TX=\"9\" TY=\"2\"/> <T FX=\"121\" FY=\"46\" TX=\"9\" TY=\"3\"/> <T FX=\"120\" FY=\"46\" TX=\"9\" TY=\"4\"/> <T FX=\"119\" FY=\"46\" TX=\"9\" TY=\"5\"/> <T FX=\"113\" FY=\"46\" TX=\"1\" TY=\"0\"/> <T FX=\"114\" FY=\"46\" TX=\"1\" TY=\"1\"/> <T FX=\"115\" FY=\"46\" TX=\"1\" TY=\"2\"/> <T FX=\"116\" FY=\"46\" TX=\"1\" TY=\"3\"/> <T FX=\"117\" FY=\"46\" TX=\"1\" TY=\"4\"/> <T FX=\"118\" FY=\"46\" TX=\"1\" TY=\"5\"/> <T FX=\"112\" FY=\"46\" TX=\"11\" TY=\"0\"/> <T FX=\"111\" FY=\"46\" TX=\"11\" TY=\"1\"/> <T FX=\"110\" FY=\"46\" TX=\"11\" TY=\"2\"/> <T FX=\"109\" FY=\"46\" TX=\"11\" TY=\"3\"/> <T FX=\"108\" FY=\"46\" TX=\"11\" TY=\"4\"/> <T FX=\"107\" FY=\"46\" TX=\"11\" TY=\"5\"/> <T FX=\"101\" FY=\"46\" TX=\"12\" TY=\"0\"/> <T FX=\"102\" FY=\"46\" TX=\"12\" TY=\"1\"/> <T FX=\"103\" FY=\"46\" TX=\"12\" TY=\"2\"/> <T FX=\"104\" FY=\"46\" TX=\"12\" TY=\"3\"/> <T FX=\"105\" FY=\"46\" TX=\"12\" TY=\"4\"/> <T FX=\"106\" FY=\"46\" TX=\"12\" TY=\"5\"/> <T FX=\"100\" FY=\"46\" TX=\"13\" TY=\"0\"/> <T FX=\"99\" FY=\"46\" TX=\"13\" TY=\"1\"/> <T FX=\"98\" FY=\"46\" TX=\"13\" TY=\"2\"/> <T FX=\"97\" FY=\"46\" TX=\"13\" TY=\"3\"/> <T FX=\"96\" FY=\"46\" TX=\"13\" TY=\"4\"/> <T FX=\"95\" FY=\"46\" TX=\"13\" TY=\"5\"/> <T FX=\"89\" FY=\"46\" TX=\"14\" TY=\"0\"/> <T FX=\"90\" FY=\"46\" TX=\"14\" TY=\"1\"/> <T FX=\"91\" FY=\"46\" TX=\"14\" TY=\"2\"/> <T FX=\"92\" FY=\"46\" TX=\"14\" TY=\"3\"/> <T FX=\"93\" FY=\"46\" TX=\"14\" TY=\"4\"/> <T FX=\"94\" FY=\"46\" TX=\"14\" TY=\"5\"/> <T FX=\"88\" FY=\"46\" TX=\"15\" TY=\"0\"/> <T FX=\"87\" FY=\"46\" TX=\"15\" TY=\"1\"/> <T FX=\"86\" FY=\"46\" TX=\"15\" TY=\"2\"/> <T FX=\"85\" FY=\"46\" TX=\"15\" TY=\"3\"/> <T FX=\"84\" FY=\"46\" TX=\"15\" TY=\"4\"/> <T FX=\"83\" FY=\"46\" TX=\"15\" TY=\"5\"/> <T FX=\"77\" FY=\"46\" TX=\"16\" TY=\"0\"/> <T FX=\"78\" FY=\"46\" TX=\"16\" TY=\"1\"/> <T FX=\"79\" FY=\"46\" TX=\"16\" TY=\"2\"/> <T FX=\"80\" FY=\"46\" TX=\"16\" TY=\"3\"/> <T FX=\"81\" FY=\"46\" TX=\"16\" TY=\"4\"/> <T FX=\"82\" FY=\"46\" TX=\"16\" TY=\"5\"/> <T FX=\"76\" FY=\"46\" TX=\"17\" TY=\"0\"/> <T FX=\"75\" FY=\"46\" TX=\"17\" TY=\"1\"/> <T FX=\"74\" FY=\"46\" TX=\"17\" TY=\"2\"/> <T FX=\"73\" FY=\"46\" TX=\"17\" TY=\"3\"/> <T FX=\"72\" FY=\"46\" TX=\"17\" TY=\"4\"/> <T FX=\"71\" FY=\"46\" TX=\"17\" TY=\"5\"/> <T FX=\"65\" FY=\"46\" TX=\"18\" TY=\"0\"/> <T FX=\"66\" FY=\"46\" TX=\"18\" TY=\"1\"/> <T FX=\"67\" FY=\"46\" TX=\"18\" TY=\"2\"/> <T FX=\"68\" FY=\"46\" TX=\"18\" TY=\"3\"/> <T FX=\"69\" FY=\"46\" TX=\"18\" TY=\"4\"/> <T FX=\"70\" FY=\"46\" TX=\"18\" TY=\"5\"/> <T FX=\"64\" FY=\"46\" TX=\"19\" TY=\"0\"/> <T FX=\"63\" FY=\"46\" TX=\"19\" TY=\"1\"/> <T FX=\"62\" FY=\"46\" TX=\"19\" TY=\"2\"/> <T FX=\"61\" FY=\"46\" TX=\"19\" TY=\"3\"/> <T FX=\"60\" FY=\"46\" TX=\"19\" TY=\"4\"/> <T FX=\"59\" FY=\"46\" TX=\"19\" TY=\"5\"/> <T FX=\"53\" FY=\"46\" TX=\"2\" TY=\"0\"/> <T FX=\"54\" FY=\"46\" TX=\"2\" TY=\"1\"/> <T FX=\"55\" FY=\"46\" TX=\"2\" TY=\"2\"/> <T FX=\"56\" FY=\"46\" TX=\"2\" TY=\"3\"/> <T FX=\"57\" FY=\"46\" TX=\"2\" TY=\"4\"/> <T FX=\"58\" FY=\"46\" TX=\"2\" TY=\"5\"/> <T FX=\"52\" FY=\"46\" TX=\"21\" TY=\"0\"/> <T FX=\"51\" FY=\"46\" TX=\"21\" TY=\"1\"/> <T FX=\"50\" FY=\"46\" TX=\"21\" TY=\"2\"/> <T FX=\"49\" FY=\"46\" TX=\"21\" TY=\"3\"/> <T FX=\"48\" FY=\"46\" TX=\"21\" TY=\"4\"/> <T FX=\"47\" FY=\"46\" TX=\"21\" TY=\"5\"/> <T FX=\"41\" FY=\"46\" TX=\"22\" TY=\"0\"/> <T FX=\"42\" FY=\"46\" TX=\"22\" TY=\"1\"/> <T FX=\"43\" FY=\"46\" TX=\"22\" TY=\"2\"/> <T FX=\"44\" FY=\"46\" TX=\"22\" TY=\"3\"/> <T FX=\"45\" FY=\"46\" TX=\"22\" TY=\"4\"/> <T FX=\"46\" FY=\"46\" TX=\"22\" TY=\"5\"/> <T FX=\"40\" FY=\"46\" TX=\"23\" TY=\"0\"/> <T FX=\"39\" FY=\"46\" TX=\"23\" TY=\"1\"/> <T FX=\"38\" FY=\"46\" TX=\"23\" TY=\"2\"/> <T FX=\"37\" FY=\"46\" TX=\"23\" TY=\"3\"/> <T FX=\"36\" FY=\"46\" TX=\"23\" TY=\"4\"/> <T FX=\"35\" FY=\"46\" TX=\"23\" TY=\"5\"/> <T FX=\"29\" FY=\"46\" TX=\"24\" TY=\"0\"/> <T FX=\"30\" FY=\"46\" TX=\"24\" TY=\"1\"/> <T FX=\"31\" FY=\"46\" TX=\"24\" TY=\"2\"/> <T FX=\"32\" FY=\"46\" TX=\"24\" TY=\"3\"/> <T FX=\"33\" FY=\"46\" TX=\"24\" TY=\"4\"/> <T FX=\"34\" FY=\"46\" TX=\"24\" TY=\"5\"/> <T FX=\"28\" FY=\"46\" TX=\"25\" TY=\"0\"/> <T FX=\"27\" FY=\"46\" TX=\"25\" TY=\"1\"/> <T FX=\"26\" FY=\"46\" TX=\"25\" TY=\"2\"/> <T FX=\"25\" FY=\"46\" TX=\"25\" TY=\"3\"/> <T FX=\"24\" FY=\"46\" TX=\"25\" TY=\"4\"/> <T FX=\"23\" FY=\"46\" TX=\"25\" TY=\"5\"/> <T FX=\"17\" FY=\"46\" TX=\"26\" TY=\"0\"/> <T FX=\"18\" FY=\"46\" TX=\"26\" TY=\"1\"/> <T FX=\"19\" FY=\"46\" TX=\"26\" TY=\"2\"/> <T FX=\"20\" FY=\"46\" TX=\"26\" TY=\"3\"/> <T FX=\"21\" FY=\"46\" TX=\"26\" TY=\"4\"/> <T FX=\"22\" FY=\"46\" TX=\"26\" TY=\"5\"/> <T FX=\"16\" FY=\"46\" TX=\"27\" TY=\"0\"/> <T FX=\"15\" FY=\"46\" TX=\"27\" TY=\"1\"/> <T FX=\"14\" FY=\"46\" TX=\"27\" TY=\"2\"/> <T FX=\"13\" FY=\"46\" TX=\"27\" TY=\"3\"/> <T FX=\"12\" FY=\"46\" TX=\"27\" TY=\"4\"/> <T FX=\"11\" FY=\"46\" TX=\"27\" TY=\"5\"/> <T FX=\"5\" FY=\"46\" TX=\"28\" TY=\"0\"/> <T FX=\"6\" FY=\"46\" TX=\"28\" TY=\"1\"/> <T FX=\"7\" FY=\"46\" TX=\"28\" TY=\"2\"/> <T FX=\"8\" FY=\"46\" TX=\"28\" TY=\"3\"/> <T FX=\"9\" FY=\"46\" TX=\"28\" TY=\"4\"/> <T FX=\"10\" FY=\"46\" TX=\"28\" TY=\"5\"/> <T FX=\"4\" FY=\"46\" TX=\"29\" TY=\"0\"/> <T FX=\"3\" FY=\"46\" TX=\"29\" TY=\"1\"/> <T FX=\"2\" FY=\"46\" TX=\"29\" TY=\"2\"/> <T FX=\"1\" FY=\"46\" TX=\"29\" TY=\"3\"/> <T FX=\"0\" FY=\"46\" TX=\"29\" TY=\"4\"/> <T FX=\"0\" FY=\"45\" TX=\"29\" TY=\"5\"/>    ";
		while (list.indexOf("/>")>0) {
			
			String single=list.substring(0, list.indexOf("/>"));
//			System.out.println(single);
			Vfei2Map.parse(single, NameValues);
			//System.out.println(Integer.valueOf(NameValues.get("FX"))+" : "+NameValues.get("FY")+","+NameValues.get("TX")+","+NameValues.get("TY"));
			waferXToOther.put(Integer.valueOf(NameValues.get("FX")), NameValues.get("FY")+","+NameValues.get("TX")+","+NameValues.get("TY"));
			list=list.substring(list.indexOf("/>")+5, list.length()-1);
			System.out.println("("+NameValues.get("FX")+","+ NameValues.get("FY")+","+NameValues.get("TX")+","+NameValues.get("TY")+"),");
		}
		//System.out.println("waferXToOther : "+waferXToOther);
		
		for (Entry<Integer,String> each : waferXToOther.entrySet()){
			System.out.println(each .getKey()+" : "+each .getValue());
			
			
		}
	}
}