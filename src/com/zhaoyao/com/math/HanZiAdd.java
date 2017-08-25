package com.zhaoyao.com.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
/**
 * 一百零二加三千五百九十四减五十六	
 * @author lw
 * 编写时间: 2016年11月18日下午1:11:21
 * 类说明 :
 */
public class HanZiAdd {
	private static char[] c = new char[]{'点','一','二','三','四','五','六','七','八','九','壹','贰','弎','肆','伍','陆','柒','捌','玖'};
	private static char[] cNumber = new char[]{'.','1','2','3','4','5','6','7','8','9','1','2','3','4','5','6','7','8','9'};
	private static char[] fh_ = new char[]{'（','）','{','}','加','减','乘','除'};
	private static char[] fh = new char[]{'(',')','{','}','+','-','*','/'};
	
	private static char[] bsqw = new char[]{'百','十','千','万','佰','万','拾','仟'};
	
	public static void main(String[] args) {
		HanZiAdd hanZiAdd = new HanZiAdd();
		hanZiAdd.start();//一二三四五六七八九十  壹贰弎肆伍陆柒捌玖拾  百千万  佰仟万
	}
	
	protected final  Stack<Character> Stack_sc = new Stack<Character>();
	
	protected final  Stack<Character> sc_zhong_wen=new Stack<Character>();
	protected final  Stack<Character> sc_fh=new Stack<Character>();
	protected final  Stack<Character> sc_shuzi=new Stack<Character>();
	public boolean is_fh(char ccc) {
		for (int i = 0; i < fh.length; i++) {
			if(ccc == fh[i])return true;
		}
		return false;
	}
	private String inLangString = "cn";//ch  cn
	
	private String outLangString = "cn";//ch  cn
	
