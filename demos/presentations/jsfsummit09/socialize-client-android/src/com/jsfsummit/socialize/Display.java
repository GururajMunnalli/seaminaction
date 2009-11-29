package com.jsfsummit.socialize;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jsfsummit.socialize.domain.Status;
import com.jsfsummit.socialize.service.TimelineService;

public class Display extends Activity
{
    private EditText text;
    private TextView feeds;

    private List<Status> statuses = new ArrayList<Status>();
    TimelineService ts = new TimelineService();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // bind the layout to the activity
        text = (EditText) findViewById(R.id.input);
        feeds = (TextView) findViewById(R.id.feeds);
        text.setText("");
    }

    // Will be connected with the buttons via XML
    public void myClickHandler(View view)
    {
        switch (view.getId())
            {
            case R.id.Refresh:
                refresh();
                break;
            case R.id.Send:
                send();
                refresh();
                break;
            case R.id.Clear:
                clear();
                break;
            }
    }

    private void clear()
    {
        feeds.setText("");
        statuses.clear();
    }

    private void send()
    {
        if (text.getText().toString().length() != 0)
        {
            Status s = new Status(text.getText().toString().trim());
            statuses.add(s);
            text.setText("");
            ts.updateUserStatus("lincolnthree", s);
        }
    }

    private void refresh()
    {
        statuses = ts.getUserStatuses("lincolnthree");
        feeds.setText(generateText());
    }

    private String generateText()
    {
        String result = "";
        for (Status s : statuses)
        {
            DateFormat format = new SimpleDateFormat("H:mm:ss");
            result = format.format(s.getSent()) + ": " + s.getMessage() + "\n" + result;
        }
        return result;
    }
}