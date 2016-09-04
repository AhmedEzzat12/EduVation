package org.mat.eduvation.FirebaseMessaging;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.mat.eduvation.NotificationModel;
import org.mat.eduvation.Utility;

/**
 * Created by ahmed on 8/23/16.
 */

public class MyFCMService extends FirebaseMessagingService {
    private FirebaseDatabase database;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        database = FirebaseDatabase.getInstance();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        NotificationModel notification = new NotificationModel(remoteMessage.getNotification().getBody(),Utility.getDate());
        //Adding values

        DatabaseReference newRef = ref.child("Notification").push();
        newRef.setValue(notification);

    }


}

