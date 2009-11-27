package com.jsfsummit.socialize.domain;

import java.util.Date;

public class Status
{
    private Date sent;
    private String message;
    
    public Status(String message)
    {
        sent = new Date();
        this.message = message;
    }

    public Date getSent()
    {
        return sent;
    }

    public void setSent(Date sent)
    {
        this.sent = sent;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
