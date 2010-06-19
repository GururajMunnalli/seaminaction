package com.socialize;

public class NoSuchUserException extends RuntimeException
{
   private String user;
   
   public NoSuchUserException(String user) {
      this.user = user;
   }
   
   public String getUser() {
      return user;
   }
}
