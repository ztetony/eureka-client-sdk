//package com.tonysfriend.ms.util;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.commons.io.IOUtils;
//
///**
// * @author tony.lu
// */
//public class HttpClientUtil {
//
//	public static JSONObject invokeArray(String url, String requestMethod,
//			JSONArray json) {
//		HttpURLConnection http = null;
//		try {
//			System.setProperty("jsse.enableSNIExtension", "false");
//
//			URL urlGet = new URL(url);
//			http = (HttpURLConnection) urlGet.openConnection();
//			http.setRequestMethod(requestMethod); // 必须是get方式请求
//			http.setRequestProperty("Content-Type",
//					"application/x-www-form-urlencoded");
//			http.setDoOutput(true);
//			http.setDoInput(true);
//			http.setConnectTimeout(2000);
//			System.setProperty("sun.net.client.defaultConnectTimeout", "5000");// 连接超时30秒
//			System.setProperty("sun.net.client.defaultReadTimeout", "5000"); // 读取超时30秒
//
//			// post信息
//			if (json != null) {
//				OutputStream os = http.getOutputStream();
//
//				os.write(json.toString().getBytes());
//				os.close();
//			}
//
//			http.connect();
//			InputStream is = http.getInputStream();
//			String message = IOUtils.toString(is);
//			if (StringUtil.isNotEmpty(message)) {
//				return JSONObject.fromObject(message);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		} finally {
//			if (http != null) {
//				http.disconnect();
//			}
//		}
//		return null;
//	}
//
//	public static JSONObject invoke(String url, String requestMethod,
//			JSONObject json) {
//		HttpURLConnection http = null;
//		try {
//			System.setProperty("jsse.enableSNIExtension", "false");
//
//			URL urlGet = new URL(url);
//			http = (HttpURLConnection) urlGet.openConnection();
//			http.setRequestMethod(requestMethod); // 必须是get方式请求
//			http.setRequestProperty("Content-Type",
//					"application/x-www-form-urlencoded");
//			http.setDoOutput(true);
//			http.setDoInput(true);
//			http.setConnectTimeout(2000);
//			System.setProperty("sun.net.client.defaultConnectTimeout", "5000");// 连接超时30秒
//			System.setProperty("sun.net.client.defaultReadTimeout", "5000"); // 读取超时30秒
//
//			// post信息
//			if (json != null) {
//				OutputStream os = http.getOutputStream();
//
//				os.write(json.toString().getBytes());
//				os.close();
//			}
//
//			http.connect();
//			InputStream is = http.getInputStream();
//			String message = IOUtils.toString(is);
//			if (StringUtil.isNotEmpty(message)) {
//				return JSONObject.fromObject(message);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		} finally {
//			if (http != null) {
//				http.disconnect();
//			}
//		}
//		return null;
//	}
//
//	public static void downloadImg(String url, String destPath, String fileName) {
//		HttpURLConnection http = null;
//		BufferedInputStream input = null;
//		BufferedOutputStream output = null;
//		try {
//			System.setProperty("jsse.enableSNIExtension", "false");
//
//			URL urlGet = new URL(url);
//			http = (HttpURLConnection) urlGet.openConnection();
//			http.setRequestMethod("GET"); // 必须是get方式请求
//			http.setRequestProperty("Content-Type",
//					"application/x-www-form-urlencoded");
//			http.setDoOutput(true);
//			http.setDoInput(true);
//			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
//			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
//
//			http.connect();
//
//			http.getResponseCode();
//
//			input = new BufferedInputStream(http.getInputStream());
//
//			File f = new File(destPath, fileName);
//
//			output = new BufferedOutputStream(new FileOutputStream(f));
//
//			byte[] bs = new byte[1024];
//			int len = 0;
//
//			while ((len = input.read(bs)) > 0) {
//				output.write(bs, 0, len);
//			}
//			output.flush();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (output != null) {
//				try {
//					output.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (http != null) {
//				http.disconnect();
//			}
//		}
//	}
//
//	public static void main(String[] args) {
//		String url = "http://125.46.106.32/wsmp/interface";
//		String json = "{\"head\":{\"action\":\"qryonlineinfo\",\"vnoCode\":\"\"},\"body\":{\"opervnocode\":\"ROOT_VNO\",\"apmac\":\"BC:30:7E:1C:A2:6D\",\"begindate\":\"2015-07-28 20:12:12\",\"enddate\":\"2015-07-31 16:12:12\"}}";
//		JSONObject dJsonObject = JSONObject.fromObject(json);
//		JSONObject res = HttpClientUtil.invoke(url, "GET", dJsonObject);
//		System.out.println(res);
//	}
//
//}