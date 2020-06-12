package pl.edu.uwr.login_PAM;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static pl.edu.uwr.login_PAM.App.CHANNEL_ID;
import static pl.edu.uwr.login_PAM.App.notificationManager;

public class IntentServiceNotifications extends IntentService
{
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> text = new ArrayList<>();

    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate()
    {
        super.onCreate();

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "app:WakeLock");
        wakeLock.acquire();

        startForegroundNotification();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        wakeLock.release();
    }

    public IntentServiceNotifications()
    {
        super("IntentServiceNotifications");
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        getValues(intent);

        while(text.size() > 0)
        {
            for (int i = 0; i < text.size(); i++)
            {
                if(time.get(i).substring(0, 16).equals(getCurrentTime()))
                {
                    createNotification(text.get(i));
                }
            }
            SystemClock.sleep(60000);
        }
    }

    private void getValues(Intent intent)
    {
        time = intent.getStringArrayListExtra("time");
        text = intent.getStringArrayListExtra("text");
    }

    private void startForegroundNotification()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Garden_Diary")
                    .setContentText("Proces powiadomien")
                    .setSmallIcon(R.drawable.button_background)
                    .build();

            startForeground(1, notification);
        }
    }

    private void createNotification(String Text)
    {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Powiadomienie")
                .setContentText(Text)
                .setSmallIcon(R.drawable.button_background)
                .build();

        notificationManager.notify(5, notification);
    }

    private String getCurrentTime()
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        return date;
    }
}
