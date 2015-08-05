package com.alexsalovrn.wifixminturnoff;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class xminoff extends ActionBarActivity {
    public static final String PREFS_NAME = "turnwifioff_prefs";
    public static final String PREFS_KEY = "off_minutes_key";

    WifiManager wifiManager;
    TimerTask timertask;
    int off_minutes;

    Button turnoff_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xminoff);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        off_minutes = settings.getInt(PREFS_KEY, 10); //10 def value

        turnoff_btn = (Button) findViewById(R.id.turnoff_btn);
        turnoff_btn.setText("Turn WiFi Off For " +  String.valueOf(off_minutes) + " Minutes");
    }

    public void turnwifioff(View v){
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
            Toast.makeText(getApplicationContext(),"WiFi disabled for " +
                            String.valueOf(off_minutes) + " minutes", Toast.LENGTH_SHORT).show();
            Timer timer = new Timer("turnwifion");

            timertask = new TimerTask() {
                @Override
                public void run() {
                    wifiManager.setWifiEnabled(true);
                }
            };

            timer.schedule(timertask, 1000* 60 * off_minutes); //100 ms * 60 secs in min
        }else{
            Toast.makeText(getApplicationContext(),"Can't disable WiFi", Toast.LENGTH_SHORT).show();
        }
    }

    public void save_settings(View v){
        EditText off_mins_te = (EditText)findViewById(R.id.off_mins_te);
        try {
            off_minutes = Integer.parseInt(off_mins_te.getText().toString());
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt(PREFS_KEY, off_minutes);
            turnoff_btn.setText("Turn WiFi Off For " +  String.valueOf(off_minutes) + " Minutes");
            Toast.makeText(getApplicationContext(), "Settings Saved", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_xminoff, menu);
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
