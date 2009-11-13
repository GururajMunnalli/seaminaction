package com.socialize.ext;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.socialize.model.User;

@Provider
@Produces("text/x-vCard")
public class VCardProvider implements MessageBodyWriter<User>
{

   public long getSize(User user, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType)
   {
      return -1;
   }

   public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType)
   {
      return User.class.isAssignableFrom(aClass);
   }

   public void writeTo(User user, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream outputStream) throws IOException, WebApplicationException
   {
      StringBuilder vcard = new StringBuilder("BEGIN:VCARD\nVERSION:3.0\n");
      vcard.append("FN:" + user.getName() + "\n");
      vcard.append("ADR;TYPE=HOME:;;" + user.getLocation().replace(",", "\\,") + ";;;;\n");
      vcard.append("URL:" + user.getUrl() + "\n");
      vcard.append("REV:" + new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'").format(new Date()) + "\n");
      vcard.append("END:VCARD");
      outputStream.write(vcard.toString().getBytes());
   }
   
}
