package com.tonysfriend.ms.util;

import com.alibaba.fastjson.JSON;
import com.tonysfriend.ms.bean.Result;
import com.tonysfriend.ms.bean.http.Header;
import com.tonysfriend.ms.bean.http.Param;
import com.tonysfriend.ms.constant.Constants;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

public class HttpClient {

    //	private static final AllowAllHostnameVerifier HOSTNAME_VERIFIER =
    private static X509TrustManager xtm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    private static X509TrustManager[] xtmArray = new X509TrustManager[]{xtm};

    private static HttpsURLConnection conn = null;

    public static InputStream sendPOSTRequestForInputStream(String path, Map<String, String> params, String encoding) throws Exception {
        // 1> 组拼实体数据
        // method=save&name=liming&timelength=100
        StringBuilder entityBuilder = new StringBuilder("");
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                entityBuilder.append(entry.getKey()).append('=');
                entityBuilder.append(URLEncoder.encode(entry.getValue(), encoding));
                entityBuilder.append('&');
            }
            entityBuilder.deleteCharAt(entityBuilder.length() - 1);
        }

        byte[] entity = entityBuilder.toString().getBytes();
        URL url = new URL(path);
        conn = (HttpsURLConnection) url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            // Trust all certificates
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(new KeyManager[0], xtmArray, new SecureRandom());
            SSLSocketFactory socketFactory = context.getSocketFactory();
            ((HttpsURLConnection) conn).setSSLSocketFactory(socketFactory);
//			((HttpsURLConnection) conn).setHostnameVerifier(HOSTNAME_VERIFIER);
        }

        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);// 允许输出数据
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(entity);
        outStream.flush();
        outStream.close();

        if (conn.getResponseCode() == 200) {
            return conn.getInputStream();
        }

        return conn.getInputStream();
    }

    /**
     *
     */
    public static void closeConnection() {
        if (conn != null)
            conn.disconnect();
    }

    /**
     * @param urlstr
     * @param method
     * @param contentType
     * @param params
     * @param timeout
     * @return
     * @throws Exception
     */
    public static Result invoke(String urlstr,
                                String method,
                                String contentType,
                                String params,
                                int timeout) throws Exception {
        Result res = new Result();
        String result = "";
        HttpURLConnection connection = null;

        try {
            InputStream is = null;
            OutputStream os = null;
            URL url = new URL(urlstr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(timeout > 0 ? timeout : 10 * 1000);
            connection.setReadTimeout(timeout > 0 ? timeout : 10 * 1000);

            connection.setRequestMethod(method);
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Type", contentType);
            connection.setDoOutput(true);

            connection.connect();
            os = connection.getOutputStream();
            if (params != null) {
                os.write(params.getBytes());
                os.flush();
                os.close();
            }
            //
            is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
//			line = new String(line.toString().getBytes("iso8859-1"), "utf-8");   
            while (line != null) {
                result = result + line;
                line = br.readLine();
            }

            int code = connection.getResponseCode();
            res.setCode(code);

        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
            res.setCode(Constants.CODE_SERVER_ERROR);

            throw new Exception(e);
        } finally {
            connection.disconnect();
            res.setData(result);
        }

        return res;
    }

    /**
     * @param urlstr
     * @param method
     * @param contentType
     * @param headers
     * @param params
     * @param timeout
     * @return
     * @throws Exception
     */
    public static Result invoke(String urlstr,
                                String method,
                                String contentType,
                                Header headers,
                                Param<String, String> params,
                                int timeout) throws Exception {

        Result res = new Result();
        String requestContent = "";
        String result = "";
        if ("application/json".equals(contentType)) {

            requestContent = JSON.toJSONString(params);

        } else if ("application/x-www-form-urlencoded".equals(contentType)) {

            StringBuilder sb = new StringBuilder();
            if (params != null && params.size() > 0) {
                for (String p : params.keySet()) {
                    sb.append(p + "=" + params.get(p) + "&");
                }
            }

            requestContent = sb.toString();

        } else {
            StringBuilder sb = new StringBuilder();
            if (params != null && params.size() > 0) {
                for (String p : params.keySet()) {
                    sb.append(p + "=" + params.get(p) + "&");
                }
            }

            requestContent = sb.toString();
        }

        HttpURLConnection connection = null;

        try {
            InputStream is = null;
            OutputStream os = null;
            URL url = new URL(urlstr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(timeout > 0 ? timeout : 10 * 1000);
            connection.setReadTimeout(timeout > 0 ? timeout : 10 * 1000);

            connection.setRequestMethod(method);
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (headers != null && headers.size() > 0) {
                for (String k : headers.keySet()) {
                    connection.setRequestProperty(k, headers.get(k));
                }
            }

            connection.setRequestProperty("Content-Type", contentType);

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            os = connection.getOutputStream();
            if (StringUtil.isNotEmpty("requestContent")) {
                os.write(requestContent.getBytes());
                os.flush();
                os.close();
            }

            //
            is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
//			line = new String(line.toString().getBytes("iso8859-1"), "utf-8");
            while (line != null) {
                result = result + line;
                line = br.readLine();
            }
            int code = connection.getResponseCode();
            res.setCode(code);

        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
            res.setCode(Constants.CODE_SERVER_ERROR);

            throw new Exception(e);
        } finally {
            connection.disconnect();
            res.setData(result);
        }

        return res;

    }

    /**
     * @param urlstr
     * @param method
     * @param timeout
     * @return
     * @throws Exception
     */
    public static Result doGet(String urlstr, String method, int timeout) throws Exception {
        Result res = new Result();
        String result = "";
        HttpURLConnection connection = null;
        try {
            InputStream is = null;
            OutputStream os = null;
            URL url = new URL(urlstr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(timeout > 0 ? timeout : 10 * 1000);
            connection.setReadTimeout(timeout > 0 ? timeout : 10 * 1000);
            connection.setRequestMethod(method);
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Mobile Safari/537.36");
            connection.setDoInput(true);
            connection.connect();
            //
            is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            while (line != null) {
                result = result + line;
                line = br.readLine();
            }
            int code = connection.getResponseCode();
            res.setCode(code);

        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
            res.setCode(Constants.CODE_SERVER_ERROR);

            throw new Exception(e);
        } finally {
            connection.disconnect();
            res.setData(result);
        }

        return res;
    }

    public static void main(String[] args) throws Exception {
        String url = "http://10.45.32.93:1111/eureka/apps/ZIOT.DMS.1.0.0.DAILY";
        ;
        Result result = doGet(url, "GET", 100 * 000);

        System.out.println(result.getCode());
        System.out.println(result.getData());

    }

}