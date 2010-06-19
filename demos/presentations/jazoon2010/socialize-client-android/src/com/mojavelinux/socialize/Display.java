package com.mojavelinux.socialize;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mojavelinux.socialize.domain.Status;
import com.mojavelinux.socialize.service.TimelineClient;

public class Display extends Activity {

   private EditText username;
   private EditText input;
   private TextView feeds;

   private List<Status> statuses = new ArrayList<Status>();
   TimelineClient ts;

   /** Called when the activity is first created. */
   @Override public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      ts = new TimelineClient(getApplicationContext().getResources().getString(R.string.server_addr));
      setContentView(R.layout.main); // bind the layout to the activity
      username = (EditText) findViewById(R.id.username);
      input = (EditText) findViewById(R.id.input);
      feeds = (TextView) findViewById(R.id.feeds);
      username.setText("mojavelinux");
      input.setText("");
   }

   // Will be connected with the buttons via XML
   public void myClickHandler(View view) {
      switch (view.getId()) {
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

   private void clear() {
      feeds.setText("");
      statuses.clear();
   }

   private void send() {
      if (input.getText().toString().length() != 0) {
         Status s = new Status(input.getText().toString().trim());
         statuses.add(s);
         input.setText("");
         ts.updateUserStatus(username.getText().toString().trim(), s);
      }
   }

   private void refresh() {
      statuses = ts.getUserStatuses(username.getText().toString().trim());
      feeds.setText(generateText());
   }

   private String generateText() {
      String result = "";
      for (Status s : statuses) {
         DateFormat format = new SimpleDateFormat("MM-dd@hh:mm:ss");
         result = format.format(s.getSent()) + ": " + s.getMessage() + "\n" + result;
      }
      return result;
   }
}