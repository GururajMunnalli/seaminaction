package com.socialize.test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.jboss.seam.mock.EnhancedMockHttpServletRequest;
import org.jboss.seam.mock.EnhancedMockHttpServletResponse;
import org.jboss.seam.mock.ResourceRequestEnvironment;
import org.jboss.seam.mock.SeamTest;
import org.jboss.seam.mock.ResourceRequestEnvironment.Method;
import org.jboss.seam.mock.ResourceRequestEnvironment.ResourceRequest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.socialize.model.Timeline;
import com.socialize.model.Update;

public class PublicTimelineRestTest extends SeamTest
{
   ResourceRequestEnvironment requestEnv;

   @BeforeClass
   public void prepareEnv() throws Exception
   {
      requestEnv = new ResourceRequestEnvironment(this)
      {
         @Override
         public Map<String, Object> getDefaultHeaders()
         {
            return new HashMap<String, Object>()
            {{
               put("Accept", "application/xml");
            }};
         }
      };
   }
   
   @Test
   public void aggregateTimelineResource() throws Exception
   {
      new ResourceRequest(requestEnv, Method.GET, "/rest/timeline")
      {
         @Override
         protected void prepareRequest(EnhancedMockHttpServletRequest request)
         {
            request.setContentType(MediaType.APPLICATION_XML);
            request.setQueryString("count=1");
         }

         @Override
         protected void onResponse(EnhancedMockHttpServletResponse response)
         {
            assert response.getStatus() == 200;
            String content = response.getContentAsString().trim();
            assert content.startsWith("<?xml");
            try
            {
               JAXBContext ctx = JAXBContext.newInstance(Timeline.class, Update.class);
               Object result = ctx.createUnmarshaller().unmarshal(new StringReader(content));
               assert result instanceof Timeline;
               Timeline timeline = (Timeline) result;
               assert timeline.getUpdates().size() == 1;
               assert "mojavelinux".equals(timeline.getUpdates().get(0).getUser().getScreenName());
               assert "I'm socializing!".equals(timeline.getUpdates().get(0).getText());
            }
            catch (JAXBException e)
            {
               assert false : "Failed to load JAXB";
            }
            
         }

      }.run();

   }
}
