package ucsd_scholars.tritonfoodpantry;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.

        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getNotification().getBody();

        // makes notification appear even when app is in foreground
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.profile);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);

        Intent notificationIntent = new Intent(this, HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);

        // Actually send it
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

        // add this new notification to HomeActivity's arraylist of recent notifications
        // MainActivity.db.addToRecentNotifications(new Story(title, body));

        Log.d(TAG, "message RECEIVED!!!");
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Title: " + title);
        Log.d(TAG, "Notification Message Body: " + body);
    }
}