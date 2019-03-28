package ro.pub.cs.systems.eim.practicaltest01var01;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

public class PracticalTest01Var01Service extends Service {
    ProcessingThread thread = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, new Notification());
        }

        String cardinalPoints = intent.getStringExtra(Constants.CARDINAL_POINTS);

        thread = new ProcessingThread(this, cardinalPoints);
        thread.start();

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.stopThread();
    }
}