	public void setInLang(String eng) {
		if(eng != null && (eng.toLowerCase().equals("set_in_ch") || eng.toLowerCase().equals("set_in_ch"))){
			inLangString = eng.split("_")[2];
			System.out.println("设置成功");
			System.out.println("输入算式   = 号 结束开始计算：\n");//(1-3)* (1-5)
		}
	}
	public void setOutLang(String eng) {
		if(eng != null && (eng.toLowerCase().equals("set_out_ch") || eng.toLowerCase().equals("set_out_ch"))){
			outLangString = eng.split("_")[2];
			System.out.println("设置成功");
			System.out.println("输入算式   = 号 结束开始计算：\n");//(1-3)* (1-5)
		}
	}
	public boolean is_cNumber(char ccc) {
		for (int i = 0; i < cNumber.length; i++) {
			if(ccc == cNumber[i])return true;
		}
		return false;
	}
	public void start() {
		StringBuilder sb = new StringBuilder();
		Scanner sc = new Scanner(System.in);
		System.out.println("set 输入语言  set_in_ch  set_in_ch  ：\n");
		System.out.println("set 输出语言  set_out_ch  set_out_ch  ：\n");
		System.out.println("输入算式   exit 退出：\n");
		System.out.println("输入算式   = 号 结束开始计算：\n");
		w1:
		while (sc.hasNext()) {
			String next = sc.next();
			if("set_in_ch".equals(next.toLowerCase()) || "set_in_ch".equals(next.toLowerCase()))
			{
				setInLang(next);
				continue;
			}else if ("set_out_ch".equals(next.toLowerCase()) || "set_out_ch".equals(next.toLowerCase())) {
				setOutLang(next);
				continue;
			}
			if(!next.trim().endsWith("=")){
				sb.append(next);
				System.out.println("读取进度："+sb.toString());
			}else if(next.trim().toLowerCase().equals("exit")){
				break;
			}else if(next.trim().endsWith("=")){
				int length = next.trim().length();
				if(length > 1)
				sb.append(next.trim().substring(0, length-1));
				if(sb.toString().contains("=") 
						|| sb.toString().contains("~")
						|| sb.toString().contains("!")
						|| sb.toString().contains("@")
						|| sb.toString().contains("$")
						|| sb.toString().contains("%")
						|| sb.toString().contains("^")
						|| sb.toString().contains("&")
						|| sb.toString().contains("`")
						|| sb.toString().contains("\\")
						|| sb.toString().contains("'")
						|| sb.toString().contains("<")
						|| sb.toString().contains(">")
						|| sb.toString().contains(",")
						|| sb.toString().contains(";")
						|| sb.toString().contains(":")
						|| sb.toString().contains("?")
						|| sb.toString().contains("|")
						){
					System.err.println(""+sb.toString()+"??    =  ");
					sb.delete(0, sb.length());
					continue;
				}
				if(inLangString.equals("ch")){
					String string = sb.toString();
					for (int i = 0; i < c.length; i++) {
						string = string.replace(c[i], cNumber[i]);
					}
					for (int i = 0; i < fh.length; i++) {
						string = string.replace(fh_[i], fh[i]);
					}
					List<String> list = new ArrayList<String>();
					StringBuilder sb_Z = new StringBuilder();
					char[] charArray = string.toCharArray();
					for (int i = 0; i < charArray.length; i++) {
						switch (charArray[i]) {
						case '(':
							if(!sc_fh.isEmpty() && sc_shuzi.isEmpty())sb_Z.append(sc_fh.pop());
							else if (!sc_shuzi.isEmpty()){
								sb_Z.append(sc_shuzi.pop()+""+sc_fh.pop());
							}
							sb_Z.append(charArray[i]);
							break;
						case ')':
							if(!sc_fh.isEmpty() && sc_shuzi.isEmpty())sb_Z.append(sc_fh.pop());
							if(!sc_shuzi.isEmpty())sb_Z.append(sc_shuzi.pop());
							sb_Z.append(charArray[i]);
							break;
						case '{':
							if(!sc_fh.isEmpty() && sc_shuzi.isEmpty())sb_Z.append(sc_fh.pop());
							sb_Z.append(charArray[i]);
							break;
						case '}':
							if(!sc_fh.isEmpty() && sc_shuzi.isEmpty())sb_Z.append(sc_fh.pop());
							if(!sc_shuzi.isEmpty())sb_Z.append(sc_shuzi.pop());
							sb_Z.append(charArray[i]);
							break;
						case '+':
							sc_fh.push(charArray[i]);
							break;
						case '-':
							sc_fh.push(charArray[i]);
							break;
						case '*':
							sc_fh.push(charArray[i]);
							break;
						case '/':
							sc_fh.push(charArray[i]);
							break;
						case '零':
							list.add("0");
							if(is_cNumber(charArray[i + 1])){
								list.add(charArray[i + 1]+"");
								++i;
							}
							sc_zhong_wen.push(charArray[i]);
							break;
						case '十':
							if(!sc_shuzi.isEmpty()){
								Character pop = sc_shuzi.pop();
								list.add(pop+"0");
							}
							sc_zhong_wen.push(charArray[i]);
							break;
						case '百':
							if(!sc_shuzi.isEmpty()){
								Character pop = sc_shuzi.pop();
								list.add(pop+"00");
							}
							sc_zhong_wen.push(charArray[i]);
							break;
						case '千':
							if(!sc_shuzi.isEmpty()){
								Character pop = sc_shuzi.pop();
								list.add(pop+"000");
							}else {
								boolean is_fh = is_fh(charArray[i+1]);
							}
							sc_zhong_wen.push(charArray[i]);
							break;
						case '万':
							if(!sc_shuzi.isEmpty()){
								Character pop = sc_shuzi.pop();
								list.add(pop+"0000");
							}
							sc_zhong_wen.push(charArray[i]);
							break;
						case '拾':
							if(!sc_shuzi.isEmpty()){
								Character pop = sc_shuzi.pop();
								list.add(pop+"0");
							}
							sc_zhong_wen.push(charArray[i]);
							break;
						case '佰':
							if(!sc_shuzi.isEmpty()){
								Character pop = sc_shuzi.pop();
								list.add(pop+"00");
							}
							sc_zhong_wen.push(charArray[i]);
							break;
						case '仟':
							if(!sc_shuzi.isEmpty()){
								Character pop = sc_shuzi.pop();
								list.add(pop+"0000");
							}
							sc_zhong_wen.push(charArray[i]);
							break;
						default:
							if(charArray[i] == '1' 
							|| charArray[i] == '2'
							|| charArray[i] == '3'
							|| charArray[i] == '4'
							|| charArray[i] == '5'
							|| charArray[i] == '6'
							|| charArray[i] == '7'
							|| charArray[i] == '8'
							|| charArray[i] == '9'
							|| charArray[i] == '.'
									){
								if(!sc_fh.isEmpty()){
									if(!sc_shuzi.isEmpty()){
										Character pop = sc_fh.pop();
										Double d = 0d;
										for (int j = 0; j < list.size(); j++) {
											String string2 = list.get(j);
											d += Double.valueOf(string2);
										}
										if(list.size() != 0)
											sb_Z.append(d+""+pop+charArray[i]);
										else if(!sc_shuzi.isEmpty())
											sb_Z.append(sc_shuzi.pop()+""+pop+charArray[i]);
										else 
											sb_Z.append(pop+""+charArray[i]);
										sc_shuzi.clear();
										list.clear();
									}else {
										Character pop = sc_fh.pop();
										Double d = 0d;
										for (int j = 0; j < list.size(); j++) {
											String string2 = list.get(j);
											d += Double.valueOf(string2);
										}
										if(list.size() != 0)
											sb_Z.append(d+""+pop);
										else 
											sb_Z.append(pop);
										sc_shuzi.push(charArray[i]);
										list.clear();
									}
									sc_zhong_wen.clear();
								}else{
									sc_shuzi.push(charArray[i]);
								}
							}else {
								System.err.println("不能识别========="+charArray[i]);
								return ;
							}
							break;
						}
						
					}
					Stack_sc.clear();
					if(!sc_shuzi.isEmpty())sb_Z.append(sc_shuzi.pop());
					System.out.println("计算的算式："+sb_Z.toString());
					jiSuan(sb_Z);
				}else if("cn".equals(inLangString)){
					for (int j = 0; j < c.length; j++) {
						sb.toString().contains(""+c[j]);
						if(sb.toString().contains(""+c[j])){
							System.err.println("cn   不能存在字符："+c[j] +"  可输入set_in_ch   再试试");
							sb.delete(0, sb.length());
							System.out.println("输入算式   = 号 结束开始计算：\n");//(1-3)* (1-5)
							continue w1;
						}
					}
					System.out.println("计算的算式："+sb.toString());
					jiSuan(sb);
				}
				sb.delete(0, sb.length());
				System.out.println("输入算式   = 号 结束开始计算：\n");//(1-3)* (1-5)
			}
		}
		sc.close();
		System.out.println("已经退出");
	}
	public void jiSuan(StringBuilder sb) {
		final StringBuffer sbkey = new StringBuffer();
		String data = sb.toString();
		data = data.trim().replace(" ", "");
		if(data == null || data.equals("")){
			System.err.println("这个不能计算："+data);
		}else {
			if(data.contains("(") || data.contains("{")){
			char[] c = data.toCharArray();
			for (int i = 0; i < c.length; i++) {//(1-3)* (1-5)
				switch (c[i]) {
				case '(':
					 Stack_sc.push(c[i]);
					 int xiao = xiao(sbkey,c, i);//(2+5-1)*6/2=
					 if(c.length > xiao+1){
						 if(c[xiao+1] == '+' || c[xiao+1] == '-' || c[xiao+1] == '*' || c[xiao+1] == '/'){
							 sbkey.append(c[xiao+1]);
						 }
					 }
					 i = xiao + 1;
					break;
				case ')':
					break;
				case '{':
					
					break;
				case '}':
					if (!Stack_sc.isEmpty() && Stack_sc.peek()=='{') {
						 
					 }else {
						 System.err.println("没有匹配的："+c[i]);
						 return ;
					 }
					break;
				case '+':
					sbkey.append(c[i]);
					break;
				case '-':
					sbkey.append(c[i]);
					break;
				case '*':
					sbkey.append(c[i]);
					break;
				case '/':
					sbkey.append(c[i]);
					break;
				default:
					if(c[i] == '1' 
					|| c[i] == '2'
					|| c[i] == '3'
					|| c[i] == '4'
					|| c[i] == '5'
					|| c[i] == '6'
					|| c[i] == '7'
					|| c[i] == '8'
					|| c[i] == '9'
					|| c[i] == '0'
					){
						sbkey.append(c[i]);
					}else {
						System.err.println("不能识别========="+c[i]);
						return;
					}
					
					break;
				}
			}
			String string = sbkey.toString();
			if(string.contains("_") || string.contains("+") || string.contains("-") || string.contains("*") || string.contains("/"))
			{
				string = jiAP(string);
				while (string.contains("_")) {
					string = string.replace("_", "-");
					string = jiAP(string);
				}
			}
			if (outLangString.equals("ch")) {
				String jieguo = "";
					for (int j = 0; j < string.length(); j++) {
						char charAt = string.charAt(j);
						for (int k = 0; k < cNumber.length; k++) {
							if(cNumber[k] == charAt){
								jieguo += c[k];
								break;
							}
						}
					}
				System.out.println("结果："+"="+jieguo);
			}else {
				System.out.println("结果："+"="+string);
			}
			
//			System.out.println("结果："+data+"="+string);
		}else {
			String jiAP = jiAP(data);
//			System.out.println("结果："+"="+jiAP);
//			System.out.println("结果："+data+"="+jiAP);
			if (outLangString.equals("ch")) {
//				String jieguo = "";
//					for (int j = 0; j < jiAP.length(); j++) {
//						char charAt = jiAP.charAt(j);
//						for (int k = 0; k < cNumber.length; k++) {
//							if(cNumber[k] == charAt){
//								jieguo += c[k];
//								break;
//							}
//						}
//					}
				System.out.println("结果："+"="+jiAP);
//				System.out.println("结果："+"="+jieguo);
			}else {
				System.out.println("结果："+"="+jiAP);
			}
		}
			sb.delete(0, sb.length());
		}
	}
	
