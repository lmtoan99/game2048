package com.example.game2048;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

public class MenuActivity extends Activity implements View.OnClickListener {
    final int requestCode = 1;
    TelephonyManager telephonyManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //device id
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                String[] permission = {Manifest.permission.READ_PHONE_STATE};
                this.requestPermissions(permission, requestCode);
            }
        }


        Button newgame = findViewById(R.id.newgame);
        newgame.setOnClickListener(this);
        Button resume = findViewById(R.id.resume);
        resume.setOnClickListener(this);
        Button exit = findViewById(R.id.charts);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (v.getId()) {
            case R.id.newgame:
                intent.putExtra("status", 0);
                startActivity(intent);
                break;
            case R.id.resume:
                intent.putExtra("status", 1);
                startActivity(intent);
                break;
            case R.id.charts:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == this.requestCode) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.SEND_SMS)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        String deviceId = telephonyManager.getDeviceId();
                        Log.d("MY_PERMISSION",deviceId);
                    } else {
                        Log.d("MY_PERMISSION","Reject request for permission");
                    }
                }
            }
        }

    }
}
