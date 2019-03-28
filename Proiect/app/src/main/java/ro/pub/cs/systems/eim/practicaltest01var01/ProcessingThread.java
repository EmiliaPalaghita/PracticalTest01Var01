package ro.pub.cs.systems.eim.practicaltest01var01;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.util.Calendar;

public class ProcessingThread extends Thread {
    private Context context;
    private boolean isRunning = true;
    private String cardinalPoints;

    public ProcessingThread(Context context, String cardinalPoints) {
        this.context = context;
        this.cardinalPoints = cardinalPoints;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        sleep();
        sendMessage();

        Log.d("[ProcessingThread]", "Thread has stopped!");

    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.SERVICE_ACTION);
        String time = getTime();
        intent.putExtra("message", time + " -- " + cardinalPoints);
        context.sendBroadcast(intent);
    }

    private String getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        return DateFormat.getDateTimeInstance().format(calendar.getTime());
    }

    private void sleep() {
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void stopThread() {
        isRunning = false;
    }
}
