package ro.pub.cs.systems.eim.practicaltest01var01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static ro.pub.cs.systems.eim.practicaltest01var01.Constants.CANCEL;
import static ro.pub.cs.systems.eim.practicaltest01var01.Constants.CARDINAL_POINTS;
import static ro.pub.cs.systems.eim.practicaltest01var01.Constants.POINTS;
import static ro.pub.cs.systems.eim.practicaltest01var01.Constants.REGISTER;
import static ro.pub.cs.systems.eim.practicaltest01var01.Constants.REQUEST;

public class PracticalTest01Var01Activity extends AppCompatActivity {

    Button northButton;
    Button westButton;
    Button eastButton;
    Button southButton;
    Button navigateToSecondActivity;
    TextView directionsTextView;
    boolean serviceStarted = false;

    private int points = 0;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var01_main);

        northButton = findViewById(R.id.north_btn);
        westButton = findViewById(R.id.west_btn);
        eastButton = findViewById(R.id.east_btn);
        southButton = findViewById(R.id.south_btn);
        navigateToSecondActivity = findViewById(R.id.navigate_to_second_act);
        directionsTextView = findViewById(R.id.directions_tv);

        directionsTextView.setText("");

        ButtonClickListener listener = new ButtonClickListener();
        northButton.setOnClickListener(listener);
        westButton.setOnClickListener(listener);
        eastButton.setOnClickListener(listener);
        southButton.setOnClickListener(listener);
        navigateToSecondActivity.setOnClickListener(listener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(POINTS)) {
                points = savedInstanceState.getInt(POINTS);
            } else {
                points = 0;
            }
        }

        intentFilter.addAction(Constants.SERVICE_ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POINTS, points);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(POINTS)) {
                points = savedInstanceState.getInt(POINTS);
            } else {
                points = 0;
            }
        }
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.north_btn: {
                    directionsTextView.append("North, ");
                    points++;
                    break;
                }
                case R.id.west_btn:
                    directionsTextView.append("West, ");
                    points++;
                    break;
                case R.id.east_btn:
                    directionsTextView.append("East, ");
                    points++;
                    break;
                case R.id.south_btn:
                    directionsTextView.append("South, ");
                    points++;
                    break;
                case R.id.navigate_to_second_act:
                    Intent intent = new Intent(PracticalTest01Var01Activity.this, PracticalTest01Var01SecondaryActivity.class);
                    intent.putExtra(CARDINAL_POINTS, directionsTextView.getText().toString());
                    startActivityForResult(intent, REQUEST);
                    directionsTextView.setText("");
                    points = 0;
                    break;
            }
            Log.d(POINTS, String.valueOf(points));

            if (points >= 4 && !serviceStarted) {
                Intent service = new Intent(PracticalTest01Var01Activity.this, PracticalTest01Var01Service.class);
                service.putExtra(CARDINAL_POINTS, directionsTextView.getText().toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(service);
                } else {
                    startService(service);
                }
                serviceStarted = true;
            }
        }


    }

    @Override
    protected void onDestroy() {
        Intent service = new Intent(this, PracticalTest01Var01Service.class);
        stopService(service);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST) {
            String text = "";
            if (resultCode == REGISTER) {
                text = "Register";
            } else if (resultCode == CANCEL) {
                text = "Cancel";
            }
            Toast.makeText(this, "The activity returned with result " + text, Toast.LENGTH_SHORT).show();
        }
    }

    class MessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("MessageBroadcast", intent.getStringExtra("message"));
        }
    }
}
