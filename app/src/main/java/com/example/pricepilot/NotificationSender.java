package com.example.pricepilot;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.Settings;
import android.util.Log;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class NotificationSender {
  private static final int NOTIFICATION_TIMEOUT = 15000; // 15 seconds
  private ActivityResultLauncher<Intent> resultLauncher;
  private Context context;
  private AlarmManager alarmManager;

  public NotificationSender(Context context, ActivityResultLauncher<Intent> resultLauncher) {
    this.resultLauncher = resultLauncher;
    this.context = context;
    this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
  }

  public void setNotificationAlarm() {
    if (!canScheduleExactAlarms()) {
      requestExactAlarmPermission();
    }
      scheduleExactAlarm();
  }

  private void scheduleExactAlarm() {
    Intent intent = new Intent(context, NotificationReceiver.class);
    intent.setAction("com.example.pricepilot.ACTION_NOTIFY");
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    long triggerTime = System.currentTimeMillis() + NOTIFICATION_TIMEOUT;

    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
    Log.i("Notifications", "Created");
  }

  public boolean canScheduleExactAlarms() {
    if (VERSION.SDK_INT >= VERSION_CODES.S) {
      return alarmManager.canScheduleExactAlarms();
    }
    return true;
  }

  private void requestExactAlarmPermission() {
    if (VERSION.SDK_INT >= VERSION_CODES.S) {
      Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
      resultLauncher.launch(intent);
    }
  }
}
