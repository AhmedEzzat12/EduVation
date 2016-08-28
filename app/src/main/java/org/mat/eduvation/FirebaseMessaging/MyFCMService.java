package org.mat.eduvation.FirebaseMessaging;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.mat.eduvation.LocaL_Database.DatabaseConnector;
import org.mat.eduvation.R;
import org.mat.eduvation.Utility;
import org.mat.eduvation.navigation_items.Announcements;

/**
 * Created by ahmed on 8/23/16.
 */

public class MyFCMService extends FirebaseMessagingService {
    DatabaseConnector databaseConnector;
    private FirebaseDatabase database;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        database = FirebaseDatabase.getInstance();
        databaseConnector = new DatabaseConnector(MyFCMService.this);
        databaseConnector.open();
        sendNotification(remoteMessage.getNotification().getBody());

    }

    private void sendNotification(String messageBody) {

        databaseConnector.insertNotification(messageBody, Utility.getDate());
        databaseConnector.close();

        Intent intent = new Intent(this, Announcements.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("EduVation")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }


}
