package ucsd_scholars.tritonfoodpantry;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotificationActivity extends AppCompatActivity {

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
                pushNotification();
            }
        });
    }

    private void pushNotification() {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.home);
        notificationBuilder.setContentTitle(messageTitle.getText().toString());
        notificationBuilder.setContentText(messageText.getText().toString());


        // cute little idiom
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);

        // Actually send it
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

}
