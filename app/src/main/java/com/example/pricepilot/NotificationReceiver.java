package com.example.pricepilot;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;

public class NotificationReceiver extends BroadcastReceiver {
  private static final String CHANNEL_ID = "Notification_channel";
  private static final int NOTIFICATION_ID = 1;

  @Override
  public void onReceive(Context context, Intent intent) {
    createNotificationChannel(context);

    Intent notificationIntent = new Intent(context, FavoritesActivity.class);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

    NotificationCompat.Builder builder = new Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.filled_heart_icon)
        .setContentTitle("Price Pilot")
        .setContentText(context.getString(R.string.notification))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true);

    NotificationManager notificationManager = (NotificationManager) context.getSystemService(
        Context.NOTIFICATION_SERVICE);

    notificationManager.cancel(NOTIFICATION_ID);

    notificationManager.notify(NOTIFICATION_ID, builder.build());
  }

  private void createNotificationChannel(Context context) {
    CharSequence name = "Notification Channel";
    String description = "Channel for notifications";
    int importance = NotificationManager.IMPORTANCE_HIGH;
    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
    channel.setDescription(description);

    NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
    notificationManager.createNotificationChannel(channel);
  }

}
