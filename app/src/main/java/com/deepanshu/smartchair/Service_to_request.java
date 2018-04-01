package com.deepanshu.smartchair;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deepanshu on 29/3/18.
 */

public class Service_to_request extends Service {

    public int flag = 0;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        thread.start();
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread_to_off_buzzer.start();
        Toast.makeText(this, "Alarm disabled ", Toast.LENGTH_LONG).show();
    }

    Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            try  {
                while (true) {
                    String api = "https://cloud.boltiot.com/remote/59983265-d10a-4e7d-87f7-89129dcfb50f/analogRead?pin=A0&deviceName=BOLT3432362";
                    String response = To_request.request_data(api);
                    shared_prefrence();
                    int value = 0;
                    value = string_to_json(response);
                    if(value>30 && flag==1)
                    {
                        String api1 = "https://cloud.boltiot.com/remote/59983265-d10a-4e7d-87f7-89129dcfb50f/digitalWrite?pin=4&state=HIGH&deviceName=BOLT3432362";
                        String response1 = To_request.request_data(api1);
                    }
                    else
                    {
                        String api2 = "https://cloud.boltiot.com/remote/59983265-d10a-4e7d-87f7-89129dcfb50f/digitalWrite?pin=4&state=LOW&deviceName=BOLT3432362";
                        String response2 = To_request.request_data(api2);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
    Thread thread_to_off_buzzer = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                String api2 = "https://cloud.boltiot.com/remote/5?9983265-d10a-4e7d-87f7-89129dcfb50f/digitalWrite?pin=4&state=LOW&deviceName=BOLT3432362";
                String response2 = To_request.request_data(api2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
    public int string_to_json(String str)
    {
        int value = 0;
        try {
            JSONObject jsonObject=new JSONObject(str);
            if(jsonObject.getString("success").equals("0"))
            {
                value = 0;
            }
            else
            {
                value = Integer.parseInt(jsonObject.getString("value"));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }
    public void shared_prefrence()
    {
        SharedPreferences prefs = getSharedPreferences("value", MODE_PRIVATE);
        String restoredText = prefs.getString("enable", "0");
        if (restoredText.equals("0")) {
            flag = 0;
        }
        else
        {
            flag = 1;
        }
    }
}
