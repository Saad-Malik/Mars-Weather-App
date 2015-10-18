package com.xdroider.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView mTxtDegrees,mTxtWeather,mTxtError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtDegrees = (TextView) findViewById(R.id.degrees);
        mTxtWeather = (TextView) findViewById(R.id.weather);
        mTxtError = (TextView) findViewById(R.id.error);

        String url = "http://marsweather.ingenology.com/v1/latest/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String minTemp, maxTemp, atmo;
                            int avgTemp;
                            jsonObject = jsonObject.getJSONObject("report");
                            minTemp = jsonObject.getString("min_temp");
                            minTemp = minTemp.substring(0, minTemp.indexOf("."));
                            maxTemp = jsonObject.getString("max_temp");
                            maxTemp = maxTemp.substring(0, maxTemp.indexOf("."));
                            avgTemp = (Integer.parseInt(minTemp)+Integer.parseInt(maxTemp))/2;
                            atmo = jsonObject.getString("atmo_opacity");

                            mTxtDegrees.setText(avgTemp+"°");
                            mTxtWeather.setText(atmo);

                        } catch (Exception e) {
                            txtError(e);

                        }
                    }
                }, new Response.ErrorListener() {
                 @Override
                public void onErrorResponse(VolleyError volleyError) {
                     txtError(volleyError);
                     Toast.makeText(MainActivity.this, "We got some errors", Toast.LENGTH_SHORT).show();
             }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    private void txtError(Exception e) {
        mTxtError.setVisibility(View.VISIBLE);
        e.printStackTrace();
    }
}
