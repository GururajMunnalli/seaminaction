package com.jsfsummit.socialize;

import java.net.URLEncoder;

public class TimelineService
{
    private HTTPHelp http = new HTTPHelp();

    public void updateUserStatus(String user, String message)
    {
        try
        {
            http.post("/timeline/" + user + "?status=" + URLEncoder.encode(message), "");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getUserStatuses(String user)
    {
        try
        {
            return http.get("/timeline/" + user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "error";
        }
    }

}
