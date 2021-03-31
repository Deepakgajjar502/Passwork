package com.b2b.passwork.Utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.b2b.passwork.Activity.SplaceScreen;
import com.b2b.passwork.R;
import com.google.firebase.messaging.RemoteMessage;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService
{
    private static final String TAG = "FCMService";
    public static final String ANDROID_CHANNEL_ID = "com.sharadtechnologies.rarome.channel";
    public static final String ANDROID_CHANNEL_NAME = "RAROME";
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        /*String notificationTitle=remoteMessage.getNotification().getTitle();
        String notificationMesaage=remoteMessage.getNotification().getBody();

        String domNotificationTitle=remoteMessage.getData().get("title");
        String domNotificationMessage=remoteMessage.getData().get("title");
        String domNotificationImgURL=remoteMessage.getData().get("img_url");*/

        //String myNotificationMessage=remoteMessage.getData().get("message");
        //showNotification(myNotificationMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        /*if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().toString());

            if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }*/


        String notificationMessage = remoteMessage.getNotification().getBody();
        String notificationTitle = remoteMessage.getNotification().getTitle();
        Log.i(TAG, "message body " + notificationMessage);
        Log.i(TAG, "message title " + notificationTitle);

        if (remoteMessage.getData().size() > 0)
        {
            Log.e(TAG, "AllData Payload: " + remoteMessage.getData().toString());

            /*try
            {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e)
            {
                e.printStackTrace();
            }*/
            handleMappedMessage(remoteMessage.getData());
        }
        else if (remoteMessage.getNotification() != null) {

            //Log.i(TAG,"message body 111"+remoteMessage.getData().get("body"));
            showNotification(notificationMessage);
        }
    }

    private void handleMappedMessage(Map<String, String> data)
    {
        String body = data.get("body");
        String timestamp = data.get("timestamp");
        String title = data.get("title");
        String imageUrl = data.get("icon");

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
                /*Intent pushNotification = new Intent(AppConstants.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);*/

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//            notificationUtils.playNotificationSound();
        }
        else
        {
            // app is in background, show the notification in notification tray
//                Intent resultIntent = new Intent(getApplicationContext(), SplashScreen.class);
            Intent resultIntent = getMessageIntent();
            resultIntent.putExtra("message", body);

            // check for image attachment
            if (TextUtils.isEmpty(imageUrl))
            {
                showNotificationMessage(getApplicationContext(), title, body, timestamp, resultIntent);
            } else {
                // image is present, show notification with image
                showNotificationMessageWithBigImage(getApplicationContext(), title, body, timestamp, resultIntent, imageUrl);
            }
        }

    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                /*Intent pushNotification = new Intent(AppConstants.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);*/

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSound();
            }
            else
                {
                // app is in background, show the notification in notification tray
//                Intent resultIntent = new Intent(getApplicationContext(), SplashScreen.class);
                Intent resultIntent = getMessageIntent();
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl))
                {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

    private Intent getMessageIntent()
    {
        Intent intent = null;
        UserSessionManager sessionManager = UserSessionManager.getInstance(getApplicationContext());
        String userId = sessionManager.getUserId();
        Log.i(TAG, "userId" + userId);
        if(TextUtils.isEmpty(userId))
        {
            intent = new Intent(this, SplaceScreen.class);
        }
        else
        {
            intent = new Intent(this, SplaceScreen.class);
        }
        return intent;
    }

    private void showNotification(String message)
    {
        Intent intent = getMessageIntent();


//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String NotificationTitle = "Rarome";
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = manager.getNotificationChannel(ANDROID_CHANNEL_ID);
            if (mChannel == null) {
                mChannel = new NotificationChannel(ANDROID_CHANNEL_ID, ANDROID_CHANNEL_NAME, importance);
//                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                manager.createNotificationChannel(mChannel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ANDROID_CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(NotificationTitle)
                .setTicker(message)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setVibrate(new long[]{100, 250, 100, 250, 100, 250})
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(pendingIntent);

        manager.notify(0, builder.build());
    }

}
