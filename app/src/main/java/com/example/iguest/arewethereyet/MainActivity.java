package com.example.iguest.arewethereyet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    private PendingIntent pendingIntent;
    private String message;
    private String phoneNo;
    private int minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText phoneNo = (EditText) MainActivity.this.findViewById(R.id.phone_no);
        phoneNo.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        Button startStop = (Button) findViewById(R.id.start_stop);
        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText message = (EditText) MainActivity.this.findViewById(R.id.message);
                EditText phoneNo = (EditText) MainActivity.this.findViewById(R.id.phone_no);
                EditText minutes = (EditText) MainActivity.this.findViewById(R.id.minutes);
                if (message.getText().length() != 0 && phoneNo.getText().length() != 0
                        && minutes.getText().length() != 0 ) {
                    Button view = (Button) v;
                    if (view.getText().equals("START")) {
                        MainActivity.this.minutes = Integer.parseInt(minutes.getText().toString());
                        MainActivity.this.message = message.getText().toString();
                        MainActivity.this.phoneNo = phoneNo.getText().toString();
                        view.setText("STOP");
                        start();
                    } else {
                        MainActivity.this.minutes = 0;
                        MainActivity.this.message = "";
                        MainActivity.this.phoneNo = "";
                        view.setText("START");
                        stop();
                    }
                }
            }
        });
    }

    public void start() {
        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        alarmIntent.putExtra("showString", this.message);
        alarmIntent.putExtra("phone", this.phoneNo);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        Log.i("MainActivity", "Start() called");
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), (long) (this.minutes * 60 * 1000), pendingIntent);
        Log.i("MainActivity", "Alarm set");
    }

    public void stop() {
        Log.i("MainActivity", "stop() called");
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
