package edu.neu.madcourse.shlokdixit1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

/**
 * Created by shlokdixit on 22/01/16.
 */
public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutMyself);


        TelephonyManager tele_Manager =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        tele_Manager.getDeviceId();
        String deviceid = tele_Manager.getDeviceId();
        TextView IMEI_id = (TextView) findViewById(R.id.IMEI);
        IMEI_id.setText(""+deviceid);
        setTitle("Shlok Dixit");

    }


}