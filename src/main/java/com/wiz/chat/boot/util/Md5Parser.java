
package com.wiz.chat.boot.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Parser {

	public static String parseStrToMd5L32(String str) {
		String reStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(str.getBytes("UTF-8"));
			StringBuffer stringBuffer = new StringBuffer();
			for (byte b : bytes) {
				int bt = b&0xff;
				if (bt < 16) {
					stringBuffer.append(0);
				}
				stringBuffer.append(Integer.toHexString(bt));
			}
			reStr = stringBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return reStr;
	}

	public static String parseStrToMd5U32(String str) {
		String reStr = parseStrToMd5L32(str);
		if (reStr != null) {
			reStr = reStr.toUpperCase();
		}
		return reStr;
	}

	public static String parseStrToMd5U16(String str) {
		String reStr = parseStrToMd5L32(str);
		if (reStr != null) {
			reStr = reStr.toUpperCase().substring(8, 24);
		}
		return reStr;
	}

	public static String parseStrToMd5L16(String str) {
		String reStr = parseStrToMd5L32(str);
		if (reStr != null) {
			reStr = reStr.substring(8, 24);
		}
		return reStr;
	}
}