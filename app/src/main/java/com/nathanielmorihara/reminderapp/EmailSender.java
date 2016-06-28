package com.nathanielmorihara.reminderapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nathaniel on 6/19/2016.
 */
public class EmailSender extends AsyncTask<String,Integer,Boolean> {

    Context context;

    String userEmail = "nathanielmorihara@gmail.com";

    EditText reminderContent;
    Button sendButton;

    public EmailSender(Context context) {
        this.context = context;

        reminderContent = (EditText) ((MainActivity) context).findViewById(R.id.reminder_content);
        sendButton = (Button) ((MainActivity) context).findViewById(R.id.send_button);
    }

    protected void onPreExecute() {
        reminderContent.setEnabled(false);
        sendButton.setEnabled(false);
        sendButton.setText(context.getResources().getString(R.string.send_in_progress));
    }

    protected Boolean doInBackground(String... content) {
        try {
            //TODO: Don't require a dummy Gmail (security reasons)
            //TODO: Don't hardcode in username and password...allow other users
            GMailSender sender = new GMailSender("nathanielreminderapp@gmail.com", "2913PooP");
            sender.sendMail("Reminder!",
                    content[0],
                    "nathanielreminderapp@gmail.com",//sender
                    userEmail); //recipient
            return true;

        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
            return false;
        }
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(Boolean sendSuccess) {
        super.onPostExecute(sendSuccess);

        reminderContent.setText("");
        reminderContent.setEnabled(true);
        sendButton.setEnabled(true);
        sendButton.setText(context.getResources().getString(R.string.send_reminder));

        if(sendSuccess) {
            Toast.makeText(context, "Reminder sent! :)", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Your Reminder failed to send :(", Toast.LENGTH_SHORT).show();
        }
    }
}