	public int xiao(final StringBuffer sbkey,char[] c,int imm) {//(1-3)* (1-5)=
		final StringBuffer qianKey = new StringBuffer();
		for (int j = ++imm; j < c.length; j++) {
			char charAt = ' ';
			if(qianKey.length() > 0)
				charAt = qianKey.charAt(qianKey.length()-1);
			switch (c[j]) {
			case '(':
				 Stack_sc.push(c[j]);
				 int xiao = xiao(qianKey,c, j);//(2+5-1)*6/2=
				 if(c.length > xiao+1){
					 j = xiao + 1;
					 if(c[xiao+1] == '+' || c[xiao+1] == '-' || c[xiao+1] == '*' || c[xiao+1] == '/'){
						 qianKey.append(c[xiao+1]);
					 }else if(c[xiao+1] == ')'){
						 j = xiao - 1;
					}
				 }
				break;
			case ')':
				if (!Stack_sc.isEmpty() && Stack_sc.peek()=='(') {
					  Stack_sc.pop();//(2+2-5*5+9*1+3/1)
					  String string = qianKey.toString();
					  string = jiAP(string);
					  sbkey.append(string);
					  return j;
				 }else {
					 System.err.println("没有匹配的："+c[j]);
					 return c.length;
				 }
			case '{':
				 Stack_sc.push(c[j]);
				  int da = da(sbkey, c, j);
					 if(c.length > da+1){
						 if(c[da+1] == '+' || c[da+1] == '-' || c[da+1] == '*' || c[da+1] == '/'){
							 sbkey.append(c[da+1]);
						 }
					 }
					 j = da + 1;
				break;
			case '+':
				if(charAt == '+' || charAt == '*' || charAt == '/'){
					System.err.println("格式不正确："+c[j]);
					return c.length;
				}
				qianKey.append(c[j]);
				break;
			case '-':
				qianKey.append(c[j]);
				break;
			case '*':
				if(charAt == '+' || charAt == '*' || charAt == '/'){
					System.err.println("格式不正确："+c[j]);
					return c.length;
				}
				qianKey.append(c[j]);
				break;
			case '/':
				if(charAt == '+' || charAt == '*' || charAt == '/'){
					System.err.println("格式不正确："+c[j]);
					return c.length;
				}
				qianKey.append(c[j]);
				break;
			default:
				if(c[j] == '1' 
				|| c[j] == '2'
				|| c[j] == '3'
				|| c[j] == '4'
				|| c[j] == '5'
				|| c[j] == '6'
				|| c[j] == '7'
				|| c[j] == '8'
				|| c[j] == '9'
				|| c[j] == '0'
				|| c[j] == '.'
				){
					qianKey.append(c[j]);
				}else {
					System.err.println("不能识别========="+c[j]);
					return c.length;
				}
				break;
			}
		}
		if(!Stack_sc.isEmpty()) System.err.println("格式错误:"+Stack_sc.peek()+"  没有结尾");
		return c.length;
	}
	private String jiAP(String string) {
		boolean isend = false;
		string = string.replace("_", "-");
		long mumber = 0 ;
		while(string.contains("+") || string.contains("-") || string.contains("*") || string.contains("/") && mumber != string.length()){
				mumber = string.length();
			  int indexOf_1 = string.indexOf("+");
			  int indexOf_2 = string.indexOf("-");
			  int indexOf_3 = string.indexOf("*");
			  int indexOf_4 = string.indexOf("/");
			  int start = 0;
			  int end = 0;
			  isend = false;
			  String start_number = "";
			  String end_number = "";
			  if(indexOf_3 >= 0 || indexOf_4 >= 0){
				  if(indexOf_3 < indexOf_4 && indexOf_3 > 0){
					  for (int k = indexOf_3+1; k < indexOf_4; k++) {
						if(string.charAt(k) == '+' || string.charAt(k) == '-'
								 || string.charAt(k) == '*'
								 || string.charAt(k) == '/'){
							end = k;
							if(end_number.equals("") && string.charAt(k) == '-' && k + 1 < string.length() && string.charAt(k + 1) == '-'){
								end_number += string.charAt(k + 1)+"";
								k ++;
								continue;
							}
							isend = true;
							  break;
						}else {
							end_number += string.charAt(k)+"";
								 end = k;
						}
					}
					  for (int k = indexOf_3-1; k >= 0; k--) {
						  if(string.charAt(k) == '+' || string.charAt(k) == '-'
									 || string.charAt(k) == '*'
									 || string.charAt(k) == '/')
						  {
							  start = k;
							  if(string.charAt(k) == '-'){
									if(k == 0){
										k--;
										start_number = "-"+start_number;
										continue;
									}
								}
							  start = k+1;
							  break;
						  }else {
							  
							  start_number = string.charAt(k) + start_number;
							  start = k;
						  }
					  }
					  Double double1 = Double.valueOf(start_number) * Double.valueOf(end_number);
					  string = string.replace(string.substring(start,  isend?end:end+1), ""+double1);
				  }else if (indexOf_4 < indexOf_3 && indexOf_4 > 0) {
					  
					  for (int k = indexOf_4+1; k < indexOf_3; k++) {
							if(string.charAt(k) == '+' || string.charAt(k) == '-'
									 || string.charAt(k) == '*'
									 || string.charAt(k) == '/'){
								end = k;
								if(end_number.equals("") && string.charAt(k) == '-' && k + 1 < string.length() && string.charAt(k + 1) == '-'){
									end_number += string.charAt(k + 1)+"";
									k ++;
									continue;
								}
								isend = true;
								  break;
							}else {
								end_number += string.charAt(k)+"";
									 end = k;
							}
						}
						  for (int k = indexOf_4-1; k >= 0; k--) {
							  if(string.charAt(k) == '+' || string.charAt(k) == '-'
										 || string.charAt(k) == '*'
										 || string.charAt(k) == '/')
							  {
								  start = k;
								  if(string.charAt(k) == '-'){
										if(k == 0){
											k--;
											start_number = "-"+start_number;
											continue;
										}
									}
								  start = k+1;
								  break;
							  }else {
								  start_number = string.charAt(k) + start_number;
								  start = k;
							  }
						  }
						  Double double1 = Double.valueOf(start_number) / Double.valueOf(end_number);
						  string = string.replace(string.substring(start,  isend?end:end+1), ""+double1);
				}
				  else if (indexOf_4 < 0) {
					  for (int k = indexOf_3+1; k < string.length(); k++) {
							if(string.charAt(k) == '+' || string.charAt(k) == '-'
									 || string.charAt(k) == '*'
									 || string.charAt(k) == '/'){
								end = k;
								if(end_number.equals("") && string.charAt(k) == '-' && k + 1 < string.length() && string.charAt(k + 1) != '-'){
									end_number += "-"+string.charAt(k + 1)+"";
									k ++;
									continue;
								}
								isend = true;
								  break;
							}else {
								end_number += string.charAt(k)+"";
									 end = k;
							}
						}
						  for (int k = indexOf_3-1; k >= 0; k--) {
							  if(string.charAt(k) == '+' || string.charAt(k) == '-'
										 || string.charAt(k) == '*'
										 || string.charAt(k) == '/'){
								  start = k;
								  if(string.charAt(k) == '-'){
										if(k == 0){
											k--;
											start_number = "-"+start_number;
											continue;
										}
									}
								  start = k+1;
								  break;
							  }else {
								  start_number = string.charAt(k) + start_number;
								  start = k;
							  }
						  }
						  Double double1 = Double.valueOf(start_number) * Double.valueOf(end_number);
						  String substring = string.substring(start,  isend?end:end+1);
						  string = string.replace(substring, ""+double1);
				}else if (indexOf_3 < 0) {
					 for (int k = indexOf_4+1; k < string.length(); k++) {
							if(string.charAt(k) == '+' || string.charAt(k) == '-'
									 || string.charAt(k) == '*'
									 || string.charAt(k) == '/'){
								end = k;
								if(end_number.equals("") && string.charAt(k) == '-' && k + 1 < string.length() && string.charAt(k + 1) != '-'){
									end_number += "-"+string.charAt(k + 1)+"";
									k ++;
									continue;
								}
								isend = true;
								  break;
							}else {
								end_number += string.charAt(k)+"";
								end = k;
							}
						}
						  for (int k = indexOf_4-1; k >= 0; k--) {
							  if(string.charAt(k) == '+' || string.charAt(k) == '-'
										 || string.charAt(k) == '*'
										 || string.charAt(k) == '/'){
								  start = k;
								  if(string.charAt(k) == '-'){
										if(k == 0){
											k--;
											start_number = "-"+start_number;
											continue;
										}
									}
								  start = k+1;
								  break;
							  }else {
								  start_number = string.charAt(k) + start_number;
								  start = k;
							  }
						  }
						  Double double1 = Double.valueOf(start_number) / Double.valueOf(end_number);
						  String substring = string.substring(start,  isend?end:end+1);
						  string = string.replace(substring, ""+double1);
				}
			  }else if (indexOf_1 > 0 || indexOf_2 > 0) {//+-
				  if(indexOf_1 > 0 && (indexOf_1 < indexOf_2 || indexOf_2 < 0)){//+
					  for (int k = indexOf_1+1; k < string.length(); k++) {
						  if(string.charAt(k) == '+' || string.charAt(k) == '-'
								  || string.charAt(k) == '*'
								  || string.charAt(k) == '/'){
							  end = k;
							  if(end_number.equals("") && string.charAt(k) == '-' && k + 1 < string.length() && string.charAt(k + 1) != '-'){
									end_number += "-"+string.charAt(k + 1)+"";
									k ++;
									continue;
								}
							  isend = true;
							  break;
						  }else {
							  end_number += string.charAt(k)+"";
							  end = k;
						  }
					  }
					  for (int k = indexOf_1-1; k >= 0; k--) {
						  if(string.charAt(k) == '+' || string.charAt(k) == '-'
									 || string.charAt(k) == '*'
									 || string.charAt(k) == '/'){
							  start = k;
							  if(string.charAt(k) == '-'){
									if(k == 0){
										k--;
										start_number = "-"+start_number;
										continue;
									}
								}
							  start = k+1;
							  break;
						  }else {
							  start_number = string.charAt(k) + start_number;
							  start = k;
						  }
					  }
					  Double double1 = Double.valueOf(start_number) + Double.valueOf(end_number);
					  String substring = string.substring(start,  isend?end:end+1);
					  string = string.replace(substring, ""+double1);
				  }else if ((indexOf_2 > 0 && indexOf_1 > indexOf_2 )||(indexOf_1 < 0 && indexOf_2 > 0)) {//-
					  for (int k = indexOf_2+1; k < string.length(); k++) {
						  if(string.charAt(k) == '+' || string.charAt(k) == '-'
								  || string.charAt(k) == '*'
								  || string.charAt(k) == '/'){
							  end = k;
							  if(string.charAt(k) == '-' && k + 1 < string.length() && string.charAt(k + 1) != '-'){
								  end_number += "-"+string.charAt(k + 1)+"";
									k ++;
									continue;
								}
								isend = true;
							  break;
						  }else {
							  end_number += string.charAt(k)+"";
							  end = k;
						  }
					  }
					  for (int k = indexOf_2-1; k >= 0; k--) {
						  if(string.charAt(k) == '+' || string.charAt(k) == '-'
									 || string.charAt(k) == '*'
									 || string.charAt(k) == '/'){
							  start = k;
							  if(string.charAt(k) == '-'){
									if(k == 0){
										k--;
										start_number = "-"+start_number;
										continue;
									}
								}
							  start = k+1;
							  break;
						  }else {
							  start_number = string.charAt(k) + start_number;
							  start = k;
						  }
					  }
					  Double double1 = Double.valueOf(start_number) - Double.valueOf(end_number);
					  String substring = string.substring(start,  isend?end:end+1);
					  string = string.replace(substring, ""+double1);
					  if(string.startsWith("-")){
						  string = string.replace("-", "_");
					  }
				}
			  }
		  }
		return string;
	}
	
