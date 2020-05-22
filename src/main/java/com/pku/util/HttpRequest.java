package com.pku.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HttpRequest{

       /**
        * 向指定URL发送GET方法的请求
        *
        * @param url
        *            发送请求的URL
        * @param param
        *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
        * @return URL 所代表远程资源的响应结果
        */
   public static String sendGe(String url, String param) {
       String result = "";
       BufferedReader in = null;
       try {
           String urlNameString = url ;
           System.out.println(urlNameString);
           URL realUrl = new URL(urlNameString);
           // 打开和URL之间的连接
           URLConnection connection = realUrl.openConnection();
           // 设置通用的请求属性
           connection.setRequestProperty("accept", "*/*");
           connection.setRequestProperty("connection", "Keep-Alive");
           connection.setRequestProperty("user-agent",
                   "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
           // 建立实际的连接
           connection.connect();
           // 获取所有响应头字段
           Map<String, List<String>> map = connection.getHeaderFields();
           // 遍历所有的响应头字段
           for (String key : map.keySet()) {
               System.out.println(key + "--->" + map.get(key));
           }
        // 定义 BufferedReader输入流来读取URL的响应
           in = new BufferedReader(new InputStreamReader(
                   connection.getInputStream()));
           String line;
           while ((line = in.readLine()) != null) {
               result += line;
           }
       } catch (Exception e) {
           System.out.println("发送GET请求出现异常" + e);
           e.printStackTrace();
       }
    // 使用finally块来关闭输入流
       finally {
           try {
               if (in != null) {
                   in.close();
               }
           } catch (Exception e2) {
               e2.printStackTrace();
           }
       }
       return result;
   }

    /**
    * 向指定 URL 发送POST方法的请求
    *
    * @param url
    *            发送请求的 URL
    * @param param
    *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
    * @return 所代表远程资源的响应结果
    */
   public static String sendPo(String url, String param) {
       System.out.println("url==========="+url);
       System.out.println("param==========="+param);
       PrintWriter out = null;
       BufferedReader in = null;
       String result = "";

       while(param.contains("Rand")){
           String randStr=String.valueOf(new Random().nextInt(9))+
                   String.valueOf(new Random().nextInt(9))+
                   String.valueOf(new Random().nextInt(9))+
                   String.valueOf(new Random().nextInt(9));
           param=param.replaceFirst("Rand",randStr);
           //System.out.println("============================="+ param);
       }

       try {
           URL realUrl = new URL(url);
           // 打开和URL之间的连接
           URLConnection conn = realUrl.openConnection();
           // 设置通用的请求属性
           conn.setRequestProperty("accept", "*/*");
           conn.setRequestProperty("connection", "Keep-Alive");
           conn.setRequestProperty("user-agent",
                   "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
           conn.setRequestProperty("Content-Type", "text/html");
          // 发送POST请求必须设置如下两行
           conn.setDoOutput(true);
           conn.setDoInput(true);
           // 获取URLConnection对象对应的输出流
           out = new PrintWriter(conn.getOutputStream());
           // 发送请求参数
           out.print(param);

           // flush输出流的缓冲
           out.flush();
           // 定义BufferedReader输入流来读取URL的响应
           in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           String line;
           while ((line = in.readLine()) != null) {
               result += line;
           }
       } catch (Exception e) {
           System.out.println("发送 POST 请求出现异常"+e);
           e.printStackTrace();
       }
       //使用finally块来关闭输出流、输入流
       finally{
           try{
               if(out!=null){
                   out.close();
               }
               if(in!=null){
                   in.close();
               }
           }
           catch(IOException ex){
               ex.printStackTrace();
           }
       }
       //System.out.println("result==========="+result);
       return result;
   }

   public String getCode(String surl){
       String code = null ;
       try {
            //url="http://www.baidu.com";
             URL url = new URL(surl);
             URLConnection rulConnection   = url.openConnection();
             HttpURLConnection httpUrlConnection  =  (HttpURLConnection) rulConnection;
             httpUrlConnection.setConnectTimeout(300000);
             httpUrlConnection.setReadTimeout(300000);
             httpUrlConnection.connect();
             code = new Integer(httpUrlConnection.getResponseCode()).toString();
             //String message = httpUrlConnection.getResponseMessage();
             //System.out.println("getResponseCode code ="+ code);
             //System.out.println("getResponseMessage message ="+ message);
             if(!code.startsWith("2")){
                  throw new Exception("ResponseCode is not begin with 2,code="+code);
             }
        }catch(Exception ex){
             //System.out.println(ex.getMessage());
        }
       return code;
}
       public static void main(String[] args) {
           String url = "http://10.100.139.18:8889/backCenter/project/changeStatus.htmls?";
           String param = "id=671&status=2";
           sendPo(url,param);
       }
}
 
 