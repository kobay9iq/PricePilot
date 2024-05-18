package com.example.pricepilot;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class NotificationSender {
  private static Integer NOTIFCITATION_TIMEOUT = 30000;
  private ActivityResultLauncher<Intent> resultLauncher;
  private Context context;
  private AlarmManager alarmManager;
  private PendingIntent pendingIntent;

  public NotificationSender(Context context, ActivityResultLauncher<Intent> resultLauncher) {
    this.resultLauncher = resultLauncher;
    this.context = context;
    alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
  }

  public void setNotificationAlarm() {
    alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    if (!canScheduleExactAlarms()) {
      requestExactAlarmPermission();
    }

    Intent intent = new Intent(context, NotificationReceiver.class);
    intent.setAction("com.example.pricepilot.ACTION_NOTIFY");
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

    long triggerTime = System.currentTimeMillis() + NOTIFCITATION_TIMEOUT;

    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
  }

  public boolean canScheduleExactAlarms() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
      return alarmManager.canScheduleExactAlarms();
    }
    return true;
  }

  private void requestExactAlarmPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
      resultLauncher.launch(intent);
    }
  }
}
