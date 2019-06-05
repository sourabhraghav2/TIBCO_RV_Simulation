package com.vfei.parser;

import java.util.HashMap;
import java.util.Map.Entry;

import com.vfei.exception.IncorrectExpressionException;

import java.util.Stack;



public class Parser {

	/*public static void main(String[] args) throws Exception {

		
		String input = ReaderWriter.readFileAsString("inputVfei.txt");
		input=padding(input);
		System.out.println(input.length());
		System.out.println(input);
		
		HashMap<Integer,Character> hm=new HashMap<>();
		for(int i=0;i<input.length();++i){
			hm.put(i, input.charAt(i));
		}
		System.out.println(hm);
		
		
		String converted=convertFromVfeiToString(input);
		
		System.out.println("Converted: " + converted);
		
		
	}*/
	public static String convertFromVfeiToString(String input) throws IncorrectExpressionException {
		input=padding(input);
		HashMap<Integer,Integer> startAndEnd = getCountOfBound(input);
		String result=formatAccordingToVfei(startAndEnd,input);
		result=result.trim();
		result=result.substring(1, result.length()-1);
		return result;
	}
	

	private static String padding(String input) {
		input=" "+input+" ";
		return input;
	}

	private static String formatAccordingToVfei(HashMap<Integer, Integer> startAndEnd, String input) {

		for (Entry<Integer, Integer> entry :startAndEnd.entrySet()){
			
			input=formatBracketStartEnd(input,entry.getKey(),entry.getValue());
			
		}
		
		return input;
	}

	private static String formatBracketStartEnd(String input, Integer start, Integer end) {

		int colonIndex=findToLeft(':',input,start);
		int endIndex=findEndToLeft(input,start-1);
		
		String type=input.substring(endIndex+1, colonIndex);
		String concatedStr="{"+type;
		/*System.out.println("Inout : "+input);
		System.out.println("concatedStr : "+concatedStr);
		*/input=addStringToLocation(input,concatedStr,endIndex+1,start);
		/*System.out.println("Output : "+input);*/
		return input;
	}
	
	public static void main1(String[] args) throws IncorrectExpressionException {
		System.out.println(" L:1{A:3{sou}}");
/*		String a=addStringToLocation(" L:1{A:3{sou}}","{L",1,4);
		String n=" {L A:3{sou}}";
		String b=addStringToLocation(n,"{A",3,7);
		
		System.out.println(a);
		System.out.println(b);
*/
		
		System.out.println(convertFromVfeiToString(" L:1{A:3{sou}} "));
	}
	

	private static String addStringToLocation(String input, String concatedStr, int i,int till) {
		
		for(int j=i,k=-1;j<=till;++j,++k){
			
			if(k==-1){
				input=replace(input,j,' ');
			}else if (k<concatedStr.length()){
				input=replace(input,j,concatedStr.charAt(k));
			}else {
				input=replace(input,j,' ');
			}
		}
		return input;
	}

	

	public static String replace(String str, int index, char replace){     
	    if(str==null){
	        return str;
	    }else if(index<0 || index>=str.length()){
	        return str;
	    }
	    char[] chars = str.toCharArray();
	    chars[index] = replace;
	    return String.valueOf(chars);       
	}

	private static int findEndToLeft( String input, Integer start) {

		while(start>=0){
			if(input.charAt(start)=='{' ||input.charAt(start)=='}' ||input.charAt(start)==' '){
				return start;
			}
			start--;
		}
		return 0;
	
	}

	private static int findToLeft(char code,String input, Integer start) {
		
		while(start>0){
			if(input.charAt(start)==code){
				return start;
			}
			start--;
		}
		return 0;
	}

	private static HashMap<Integer,Integer> getCountOfBound(String input) throws IncorrectExpressionException {
		Stack<Data> stack = new Stack<>();
		HashMap<Integer,Integer> hm=new HashMap<>();
		int boundCount = 0;
	
		for (int i = 0; i < input.length(); ++i) {
			if (input.charAt(i) == '{') {
				stack.add(new Data(input.charAt(i),i));
			} else if (input.charAt(i) == '}') {
				if (stack.empty()) {
					throw new IncorrectExpressionException(" Starting from }");
				} else {
					int start=0;
					if (stack.peek().key == '{') {
						start=stack.peek().value;
						stack.pop();
					}
					hm.put(start, i);
					boundCount++;
				}
			}
		}
		return hm;
	}

}

class Data {
	Character key;
	Integer value;

	public Data(Character key, Integer value) {
		this.key = key;
		this.value = value;
	}

}

