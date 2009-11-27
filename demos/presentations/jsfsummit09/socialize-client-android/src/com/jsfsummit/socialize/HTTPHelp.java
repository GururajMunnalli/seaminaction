package com.jsfsummit.socialize;

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

public class HTTPHelp
{

    DefaultHttpClient httpClient = new DefaultHttpClient();
    HttpContext localContext = new BasicHttpContext();
    private String ret;

    HttpResponse response = null;
    HttpPost httpPost = null;
    private HttpHost httpHost;
    private HttpGet httpGet;

    public HTTPHelp()
    {
        httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);
        httpHost = new HttpHost("10.0.2.2", 9090);
    }

    private String getURI(String url)
    {
        return "/socialize/seam/resource/rest" + url;
    }

    public void clearCookies()
    {
        httpClient.getCookieStore().clear();
    }

    public void abort()
    {
        try
        {
            if (httpClient != null)
            {
                System.out.println("Abort.");
                httpPost.abort();
            }
        }
        catch (Exception e)
        {
            System.out.println("HTTPHelp : Abort Exception : " + e);
        }
    }

    public String post(String url, String data)
    {

        try
        {
            ret = null;

            httpPost = new HttpPost(getURI(url));
            response = null;

            StringEntity tmp = null;

            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

            tmp = new StringEntity(data, "UTF-8");

            httpPost.setEntity(tmp);

            response = httpClient.execute(httpHost, httpPost, localContext);

            ret = response.getStatusLine().toString();
            ret = slurp(response.getEntity().getContent());
        }
        catch (IllegalStateException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return ret;
    }

    public String get(String url)
    {
        try
        {
            ret = null;

            httpGet = new HttpGet(getURI(url));
            response = null;

            httpPost.setHeader("Content-Type", "text/plain");

            response = httpClient.execute(httpHost, httpGet, localContext);
            ret = response.getStatusLine().toString();
            ret = slurp(response.getEntity().getContent());
        }
        catch (ClientProtocolException e)
        {
            System.out.println("HTTPHelp : ClientProtocolException : " + e);
        }
        catch (IOException e)
        {
            System.out.println("HTTPHelp : IOException : " + e);
        }

        return ret;
    }

    public static String slurp(InputStream in)
    {
        StringBuffer out = new StringBuffer();
        try
        {
            byte[] b = new byte[4096];
            for (int n; (n = in.read(b)) != -1;)
            {
                out.append(new String(b, 0, n));
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return out.toString();
    }
}
