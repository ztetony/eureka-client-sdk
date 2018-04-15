package com.tonysfriend.ms.util;

import com.tonysfriend.ms.bean.Result;
import com.tonysfriend.ms.constant.Constants;
import net.sf.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    //	private static final AllowAllHostnameVerifier HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
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

    public static void closeConnection() {
        if (conn != null)
            conn.disconnect();
    }



    /**
     * @return
     */
    public static Result invoke (String urlstr, String method, String contentType, String params, int timeout) throws Exception {
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
            res.setContent(line);
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            connection.disconnect();
        }


        return res;
    }

    public static void downloadImg(String url, String destPath, String fileName) {
        HttpURLConnection http = null;
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            System.setProperty("jsse.enableSNIExtension", "false");

            URL urlGet = new URL(url);
            http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

            http.connect();

            http.getResponseCode();

            input = new BufferedInputStream(http.getInputStream());

            File f = new File(destPath, fileName);

            output = new BufferedOutputStream(new FileOutputStream(f));

            byte[] bs = new byte[1024];
            int len = 0;

            while ((len = input.read(bs)) > 0) {
                output.write(bs, 0, len);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (http != null) {
                http.disconnect();
            }
        }
    }

    public static void main(String []args) throws Exception{
     Result  result =    invoke(Constants.REGISTER_INSTANCE_URL,"POST","application/xml" ,Constants.REGISTER_XML_STRING,10000);
     System.out.println(result);

    }

}