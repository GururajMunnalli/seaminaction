package com.mojavelinux.socialize.service;

import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mojavelinux.socialize.domain.Status;

public class TimelineClient {

   private HTTPExecutor http;
   private DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

   public TimelineClient(String serverAddr) {
      http = new HTTPExecutor(serverAddr);
   }

   public void updateUserStatus(String user, Status message) {
      try {
         http.post("/timeline/" + user + "?status=" + URLEncoder.encode(message.getMessage()), "");
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   public List<Status> getUserStatuses(String user) {
      List<Status> result = new ArrayList<Status>();
      try {
         InputStream timeline = http.get("/timeline/" + user);
         Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(timeline);
         NodeList updates = dom.getFirstChild().getChildNodes();

         for (int i = 0; i < updates.getLength(); i++) {
            Node n = updates.item(i);

            Node created = n.getChildNodes().item(0);
            Node text = n.getChildNodes().item(2);

            Date d = fmt.parse(created.getFirstChild().getNodeValue());
            Status s = new Status(text.getFirstChild().getNodeValue());
            s.setSent(d);
            result.add(s);
         }
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      return result;
   }

}
