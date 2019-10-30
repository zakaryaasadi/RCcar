package com.shahbasoft.rccar;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter;


public class ControlMethodsActivity extends AppCompatActivity {

    CardView buttons, sensors, auto;
    private TextView txtStatus;
    private BluetoothWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_methods);

        txtStatus = findViewById(R.id.txtStatus);
        writer = new BluetoothWriter(Common.service);

        Common.service.setOnEventCallback(new BluetoothService.OnBluetoothEventCallback() {
            @Override
            public void onDataRead(byte[] bytes, int i) {

            }

            @Override
            public void onStatusChange(BluetoothStatus bluetoothStatus) {
                txtStatus.setText("Status: " + bluetoothStatus.name());
            }

            @Override
            public void onDeviceName(String s) {

            }

            @Override
            public void onToast(String s) {

            }

            @Override
            public void onDataWrite(byte[] bytes) {

            }
        });

        buttons = findViewById(R.id.buttons);
        sensors = findViewById(R.id.sensors);
        auto = findViewById(R.id.auto);


        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.service.getStatus() == BluetoothStatus.CONNECTED && Common.foundDevice != null) {
                    writer.write("x");
                    Intent i = new Intent(ControlMethodsActivity.this, ButtonsActivity.class);
                    startActivity(i);
                }else
                    Toast.makeText(getApplicationContext(), "Please connect to device ...", Toast.LENGTH_SHORT).show();

            }
        });

        sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.service.getStatus() == BluetoothStatus.CONNECTED && Common.foundDevice != null) {
                    writer.write("x");
                    Intent i = new Intent(ControlMethodsActivity.this, SensorsActivity.class);
                    startActivity(i);
                }else
                    Toast.makeText(getApplicationContext(), "Please connect to device ...", Toast.LENGTH_SHORT).show();

            }
        });

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.service.getStatus() == BluetoothStatus.CONNECTED && Common.foundDevice != null) {
                    writer.write("y");
                    Intent i = new Intent(ControlMethodsActivity.this, AutoActivity.class);
                    startActivity(i);
                }else
                    Toast.makeText(getApplicationContext(), "Please connect to device ...", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
