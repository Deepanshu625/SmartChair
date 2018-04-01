package com.deepanshu.smartchair;

import android.os.Build;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by deepanshu on 29/3/18.
 */

public class To_request {

    public To_request()
    {

    }

    public static String request_data(String api) {
        String line, response = "";
//        String api = url;
//
        try
        {
            URL url = new URL(api);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            }
            //conn.setRequestProperty("Accept-Encoding", "");
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // read the response
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }
}
