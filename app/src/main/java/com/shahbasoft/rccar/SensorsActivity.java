package com.shahbasoft.rccar;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter;




public class SensorsActivity extends AppCompatActivity {


    TextView textX, textY, textXX, textYY;
    SensorManager sensorManager;
    Sensor sensor;
    private BluetoothWriter writer;
    private int yValue = 0;
    private int xValue = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        textX = findViewById(R.id.textX);
        textY = findViewById(R.id.textY);
        textXX = findViewById(R.id.textXX);
        textYY = findViewById(R.id.textYY);

        writer = new BluetoothWriter(Common.service);

        sendMessage("s");



        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    public void onResume() {
        super.onResume();
        sensorManager.registerListener(grivityListener, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(grivityListener);
    }

    @Override
    public void finish() {
        sendMessage("s");
        sendMessage("z");
        super.finish();
    }

    String prefb = "", prerl = "";

    public SensorEventListener grivityListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) {
        }

        public void onSensorChanged(SensorEvent event) {
            int y = (int) event.values[2];
            int x = (int) event.values[1];

            textX.setText("Y : " + y);

            textY.setText("X : " + x);

            if(y != yValue){
                yValue = y;
                String fb ="";
                if(y > 5){
                    fb = "f";
                }else if(y < -1){
                    fb = "b";
                }else if(y >= -1 && y <= 5){
                    fb = "a";
                }
                if(!fb.equals(prefb)){
                    prefb = fb;
                    textYY.setText(fb);
                    sendMessage(fb);
                }

            }


            if(x != xValue){
                xValue = x;
                String rl = "";
                if(x > 3){
                    rl = "r";
                }else if(x < -3){
                    rl = "l";
                }else if(x >= -3 && x <= 3){
                    rl = "c";
                }
                if(!rl.equals(prerl)){
                    prerl = rl;
                    textXX.setText(rl);
                    sendMessage(rl);
                }

            }

        }
    };


    private void sendMessage(String msg) {
        if (Common.service.getStatus() == BluetoothStatus.CONNECTED && Common.foundDevice != null)
            writer.write(msg);
        else
            Toast.makeText(getApplicationContext(), "Please connect to device ...", Toast.LENGTH_SHORT).show();
    }
}
