package cn.mimukeji.util;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class HttpUtil {
  
  private static final Logger logger = Logger.getLogger(HttpUtil.class);
  
  private static String IP = "";
  
  /**
   * 发送一个httpGET请求
   * 
   * @param url
   *        请求的url
   * @return
   * @throws IOException
   */
  public static Object httpGet(String url) throws IOException {
    HttpClient client = new HttpClient();
    HttpMethod method = new GetMethod(url);
    client.executeMethod(method);

    // System.out.println("HttpUtil.httpGet中的statusLine"+method.getStatusLine());
    // System.out.println("HttpUtil.httpGet中的sponseBody"+method.getResponseBodyAsString());
    
    return method.getResponseBodyAsString();
    // return
    // "<?xml version='1.0' encoding='utf-8' ?><soufun_passport> <common> <interfacename>校验cookie</interfacename> <return_result>100</return_result> <error_reason>成功</error_reason> <userid>52512351</userid> <username>哈哈</username> <usertype>1</usertype> <service>jr-txdai-web</service> <role>0</role> <isvalid>1</isvalid> </common> </soufun_passport>";
    
  }

  /**
   * 发送一个httpGET请求
   *
   * @param url
   *        请求的url
   * @return
   * @throws IOException
   */
  public static Object httpGet(String url,int timeout) throws IOException {
    HttpClient client = new HttpClient();
    client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
    client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
    HttpMethod method = new GetMethod(url);
    client.executeMethod(method);

    // System.out.println("HttpUtil.httpGet中的statusLine"+method.getStatusLine());
    // System.out.println("HttpUtil.httpGet中的sponseBody"+method.getResponseBodyAsString());

    return method.getResponseBodyAsString();
    // return
    // "<?xml version='1.0' encoding='utf-8' ?><soufun_passport> <common> <interfacename>校验cookie</interfacename> <return_result>100</return_result> <error_reason>成功</error_reason> <userid>52512351</userid> <username>哈哈</username> <usertype>1</usertype> <service>jr-txdai-web</service> <role>0</role> <isvalid>1</isvalid> </common> </soufun_passport>";

  }

  /**
   * 发送一个httpGET请求
   *
   * @param url
   *        请求的url
   * @return
   * @throws IOException
   */
  public static int httpGetResponseCode(String url,int timeout) throws IOException {
    HttpClient client = new HttpClient();
    client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
    client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
    HttpMethod method = new GetMethod(url);
    client.executeMethod(method);

    return method.getStatusCode();
  }

  /**
   * 发送一个httpGET请求
   *
   * @param url
   *        请求的url
   * @return
   * @throws IOException
   */
  public static int httpGetResponseCode(String url) throws IOException {
    HttpClient client = new HttpClient();
    HttpMethod method = new GetMethod(url);
    client.executeMethod(method);

    return method.getStatusCode();
  }

  /**
   * 发送一个带Cookie的httpGET请求
   * 
   * @param url
   *        请求的url
   * @return
   * @throws IOException
   */
  public static Object httpWithCookieGet(String url,String cookieStr) throws IOException {
    HttpClient client = new HttpClient();
    HttpMethod method = new GetMethod(url);
    method.setRequestHeader("Cookie", cookieStr);
    client.executeMethod(method);
    
    // System.out.println("HttpUtil.httpGet中的statusLine"+method.getStatusLine());
    // System.out.println("HttpUtil.httpGet中的sponseBody"+method.getResponseBodyAsString());
    
    return method.getResponseBodyAsString();
    // return
    // "<?xml version='1.0' encoding='utf-8' ?><soufun_passport> <common> <interfacename>校验cookie</interfacename> <return_result>100</return_result> <error_reason>成功</error_reason> <userid>52512351</userid> <username>哈哈</username> <usertype>1</usertype> <service>jr-txdai-web</service> <role>0</role> <isvalid>1</isvalid> </common> </soufun_passport>";
    
  }
  
  /**
   * 获取本地IP地址
   *
   * @throws SocketException
   */
  public static String getLocalIP() {
    if (StringUtils.isBlank(IP)) {
      if (isWindowsOS()) {
        try {
          IP = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
          // TODO Auto-generated catch block
          
          logger.info(e);
        }
      }
      else {
        IP = getLinuxLocalIp();
      }
      
    }
    
    return StringUtils.isBlank(IP) ? "127.0.0.1" : IP;
    
  }
  
  /**
   * 判断操作系统是否是Windows
   *
   * @return
   */
  private static boolean isWindowsOS() {
    boolean isWindowsOS = false;
    String osName = System.getProperty("os.name");
    if (osName.toLowerCase().indexOf("windows") > -1) {
      isWindowsOS = true;
    }
    return isWindowsOS;
  }
  
  /**
   * 获取本地Host名称
   */
  public static String getLocalHostName() throws UnknownHostException {
    return InetAddress.getLocalHost().getHostName();
  }
  
  /**
   * 获取Linux下的IP地址
   *
   * @return IP地址
   * @throws SocketException
   */
  private static String getLinuxLocalIp() {
    String ip = "";
    try {
      for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
          .hasMoreElements();) {
        NetworkInterface intf = en.nextElement();
        String name = intf.getName();
        if (!name.contains("docker") && !name.contains("lo")) {
          for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
              .hasMoreElements();) {
            InetAddress inetAddress = enumIpAddr.nextElement();
            if (!inetAddress.isLoopbackAddress()) {
              String ipaddress = inetAddress.getHostAddress().toString();
              if (!ipaddress.contains("::") && !ipaddress.contains("0:0:")
                  && !ipaddress.contains("fe80")) {
                ip = ipaddress;
                logger.info("本机ip是 the host ip is " + ip);
              }
            }
          }
        }
      }
    }
    catch (SocketException ex) {
      logger.info("获取本机ip失败, get local ip failed  " + ex.getMessage());
      ip = "127.0.0.1";
    }
    logger.info("IP " + ip);
    return ip;
  }
  
  /**
   * http post
   * 
   * @param url
   * @param paramMap
   * @return
   * @throws IOException
   */
  public static String httpPost(String url, Map<String, String> paramMap) throws IOException {
    
    NameValuePair[] paramArray = new NameValuePair[paramMap.size()];
    Iterator keyIterator = paramMap.keySet().iterator();
    int i = 0;
    while (keyIterator.hasNext()) {
      String key = (String) keyIterator.next();
      String value = (String) paramMap.get(key);
      paramArray[i] = new NameValuePair(key, value);
      i++;
    }
    
    HttpClient httpClient = new HttpClient();
    PostMethod postMethod = new PostMethod(url);
    postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
    for (NameValuePair nameValuePair : paramArray) {
      System.err.println(nameValuePair);
    }
    postMethod.setRequestBody(paramArray);
    httpClient.executeMethod(postMethod);
    String response = postMethod.getResponseBodyAsString();
    System.err.println(response);
    return response;
  }

  public static String httpPostWithJson2(String url, String jsonString) throws IOException {
    HttpClient httpClient = new HttpClient();
    PostMethod postMethod = new PostMethod(url);

    postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
    postMethod.setRequestHeader("Accept", "application/json; charset=utf-8");
//    postMethod.setRequestHeader("Content-type", "application/json; charset=gb2312");
//    postMethod.setRequestHeader("Accept", "application/json; charset=gb2312");
    postMethod.setRequestBody(jsonString);
    postMethod.getResponseCharSet();
    postMethod.getResponseBody();
    httpClient.executeMethod((HttpMethod)postMethod);
    String response = postMethod.getResponseBodyAsString();
    return response;
  }

  public static String httpPostWithJson(String url, String jsonString) throws IOException {
    HttpClient httpClient = new HttpClient();
    PostMethod postMethod = new PostMethod(url);

    postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
    postMethod.setRequestHeader("Accept", "application/json; charset=utf-8");
    postMethod.setRequestBody(jsonString);
    httpClient.executeMethod((HttpMethod)postMethod);

    String result = "";
    BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), "utf-8"));
    String lines = "";
    while ((lines = reader.readLine()) != null)
      result = result + lines;
    return result;
  }

  public static String httpPostWithMap(String url, Map<String, Object> paramMap) throws IOException {
    NameValuePair[] paramArray = new NameValuePair[paramMap.size()];
    Iterator<String> keyIterator = paramMap.keySet().iterator();
    int i = 0;
    while (keyIterator.hasNext()) {
      String key = keyIterator.next();
      String value = (String)paramMap.get(key);
      paramArray[i] = new NameValuePair(key, value);
      i++;
    }
    HttpClient httpClient = new HttpClient();
    PostMethod postMethod = new PostMethod(url);
    postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
    postMethod.setRequestBody(paramArray);
    httpClient.executeMethod((HttpMethod)postMethod);
    String response = postMethod.getResponseBodyAsString();
    System.err.println(response);
    return response;
  }

  public static String httpPostWithFile(String url, Map<String, String> map, File file) throws Exception {
    String BOUNDARY = "BOUNDARY";
    String filename = file.getName();
    StringBuilder sb = new StringBuilder();
    if (null != map)
      for (Map.Entry<String, String> entry : map.entrySet()) {
        sb.append("--" + BOUNDARY + "\r\n");
        sb.append("Content-Disposition: form-data; name=\"" + (String)entry
                .getKey() + "\"\r\n");
        sb.append("\r\n");
        sb.append((String)entry.getValue() + "\r\n");
      }
    sb.append("--" + BOUNDARY + "\r\n");
    sb.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + filename + "\"\r\n");
    sb.append("Content-Type: image/pjpeg\r\n");
    sb.append("\r\n");
    byte[] before = sb.toString().getBytes("UTF-8");
    byte[] after = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
    URL urlObject = new URL(url);
    HttpURLConnection conn = (HttpURLConnection)urlObject.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
    conn.setRequestProperty("Content-Length", String.valueOf(before.length + file.length() + after.length));
    conn.setRequestProperty("HOST", urlObject.getHost());
    conn.setDoOutput(true);
    OutputStream out = conn.getOutputStream();
    InputStream in = new FileInputStream(file);
    out.write(before);
    byte[] buf = new byte[1024];
    int len;
    while (-1 != (len = in.read(buf)))
      out.write(buf, 0, len);
    out.write(after);
    String result = "";
    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
    String lines = "";
    while ((lines = reader.readLine()) != null)
      result = result + lines;
    in.close();
    out.close();
    return result;
  }

  /**
   * 发送一个httpGET请求
   *
   * @param url
   *        请求的url
   * @return
   * @throws IOException
   */
  public static Object httpsGet(String url,String proxyHost,int proxyPort) throws IOException {

    HttpRequest request = HttpRequest
            .get(url)
            .useProxy(proxyHost, proxyPort)
            .trustAllCerts()
            .trustAllHosts();
    return request.body().toString();
  }
}