	public int da(final StringBuffer sbkey,char[] c,int ida) {
		final StringBuffer qianKey = new StringBuffer();
		for (int j = ++ida; j < c.length; j++) {
			char charAt = ' ';
			if(qianKey.length() > 0)
				charAt = qianKey.charAt(qianKey.length()-1);
			switch (c[j]) {
			case '(':
				 Stack_sc.push(c[j]);
				  int xiao = xiao(sbkey,c, j);//(2+5-1)*6/2=
					 if(c.length > xiao+1){
						 if(c[xiao+1] == '+' || c[xiao+1] == '-' || c[xiao+1] == '*' || c[xiao+1] == '/'){
							 sbkey.append(c[xiao+1]);
						 }
					 }
					 j = xiao + 1;
				break;
			case '{':
				 Stack_sc.push(c[j]);
				  int da = da(sbkey, c, j);
					 if(c.length > da+1){
						 if(c[da+1] == '+' || c[da+1] == '-' || c[da+1] == '*' || c[da+1] == '/'){
							 sbkey.append(c[da+1]);
						 }
					 }
					 j = da + 1;
				break;
			case '}':
				if (!Stack_sc.isEmpty() && Stack_sc.peek()=='{') {
					  Stack_sc.pop();//(2+2-5*5+9*1+3/1)
					  String string = qianKey.toString();
					  string = jiAP(string);
					  sbkey.append(string);
					  return j;
				 }else {
					 System.err.println("没有匹配的："+c[j]);
					 return c.length;
				 }
			case '+':
				if(charAt == '+' || charAt == '*' || charAt == '/'){
					System.err.println("格式不正确："+c[j]);
					return c.length;
				}
				qianKey.append(c[j]);
				break;
			case '-':
				qianKey.append(c[j]);
				break;
			case '*':
				if(charAt == '+' || charAt == '*' || charAt == '/'){
					System.err.println("格式不正确："+c[j]);
					return c.length;
				}
				qianKey.append(c[j]);
				break;
			case '/':
				if(charAt == '+' || charAt == '*' || charAt == '/'){
					System.err.println("格式不正确："+c[j]);
					return c.length;
				}
				qianKey.append(c[j]);
				break;
			default:
				if(c[j] == '1' 
				|| c[j] == '2'
				|| c[j] == '3'
				|| c[j] == '4'
				|| c[j] == '5'
				|| c[j] == '6'
				|| c[j] == '7'
				|| c[j] == '8'
				|| c[j] == '9'
				|| c[j] == '0'
				){
					qianKey.append(c[j]);
				}else {
					System.err.println("不能识别========="+c[j]);
					return c.length;
				}
				
				break;
			}
		}
		return ida;
	}
}
