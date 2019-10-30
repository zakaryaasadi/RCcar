package com.shahbasoft.rccar;

import android.app.Application;
import android.bluetooth.BluetoothDevice;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothClassicService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothConfiguration;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;

import java.util.UUID;

public class Configuration extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BluetoothConfiguration config = new BluetoothConfiguration();
        config.context = getApplicationContext();
        config.bluetoothServiceClass = BluetoothClassicService.class; // BluetoothClassicService.class or BluetoothLeService.class
        config.bufferSize = 1024;
        config.characterDelimiter = '\n';
        config.deviceName = "MY CAR";
        config.callListenersInMainThread = true;

// Bluetooth Classic
        config.uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Set null to find all devices on scan.

// Bluetooth LE
        config.uuidService = UUID.fromString("e7810a71-73ae-499d-8c15-faa9aef0c3f2");
        config.uuidCharacteristic = UUID.fromString("bef8d6c9-9c21-4c9e-b632-bd58c1009f9f");
        config.transport = BluetoothDevice.TRANSPORT_LE; // Only for dual-mode devices

        BluetoothService.init(config);
    }

}
