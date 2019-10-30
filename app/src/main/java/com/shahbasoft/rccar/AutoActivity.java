package com.shahbasoft.rccar;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter;


public class AutoActivity extends AppCompatActivity {


    TextView textX, textY, textXX, textYY;
    private BluetoothWriter writer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);


        textX = findViewById(R.id.textX);
        textY = findViewById(R.id.textY);
        textXX = findViewById(R.id.textXX);
        textYY = findViewById(R.id.textYY);

        writer = new BluetoothWriter(Common.service);



        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



    }


    public void onResume() {
        super.onResume();
    }

    public void onStop() {
        super.onStop();
    }



    @Override
    public void finish() {
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
