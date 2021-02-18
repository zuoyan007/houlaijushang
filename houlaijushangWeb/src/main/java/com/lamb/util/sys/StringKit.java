package com.lamb.util.sys;

import com.jeesite.common.lang.StringUtils;

import java.util.Arrays;
import java.util.Random;

public class StringKit extends StringUtils {

	public static final String CHARSET_NAME = "UTF-8";
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(random(0,1));
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(Arrays.asList(random(1, 5, 0, 4)).toString());
		}
		System.out.println("-----------------------------------");
		for (int i = 0; i < 10; i++) {
			System.out.println(Arrays.asList(random(2, 5, 0, 4)).toString());
		}
	}
	
	/**
	 * 
	 * @param type 1 每次取不一样。2 可取一样
	 * @param min随机数最小值，包含
	 * @param max随机数最大值，包含
	 * @return
	 * @author gexu
	 * @date 2019年10月25日 下午4:00:04
	 */
	public static Integer[] random(int type,int length,int min,int max) {
		Integer[] dom = new Integer[length];
		if(type==1&&(length>(max-min+1))) {
			return new Integer[] {};
		}
		int index = 0;
		do {
			Integer num = random(min,max);
			if(type==1) {
				if(Arrays.asList(dom).contains(num)) {
					continue;
				}
			}
			dom[index++] = num;
		} while (index<length);
		return dom;
	}
	
	public static int random(int min,int max){
		Random random = new Random();
        return random.nextInt(max-min+1) + min;
	}
	
	/**
	 * 获取数组的真实长度，忽略空值
	 * @param str
	 * @return
	 * @author gexu
	 * @date 2019年10月25日 下午3:55:01
	 */
	public static int getRealLength(String[] str) {
		int count = 0;
		for (String string : str) {
			if(!StringKit.isBlank(string)) {
				count++;
			}
		}
		return count;
	}
	
	public static boolean isAllBlank(String[] ...obj) {
		if(obj==null) {
			return true;
		}
		for (String[] strings : obj) {
			if(strings!=null&&strings.length>0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 比较是否为最新版本 
	 * @param curVersion 当前版本号 
	 * @param newVersion 最新版本号
	 * @return
	 * @author gexu
	 * @date 2019-10-21
	 */
	public static boolean isNewVersion(String curVersion, String newVersion) {
		if(StringKit.isAnyBlank(curVersion,newVersion)) {
			return false;
		}
		if (curVersion.equals(newVersion)) {
			return true;
		}
		String[] curVersions = curVersion.split("\\.");
		String[] newVersions = newVersion.split("\\.");
		//取到短版本号的长度
		int length = newVersions.length <= curVersions.length ? curVersions.length : newVersions.length;
		StringBuilder curStr = new StringBuilder();
		for (String string : curVersions) {
			curStr.append(Integer.valueOf(string)+1000);
		}
		StringBuilder newStr = new StringBuilder();
		for (String string : newVersions) {
			newStr.append(Integer.valueOf(string)+1000);
		}
		for (int i = 0; i < length-curVersions.length; i++) {
			curStr.append(1000);
		}
		for (int i = 0; i < length-newVersions.length; i++) {
			newStr.append(1000);
		}
		long curLong = Long.valueOf(curStr.toString());
		long newLong = Long.valueOf(newStr.toString());
		return curLong>=newLong;
	}
	
	public static String encryptName(String name){
		if(StringKit.isBlank(name)){
			return name;
		}
		return encryptPart(name,"NAME");
	}
	

	public static String encryptEntName(String name){
		if(StringKit.isBlank(name)){
			return name;
		}
		if(name.length()<2) {
			return name = "***"+name.substring(name.length()-1);
		}
		StringBuilder encryptStr = new StringBuilder();
		for(int i=0;i<10;i++){
			encryptStr.append("*");
		}
		return name = encryptStr.toString()+name.substring(name.length()-2);
	}
	
	/**en_enterprise_list
	 * 半加密字符串（只加密部分）
	 * 13812341234 138****1234
	 * 
	 * 612324198001011234 61****************34
	 * 
	 * @param pwd_len
	 * @return
	 * @throws Exception 
	 */
	public static String encryptPart(String str,String type){
		if(StringKit.isBlank(str)){
			return str;
		}
		int len = str.length();
		StringBuilder encryptStr = new StringBuilder();
		if("IDCARD".equals(type)){
			for(int i=0;i<len-2-2;i++){
				encryptStr.append("*");
			}
			return str = str.substring(0, 2)+encryptStr.toString()+str.substring(len-2);
		}else if("NAME".equals(type)){
			if(len<2){
				return str;
			}else if(len==2){
				return str = "*"+str.substring(len-1);
			}else if(len==3){
				return str = "**"+str.substring(len-1);
			}else{
				for(int i=0;i<len-2;i++){
					encryptStr.append("*");
				}
				return str = str.substring(0, 2)+encryptStr.toString()+str.substring(len-1);
			}
		}else if("PHONE".equals(type)){
			for(int i=0;i<len-7;i++){
				encryptStr.append("*");
			}
			return str = str.substring(0, 3)+encryptStr.toString()+str.substring(len-4);
		}else if("BANK_CARD".equals(type)){
			for(int i=0;i<len-4;i++){
				encryptStr.append("*");
			}
			return str = encryptStr.toString()+str.substring(len-4);
		}else{
			if(len<2){
				return str;
			}else if(len==2){
				return str = "*"+str.substring(len-1);
			}else if(len==3){
				return str = "**"+str.substring(len-1);
			}else{
				for(int i=0;i<len-2;i++){
					encryptStr.append("*");
				}
				return str = str.substring(0, 2)+encryptStr.toString()+str.substring(len-1);
			}
		}
	}
	
	public static boolean isNull(Object cs) {
		if(cs==null) {
			return true;
		}
		String str = cs.toString();
		if(StringKit.isBlank(str)) {
			return true;
		}
		return "null".equals(str)||"NULL".equals(str);
	}

	/**
	 * @auther gexu
	 * @return
	 */
	public static String invalidIDToEmpty(String id){
		return isValidID(id)?id: StringKit.EMPTY;
	}


	/**
	 * 校验ID是否有效值的方法
	 * @auther gexu
	 * @return
	 */
	public static boolean isValidID(String id){
		if(StringKit.isBlank(id)){
			return false;
		}
		if("-1".equals(id)){
			return false;
		}
		if("0".equals(id)){
			return false;
		}
		return true;
	}


	/**
	 * 将path转换为url
	 * @return
	 */
	public static String pathToUrl(String path){
		if(StringKit.isBlank(path)){
			return StringKit.EMPTY;
		}
		String url = path.replaceAll("\\\\", "/");
		url = url.replaceAll("\\\\", "/");
		url = url.replaceAll("\\\\", "/");
		url = url.replaceAll("\\\\", "/");
		url = url.replaceAll("\\\\", "/");
		url = url.replaceAll("\\\\", "/");

		url = url.replaceAll("////", "/");
		url = url.replaceAll("///", "/");
		url = url.replaceAll("//", "/");
		return url;
	}
	/**
	 * 将url转换为path
	 * @return
	 */
	public static String urlToPath(String url){
		return urlToPath(null,url);
	}


	/**
	 * 将url转换为path
	 * @return
	 */
	public static String urlToPath(String baseUrl,String url){
		if(StringKit.isBlank(url)){
			return StringKit.EMPTY;
		}
		String path = url;
		if(StringKit.isNotBlank(baseUrl)){
			int index = path.indexOf(baseUrl);
			if(index!=-1){
				path = path.substring(index+baseUrl.length());
			}
		}

		path = path.replaceAll("//////", "/");
		path = path.replaceAll("/////", "/");
		path = path.replaceAll("////", "/");
		path = path.replaceAll("///", "/");
		path = path.replaceAll("//", "/");
		path = path.replaceAll("//", "/");
		path = path.replaceAll("//", "/");

		path = path.replaceAll("/", "\\\\");

		return path;
	}


	public static String purgeIDS(String ids){
		if(StringKit.isBlank(ids)){
			return StringKit.EMPTY;
		}
		if(ids.startsWith(",")){
			ids  = ids.substring(0,1);
		}
		if(ids.endsWith(",")){
			ids  = ids.substring(0,ids.length()-1);
		}
		ids = ids.replaceAll(",,",",");
		ids = ids.replaceAll(",,",",");
		ids = ids.replaceAll(",,",",");
		return ids;
	}


	/**
	 * 截取字符串str中指定字符 strStart、strEnd之间的字符串
	 *
	 * @param string
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String subString(String str, String strStart, String strEnd) {

		/* 找出指定的2个字符在 该字符串里面的 位置 */
		int strStartIndex = str.indexOf(strStart);
		int strEndIndex = str.indexOf(strEnd);

		/* index 为负数 即表示该字符串中 没有该字符 */
		if (strStartIndex < 0) {
			return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
		}
		if (strEndIndex < 0) {
			return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
		}
		/* 开始截取 */
		String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
		return result;
	}
}
