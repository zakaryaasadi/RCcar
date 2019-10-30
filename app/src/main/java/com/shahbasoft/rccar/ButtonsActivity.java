package com.shahbasoft.rccar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter;

public class ButtonsActivity extends AppCompatActivity {

    private ImageView up, left, right, down;
    private BluetoothWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        up = findViewById(R.id.up);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        down = findViewById(R.id.down);




        writer = new BluetoothWriter(Common.service);


        sendMessage("s");


        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendMessage("f");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendMessage("a");
                }

                return true;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendMessage("r");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendMessage("c");
                }
                return true;
            }
        });


        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendMessage("l");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendMessage("e");
                }

                return true;
            }
        });


        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendMessage("b");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendMessage("d");
                }
                return true;
            }
        });


    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void finish() {
        sendMessage("s");
        sendMessage("z");
        super.finish();
    }

    private void sendMessage(String msg) {
        if (Common.service.getStatus() == BluetoothStatus.CONNECTED && Common.foundDevice != null)
            writer.write(msg);
        else
            Toast.makeText(getApplicationContext(), "Please connect to device ...", Toast.LENGTH_SHORT).show();
    }
}
