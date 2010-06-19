package com.mojavelinux.socialize.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HTTPExecutor {

   DefaultHttpClient httpClient = new DefaultHttpClient();
   HttpContext localContext = new BasicHttpContext();

   private HttpHost httpHost;

   public HTTPExecutor(String serverAddr) {
      httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);
      httpHost = new HttpHost(serverAddr, 9090);
   }

   private String getURI(String url) {
      return "/socialize/seam/resource/rest" + url;
   }

   public void clearCookies() {
      httpClient.getCookieStore().clear();
   }

   public String post(String url, String data) {

      String ret = null;
      try {
         HttpPost httpPost = new HttpPost(getURI(url));
         StringEntity tmp = null;

         httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

         tmp = new StringEntity(data, "UTF-8");
         httpPost.setEntity(tmp);
         HttpResponse response = httpClient.execute(httpHost, httpPost, localContext);
         ret = slurp(response.getEntity().getContent());
      }
      catch (IllegalStateException e) {
         e.printStackTrace();
      }
      catch (IOException e) {
         e.printStackTrace();
      }

      return ret;
   }

   public InputStream get(String url) {
      try {
         HttpGet httpGet = new HttpGet(getURI(url));

         httpGet.setHeader("Content-Type", "text/plain");
         httpGet.setHeader("Accept", "application/xml");

         HttpResponse response = httpClient.execute(httpHost, httpGet, localContext);
         return response.getEntity().getContent();
      }
      catch (ClientProtocolException e) {
         System.out.println("HTTPExecutor : ClientProtocolException : " + e);
      }
      catch (IOException e) {
         System.out.println("HTTPExecutor : IOException : " + e);
      }
      return null;
   }

   public static String slurp(InputStream in) {
      StringBuffer out = new StringBuffer();
      try {
         byte[] b = new byte[4096];
         for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
         }
      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
      return out.toString();
   }
}
