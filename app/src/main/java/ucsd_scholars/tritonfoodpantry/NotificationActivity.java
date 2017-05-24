package ucsd_scholars.tritonfoodpantry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    // this sender_id is our unique firebase id for sending notifications to users
    private static final String SENDER_ID = "778703925901";
    // server key used for sending messages to server
    private static final String LEGACY_SERVER_KEY = "AIzaSyB2Xh2wEtqgDWYQKDzmXruUuSggGe1Z6Y0";

    private EditText messageTitle;
    private EditText messageText;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btn = (Button)findViewById(R.id.button_sendNotification);
        messageTitle = (EditText) findViewById(R.id.title);
        messageText =  (EditText) findViewById(R.id.messageText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPromptSend(v);
            }
        });
    }

    private void pushNotification() {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this);

        if(messageTitle.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a Title", Toast.LENGTH_SHORT).show();
            return;
        }
        if(messageText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter a Message", Toast.LENGTH_SHORT).show();
            return;
        }

        /*notificationBuilder.setSmallIcon(R.drawable.profile);
        notificationBuilder.setContentTitle(messageTitle.getText().toString());
        notificationBuilder.setContentText(messageText.getText().toString());

        // cute little idiom
        Intent notificationIntent = new Intent(this, HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);

        // Actually send it
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());*/

        // sends push notification to all devices using volley
        sendFCMPush(messageTitle.getText().toString(), messageText.getText().toString());

        Toast.makeText(getApplicationContext(), "Notification Sent!", Toast.LENGTH_SHORT).show();
    }

    // http://stackoverflow.com/questions/37435750/how-to-send-device-to-device-messages-using-firebase-cloud-messaging
    // method for sending push notifications using volley?
    private void sendFCMPush(String title, String msg) {
        String token = FirebaseInstanceId.getInstance().getToken();

        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;

        try {
            obj = new JSONObject();
            objData = new JSONObject();

            objData.put("body", msg);
            objData.put("title", title);
            objData.put("sound", "default");
            objData.put("icon", "icon_name"); //   icon_name image must be there in drawable
            objData.put("tag", token);
            objData.put("to","/topics/news");
            objData.put("priority", "high");

            dataobjData = new JSONObject();
            dataobjData.put("text", msg);
            dataobjData.put("title", title);

            //obj.put("to", token);
            obj.put("to","/topics/news");
            //obj.put("priority", "high");

            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("!_@rj@_@@_PASS:>", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send", obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("!_@@_SUCESS", response + "");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("!_@@_Errors--", error + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + LEGACY_SERVER_KEY);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }

    // pop up dialog to the user asking if he/she wants to cancel notification
    public void dialogPromptCancel(View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to delete this notification?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        messageText.setText("");
                        messageTitle.setText("");
                        dialog.cancel();
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    // asks if admin is sure about sending notification
    public void dialogPromptSend(View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to send this notification?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        pushNotification();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
