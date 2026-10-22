package com.example.kisaansathi.data

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kisaansathi.MainActivity
import com.example.kisaansathi.model.CropPlan
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object CropReminderScheduler {
    private const val REQUEST_CODE = 9107
    private const val CHANNEL_ID = "crop_reminders"

    fun schedule(context: Context, plan: CropPlan?) {
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val pendingIntent = reminderIntent(context)
        if (plan == null) {
            alarmManager.cancel(pendingIntent)
            return
        }
        val dueDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(plan.reminderDate)
            ?: return
        val calendar = Calendar.getInstance().apply {
            time = dueDate
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            alarmManager.cancel(pendingIntent)
            return
        }
        val title = "Crop care reminder"
        val body = "${plan.crop}: ${plan.reminderTask}"
        val scheduledIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            Intent(context, CropReminderReceiver::class.java)
                .putExtra(CropReminderReceiver.EXTRA_TITLE, title)
                .putExtra(CropReminderReceiver.EXTRA_BODY, body),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            scheduledIntent
        )
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Crop care reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            context.getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }

    fun notification(context: Context, title: String, body: String) =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(com.example.R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CODE,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setAutoCancel(true)
            .build()

    private fun reminderIntent(context: Context) =
        PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            Intent(context, CropReminderReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
}

class CropReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        CropReminderScheduler.createNotificationChannel(context)
        NotificationManagerCompat.from(context).notify(
            9107,
            CropReminderScheduler.notification(
                context,
                intent.getStringExtra(EXTRA_TITLE) ?: "Crop care reminder",
                intent.getStringExtra(EXTRA_BODY) ?: "Check your planned field task."
            )
        )
    }
    companion object {
        const val EXTRA_TITLE = "reminder_title"
        const val EXTRA_BODY = "reminder_body"
    }
}

class CropReminderBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            LocalStore(context).restoreCropReminder()
        }
    }
}
